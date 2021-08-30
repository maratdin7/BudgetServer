package org.budget.budgetserver.service

interface PlannedExpenseService {

    fun createPlannedExpense(categoryId: Int, cashAccountId: Int, day: Int, price: Double, comment: String?)

}
