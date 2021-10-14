package org.budget.budgetserver.service.internal

import org.budget.budgetserver.exception.CashAccountNotFoundException
import org.budget.budgetserver.jpa.AccessEntity
import org.budget.budgetserver.jpa.CashAccountEntity
import org.budget.budgetserver.jpa.ExpenseType
import org.budget.budgetserver.repository.CashAccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CashAccountServiceInternal {

    @Autowired
    private lateinit var cashAccountRepository: CashAccountRepository

    val updateCashAccount = UpdateCache()

    fun createCashAccount(
        name: String,
        cash: Double,
        accessEntity: AccessEntity,
    ): CashAccountEntity {
        updateCashAccount.needAnUpdate()
        return cashAccountRepository.save(
            CashAccountEntity(
                name = name,
                cash = cash,
                refAccessEntity = accessEntity
            )
        )
    }

    @Caching(
        put = [CachePut(value = ["cashAccounts"], condition = "#root.target.updateCashAccount.isUpdate()")],
        cacheable = [Cacheable(value = ["cashAccounts"])],
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
        updateCashAccount.needAnUpdate()
        return update(
            cashAccountEntity.apply {
                when (type) {
                    ExpenseType.EXPENSE -> cash -= price
                    ExpenseType.INCOME -> cash += price
                }
            })
    }

}