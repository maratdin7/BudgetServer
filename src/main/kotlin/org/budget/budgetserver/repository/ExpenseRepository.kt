package org.budget.budgetserver.repository

import org.budget.budgetserver.jpa.*
import org.budget.budgetserver.service.impl.Filter
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.sql.Date
import javax.persistence.*
import javax.persistence.criteria.*

@Repository
interface ExpenseRepository : JpaRepository<ExpenseEntity, Int>, ExpenseCriteria {
}

@Repository
class ExpenseCriteriaImpl : ExpenseCriteria {

    @PersistenceContext
    private lateinit var em: EntityManager

    private val criteriaBuilder: CriteriaBuilder by lazy {
        em.criteriaBuilder
    }

    fun <T : Comparable<T>> CriteriaBuilder.inRangePredicate(
        expression: Expression<T>,
        from: T?,
        to: T?,
    ): Predicate? =
        if (from != null && to != null)
            between(expression, from, to)
        else if (from != null)
            greaterThanOrEqualTo(expression, from)
        else if (to != null)
             lessThanOrEqualTo(expression, to)
        else null

    private fun <T : Comparable<T>> CriteriaBuilder.eqPredicate(
        expression: Expression<T>,
        v: T?,
        cond: (T?) -> Boolean = { it != null },
    ): Predicate? =
        if (cond(v))
            equal(expression, v)
        else null

    private fun <T : Comparable<T>> CriteriaBuilder.orderByPredicate(
        expression: Expression<T>,
        direction: Sort.Direction?,
    ): Order? = when (direction) {
        Sort.Direction.ASC -> asc(expression)
        Sort.Direction.DESC -> desc(expression)
        else -> null
    }

    private fun <T> MutableList<T>.addNotNull(el: T?) {
        el?.let(this::add)
    }

    private fun <T> CriteriaBuilder.requestBody(
        cq: CriteriaQuery<T>,
        groupId: Int,
        filter: Filter,
    ): Triple<Root<ExpenseEntity>, Join<ExpenseEntity, CategoryEntity>, Join<ExpenseEntity, CashAccountEntity>> {
        val expense: Root<ExpenseEntity> = cq.from(ExpenseEntity::class.java)
        val cashAccount: Join<ExpenseEntity, CashAccountEntity> = expense.join("refCashAccountEntity")
        val access: Join<CashAccountEntity, AccessEntity> = cashAccount.join("refAccessEntity")
        val category: Join<ExpenseEntity, CategoryEntity> = expense.join("refCategoryEntity")

        val priceField = expense.get<Double>("price")
        val dateField = expense.get<Date>("date")
        val categoryIdField = category.get<Int>("id")
        val typeField = category.get<ExpenseType>("type")
        val groupIdField = access.get<Int>("groupId")

        val predicates = mutableListOf<Predicate>(equal(groupIdField, groupId))

        with(filter) {
            with(predicates) {
                addNotNull(inRangePredicate(priceField, from, to))
                addNotNull(inRangePredicate(dateField, afterDate, beforeDate))
                addNotNull(eqPredicate(categoryIdField, categoryId))
                addNotNull(eqPredicate(typeField, expenseType))
            }
        }

        val orders = mutableListOf<Order>()
        orders.addNotNull(orderByPredicate<Double>(priceField, filter.priceSortDir))
        orders.addNotNull(orderByPredicate<Date>(dateField, filter.dateSortDir))

        cq.where(*predicates.toTypedArray()).orderBy(orders)

        return Triple(expense, category, cashAccount)
    }

    private fun CriteriaBuilder.filterRequest(
        groupId: Int,
        filter: Filter,
    ): CriteriaQuery<Tuple> {
        val cq = createTupleQuery()

        val (expense, category, cashAccount) =
            criteriaBuilder.requestBody<Tuple>(cq, groupId, filter)
        cq.select(tuple(expense, category, cashAccount))

        return cq
    }

    private fun TypedQuery<Tuple>.pageQuery(
        page: Int,
        pageSize: Int,
    ) {
        firstResult = page * pageSize
        maxResults = pageSize
    }

    override fun getExpenses(
        groupId: Int,
        filter: Filter,
        page: Int,
        pageSize: Int,
    ): List<ExpenseCriteria.ExpensesAns> {

        val cq: CriteriaQuery<Tuple> =
            criteriaBuilder.filterRequest(groupId, filter)

        val q = em.createQuery(cq)

        q.pageQuery(page, pageSize)

        return q.resultList.map {
            ExpenseCriteria.ExpensesAns(
                it[0] as ExpenseEntity,
                it[1] as CategoryEntity,
                it[2] as CashAccountEntity
            )
        }
    }

    override fun getSum(
        groupId: Int,
        filter: Filter,
    ): Double? {
        val cq = criteriaBuilder.createQuery(Double::class.java)

        val (expense, _, _) =
            criteriaBuilder.requestBody(cq, groupId, filter)

        cq.select(criteriaBuilder.sum(expense.get<Double>("price")))

        val q = em.createQuery(cq)
        return q.singleResult
    }
}

interface ExpenseCriteria {

    data class ExpensesAns(
        val expenseEntity: ExpenseEntity,
        val categoryEntity: CategoryEntity,
        val cashAccountEntity: CashAccountEntity,
    )

    fun getExpenses(
        groupId: Int,
        filter: Filter,
        page: Int,
        pageSize: Int,
    ): List<ExpensesAns>?

    fun getSum(
        groupId: Int,
        filter: Filter,
    ): Double?
}

