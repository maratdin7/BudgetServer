package org.budget.budgetserver.jpa

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "tb_income", schema = "public", catalog = "Budget")
data class IncomeEntity(
    @get:Id
    @get:Access(AccessType.FIELD)
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    @get:Column(name = "id", nullable = false)
    val id: Int,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "income", nullable = false)
    val income: Double,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "category_id", nullable = true, insertable = false, updatable = false)
    val categoryId: Int,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "cash_account_id", nullable = true, insertable = false, updatable = false)
    val cashAccountId: Int,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "date", nullable = false)
    val date: java.sql.Date,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "comment", nullable = true)
    val comment: String?,

    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:Access(AccessType.FIELD)
    @get:JoinColumn(name = "category_id", referencedColumnName = "id")
    @JsonIgnore
    val refCategoryEntity: CategoryEntity?,

    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:Access(AccessType.FIELD)
    @get:JoinColumn(name = "cash_account_id", referencedColumnName = "id")
    @JsonIgnore val refCashAccountEntity: CashAccountEntity?,

    ) {
    override fun toString(): String =
        "Entity of type: ${javaClass.name} ( " +
                "id = $id " +
                "income = $income " +
                "categoryId = $categoryId " +
                "cashAccountId = $cashAccountId " +
                "date = $date " +
                "comment = $comment " +
                ")"
}


