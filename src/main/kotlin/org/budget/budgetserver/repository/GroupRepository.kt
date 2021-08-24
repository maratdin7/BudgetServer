package org.budget.budgetserver.repository

import org.budget.budgetserver.jpa.GroupEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface GroupRepository : JpaRepository<GroupEntity, Int> {

    fun findByName(name: String): List<GroupEntity>
}