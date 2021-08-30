package org.budget.budgetserver.service.impl

import org.budget.budgetserver.exception.DateFormatException
import org.budget.budgetserver.jpa.ExpenseEntity
import org.budget.budgetserver.jpa.PlannedExpenseEntity
import org.budget.budgetserver.repository.PlannedExpenseRepository
import org.budget.budgetserver.service.PlannedExpenseService
import org.budget.budgetserver.service.internal.ExpenseServiceInternal
import org.budget.budgetserver.service.internal.PlannedExpenseServiceInternal
import org.budget.budgetserver.service.token.DateConverter.toSqlDate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
@EnableScheduling
class PlannedExpenseServiceImpl : PlannedExpenseService {

    @Autowired
    private lateinit var expenseServiceInternal: ExpenseServiceInternal

    @Autowired
    private lateinit var plannedExpenseServiceInternal: PlannedExpenseServiceInternal

    override fun createPlannedExpense(categoryId: Int, cashAccountId: Int, day: Int, price: Double, comment: String?) {
        val (categoryEntity, cashAccountEntity) =
            expenseServiceInternal.getCategoryAndCashAccount(categoryId, cashAccountId)

        if ((day in 1..31).not())
            throw DateFormatException()

        plannedExpenseServiceInternal.savePlannedExpense(PlannedExpenseEntity(
            refCategoryEntity = categoryEntity,
            refCashAccountEntity = cashAccountEntity,
            day = day,
            price = price,
            comment = comment
        ))
    }

    @Scheduled(cron = "0 0 0 * * ?")
    fun addExpenseFromPlanned() {
        val now = LocalDate.now()

        plannedExpenseServiceInternal.addExpenseFromPlanned(now)
    }

}