package org.budget.budgetserver.service

import org.budget.budgetserver.jpa.CategoryEntity
import org.budget.budgetserver.jpa.ExpenseType

interface CategoryService {
    fun createCategory(groupId: Int, name: String, parentId: Int?, type: ExpenseType)

    fun getSubCategories(categoryId: Int): List<CategoryEntity>

    fun getAllCategories(groupId: Int, type: ExpenseType?): List<CategoryEntity>

    fun getCategory(categoryId: Int): CategoryEntity
}