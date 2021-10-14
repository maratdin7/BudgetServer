package org.budget.budgetserver.service

import org.budget.budgetserver.jpa.LocalExchangeEntity

interface LocalExchangeService {
    fun createLocalExchange(
        senderId: Int,
        receiverId: Int,
        sent: Double,
        date: String,
        comment: String?,
    ): LocalExchangeEntity

    fun getAllLocalExchange(groupId: Int): List<LocalExchangeEntity>

}