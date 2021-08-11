package org.budget.budgetserver.service

import org.budget.budgetserver.jpa.ExpenseEntity

interface ExpenseServiceImpl {
    fun createExpense(
        groupId: Int,
        categoryId: Int,
        cashAccountId: Int,
        date: String,
        price: Double,
        comment: String,
    )

    fun getExpensesByDate(
        groupId: Int,
        startDate: String,
        endDate: String,
        offset: Int,
        limit: Int,
    ): List<ExpenseEntity>

    fun getExpensesByPrice(
        groupId: Int,
        startDate: String,
        endDate: String,
        offset: Int,
        limit: Int,
    ): List<ExpenseEntity>
}