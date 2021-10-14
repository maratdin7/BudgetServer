package org.budget.budgetserver.service.impl

import org.budget.budgetserver.jpa.CategoryEntity
import org.budget.budgetserver.jpa.ExpenseType
import org.budget.budgetserver.service.internal.AccessServiceInternal
import org.budget.budgetserver.service.CategoryService
import org.budget.budgetserver.service.internal.CategoryServiceInternal
import org.budget.budgetserver.service.internal.Service.getLoggedUserId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl : CategoryService {

    @Autowired
    private lateinit var accessServiceInternal: AccessServiceInternal

    @Autowired
    private lateinit var categoryServiceInternal: CategoryServiceInternal

    override fun createCategory(groupId: Int, name: String, parentId: Int?, type: ExpenseType): CategoryEntity {
        val accessEntity = accessServiceInternal.findUserAccess(getLoggedUserId(), groupId)

        return categoryServiceInternal.
            createCategoryEntity(groupId, name, parentId, type, accessEntity)
    }


    override fun getSubCategories(categoryId: Int): List<CategoryEntity> {
        TODO("Not yet implemented")
    }

    override fun getCategory(categoryId: Int): CategoryEntity {
        val categoryEntity = categoryServiceInternal.getCategory(categoryId)
        val groupId: Int = categoryEntity.refGroupEntity.id

        accessServiceInternal.userMemberOfGroup(getLoggedUserId(), groupId)

        return categoryEntity
    }

    override fun getAllCategories(groupId: Int, type: ExpenseType?): List<CategoryEntity> {
        accessServiceInternal.userMemberOfGroup(getLoggedUserId(), groupId)

        return categoryServiceInternal.getAllCategories(type, groupId)
    }


}