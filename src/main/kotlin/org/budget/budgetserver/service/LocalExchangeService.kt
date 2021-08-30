package org.budget.budgetserver.service

import org.budget.budgetserver.jpa.LocalExchangeEntity

interface LocalExchangeService {
    fun createLocalExchange(
        senderId: Int,
        receiverId: Int,
        sent: Double,
        date: String,
        comment: String?,
    )

    fun getAllLocalExchange(groupId: Int): List<LocalExchangeEntity>

}