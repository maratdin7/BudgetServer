package org.budget.budgetserver.repository

import org.budget.budgetserver.jpa.ExpenseEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.sql.Date

@Repository
interface ExpenseRepository : JpaRepository<ExpenseEntity, Int> {

    @Query(value = """SELECT tb_expense.*
                FROM tb_expense
                        INNER JOIN tb_cash_account tca on tca.id = tb_expense.cash_account_id
                        INNER JOIN tb_access ta on ta.id = tca.access_id and group_id = :groupId
                WHERE date between :startDate and :endDate
                ORDER BY date, tb_expense.id 
                offset :offset limit :limit""", nativeQuery = true)
    fun resultsOrderedByDate(
        startDate: Date,
        endDate: Date,
        groupId: Int,
        offset: Int,
        limit: Int,
    ): List<ExpenseEntity>

    @Query(value = """SELECT tb_expense.*
                FROM tb_expense
                        INNER JOIN tb_cash_account tca on tca.id = tb_expense.cash_account_id
                        INNER JOIN tb_access ta on ta.id = tca.access_id and group_id = :groupId
                WHERE date between :startDate and :endDate
                ORDER BY price DESC, tb_expense.id 
                offset :offset limit :limit""", nativeQuery = true)
    fun resultsOrderedByPrice(
        startDate: Date,
        endDate: Date,
        groupId: Int,
        offset: Int,
        limit: Int,
    ): List<ExpenseEntity>
}