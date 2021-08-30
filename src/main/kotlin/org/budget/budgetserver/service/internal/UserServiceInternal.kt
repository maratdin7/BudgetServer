package org.budget.budgetserver.service.internal

import org.budget.budgetserver.exception.UsernameAlreadyExistException
import org.budget.budgetserver.exception.UsernameNotFoundException
import org.budget.budgetserver.exception.WrongPasswordException
import org.budget.budgetserver.jpa.UserEntity
import org.budget.budgetserver.repository.UserRepository
import org.budget.budgetserver.service.token.JwtService
import org.budget.budgetserver.service.token.RefreshTokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Service
class UserServiceInternal {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var encode: PasswordEncoder

    private fun String.decode(encodedPass: String) =
        encode.matches(this, encodedPass)

    fun findUserEntityByName(email: String): UserEntity {
        return userRepository.findUserEntityByName(email)
            ?: throw UsernameNotFoundException("User with name $email not found")
    }

    fun saveOrUpdate(email: String, pass: String, id: Int = 0): UserEntity {
        return userRepository.save(
            UserEntity(
                id = id,
                name = email,
                password = encode.encode(pass),
            )
        )
    }

    fun createUserEntity(email: String, pass: String): UserEntity {
        if (userRepository.existsByName(email))
            throw UsernameAlreadyExistException()

        return saveOrUpdate(email, pass)
    }

    fun findUserAndCheck(email: String, pass: String): UserEntity {
        val userEntity = findUserEntityByName(email)
        if (pass.decode(userEntity.password)) {
            return userEntity
        } else
            throw WrongPasswordException()
    }

    fun updateUserEntity(userEntity: UserEntity): UserEntity =
        userRepository.save(userEntity)
}

class AccessRefreshTokens(
    jwtTokenService: JwtService,
    refreshTokenService: RefreshTokenService,
    userEntity: UserEntity
) {

    val accessToken: String
    val refreshToken: String

    init {
        accessToken = jwtTokenService.generateToken(userEntity)
        refreshToken = refreshTokenService.generateToken(userEntity)
    }
}