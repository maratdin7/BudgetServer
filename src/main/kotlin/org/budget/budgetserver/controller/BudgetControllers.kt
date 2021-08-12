package org.budget.budgetserver.controller

import org.budget.budgetserver.request.AuthRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*




//@RestController
//@RequestMapping("/group")
//class GroupController {
//
//    @Autowired
//    lateinit var creationRequests: CreationRequests
//
//    @PostMapping("/create")
//    fun createGroup(@RequestParam groupName: String): Int =
//        creationRequests.createGroup(groupName)
//
//    @PostMapping("/addUserToGroup")
//    fun addUserToGroup(@RequestParam groupId: Int, @RequestParam email: String) =
//        creationRequests.addUserToGroup(groupId, email)
//
//    @PostMapping("/allMyGroup")
//    fun getAllGroup(): List<GroupEntity?> {
//        return creationRequests.getAllUserGroup()
//    }
//
//    @PostMapping("/allUsersInGroup")
//    fun getAllUsers(@RequestParam groupId: Int): List<UserEntity> {
//        return creationRequests.getAllUsersOfUserGroup(groupId)
//    }
//
//}
//
//@RestController
//@RequestMapping("/category")
//class CategoryController {
//
//    @Autowired
//    lateinit var creationRequests: CreationRequests
//
//    @PostMapping("/create")
//    fun createCategory(
//        @RequestParam groupId: Int,
//        @RequestParam name: String,
//        @RequestParam parentId: Int?,
//        @RequestParam isIncome: Boolean,
//    ):Int =
//        creationRequests.createCategory(groupId, name, parentId, isIncome)
//
//    @PostMapping("/subCategories")
//    fun getSubCategories(@RequestParam categoryId: Int): CategoryEntity =
//        creationRequests.getSubCategories(categoryId)
//
//    @PostMapping("/all")
//    fun getCategories(@RequestParam groupId: Int, @RequestParam isIncome: Boolean): List<CategoryEntity> =
//        creationRequests.getAllCategories(groupId, isIncome)
//}
//
//@RestController
//@RequestMapping("/cashAccount")
//class CashAccountController {
//
//    @Autowired
//    lateinit var creationRequests: CreationRequests
//
//    @PostMapping("/create")
//    fun createCashAccount(@RequestParam groupId: Int, @RequestParam name: String, @RequestParam cash: Double) =
//        creationRequests.createCashAccount(groupId, name, cash)
//
//    @PostMapping("/all")
//    fun allCashAccounts(@RequestParam groupId: Int): List<CashAccountEntity> =
//        creationRequests.allCashAccounts(groupId)
//}
//
//@RestController
//@RequestMapping("/expense")
//class ExpenseController {
//
//    @Autowired
//    lateinit var creationRequests: CreationRequests
//
//    @PostMapping("/create")
//    fun createExpense(
//        groupId: Int,
//        categoryId: Int,
//        cashAccountId: Int,
//        date: String,
//        price: Double,
//        comment: String,
//    ) = creationRequests.createExpense(groupId, categoryId, cashAccountId, LocalDate.parse(date), price, comment)
//
//    @PostMapping("/allByDate")
//    fun getExpensesByDate(
//        groupId: Int,
//        startDate: String,
//        endDate: String,
//        offset: Int,
//        limit: Int,
//    ) = creationRequests.getAllExpensesByDate(groupId,
//        Date.valueOf(startDate),
//        Date.valueOf(endDate),
//        offset,
//        limit)
//
//    @PostMapping("/allByPrice")
//    fun getExpensesByPrice(
//        groupId: Int,
//        startDate: String,
//        endDate: String,
//        offset: Int,
//        limit: Int,
//    ) = creationRequests.getAllExpensesByPrice(groupId,
//        Date.valueOf(startDate),
//        Date.valueOf(endDate),
//        offset,
//        limit)
//}
//
//@RestController
//@RequestMapping("/income")
//class IncomeController {
//
//    @Autowired
//    lateinit var creationRequests: CreationRequests
//
//    @PostMapping("/create")
//    fun createIncome(
//        groupId: Int,
//        income: Double,
//        categoryId: Int,
//        cashAccountId: Int,
//        date: String,
//        comment: String
//    ) = creationRequests.createIncome(groupId, income, categoryId, cashAccountId, Date.valueOf(date), comment)
//
//    @PostMapping("/allByDate")
//    fun getIncomesByDate(
//        groupId: Int,
//        startDate: String,
//        endDate: String,
//        offset: Int,
//        limit: Int,
//    ) = creationRequests.getAllIncomesByDate(groupId,
//        Date.valueOf(startDate),
//        Date.valueOf(endDate),
//        offset,
//        limit)
//
//    @PostMapping("/allByPrice")
//    fun getExpensesByPrice(
//        groupId: Int,
//        startDate: String,
//        endDate: String,
//        offset: Int,
//        limit: Int,
//    ) = creationRequests.getAllIncomesByPrice(groupId,
//        Date.valueOf(startDate),
//        Date.valueOf(endDate),
//        offset,
//        limit)
//
//}
//
//@RestController
//@RequestMapping("/localExchange")
//class LocalExchangeController {
//
//    @Autowired
//    lateinit var creationRequests: CreationRequests
//
//    @PostMapping("/create")
//    fun createLocalExchange(
//        groupId: Int,
//        senderId: Int,
//        receiverId: Int,
//        sent: Double,
//        date: String,
//        comment: String
//    ) = creationRequests.createLocalExchange(groupId, senderId, receiverId, sent, Date.valueOf(date), comment)
//
//    @PostMapping("/allByDate")
//    fun getLocalExchangeByDate(
//        groupId: Int,
//        startDate: String,
//        endDate: String,
//        offset: Int,
//        limit: Int,
//    ) = creationRequests.getAllLocalExchangeByDate(groupId,
//        Date.valueOf(startDate),
//        Date.valueOf(endDate),
//        offset,
//        limit)
//
//    @PostMapping("/allByPrice")
//    fun getLocalExchangeByPrice(
//        groupId: Int,
//        startDate: String,
//        endDate: String,
//        offset: Int,
//        limit: Int,
//    ) = creationRequests.getAllLocalExchangeByPrice(groupId,
//        Date.valueOf(startDate),
//        Date.valueOf(endDate),
//        offset,
//        limit)
//
//}