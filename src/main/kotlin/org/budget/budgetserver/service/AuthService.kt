package org.budget.budgetserver.service

import org.budget.budgetserver.service.impl.AccessRefreshTokens

interface AuthService {

    fun signIn(login: String, pass: String): AccessRefreshTokens

    fun signUp(email: String, pass: String): AccessRefreshTokens

    fun requestAccountConfirmation(email: String)

    fun generateAccessToken(email: String, refreshToken: String): AccessRefreshTokens

    fun accountConfirmation(email: String, token: String)

    fun resetPassword(email: String)

    fun confirmResetPassword(email: String, token: Int): String
}