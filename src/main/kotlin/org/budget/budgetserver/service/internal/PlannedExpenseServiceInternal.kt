package org.budget.budgetserver.service.internal

import org.budget.budgetserver.jpa.ExpenseEntity
import org.budget.budgetserver.jpa.PlannedExpenseEntity
import org.budget.budgetserver.repository.PlannedExpenseRepository
import org.budget.budgetserver.service.token.DateConverter.toSqlDate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class PlannedExpenseServiceInternal {

    @Autowired
    private lateinit var expenseServiceInternal: ExpenseServiceInternal

    @Autowired
    private lateinit var plannedExpenseRepository: PlannedExpenseRepository

    fun savePlannedExpense(plannedExpenseEntity: PlannedExpenseEntity): PlannedExpenseEntity =
        plannedExpenseRepository.save(plannedExpenseEntity)

    private fun findByDay(now: LocalDate): List<PlannedExpenseEntity> {
        val dayInMonth = now.lengthOfMonth()
        val nowDay = now.dayOfMonth

        with(plannedExpenseRepository) {
            return if (nowDay == dayInMonth) findByDayGreaterThanEqual(nowDay)
            else findByDay(nowDay)
        }
    }

    fun addExpenseFromPlanned(now: LocalDate) {
        val plannedExpenseEntities = findByDay(now)

        plannedExpenseEntities.map {
            expenseServiceInternal.saveExpenseEntity(ExpenseEntity(
                refCategoryEntity = it.refCategoryEntity,
                refCashAccountEntity = it.refCashAccountEntity,
                price = it.price,
                date = now.toSqlDate(),
                comment = it.comment
            ))
        }
    }


}