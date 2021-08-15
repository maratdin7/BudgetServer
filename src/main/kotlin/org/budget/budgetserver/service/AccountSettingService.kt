package org.budget.budgetserver.service

import org.budget.budgetserver.service.impl.AccessRefreshTokens

interface AccountSettingService {
    fun signOut(refreshToken: String)

    fun changePassword(pass: String): AccessRefreshTokens
}