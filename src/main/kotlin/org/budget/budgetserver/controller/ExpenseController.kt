package org.budget.budgetserver.controller

import org.budget.budgetserver.jpa.ExpenseType
import org.budget.budgetserver.repository.ExpenseCriteria
import org.budget.budgetserver.service.ExpenseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/expense")
class ExpenseController {

    @Autowired
    private lateinit var expenseService: ExpenseService

    @PostMapping("/create")
    fun createExpense(
        @RequestParam categoryId: Int,
        @RequestParam cashAccountId: Int,
        @RequestParam date: String,
        @RequestParam price: Double,
        comment: String?,
    ) = expenseService.createExpense(categoryId, cashAccountId, date, price, comment)

    @GetMapping("/getExpenses")
    fun getExpenses(
        @RequestParam groupId: Int,
        @RequestParam(defaultValue = "0") page: Int,
        expenseType: ExpenseType?,
        categoryId: Int?,
        afterDate: String?,
        beforeDate: String?,
        from: Double?,
        to: Double?,
        direction: Sort.Direction?,
    ): List<ExpenseCriteria.ExpensesAns> =
        expenseService.getExpenses(
            groupId, page, expenseType, categoryId, afterDate, beforeDate, from, to, direction
        )

    @GetMapping("/getSum")
    fun getSum(
        @RequestParam groupId: Int,
        @RequestParam(defaultValue = "EXPENSE") expenseType: ExpenseType,
        categoryId: Int?,
        afterDate: String?,
        beforeDate: String?,
        from: Double?,
        to: Double?,
    ): Double =
        expenseService.getSumByFilters(
            groupId, expenseType, categoryId, afterDate, beforeDate, from, to
        )

}
