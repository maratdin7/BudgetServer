package org.budget.budgetserver.controller

import org.budget.budgetserver.jpa.PlannedExpenseEntity
import org.budget.budgetserver.service.PlannedExpenseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/plannedExpense")
class PlannedExpenseController {

    @Autowired
    private lateinit var plannedExpenseService: PlannedExpenseService

    @PostMapping("/create")
    fun createPlannedExpense(
        @RequestParam categoryId: Int,
        @RequestParam cashAccountId: Int,
        @RequestParam day: Int,
        @RequestParam price: Double,
        comment: String?,
    ) = plannedExpenseService.createPlannedExpense(categoryId, cashAccountId, day, price, comment)

    @GetMapping("/all")
    fun getAllPlannedExpense(
        @RequestParam groupId: Int,
    ): List<PlannedExpenseEntity> =
        plannedExpenseService.getAllPlannedExpenses(groupId)
}