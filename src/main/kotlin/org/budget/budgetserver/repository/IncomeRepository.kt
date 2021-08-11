package org.budget.budgetserver.repository

import org.budget.budgetserver.jpa.IncomeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.sql.Date

@Repository
interface IncomeRepository : JpaRepository<IncomeEntity, Int> {

    @Query(value = """SELECT tb_income.*
                FROM tb_income
                        INNER JOIN tb_cash_account tca on tca.id = tb_income.cash_account_id
                        INNER JOIN tb_access ta on ta.id = tca.access_id and group_id = :groupId
                WHERE date between :startDate and :endDate
                ORDER BY date, tb_income.id 
                offset :offset limit :limit""", nativeQuery = true)
    fun resultsOrderedByDate(
        startDate: Date,
        endDate: Date,
        groupId: Int,
        offset: Int,
        limit: Int,
    ): List<IncomeEntity>

    @Query(value = """SELECT tb_income.*
                FROM tb_income
                        INNER JOIN tb_cash_account tca on tca.id = cash_account_id
                        INNER JOIN tb_access ta on ta.id = tca.access_id and group_id = :groupId
                WHERE date between :startDate and :endDate
                ORDER BY income DESC, tb_income.id 
                offset :offset limit :limit""", nativeQuery = true)
    fun resultsOrderedByPrice(
        startDate: Date,
        endDate: Date,
        groupId: Int,
        offset: Int,
        limit: Int,
    ): List<IncomeEntity>
}