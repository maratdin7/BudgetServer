package org.budget.budgetserver.jpa

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "tb_group", schema = "public", catalog = "Budget")
data class GroupEntity(
    @get:Id
    @get:Access(AccessType.FIELD)
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    @get:Column(name = "id", nullable = false, insertable = true, updatable = false)
    val id: Int,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "name", nullable = false)
    val name: String,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "last_change", nullable = false)
    val lastChange: java.sql.Date,

    @get:OneToMany(mappedBy = "refGroupEntity")
    @get:Access(AccessType.FIELD)
    @JsonIgnore
    val refAccessEntities: List<AccessEntity>?,

    @get:OneToMany(mappedBy = "refGroupEntity")
    @get:Access(AccessType.FIELD)
    @JsonIgnore
    val refCategoryEntities: List<CategoryEntity>?,

    @get:OneToMany(mappedBy = "refGroupEntity")
    @get:Access(AccessType.FIELD)
    @JsonIgnore
    val refHistoryEntities: List<HistoryEntity>?,
    ) {
    override fun toString(): String =
        "Entity of type: ${javaClass.name} ( " +
                "id = $id " +
                "name = $name " +
                "lastChange = $lastChange " +
                ")"
}


