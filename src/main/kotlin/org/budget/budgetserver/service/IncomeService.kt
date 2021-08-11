package org.budget.budgetserver.service

interface IncomeService {
        fun createIncome(
        groupId: Int,
        income: Double,
        categoryId: Int,
        cashAccountId: Int,
        date: String,
        comment: String
    )

    fun getIncomesByDate(
        groupId: Int,
        startDate: String,
        endDate: String,
        offset: Int,
        limit: Int,
    )

    fun getExpensesByPrice(
        groupId: Int,
        startDate: String,
        endDate: String,
        offset: Int,
        limit: Int,
    )
}