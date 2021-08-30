package org.budget.budgetserver.repository

import org.budget.budgetserver.jpa.AccessEntity
import org.budget.budgetserver.jpa.GroupEntity
import org.budget.budgetserver.jpa.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface AccessRepository : JpaRepository<AccessEntity, Int> {

    fun existsByUserIdAndGroupId(userId: Int, groupId: Int): Boolean

    fun findByUserIdAndGroupId(userId: Int, groupId: Int): AccessEntity?

    fun findByUserId(userId: Int): List<AccessEntity>

    fun findByGroupId(groupId: Int): List<AccessEntity>

    @Query(
        """
        SELECT ge 
        FROM AccessEntity ae
                INNER JOIN GroupEntity ge 
                    ON ae.userId = :userId
                        AND ae.groupId = ge.id 
    """
    )
    fun findAllUserGroups(@Param("userId") userId: Int): List<GroupEntity>

    @Query(
        """
        SELECT ge   
        FROM AccessEntity ae
                INNER JOIN GroupEntity ge 
                    ON ae.userId = :userId
                        AND ae.groupId = ge.id
        WHERE ge.name = :groupName         
    """
    )
    fun isUserMemberOfGroupByName(
        @Param("userId") userId: Int,
        @Param("groupName") groupName: String
    ): List<GroupEntity>

    @Query(
        """SELECT ue
           FROM AccessEntity ae
           INNER JOIN UserEntity ue
                    on ue.id = ae.userId 
                        AND ae.groupId = :groupId"""
    )
    fun findAllUsersInGroup(@Param("groupId") groupId: Int): List<UserEntity>

}