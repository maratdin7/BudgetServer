package org.budget.budgetserver.service

import org.budget.budgetserver.jpa.PlannedExpenseEntity

interface PlannedExpenseService {

    fun createPlannedExpense(categoryId: Int, cashAccountId: Int, day: Int, price: Double, comment: String?)

    fun getAllPlannedExpenses(groupId: Int): List<PlannedExpenseEntity>
}
