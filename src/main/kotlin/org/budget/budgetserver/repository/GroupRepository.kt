package org.budget.budgetserver.repository

import org.budget.budgetserver.jpa.GroupEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GroupRepository : JpaRepository<GroupEntity, Int> {

}