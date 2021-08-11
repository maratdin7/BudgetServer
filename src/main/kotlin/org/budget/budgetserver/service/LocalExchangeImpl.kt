package org.budget.budgetserver.service

class LocalExchangeImpl : LocalExchange {
    override fun createLocalExchange(
        groupId: Int,
        senderId: Int,
        receiverId: Int,
        sent: Double,
        date: String,
        comment: String,
    ) {
        TODO("Not yet implemented")
    }

    override fun getLocalExchangeByDate(groupId: Int, startDate: String, endDate: String, offset: Int, limit: Int) {
        TODO("Not yet implemented")
    }

    override fun getLocalExchangeByPrice(groupId: Int, startDate: String, endDate: String, offset: Int, limit: Int) {
        TODO("Not yet implemented")
    }
}