package org.budget.budgetserver.controller

import org.budget.budgetserver.jpa.LocalExchangeEntity
import org.budget.budgetserver.service.LocalExchangeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/localExchange")
class LocalExchangeController {

    @Autowired
    private lateinit var localExchangeService: LocalExchangeService

    @PostMapping("/create")
    fun createLocalExchange(
        @RequestParam senderId: Int,
        @RequestParam receiverId: Int,
        @RequestParam sent: Double,
        @RequestParam date: String,
        comment: String?,
    ) = localExchangeService.createLocalExchange(senderId, receiverId, sent, date, comment)

    @GetMapping("/all")
    fun getAllLocalExchange(@RequestParam groupId: Int): List<LocalExchangeEntity> =
        localExchangeService.getAllLocalExchange(groupId)

}