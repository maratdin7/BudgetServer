package org.budget.budgetserver.jwt

import org.budget.budgetserver.exception.UsernameNotFoundException
import org.budget.budgetserver.jpa.UserEntity
import org.budget.budgetserver.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component


@Component
class CustomUserDetailsService : UserDetailsService {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(login: String): CustomUserDetails {
        val userEntity = userRepository.findUserEntityByName(login)
            ?: throw UsernameNotFoundException("User with name $login not found")
        return CustomUserDetails(userEntity)
    }
}

class CustomUserDetails(private val userEntity: UserEntity) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return listOf()
    }

    override fun getPassword(): String = userEntity.password

    override fun getUsername(): String = userEntity.name

    fun getUserEntity(): UserEntity = userEntity

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

}