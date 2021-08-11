package org.budget.budgetserver.repository

import org.budget.budgetserver.jpa.LocalExchangeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.sql.Date

@Repository
interface LocalExchangeRepository : JpaRepository<LocalExchangeEntity, Int> {

    @Query(value = """SELECT tb_local_exchange.*
                FROM tb_local_exchange
                        INNER JOIN tb_cash_account tca on tca.id = tb_local_exchange.sender_id
                        INNER JOIN tb_access ta on ta.id = tca.access_id and group_id = :groupId
                WHERE date between :startDate and :endDate
                ORDER BY date, tb_local_exchange.id 
                offset :offset limit :limit""", nativeQuery = true)
    fun resultsOrderedByDate(
        startDate: Date,
        endDate: Date,
        groupId: Int,
        offset: Int,
        limit: Int,
    ): List<LocalExchangeEntity>

    @Query(value = """SELECT tb_local_exchange.*
                FROM tb_local_exchange
                        INNER JOIN tb_cash_account tca on tca.id = tb_local_exchange.sender_id
                        INNER JOIN tb_access ta on ta.id = tca.access_id and group_id = :groupId
                WHERE date between :startDate and :endDate
                ORDER BY sent DESC, tb_local_exchange.id 
                offset :offset limit :limit""", nativeQuery = true)
    fun resultsOrderedByPrice(
        startDate: Date,
        endDate: Date,
        groupId: Int,
        offset: Int,
        limit: Int,
    ): List<LocalExchangeEntity>
}