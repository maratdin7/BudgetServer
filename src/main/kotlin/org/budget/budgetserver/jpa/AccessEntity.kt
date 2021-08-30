package org.budget.budgetserver.jpa

import com.fasterxml.jackson.annotation.JsonIgnore
import org.budget.budgetserver.service.internal.RoleInGroup
import javax.persistence.*

@Entity
@Table(name = "tb_access", schema = "public", catalog = "Budget")
data class AccessEntity(
    @get:Id
    @get:Access(AccessType.FIELD)
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    @get:Column(name = "id", nullable = false, insertable = true, updatable = false)
    var id: Int = 0,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "group_id", nullable = false, insertable = false, updatable = false)
    var groupId: Int = 0,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    var userId: Int = 0,

    @get:Basic
    @get:Enumerated(EnumType.STRING)
    @get:Access(AccessType.FIELD)
    @get:Column(name = "role", nullable = false, insertable = false, updatable = true)
    var role: RoleInGroup = RoleInGroup.USER,

    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:Access(AccessType.FIELD)
    @get:JoinColumn(name = "group_id", referencedColumnName = "id")
    val refGroupEntity: GroupEntity,

    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:Access(AccessType.FIELD)
    @get:JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore val refUserEntity: UserEntity,

    @get:OneToMany(mappedBy = "refAccessEntity")
    @get:Access(AccessType.FIELD)
    @JsonIgnore val refCashAccountEntities: List<CashAccountEntity>? = null,

    ) {
    override fun toString(): String =
        "Entity of type: ${javaClass.name} ( " +
                "id = $id " +
                "groupId = $groupId " +
                "userId = $userId " +
                "role = $role " +
                ")"
}
