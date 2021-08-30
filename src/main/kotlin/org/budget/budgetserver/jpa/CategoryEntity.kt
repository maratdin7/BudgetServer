package org.budget.budgetserver.jpa

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "tb_category", schema = "public", catalog = "Budget")
data class CategoryEntity(
    @get:Id
    @get:Access(AccessType.FIELD)
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    @get:Column(name = "id", nullable = false, insertable = false, updatable = false)
    val id: Int = 0,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "name", nullable = false)
    val name: String,

    @get:Basic
    @get:Enumerated(EnumType.STRING)
    @get:Access(AccessType.FIELD)
    @get:Column(name = "type", nullable = false)
    val type: ExpenseType = ExpenseType.EXPENSE,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "parent_id", nullable = true, insertable = false, updatable = false)
    @JsonIgnore val parentId: Int? = null,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "group_id", nullable = true, insertable = false, updatable = false)
    val groupId: Int = 0,

    @get:OneToMany(mappedBy = "refCategoryEntity", fetch = FetchType.LAZY)
    @get:Access(AccessType.FIELD)
    val refCategoryEntities: List<CategoryEntity>? = null,

    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:Access(AccessType.FIELD)
    @get:JoinColumn(name = "parent_id", referencedColumnName = "id")
    @JsonIgnore val refCategoryEntity: CategoryEntity? = null,

    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:Access(AccessType.FIELD)
    @get:JoinColumn(name = "group_id", referencedColumnName = "id")
    @JsonIgnore val refGroupEntity: GroupEntity,

    @get:OneToMany(mappedBy = "refCategoryEntity")
    @get:Access(AccessType.FIELD)
    @JsonIgnore
    val refExpenseEntities: List<ExpenseEntity>? = null,

    @get:OneToMany(mappedBy = "refCategoryEntity")
    @get:Access(AccessType.FIELD)
    @JsonIgnore
    val refHistoryEntities: List<HistoryEntity>? = null,

    @get:OneToMany(mappedBy = "refCategoryEntity")
    @get:Access(AccessType.FIELD)
    @JsonIgnore
    val refIncomeEntities: List<IncomeEntity>? = null,

    @get:OneToMany(mappedBy = "refCategoryEntity")
    @get:Access(AccessType.FIELD)
    @JsonIgnore
    val refPlannedExpenseEntities: List<PlannedExpenseEntity>? = null,

    ) {
    override fun toString(): String =
        "Entity of type: ${javaClass.name} ( " +
                "id = $id " +
                "name = $name " +
                "isIncome = $type " +
                "parentId = $parentId " +
                "groupId = $groupId " +
                ")"
}

enum class ExpenseType {
    EXPENSE,
    INCOME,
}