package org.budget.budgetserver.repository

import org.budget.budgetserver.jpa.CategoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<CategoryEntity, Int> {

    fun findByGroupIdAndParentIdAndIncome(groupId: Int, parentId: Int?, income: Boolean): List<CategoryEntity>

    fun findByIdAndGroupIdAndIncome(id: Int, groupId: Int, income: Boolean): List<CategoryEntity>

    fun findByIdAndGroupId(id: Int, groupId: Int): List<CategoryEntity>

    fun existsByGroupIdAndParentId(groupId: Int, parentId: Int?): Boolean
}