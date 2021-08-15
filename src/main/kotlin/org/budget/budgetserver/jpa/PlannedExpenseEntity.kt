package org.budget.budgetserver.jpa

import javax.persistence.*

@Entity
@Table(name = "tb_planned_expense", schema = "public", catalog = "Budget")
data class PlannedExpenseEntity(
    @get:Id
    @get:Access(AccessType.FIELD)
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    @get:Column(name = "id", nullable = false)
    val id: Int,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "category_id", nullable = false, insertable = false, updatable = false)
    val categoryId: Int,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "price", nullable = false)
    val price: Double,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "cash_account_id", nullable = true, insertable = false, updatable = false)
    val cashAccountId: Int,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "day", nullable = false)
    val day: Int,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "comment", nullable = true)
    val comment: String?,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "priority", nullable = false)
    val priority: Int = 0,

    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:Access(AccessType.FIELD)
    @get:JoinColumn(name = "category_id", referencedColumnName = "id")
    val refCategoryEntity: CategoryEntity?,

    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:Access(AccessType.FIELD)
    @get:JoinColumn(name = "cash_account_id", referencedColumnName = "id")
    val refCashAccountEntity: CashAccountEntity?,


    ) {
    override fun toString(): String =
        "Entity of type: ${javaClass.name} ( " +
                "id = $id " +
                "categoryId = $categoryId " +
                "price = $price " +
                "cashAccountId = $cashAccountId " +
                "day = $day " +
                "comment = $comment " +
                "priority = $priority " +
                ")"

}

