package org.budget.budgetserver.repository

import org.budget.budgetserver.jpa.LocalExchangeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.sql.Date

@Repository
interface LocalExchangeRepository : JpaRepository<LocalExchangeEntity, Int> {

    @Query("""
        SELECT lee FROM LocalExchangeEntity lee
                    INNER JOIN CashAccountEntity cae on cae.id = lee.senderId
                    INNER JOIN AccessEntity ae on cae.accessId = ae.id
        WHERE ae.groupId = :groupId
    """)
    fun findByGroupId(@Param("groupId") groupId: Int): List<LocalExchangeEntity>
}