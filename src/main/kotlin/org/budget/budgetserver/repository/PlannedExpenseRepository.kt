package org.budget.budgetserver.repository

import org.budget.budgetserver.jpa.PlannedExpenseEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PlannedExpenseRepository : JpaRepository<PlannedExpenseEntity, Int> {

    fun findByDay(day: Int): List<PlannedExpenseEntity>

    fun findByDayGreaterThanEqual(day: Int): List<PlannedExpenseEntity>

}