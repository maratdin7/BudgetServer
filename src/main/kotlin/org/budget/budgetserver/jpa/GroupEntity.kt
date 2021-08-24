package org.budget.budgetserver.jpa

import com.fasterxml.jackson.annotation.JsonIgnore
import org.budget.budgetserver.service.token.DateConverter.toSqlDate
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "tb_group", schema = "public", catalog = "Budget")
data class GroupEntity(
    @get:Id
    @get:Access(AccessType.FIELD)
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    @get:Column(name = "id", nullable = false, insertable = true, updatable = false)
    val id: Int = 0,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "name", nullable = false)
    val name: String,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "last_change", nullable = false)
    val lastChange: java.sql.Date = LocalDate.now().toSqlDate(),

    @get:OneToMany(mappedBy = "refGroupEntity")
    @get:Access(AccessType.FIELD)
    @JsonIgnore
    var refAccessEntities: List<AccessEntity>? = null,

    @get:OneToMany(mappedBy = "refGroupEntity")
    @get:Access(AccessType.FIELD)
    @JsonIgnore
    var refCategoryEntities: List<CategoryEntity>? = null,

    @get:OneToMany(mappedBy = "refGroupEntity")
    @get:Access(AccessType.FIELD)
    @JsonIgnore
    var refHistoryEntities: List<HistoryEntity>? = null,

    ) {
    override fun toString(): String =
        "Entity of type: ${javaClass.name} ( " +
                "id = $id " +
                "name = $name " +
                "lastChange = $lastChange " +
                ")"
}


