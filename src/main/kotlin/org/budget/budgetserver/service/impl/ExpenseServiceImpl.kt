package org.budget.budgetserver.service.impl

import org.budget.budgetserver.exception.RequestErrorException
import org.budget.budgetserver.jpa.CashAccountEntity
import org.budget.budgetserver.jpa.CategoryEntity
import org.budget.budgetserver.jpa.ExpenseEntity
import org.budget.budgetserver.jpa.ExpenseType
import org.budget.budgetserver.repository.ExpenseCriteria
import org.budget.budgetserver.repository.ExpenseRepository
import org.budget.budgetserver.service.ExpenseService
import org.budget.budgetserver.service.internal.AccessServiceInternal
import org.budget.budgetserver.service.internal.CashAccountServiceInternal
import org.budget.budgetserver.service.internal.CategoryServiceInternal
import org.budget.budgetserver.service.internal.ExpenseServiceInternal
import org.budget.budgetserver.service.internal.Service.getLoggedUserId
import org.budget.budgetserver.service.token.DateConverter.toSqlDate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.sql.Date

@Service
class ExpenseServiceImpl : ExpenseService {

    @Autowired
    private lateinit var expenseServiceInternal: ExpenseServiceInternal

    @Value("\${int.pageSize}")
    private var pageSize: Int = 0

    override fun createExpense(
        categoryId: Int,
        cashAccountId: Int,
        strDate: String,
        price: Double,
        comment: String?,
    ): ExpenseEntity {
        val (categoryEntity, cashAccountEntity) = expenseServiceInternal.getCategoryAndCashAccount(categoryId,
            cashAccountId)

        return expenseServiceInternal.saveExpenseEntity(
            ExpenseEntity(
                refCategoryEntity = categoryEntity,
                refCashAccountEntity = cashAccountEntity,
                price = price,
                date = strDate.toSqlDate(),
                comment = comment
            )
        )
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
    ): List<ExpenseEntity> {
        val filter = Filter(
            expenseType = expenseType,
            categoryId = categoryId,
            afterDate = afterDate?.toSqlDate(),
            beforeDate = beforeDate?.toSqlDate(),
            from = from,
            to = to,
            priceSortDir = direction
        )

        return expenseServiceInternal.getExpenses(groupId, filter, page, pageSize)
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
        return expenseServiceInternal.getSumByFilters(groupId, filter)
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
    val dateSortDir: Sort.Direction? = Sort.Direction.DESC,
)
