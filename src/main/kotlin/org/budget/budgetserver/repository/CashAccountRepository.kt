package org.budget.budgetserver.repository

import org.budget.budgetserver.jpa.CashAccountEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CashAccountRepository : JpaRepository<CashAccountEntity, Int> {

    @Query(
        """
        SELECT cae
        FROM CashAccountEntity cae
                INNER JOIN AccessEntity ae 
                    on cae.accessId = ae.id 
                        AND ae.groupId = :groupId"""
    )
    fun findAllByGroupId(@Param("groupId") groupId: Int): List<CashAccountEntity>

    @Query(
        """
        SELECT cae
        FROM CashAccountEntity cae
                INNER JOIN AccessEntity ae 
                    on cae.id = :id 
                        AND cae.accessId = ae.id 
                        AND ae.groupId = :groupId
    """
    )
    fun findByIdAndGroupId(@Param("id") id: Int, @Param("groupId") groupId: Int): CashAccountEntity?
}