package org.budget.budgetserver.service

interface AccountSettingService {
    fun signOut(refreshToken: String)

    fun changePassword(pass: String): AccessRefreshTokens
}