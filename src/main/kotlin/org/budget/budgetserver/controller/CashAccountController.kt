package org.budget.budgetserver.controller

import org.budget.budgetserver.jpa.CashAccountEntity
import org.budget.budgetserver.service.CashAccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cashAccount")
class CashAccountController {

    @Autowired
    private lateinit var cashAccountService: CashAccountService

    @PostMapping("/create")
    fun createCashAccount(
        @RequestParam groupId: Int,
        @RequestParam name: String,
        @RequestParam(defaultValue = "0.0") cash: Double
    ) =
        cashAccountService.createCashAccount(groupId, name, cash)

    @GetMapping("/all")
    fun allCashAccounts(@RequestParam groupId: Int): List<CashAccountEntity> =
        cashAccountService.getAllCashAccounts(groupId)

    @GetMapping("/getCashAccount")
    fun getCashAccount(@RequestParam groupId: Int, @RequestParam cashAccountId: Int): CashAccountEntity =
        cashAccountService.getCashAccount(groupId, cashAccountId)

}