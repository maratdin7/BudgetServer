package org.budget.budgetserver.service

import org.budget.budgetserver.jpa.UserEntity
import org.budget.budgetserver.service.internal.AccessRefreshTokens

interface AccountSettingService {
    fun signOut(refreshToken: String): UserEntity

    fun changePassword(pass: String): AccessRefreshTokens
}