package org.budget.budgetserver.service.impl

import org.budget.budgetserver.jpa.ExpenseType
import org.budget.budgetserver.jpa.LocalExchangeEntity
import org.budget.budgetserver.repository.LocalExchangeRepository
import org.budget.budgetserver.service.LocalExchangeService
import org.budget.budgetserver.service.internal.AccessServiceInternal
import org.budget.budgetserver.service.internal.CashAccountServiceInternal
import org.budget.budgetserver.service.internal.Service.getLoggedUserId
import org.budget.budgetserver.service.token.DateConverter.toSqlDate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LocalExchangeServiceImpl : LocalExchangeService {

    @Autowired
    private lateinit var localExchangeRepository: LocalExchangeRepository

    @Autowired
    private lateinit var cashAccountServiceInternal: CashAccountServiceInternal

    @Autowired
    private lateinit var accessServiceInternal: AccessServiceInternal

    override fun createLocalExchange(senderId: Int, receiverId: Int, sent: Double, date: String, comment: String?) {
        val senderCashAccountEntity = cashAccountServiceInternal.findById(senderId)

        val groupId = senderCashAccountEntity.refAccessEntity!!.groupId
        accessServiceInternal.userMemberOfGroup(getLoggedUserId(), groupId)

        val receiverCashAccountEntity = cashAccountServiceInternal.findByIdAndGroupId(receiverId, groupId)

        localExchangeRepository.save(LocalExchangeEntity(
            refCashAccountEntitySend = senderCashAccountEntity,
            refCashAccountEntityReceive = receiverCashAccountEntity,
            sent = sent,
            date = date.toSqlDate(),
            comment = comment
        ))

        cashAccountServiceInternal.updateCash(senderCashAccountEntity, sent, ExpenseType.EXPENSE)
        cashAccountServiceInternal.updateCash(receiverCashAccountEntity, sent, ExpenseType.INCOME)

    }

    override fun getAllLocalExchange(groupId: Int): List<LocalExchangeEntity> {
        accessServiceInternal.userMemberOfGroup(getLoggedUserId(), groupId)

        return localExchangeRepository.findByGroupId(groupId)
    }
}