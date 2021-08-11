package org.budget.budgetserver.service

interface LocalExchange {
    fun createLocalExchange(
        groupId: Int,
        senderId: Int,
        receiverId: Int,
        sent: Double,
        date: String,
        comment: String,
    )

    fun getLocalExchangeByDate(
        groupId: Int,
        startDate: String,
        endDate: String,
        offset: Int,
        limit: Int,
    )

    fun getLocalExchangeByPrice(
        groupId: Int,
        startDate: String,
        endDate: String,
        offset: Int,
        limit: Int,
    )

}