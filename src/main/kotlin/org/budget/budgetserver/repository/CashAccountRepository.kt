package org.budget.budgetserver.repository

import org.budget.budgetserver.jpa.CashAccountEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CashAccountRepository : JpaRepository<CashAccountEntity, Int>