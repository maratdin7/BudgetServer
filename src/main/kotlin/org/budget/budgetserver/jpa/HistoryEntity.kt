package org.budget.budgetserver.jpa

import javax.persistence.*

@Entity
@Table(name = "tb_history", schema = "public", catalog = "Budget")
data class HistoryEntity(
    @get:Id
    @get:Access(AccessType.FIELD)
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    @get:Column(name = "id", nullable = false)
    val id: Int,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "date", nullable = false)
    val date: java.sql.Date,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "category_id", nullable = true, insertable = false, updatable = false)
    val categoryId: Int,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "sum", nullable = false)
    val sum: Double,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "group_id", nullable = true, insertable = false, updatable = false)
    val groupId: Int,

    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:Access(AccessType.FIELD)
    @get:JoinColumn(name = "category_id", referencedColumnName = "id")
    val refCategoryEntity: CategoryEntity?,

    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:Access(AccessType.FIELD)
    @get:JoinColumn(name = "group_id", referencedColumnName = "id")
    val refGroupEntity: GroupEntity?,
) {

    override fun toString(): String =
        "Entity of type: ${javaClass.name} ( " +
                "id = $id " +
                "date = $date " +
                "categoryId = $categoryId " +
                "sum = $sum " +
                "groupId = $groupId " +
                ")"
}
