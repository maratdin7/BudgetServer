package org.budget.budgetserver.service.internal

import org.budget.budgetserver.exception.CategoryAlreadyExistsException
import org.budget.budgetserver.exception.CategoryNotFoundException
import org.budget.budgetserver.jpa.AccessEntity
import org.budget.budgetserver.jpa.CategoryEntity
import org.budget.budgetserver.jpa.ExpenseType
import org.budget.budgetserver.repository.CategoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CategoryServiceInternal {

    @Autowired
    private lateinit var categoryRepository: CategoryRepository

    val updateCategory = UpdateCache()

    fun createCategoryEntity(
        groupId: Int,
        name: String,
        parentId: Int?,
        type: ExpenseType,
        accessEntity: AccessEntity,
    ): CategoryEntity {
        val isExist =
            categoryRepository.existsByGroupIdAndNameAndParentIdAndType(groupId, name, parentId, type)

        if (isExist)
            throw CategoryAlreadyExistsException()

        updateCategory.needAnUpdate()

        return categoryRepository.save(
            CategoryEntity(
                name = name,
                parentId = parentId,
                type = type,
                refGroupEntity = accessEntity.refGroupEntity
            )
        )
    }

    @Caching(
        put = [CachePut(value = ["categories"], condition = "#root.target.updateCategory.isUpdate()")],
        cacheable = [Cacheable(value = ["categories"])],
    )
    fun getAllCategories(type: ExpenseType?, groupId: Int): List<CategoryEntity> =
        if (type == null) categoryRepository.findByGroupId(groupId)
        else categoryRepository.findByGroupIdAndType(groupId, type)

    fun getCategory(categoryId: Int): CategoryEntity =
        categoryRepository.findByIdOrNull(categoryId) ?: throw CategoryNotFoundException()

}