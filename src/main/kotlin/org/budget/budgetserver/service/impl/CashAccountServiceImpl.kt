package org.budget.budgetserver.service.impl

import org.budget.budgetserver.exception.CashAccountNotFoundException
import org.budget.budgetserver.exception.UserIsNotMemberOfGroupException
import org.budget.budgetserver.jpa.CashAccountEntity
import org.budget.budgetserver.repository.CashAccountRepository
import org.budget.budgetserver.service.AccessService
import org.budget.budgetserver.service.CashAccountService
import org.budget.budgetserver.service.Service.getUserEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CashAccountServiceImpl : CashAccountService {

    @Autowired
    private lateinit var accessService: AccessService

    @Autowired
    private lateinit var cashAccountRepository: CashAccountRepository

    private fun getUserId(): Int = getUserEntity().id

    override fun createCashAccount(groupId: Int, name: String, cash: Double) {

        val accessEntity =
            accessService.findUserAccess(getUserId(), groupId) ?: throw UserIsNotMemberOfGroupException()

        cashAccountRepository.save(
            CashAccountEntity(
                name = name,
                cash = cash,
                refAccessEntity = accessEntity
            )
        )
    }

    override fun allCashAccounts(groupId: Int): List<CashAccountEntity> {
        accessService.userMemberOfGroup(getUserId(), groupId)

        return cashAccountRepository.findAllByGroupId(groupId)
    }

    override fun getCashAccount(groupId: Int, cashAccountId: Int): CashAccountEntity {
        accessService.userMemberOfGroup(getUserId(), groupId)

        return cashAccountRepository.findByIdAndGroupId(cashAccountId, groupId)
            ?: throw CashAccountNotFoundException()
    }

}