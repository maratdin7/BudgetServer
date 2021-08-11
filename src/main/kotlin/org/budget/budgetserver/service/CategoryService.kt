package org.budget.budgetserver.service

import org.budget.budgetserver.jpa.CategoryEntity

interface CategoryService {
    fun createCategory(groupId: Int, name: String, parentId: Int?, isIncome: Boolean): Int

    fun getSubCategories(categoryId: Int): List<CategoryEntity>

    fun getCategories(groupId: Int, isIncome: Boolean): List<CategoryEntity>
}