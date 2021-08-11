package org.budget.budgetserver.service

import org.budget.budgetserver.jpa.CategoryEntity

class CategoryServiceImpl : CategoryService {
    override fun createCategory(groupId: Int, name: String, parentId: Int?, isIncome: Boolean): Int {
        TODO("Not yet implemented")
    }

    override fun getSubCategories(categoryId: Int): List<CategoryEntity> {
        TODO("Not yet implemented")
    }

    override fun getCategories(groupId: Int, isIncome: Boolean): List<CategoryEntity> {
        TODO("Not yet implemented")
    }
}