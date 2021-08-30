package org.budget.budgetserver.controller

import org.budget.budgetserver.service.PlannedExpenseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

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
        comment: String?
    ) = plannedExpenseService.createPlannedExpense(categoryId, cashAccountId, day, price, comment)


}