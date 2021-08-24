package org.budget.budgetserver.service

import org.budget.budgetserver.exception.SecurityContextAuthNotExistException
import org.budget.budgetserver.jpa.UserEntity
import org.budget.budgetserver.jwt.CustomUserDetails
import org.springframework.security.core.context.SecurityContextHolder

object Service {
    fun getUserEntity(): UserEntity {
        val securityContext = SecurityContextHolder.getContext()

        val authentication =
            securityContext.authentication
                ?: throw SecurityContextAuthNotExistException()

        val userDetails = authentication.principal as CustomUserDetails
        return userDetails.getUserEntity()
    }

}