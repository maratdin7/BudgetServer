package org.budget.budgetserver.repository

import org.budget.budgetserver.jpa.PlannedExpenseEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PlannedExpenseRepository : JpaRepository<PlannedExpenseEntity, Int> {

    fun findByDay(day: Int): List<PlannedExpenseEntity>

    fun findByDayGreaterThanEqual(day: Int): List<PlannedExpenseEntity>

    @Query("""
        SELECT pee FROM PlannedExpenseEntity pee
            INNER JOIN CategoryEntity ce on pee.categoryId = ce.id
        WHERE ce.groupId = :groupId
    """)
    fun findByGroupId(@Param("groupId") groupId: Int): List<PlannedExpenseEntity>

}