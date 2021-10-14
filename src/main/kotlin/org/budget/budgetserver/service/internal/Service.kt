package org.budget.budgetserver.service.internal

import org.budget.budgetserver.exception.SecurityContextAuthNotExistException
import org.budget.budgetserver.jpa.UserEntity
import org.budget.budgetserver.jwt.CustomUserDetails
import org.springframework.security.core.context.SecurityContextHolder

object Service {
    fun getLoggedUserEntity(): UserEntity {
        val securityContext = SecurityContextHolder.getContext()

        val authentication =
            securityContext.authentication
                ?: throw SecurityContextAuthNotExistException()

        val userDetails = authentication.principal as CustomUserDetails
        return userDetails.getUserEntity()
    }

    fun getLoggedUserId(): Int = getLoggedUserEntity().id

}

class UpdateCache {
    private var updateCache: Boolean = false

    fun isUpdate(): Boolean = updateCache.run {
        val u = this
        if (this)
            updateCache = false

        return u
    }

    fun needAnUpdate() {
        updateCache = true
    }
}