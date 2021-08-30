package org.budget.budgetserver.controller

import org.budget.budgetserver.service.internal.AccessRefreshTokens
import org.budget.budgetserver.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/auth")
class AuthController {

    @Autowired
    private lateinit var authService: AuthService

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = ["/login", "/signIn"])
    fun signIn(@RequestParam email: String, @RequestParam pass: String): AccessRefreshTokens =
        authService.signIn(email, pass)

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signUp")
    fun signUp(@RequestParam email: String, @RequestParam pass: String): AccessRefreshTokens =
        authService.signUp(email, pass)

    @PostMapping("/generateAccessToken")
    fun generateAccessToken(@RequestParam email: String, @RequestParam refreshToken: String): AccessRefreshTokens =
        authService.generateAccessToken(email, refreshToken)

    @GetMapping("/accountConfirmation")
    fun accountConfirmation(@RequestParam email: String, @RequestParam token: String) =
        authService.accountConfirmation(email, token)

    @GetMapping("/resetPassword")
    fun resetPassword(@RequestParam email: String) = authService.resetPassword(email)

    @GetMapping("/confirmResetPassword")
    fun confirmResetPassword(@RequestParam email: String, @RequestParam token: Int): String =
        authService.confirmResetPassword(email, token)

    @GetMapping("/requestAccountConfirmation")
    fun requestAccountConfirmation(@RequestParam email: String) = authService.requestAccountConfirmation(email)

}