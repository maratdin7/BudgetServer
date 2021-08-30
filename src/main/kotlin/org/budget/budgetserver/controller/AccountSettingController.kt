package org.budget.budgetserver.controller

import org.budget.budgetserver.service.internal.AccessRefreshTokens
import org.budget.budgetserver.service.AccountSettingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/account/setting")
class AccountSettingController {

    @Autowired
    private lateinit var accountSettingService: AccountSettingService

    @PostMapping("/signOut")
    fun signOut(@RequestParam refreshToken: String) = accountSettingService.signOut(refreshToken)

    @PostMapping("/changePassword")
    fun changePassword(@RequestParam pass: String): AccessRefreshTokens =
        accountSettingService.changePassword(pass)

    @GetMapping("/test")
    fun test() = "This work!!!"
}