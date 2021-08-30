package org.budget.budgetserver.service

import org.budget.budgetserver.jpa.ExpenseType
import org.budget.budgetserver.repository.ExpenseCriteria
import org.springframework.data.domain.Sort

interface ExpenseService {

    fun getExpenses(
        groupId: Int,
        page: Int,
        expenseType: ExpenseType?,
        categoryId: Int?,
        afterDate: String?,
        beforeDate: String?,
        from: Double?,
        to: Double?,
        direction: Sort.Direction?
    ): List<ExpenseCriteria.ExpensesAns>

    fun createExpense(categoryId: Int, cashAccountId: Int, strDate: String, price: Double, comment: String?)

    fun getSumByFilters(
        groupId: Int,
        expenseType: ExpenseType?,
        categoryId: Int?,
        afterDate: String?,
        beforeDate: String?,
        from: Double?,
        to: Double?
    ): Double
}