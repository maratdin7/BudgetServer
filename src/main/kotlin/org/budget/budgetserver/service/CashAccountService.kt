package org.budget.budgetserver.service

import org.budget.budgetserver.jpa.CashAccountEntity

interface CashAccountService {
    fun createCashAccount(groupId: Int, name: String, cash: Double): CashAccountEntity

    fun getAllCashAccounts(groupId: Int): List<CashAccountEntity>

    fun getCashAccount(groupId: Int, cashAccountId: Int): CashAccountEntity
}