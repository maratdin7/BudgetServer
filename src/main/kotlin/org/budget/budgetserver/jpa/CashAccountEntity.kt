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
    val id: Int = 0,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "access_id", nullable = true, insertable = false, updatable = false)
    val accessId: Int = 0,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "name", nullable = false)
    val name: String,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "cash", nullable = false)
    val cash: Double = 0.0,

    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:JoinColumn(name = "access_id", referencedColumnName = "id")
    @get:Access(AccessType.FIELD)
    @JsonIgnore
    val refAccessEntity: AccessEntity?,

    @get:OneToMany(mappedBy = "refCashAccountEntity")
    @get:Access(AccessType.FIELD)
    @JsonIgnore
    val refExpenseEntities: List<ExpenseEntity>? = null,

    @get:OneToMany(mappedBy = "refCashAccountEntity")
    @get:Access(AccessType.FIELD)
    @JsonIgnore
    val refIncomeEntities: List<IncomeEntity>? = null,

    @get:OneToMany(mappedBy = "refCashAccountEntitySend")
    @get:Access(AccessType.FIELD)
    @JsonIgnore
    val refLocalExchangeEntitiesSend: List<LocalExchangeEntity>? = null,

    @get:OneToMany(mappedBy = "refCashAccountEntityReceive")
    @get:Access(AccessType.FIELD)
    @JsonIgnore
    val refLocalExchangeEntitiesReceive: List<LocalExchangeEntity>? = null,

    @get:OneToMany(mappedBy = "refCashAccountEntity")
    @get:Access(AccessType.FIELD)
    @JsonIgnore
    val refPlannedExpenseEntities: List<PlannedExpenseEntity>? = null,

    ) {
    override fun toString(): String =
        "Entity of type: ${javaClass.name} ( " +
                "id = $id " +
                "accessId = $accessId " +
                "name = $name " +
                "cash = $cash " +
                ")"
}

