package org.budget.budgetserver.service

interface AuthService {

    fun signIn(login: String, pass: String): String

    fun signUp(login: String, pass: String): String

    fun signOut()

}