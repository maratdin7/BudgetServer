package org.budget.budgetserver.service.impl

import org.budget.budgetserver.jpa.UserEntity
import org.budget.budgetserver.service.internal.AccessRefreshTokens
import org.budget.budgetserver.service.AccountSettingService
import org.budget.budgetserver.service.internal.Service.getLoggedUserEntity
import org.budget.budgetserver.service.internal.UserServiceInternal
import org.budget.budgetserver.service.token.JwtService
import org.budget.budgetserver.service.token.RefreshTokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AccountSettingServiceImpl : AccountSettingService {

    @Autowired
    private lateinit var userServiceInternal: UserServiceInternal

    @Autowired
    private lateinit var jwtService: JwtService

    @Autowired
    private lateinit var refreshTokenService: RefreshTokenService

    override fun signOut(refreshToken: String): UserEntity {
        val userEntity = getLoggedUserEntity()
        refreshTokenService.deleteToken(userEntity.id, refreshToken)
        return userEntity
    }

    override fun changePassword(pass: String): AccessRefreshTokens {
        val userEntity = getLoggedUserEntity()
        with(userEntity) {
            userServiceInternal.saveOrUpdate(name, pass, id)

            refreshTokenService.deleteAllTokens(id)
            return AccessRefreshTokens(jwtService, refreshTokenService, userEntity)
        }
    }
}