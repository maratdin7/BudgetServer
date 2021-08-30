package org.budget.budgetserver.service.internal

import org.budget.budgetserver.exception.CashAccountNotFoundException
import org.budget.budgetserver.jpa.AccessEntity
import org.budget.budgetserver.jpa.CashAccountEntity
import org.budget.budgetserver.jpa.ExpenseType
import org.budget.budgetserver.repository.CashAccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CashAccountServiceInternal {

    @Autowired
    private lateinit var cashAccountRepository: CashAccountRepository

    fun createCashAccount(
        name: String,
        cash: Double,
        accessEntity: AccessEntity,
    ): CashAccountEntity =
        cashAccountRepository.save(
            CashAccountEntity(
                name = name,
                cash = cash,
                refAccessEntity = accessEntity
            )
        )

    fun findAllByGroupId(groupId: Int) = cashAccountRepository.findAllByGroupId(groupId)

    fun findByIdAndGroupId(cashAccountId: Int, groupId: Int): CashAccountEntity =
        cashAccountRepository.findByIdAndGroupId(cashAccountId, groupId)
            ?: throw CashAccountNotFoundException()

    fun findById(cashAccountId: Int): CashAccountEntity =
        cashAccountRepository.findByIdOrNull(cashAccountId)
            ?: throw CashAccountNotFoundException()

    fun update(cashAccountEntity: CashAccountEntity) = cashAccountRepository.save(cashAccountEntity)

    fun updateCash(cashAccountEntity: CashAccountEntity, price: Double, type: ExpenseType): CashAccountEntity {
        return update(
            cashAccountEntity.apply {
                when (type) {
                    ExpenseType.EXPENSE -> cash -= price
                    ExpenseType.INCOME -> cash += price
                }
            })
    }

}