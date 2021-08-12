package org.budget.budgetserver.controller

import org.budget.budgetserver.request.AuthRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController {

    @Autowired
    private lateinit var authRequest: AuthRequest

    //not auth
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = ["/login", "/signIn"])
    fun signIn(@RequestBody email: String, @RequestBody pass: String): AuthRequest.AccessRefreshTokens =
        authRequest.signIn(email, pass)

    //not auth
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signUp")
    fun signUp(@RequestBody email: String, @RequestBody pass: String): AuthRequest.AccessRefreshTokens =
        authRequest.signUp(email, pass)

    @PostMapping("/signOut")
    fun signOut(@RequestBody refreshToken: String) = authRequest.signOut()

    //not auth
    @PostMapping("/generateAccessToken")
    fun generateAccessToken(@RequestBody refreshToken: String): String = authRequest.generateAccessToken(refreshToken)

    //not auth
    @GetMapping("/accountConfirmation")
    fun accountConfirmation(@RequestParam userId: Int, @RequestParam token: String) =
        authRequest.accountConfirmation(userId, token)

    @GetMapping("/requestAccountConfirmation")
    fun requestAccountConfirmation() = authRequest.requestAccountConfirmation()

    //not auth
    @GetMapping("/resetPassword")
    fun resetPassword(@RequestParam email: String) = authRequest.resetPassword(email)

    //not auth
    @GetMapping("/confirmResetPassword")
    fun confirmResetPassword(@RequestParam email: String, @RequestParam token: Int): String =
        authRequest.confirmResetPassword(email, token)

    //not auth
    @PostMapping("/changePassword")
    fun changePassword(@RequestBody email: String, @RequestBody pass: String): AuthRequest.AccessRefreshTokens =
        authRequest.changePassword(pass)

    @GetMapping("/test")
    fun test() = "This work!!!"
}
