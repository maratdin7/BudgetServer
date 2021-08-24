package org.budget.budgetserver.service.impl

import org.budget.budgetserver.service.AccessRefreshTokens
import org.budget.budgetserver.service.AccountSettingService
import org.budget.budgetserver.service.Service.getUserEntity
import org.budget.budgetserver.service.UserService
import org.budget.budgetserver.service.token.JwtService
import org.budget.budgetserver.service.token.RefreshTokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AccountSettingServiceImpl : AccountSettingService {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var jwtService: JwtService

    @Autowired
    private lateinit var refreshTokenService: RefreshTokenService

    override fun signOut(refreshToken: String) {
        val userEntity = getUserEntity()
        refreshTokenService.deleteToken(userEntity.id, refreshToken)
    }

    override fun changePassword(pass: String): AccessRefreshTokens {
        val userEntity = getUserEntity()
        with(userEntity) {
            userService.saveOrUpdate(name, pass, id)

            refreshTokenService.deleteAllTokens(id)
            return AccessRefreshTokens(jwtService, refreshTokenService, userEntity)
        }
    }
}