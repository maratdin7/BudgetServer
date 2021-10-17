package org.budget.budgetserver.service.internal

import org.budget.budgetserver.exception.RequestErrorException
import org.budget.budgetserver.jpa.CashAccountEntity
import org.budget.budgetserver.jpa.CategoryEntity
import org.budget.budgetserver.jpa.ExpenseEntity
import org.budget.budgetserver.repository.ExpenseRepository
import org.budget.budgetserver.service.impl.Filter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.stereotype.Service

@Service
class ExpenseServiceInternal {

    @Autowired
    private lateinit var expenseRepository: ExpenseRepository

    @Autowired
    private lateinit var categoryServiceInternal: CategoryServiceInternal

    @Autowired
    private lateinit var cashAccountServiceInternal: CashAccountServiceInternal

    @Autowired
    private lateinit var accessServiceInternal: AccessServiceInternal

    fun getCategoryAndCashAccount(
        categoryId: Int,
        cashAccountId: Int,
    ): Pair<CategoryEntity, CashAccountEntity> {
        val categoryEntity = categoryServiceInternal.getCategory(categoryId)
        val groupId = categoryEntity.groupId

        accessServiceInternal.userMemberOfGroup(org.budget.budgetserver.service.internal.Service.getLoggedUserId(),
            groupId)

        val cashAccountEntity =
            cashAccountServiceInternal.findByIdAndGroupId(cashAccountId, groupId)
        return Pair(categoryEntity, cashAccountEntity)
    }

    val updateExpenses: UpdateCache = UpdateCache()

    fun saveExpenseEntity(e: ExpenseEntity): ExpenseEntity {
        val expenseEntity = expenseRepository.save(e)

        with(expenseEntity) {
            cashAccountServiceInternal.updateCash(refCashAccountEntity, price, refCategoryEntity.type)
        }
        updateExpenses.needAnUpdate()
        return expenseEntity
    }

    fun getSumByFilters(groupId: Int, filter: Filter): Double =
        expenseRepository.getSum(groupId, filter) ?: throw RequestErrorException()

    @Caching(
        put = [CachePut(value = ["expenses"], condition = "#root.target.updateExpenses.isUpdate()")],
        cacheable = [Cacheable(value = ["expenses"])],
    )
    fun getExpenses(groupId: Int, filter: Filter, page: Int, pageSize: Int): List<ExpenseEntity> {

        val ans = expenseRepository.getExpenses(
            groupId = groupId,
            filter,
            page = page,
            pageSize = pageSize
        ) ?: throw RequestErrorException()
        return ans.map { it.expenseEntity }
    }
}