package org.budget.budgetserver.service

import org.budget.budgetserver.exception.PasswordAuthenticationException
import org.budget.budgetserver.exception.UsernameNotFoundException
import org.budget.budgetserver.repository.UserRepository

class AuthServiceImpl(private val userRepository: UserRepository) : AuthService {

    override fun signIn(login: String, pass: String): String {
            val user = userRepository.findUserEntityByName(login) ?: throw UsernameNotFoundException("User with name $login not found")
        return ""
//            if (pass.encodePass(user.password!!))
//                jwtProvider.generateToken(login)
//            else
//                throw PasswordAuthenticationException()
    }

    override fun signUp(login: String, pass: String): String {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        TODO("Not yet implemented")
    }

    override fun checkingToken() {
        TODO("Not yet implemented")
    }
}