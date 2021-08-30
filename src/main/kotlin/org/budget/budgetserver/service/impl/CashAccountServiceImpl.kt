package org.budget.budgetserver.service.impl

import org.budget.budgetserver.jpa.CashAccountEntity
import org.budget.budgetserver.service.internal.AccessServiceInternal
import org.budget.budgetserver.service.CashAccountService
import org.budget.budgetserver.service.internal.CashAccountServiceInternal
import org.budget.budgetserver.service.internal.Service.getLoggedUserId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CashAccountServiceImpl : CashAccountService {

    @Autowired
    private lateinit var accessServiceInternal: AccessServiceInternal

    @Autowired
    private lateinit var cashAccountServiceInternal: CashAccountServiceInternal

    override fun createCashAccount(groupId: Int, name: String, cash: Double) {

        val accessEntity =
            accessServiceInternal.findUserAccess(getLoggedUserId(), groupId)

        cashAccountServiceInternal.createCashAccount(name, cash, accessEntity)
    }

    override fun getAllCashAccounts(groupId: Int): List<CashAccountEntity> {
        accessServiceInternal.userMemberOfGroup(getLoggedUserId(), groupId)

        return cashAccountServiceInternal.findAllByGroupId(groupId)
    }

    override fun getCashAccount(groupId: Int, cashAccountId: Int): CashAccountEntity {
        accessServiceInternal.userMemberOfGroup(getLoggedUserId(), groupId)

        return cashAccountServiceInternal.findByIdAndGroupId(cashAccountId, groupId)
    }
}