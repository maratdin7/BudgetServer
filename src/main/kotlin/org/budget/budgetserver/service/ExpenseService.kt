package org.budget.budgetserver.service

import org.budget.budgetserver.jpa.ExpenseEntity
import org.budget.budgetserver.service.impl.ExpenseServiceImpl

class ExpenseService : ExpenseServiceImpl {
    override fun createExpense(
        groupId: Int,
        categoryId: Int,
        cashAccountId: Int,
        date: String,
        price: Double,
        comment: String,
    ) {
        TODO("Not yet implemented")
    }

    override fun getExpensesByDate(
        groupId: Int,
        startDate: String,
        endDate: String,
        offset: Int,
        limit: Int,
    ): List<ExpenseEntity> {
        TODO("Not yet implemented")
    }

    override fun getExpensesByPrice(
        groupId: Int,
        startDate: String,
        endDate: String,
        offset: Int,
        limit: Int,
    ): List<ExpenseEntity> {
        TODO("Not yet implemented")
    }

}