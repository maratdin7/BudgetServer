package org.budget.budgetserver.controller

import org.budget.budgetserver.jpa.CategoryEntity
import org.budget.budgetserver.jpa.ExpenseType
import org.budget.budgetserver.service.CategoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/category")
class CategoryController {

    @Autowired
    lateinit var categoryService: CategoryService

    @PostMapping("/create")
    fun createCategory(
        @RequestParam groupId: Int,
        @RequestParam name: String,
        parentId: Int?,
        @RequestParam(defaultValue = "EXPENSE") type: ExpenseType,
    ) =
        categoryService.createCategory(groupId, name, parentId, type)

    @GetMapping("/all")
    fun getAllCategories(
        @RequestParam groupId: Int,
        type: ExpenseType?
    ): List<CategoryEntity> =
        categoryService.getAllCategories(groupId, type)

    @GetMapping("/getCategory")
    fun getCategory(
        @RequestParam categoryId: Int
    ): CategoryEntity =
        categoryService.getCategory(categoryId)
}