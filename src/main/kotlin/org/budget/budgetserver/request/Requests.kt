package org.budget.budgetserver.request

//@Component
//class CreationRequests(
//    private val userRepository: UserRepository,
//    private val groupRepository: GroupRepository,
//    private val accessRepository: AccessRepository,
//    private val categoryRepository: CategoryRepository,
//    private val cashAccountRepository: CashAccountRepository,
//    private val expenseRepository: ExpenseRepository,
//    private val incomeRepository: IncomeRepository,
//    private val localExchangeRepository: LocalExchangeRepository,
//) {
//
//    data class ResultOfCreation(val status: Status, val id: Int = 0)
//
//    @Autowired
//    lateinit var jwtProvider: JwtProvider
//
//    private fun getUserEntity(): UserEntity {
//        val userDetails = SecurityContextHolder.getContext().authentication.principal as CustomUserDetails
//        return userDetails.getUserEntity()
//    }
//
//    fun findUserByName(login: String): UserEntity {
//        val user = userRepository.findUserEntityByName(login)
//        return if (user.isNotEmpty())
//            user.first()
//        else
//            throw UsernameNotFoundException("User with name $login not found")
//    }
////    @CachePut(cacheNames= ["Group"])
//    fun createGroup(groupName: String): ResultOfCreation {
//        val groupEntity = groupRepository.save(GroupEntity().apply {
//            name = groupName
//            lastChange = Date.valueOf(LocalDate.now())
//        })
//
//        val accessEntity = createAccess(groupEntity, getUserEntity())
//        return ResultOfCreation(Status.OK, accessEntity.id!!)
//    }
//
//    fun <T> List<T>.getOnlyOneElement(): T {
//        if (size == 1)
//            return first()
//        else
//            throw IllegalArgumentException()
//    }
//
//    fun findAccessByGroupIdAndUserId(groupId: Int, userId: Int): AccessEntity {
//        val accessEntities = accessRepository.findByGroupIdAndUserId(groupId, userId)
//
//        return accessEntities.getOnlyOneElement()
//    }
////    @CachePut(cacheNames= ["UsersInGroup"])
//    fun addUserToGroup(groupId: Int, email: String): ResultOfCreation {
//        return try {
//            val userEntity = try {
//                findUserByName(email)
//            } catch (e: UsernameNotFoundException) {
//                return ResultOfCreation(Status.USER_NOT_FOUND_ON_GROUP)
//            }
//
//            val groupEntity = groupRepository.findByIdOrNull(groupId) ?: throw IllegalArgumentException()
//
//            if (accessRepository.existsByUserIdAndGroupId(userEntity.id!!, groupEntity.id!!))
//                return ResultOfCreation(Status.ACCESS_ALREADY_EXIST)
//
//            val whoInvited = try {
//                findAccessByGroupIdAndUserId(groupId, getUserEntity().id!!)
//            } catch (e: IllegalArgumentException) {
//                return ResultOfCreation(Status.USER_IS_NOT_MEMBER_OF_GROUP)
//            }
//
//            val accessEntity = createAccess(groupEntity, userEntity, whoInvited)
//            ResultOfCreation(Status.OK, accessEntity.id!!)
//
//        } catch (e: IllegalArgumentException) {
//            ResultOfCreation(Status.ERROR)
//        }
//    }
//
//    fun createAccess(groupEntity: GroupEntity, userEntity: UserEntity, whoInvited: AccessEntity? = null): AccessEntity {
//        return accessRepository.save(AccessEntity().apply {
//            refGroupEntity = groupEntity
//            refUserEntity = userEntity
//            refAccessEntity = whoInvited
//        })
//    }
//
//    fun <T> T.unproxy() = Hibernate.unproxy(this)
//
//    fun getAllUserGroup(): List<GroupEntity?> {
//        val allAccessEntity = accessRepository.findByUserId(getUserEntity().id!!)
//        return allAccessEntity.map { it.refGroupEntity.unproxy() as GroupEntity }
//    }
//
////    @Cacheable("UsersInGroup", key="#groupId")
//    fun getAllUsersOfUserGroup(groupId: Int): List<UserEntity> {
//        return if (accessRepository.existsByUserIdAndGroupId(getUserEntity().id!!, groupId)) {
//            val allAccessEntity = accessRepository.findByGroupId(groupId)
//            allAccessEntity.map { it.refUserEntity.unproxy() as UserEntity }
//        } else listOf()
//    }
//
//    data class ResultsList<T>(val status: Status, val list: List<T> = listOf())
//
////    @Cacheable("SubCategories", key="#categotyId")
//    fun getSubCategories(categoryId: Int): ResultsList<CategoryEntity> {
//        val categoryEntity =
//            categoryRepository.findByIdOrNull(categoryId) ?: return ResultsList(Status.CATEGORY_NOT_FOUND)
//
//        val userId = getUserEntity().id!!
//        val groupId = categoryEntity.groupId!!
//
//        return if (accessRepository.existsByUserIdAndGroupId(userId, groupId))
//            ResultsList(Status.OK, listOf(categoryEntity.unproxy() as CategoryEntity))
//        else
//            ResultsList(Status.USER_IS_NOT_MEMBER_OF_GROUP)
//    }
////    @Cacheable("AllCategories")
//    fun getAllCategories(groupId: Int, isIncome: Boolean): ResultsList<CategoryEntity> {
//        if (accessRepository.existsByUserIdAndGroupId(getUserEntity().id!!, groupId).not())
//            return ResultsList(Status.USER_IS_NOT_MEMBER_OF_GROUP)
//
//        val parentCategories =
//            categoryRepository.findByGroupIdAndParentIdAndIncome(groupId, null, isIncome)
//        val allCategories = parentCategories.map { it.unproxy() as CategoryEntity }
//
//        return ResultsList(Status.OK, allCategories)
//    }
//
////    @CachePut(cacheNames= ["AllCategories", "SubCategories"])
//    fun createCategory(groupId: Int, name: String, parentId: Int?, isIncome: Boolean): ResultOfCreation {
//        if (accessRepository.existsByUserIdAndGroupId(getUserEntity().id!!, groupId).not())
//            return ResultOfCreation(Status.USER_IS_NOT_MEMBER_OF_GROUP)
//
//        val parentCategoryEntity = if (parentId != null) {
//            try {
//                categoryRepository.findByIdAndGroupIdAndIncome(parentId, groupId, isIncome).getOnlyOneElement()
//            } catch (e: IllegalArgumentException) {
//                return ResultOfCreation(Status.CATEGORY_NOT_FOUND)
//            }
//        } else null
//
//        val groupEntity = groupRepository.findById(groupId).get()
//
//        val categoryEntity = categoryRepository.save(CategoryEntity().apply {
//            refCategoryEntity = parentCategoryEntity
//            refGroupEntity = groupEntity
//            this.income = isIncome
//            this.name = name
//        })
//
//        return ResultOfCreation(Status.OK, categoryEntity.id!!)
//    }
//
////    @CachePut(cacheNames= ["AllCashAccounts"])
//    fun createCashAccount(groupId: Int, name: String, cash: Double): ResultOfCreation {
//        val accountEntity = try {
//            accessRepository.findByGroupIdAndUserId(groupId, getUserEntity().id!!).getOnlyOneElement()
//        } catch (e: IllegalArgumentException) {
//            return ResultOfCreation(Status.USER_IS_NOT_MEMBER_OF_GROUP)
//        }
//
//        val cashAccountEntity = cashAccountRepository.save(CashAccountEntity().apply {
//            refAccessEntity = accountEntity
//            this.cash = cash
//            this.name = name
//        })
//
//        return ResultOfCreation(Status.OK, cashAccountEntity.id!!)
//    }
////    @Cacheable("AllCashAccounts", key="#groupId")
//    fun allCashAccounts(groupId: Int): ResultsList<CashAccountEntity> {
//
//        if (accessRepository.existsByUserIdAndGroupId(getUserEntity().id!!, groupId).not())
//            return ResultsList(Status.USER_IS_NOT_MEMBER_OF_GROUP)
//
//        val allAccessEntities = accessRepository.findByGroupId(groupId)
//
//        val cashAccountEntities = mutableListOf<CashAccountEntity>()
//        for (i in allAccessEntities) {
//            if (i.refCashAccountEntities != null) {
//                i.refCashAccountEntities!!.forEach { cashAccount ->
//                    cashAccount.unproxy() as CashAccountEntity
//                    cashAccountEntities.add(cashAccount)
//                }
//            }
//        }
//        return ResultsList(Status.OK, cashAccountEntities)
//    }
//
//    private fun userIsMemberOfGroup(groupId: Int): Boolean {
//        val userId = getUserEntity().id!!
//
//        return accessRepository.existsByUserIdAndGroupId(userId, groupId)
//    }
//
//    private fun getCashAccount(groupId: Int, cashAccountId: Int): CashAccountEntity {
//        val cashAccountEntity =
//            cashAccountRepository.findByIdOrNull(cashAccountId)
//
//        if (cashAccountEntity == null || cashAccountEntity.refAccessEntity!!.groupId!! != groupId)
//            throw IllegalArgumentException()
//
//        return cashAccountEntity
//
//    }
//
//    @CachePut(cacheNames= ["AllExpensesByDate", "AllExpensesByPrice"])
//    fun createExpense(
//        groupId: Int,
//        categoryId: Int,
//        cashAccountId: Int,
//        date: LocalDate,
//        price: Double,
//        comment: String,
//    ): ResultOfCreation {
//
//        if (userIsMemberOfGroup(groupId).not())
//            return ResultOfCreation(Status.USER_IS_NOT_MEMBER_OF_GROUP)
//
//        val cashAccountEntity = try {
//            getCashAccount(groupId, cashAccountId)
//        } catch (e: IllegalArgumentException) {
//            return ResultOfCreation(Status.CASH_ACCOUNT_NOT_FOUND)
//        }
//
//        val categoryEntity = try {
//            categoryRepository.findByIdAndGroupId(categoryId, groupId).getOnlyOneElement()
//        } catch (e: IllegalArgumentException) {
//            return ResultOfCreation(Status.CATEGORY_NOT_FOUND)
//        }
//
//        val expenseEntity = expenseRepository.save(ExpenseEntity().apply {
//            refCategoryEntity = categoryEntity
//            refCashAccountEntity = cashAccountEntity
//            this.date = Date.valueOf(date)
//            this.price = price
//            this.comment = comment
//        })
//
//        cashAccountEntity.cash = cashAccountEntity.cash!! - price
//        cashAccountRepository.save(cashAccountEntity)
//
//        return ResultOfCreation(Status.OK, expenseEntity.id!!)
//    }
//    @Cacheable("AllExpensesByDate", key="#groupId")
//    fun getAllExpensesByDate(
//        groupId: Int,
//        startDate: Date,
//        endDate: Date,
//        offset: Int,
//        limit: Int,
//    ): ResultsList<ExpenseEntity> {
//        if (accessRepository.existsByUserIdAndGroupId(getUserEntity().id!!, groupId).not())
//            return ResultsList(Status.USER_IS_NOT_MEMBER_OF_GROUP)
//
//        return try {
//            val expenseEntity = expenseRepository.resultsOrderedByDate(startDate,
//                endDate,
//                groupId,
//                offset,
//                limit)
//            ResultsList(Status.OK, expenseEntity)
//        } catch (e: SQLException) {
//            ResultsList(Status.ERROR)
//        }
//    }
//    @Cacheable("AllExpensesByPrice", key="#groupId")
//    fun getAllExpensesByPrice(
//        groupId: Int,
//        startDate: Date,
//        endDate: Date,
//        offset: Int,
//        limit: Int,
//    ): ResultsList<ExpenseEntity> {
//        if (accessRepository.existsByUserIdAndGroupId(getUserEntity().id!!, groupId).not())
//            return ResultsList(Status.USER_IS_NOT_MEMBER_OF_GROUP)
//
//        return try {
//            val expenseEntity = expenseRepository.resultsOrderedByPrice(startDate,
//                endDate,
//                groupId,
//                offset,
//                limit)
//            ResultsList(Status.OK, expenseEntity)
//        } catch (e: SQLException) {
//            ResultsList(Status.ERROR)
//        }
//    }
//    @CachePut(cacheNames= ["AllIncomesByDate", "AllIncomesByPrice"])
//    fun createIncome(
//        groupId: Int,
//        income: Double,
//        categoryId: Int,
//        cashAccountId: Int,
//        date: Date,
//        comment: String,
//    ): ResultOfCreation {
//
//        if (userIsMemberOfGroup(groupId).not())
//            return ResultOfCreation(Status.USER_IS_NOT_MEMBER_OF_GROUP)
//
//        val cashAccountEntity = try {
//            getCashAccount(groupId, cashAccountId)
//        } catch (e: IllegalArgumentException) {
//            return ResultOfCreation(Status.CASH_ACCOUNT_NOT_FOUND)
//        }
//
//        val categoryEntity = try {
//            categoryRepository.findByIdAndGroupId(categoryId, groupId).getOnlyOneElement()
//        } catch (e: IllegalArgumentException) {
//            return ResultOfCreation(Status.CATEGORY_NOT_FOUND)
//        }
//
//
//        val incomeEntity = incomeRepository.save(IncomeEntity().apply {
//            this.income = income
//            this.date = date
//            this.comment = comment
//            refCashAccountEntity = cashAccountEntity
//            refCategoryEntity = categoryEntity
//        })
//
//        cashAccountEntity.cash = cashAccountEntity.cash!! + income
//        cashAccountRepository.save(cashAccountEntity)
//
//        return ResultOfCreation(Status.OK, incomeEntity.id!!)
//    }
//
//    @Cacheable("AllIncomesByDate", key="#groupId")
//    fun getAllIncomesByDate(
//        groupId: Int,
//        startDate: Date,
//        endDate: Date,
//        offset: Int,
//        limit: Int,
//    ): ResultsList<IncomeEntity> {
//        if (accessRepository.existsByUserIdAndGroupId(getUserEntity().id!!, groupId).not())
//            return ResultsList(Status.USER_IS_NOT_MEMBER_OF_GROUP)
//
//        return try {
//            val incomeEntity = incomeRepository.resultsOrderedByDate(startDate,
//                endDate,
//                groupId,
//                offset,
//                limit)
//            ResultsList(Status.OK, incomeEntity)
//        } catch (e: SQLException) {
//            ResultsList(Status.ERROR)
//        }
//    }
//    @Cacheable("AllIncomesByPrice", key="#groupId")
//    fun getAllIncomesByPrice(
//        groupId: Int,
//        startDate: Date,
//        endDate: Date,
//        offset: Int,
//        limit: Int,
//    ): ResultsList<IncomeEntity> {
//        if (accessRepository.existsByUserIdAndGroupId(getUserEntity().id!!, groupId).not())
//            return ResultsList(Status.USER_IS_NOT_MEMBER_OF_GROUP)
//
//        return try {
//            val incomeEntity = incomeRepository.resultsOrderedByPrice(startDate,
//                endDate,
//                groupId,
//                offset,
//                limit)
//            ResultsList(Status.OK, incomeEntity)
//        } catch (e: SQLException) {
//            ResultsList(Status.ERROR)
//        }
//    }
//
//    @CachePut(cacheNames= ["AllLocalExchangeByDate", "AllLocalExchangeByPrice"])
//    fun createLocalExchange(
//        groupId: Int,
//        senderId: Int,
//        receiverId: Int,
//        sent: Double,
//        date: Date,
//        comment: String,
//    ): ResultOfCreation {
//        if (userIsMemberOfGroup(groupId).not())
//            return ResultOfCreation(Status.USER_IS_NOT_MEMBER_OF_GROUP)
//
//        val senderCashAccountEntity: CashAccountEntity
//        val receiverCashAccountEntity: CashAccountEntity
//        try {
//            senderCashAccountEntity = getCashAccount(groupId, senderId)
//            receiverCashAccountEntity = getCashAccount(groupId, receiverId)
//        } catch (e: IllegalArgumentException) {
//            return ResultOfCreation(Status.CASH_ACCOUNT_NOT_FOUND)
//        }
//
//        val localExchangeEntity = localExchangeRepository.save(LocalExchangeEntity().apply {
//            refCashAccountEntitySend = senderCashAccountEntity
//            refCashAccountEntityReceive = receiverCashAccountEntity
//            this.sent = sent
//            this.date = date
//            this.comment = comment
//        })
//
//        senderCashAccountEntity.cash = senderCashAccountEntity.cash!! - sent
//        cashAccountRepository.save(senderCashAccountEntity)
//
//        receiverCashAccountEntity.cash = receiverCashAccountEntity.cash!! + sent
//        cashAccountRepository.save(receiverCashAccountEntity)
//
//        return ResultOfCreation(Status.OK, localExchangeEntity.id!!)
//    }
//
//    @Cacheable("AllLocalExchangeByDate", key="#groupId")
//    fun getAllLocalExchangeByDate(
//        groupId: Int,
//        startDate: Date,
//        endDate: Date,
//        offset: Int,
//        limit: Int,
//    ): ResultsList<LocalExchangeEntity> {
//        if (accessRepository.existsByUserIdAndGroupId(getUserEntity().id!!, groupId).not())
//            return ResultsList(Status.USER_IS_NOT_MEMBER_OF_GROUP)
//
//        return try {
//            val localExchange = localExchangeRepository.resultsOrderedByDate(startDate,
//                endDate,
//                groupId,
//                offset,
//                limit)
//            ResultsList(Status.OK, localExchange)
//        } catch (e: SQLException) {
//            ResultsList(Status.ERROR)
//        }
//    }
//    @Cacheable("AllLocalExchangeByPrice", key="#groupId")
//    fun getAllLocalExchangeByPrice(
//        groupId: Int,
//        startDate: Date,
//        endDate: Date,
//        offset: Int,
//        limit: Int,
//    ): ResultsList<LocalExchangeEntity> {
//        if (accessRepository.existsByUserIdAndGroupId(getUserEntity().id!!, groupId).not())
//            return ResultsList(Status.USER_IS_NOT_MEMBER_OF_GROUP)
//
//        return try {
//            val localExchangeEntity = localExchangeRepository.resultsOrderedByPrice(startDate,
//                endDate,
//                groupId,
//                offset,
//                limit)
//            ResultsList(Status.OK, localExchangeEntity)
//        } catch (e: SQLException) {
//            ResultsList(Status.ERROR)
//        }
//    }
//
//
//}