package org.budget.budgetserver.jpa

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "tb_cash_account", schema = "public", catalog = "Budget")
data class CashAccountEntity(
    @get:Id
    @get:Access(AccessType.FIELD)
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    @get:Column(name = "id", nullable = false, insertable = false, updatable = false)
    val id: Int,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "access_id", nullable = true, insertable = false, updatable = false)
    val accessId: Int,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "name", nullable = false)
    val name: String,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "cash", nullable = false)
    val cash: Double,

    @ManyToOne(fetch = FetchType.LAZY)
    @get:JoinColumn(name = "access_id", referencedColumnName = "id")
    @get:Access(AccessType.FIELD)
    @JsonIgnore
    val refAccessEntity: AccessEntity?,

    @OneToMany(mappedBy = "refCashAccountEntity")
    @get:Access(AccessType.FIELD)
    @JsonIgnore
    val refExpenseEntities: List<ExpenseEntity>?,

    @OneToMany(mappedBy = "refCashAccountEntity")
    @get:Access(AccessType.FIELD)
    @JsonIgnore
    val refIncomeEntities: List<IncomeEntity>?,

    @OneToMany(mappedBy = "refCashAccountEntitySend")
    @get:Access(AccessType.FIELD)
    @JsonIgnore
    val refLocalExchangeEntitiesSend: List<LocalExchangeEntity>?,

    @OneToMany(mappedBy = "refCashAccountEntityReceive")
    @get:Access(AccessType.FIELD)
    @JsonIgnore
    val refLocalExchangeEntitiesReceive: List<LocalExchangeEntity>?,

    @OneToMany(mappedBy = "refCashAccountEntity")
    @get:Access(AccessType.FIELD)
    @JsonIgnore
    val refPlannedExpenseEntities: List<PlannedExpenseEntity>?,

    ) {
    override fun toString(): String =
        "Entity of type: ${javaClass.name} ( " +
                "id = $id " +
                "accessId = $accessId " +
                "name = $name " +
                "cash = $cash " +
                ")"
}

