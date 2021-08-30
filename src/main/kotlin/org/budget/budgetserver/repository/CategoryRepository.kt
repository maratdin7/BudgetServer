package org.budget.budgetserver.repository

import org.budget.budgetserver.jpa.CategoryEntity
import org.budget.budgetserver.jpa.ExpenseType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<CategoryEntity, Int> {

    fun findByIdAndGroupId(id: Int, groupId: Int): List<CategoryEntity>

    fun existsByGroupIdAndNameAndParentIdAndType(
        groupId: Int,
        name: String,
        parentId: Int?,
        type: ExpenseType
    ): Boolean

    fun findByGroupIdAndType(groupId: Int, type: ExpenseType): List<CategoryEntity>

    fun findByGroupId(groupId: Int): List<CategoryEntity>
}