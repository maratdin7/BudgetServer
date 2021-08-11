package org.budget.budgetserver.repository

import org.budget.budgetserver.jpa.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserEntity, Int> {
    fun existsByName(login: String): Boolean

    fun findUserEntityByName(login: String): UserEntity?

    @Query(value = """SELECT password FROM tb_user WHERE name=:name""", nativeQuery = true)
    fun getUserPassword(name: String): String?
}