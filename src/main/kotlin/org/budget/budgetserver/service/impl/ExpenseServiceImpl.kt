package org.budget.budgetserver.service.impl

import org.budget.budgetserver.exception.RequestErrorException
import org.budget.budgetserver.jpa.ExpenseEntity
import org.budget.budgetserver.jpa.ExpenseType
import org.budget.budgetserver.repository.ExpenseCriteria
import org.budget.budgetserver.repository.ExpenseRepository
import org.budget.budgetserver.service.ExpenseService
import org.budget.budgetserver.service.internal.AccessServiceInternal
import org.budget.budgetserver.service.internal.CashAccountServiceInternal
import org.budget.budgetserver.service.internal.CategoryServiceInternal
import org.budget.budgetserver.service.internal.Service.getLoggedUserId
import org.budget.budgetserver.service.token.DateConverter.toSqlDate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.sql.Date

@Service
class ExpenseServiceImpl : ExpenseService {

    @Autowired
    private lateinit var expenseRepository: ExpenseRepository

    @Autowired
    private lateinit var categoryServiceInternal: CategoryServiceInternal

    @Autowired
    private lateinit var cashAccountServiceInternal: CashAccountServiceInternal

    @Autowired
    private lateinit var accessServiceInternal: AccessServiceInternal

    @Value("\${int.pageSize}")
    private var pageSize: Int = 0

    override fun createExpense(
        categoryId: Int,
        cashAccountId: Int,
        strDate: String,
        price: Double,
        comment: String?,
    ) {
        val categoryEntity = categoryServiceInternal.getCategory(categoryId)
        val groupId = categoryEntity.groupId

        accessServiceInternal.userMemberOfGroup(getLoggedUserId(), groupId)

        val cashAccountEntity =
            cashAccountServiceInternal.findByIdAndGroupId(cashAccountId, groupId)

        expenseRepository.save(ExpenseEntity(
            price = price,
            date = strDate.toSqlDate(),
            refCategoryEntity = categoryEntity,
            refCashAccountEntity = cashAccountEntity
        ))

        cashAccountServiceInternal.update(cashAccountEntity.apply {
            when (categoryEntity.type) {
                ExpenseType.EXPENSE -> this.cash -= price
                ExpenseType.INCOME -> cash += price
            }
        })
    }

    override fun getExpenses(
        groupId: Int,
        page: Int,
        expenseType: ExpenseType?,
        categoryId: Int?,
        afterDate: String?,
        beforeDate: String?,
        from: Double?,
        to: Double?,
        direction: Sort.Direction?,
    ): List<ExpenseCriteria.ExpensesAns> {

        val filter = Filter(
            expenseType = expenseType,
            categoryId = categoryId,
            afterDate = afterDate?.toSqlDate(),
            beforeDate = beforeDate?.toSqlDate(),
            from = from,
            to = to,
            priceSortDir = direction
        )

        val ans = expenseRepository.getExpenses(
            groupId = groupId,
            filter,
            page = page,
            pageSize = pageSize
        )
        return ans ?: throw RequestErrorException()
    }

    override fun getSumByFilters(
        groupId: Int,
        expenseType: ExpenseType?,
        categoryId: Int?,
        afterDate: String?,
        beforeDate: String?,
        from: Double?,
        to: Double?,
    ): Double {

        val filter = Filter(
            expenseType = expenseType,
            categoryId = categoryId,
            afterDate = afterDate?.toSqlDate(),
            beforeDate = beforeDate?.toSqlDate(),
            from = from,
            to = to,
            dateSortDir = null
        )
        return expenseRepository.getSum(groupId, filter) ?: throw RequestErrorException()
    }
}

data class Filter(
    val expenseType: ExpenseType? = null,
    val categoryId: Int? = null,
    val afterDate: Date? = null,
    val beforeDate: Date? = null,
    val from: Double? = null,
    val to: Double? = null,
    val priceSortDir: Sort.Direction? = null,
    val dateSortDir: Sort.Direction? = Sort.Direction.DESC
    )
