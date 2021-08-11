package org.budget.budgetserver.jpa

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import javax.persistence.NamedQueries
import javax.persistence.NamedQuery

@Entity
@Table(name = "tb_access", schema = "public", catalog = "Budget")
data class AccessEntity(
    @get:Id
    @get:Access(AccessType.FIELD)
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    @get:Column(name = "id", nullable = false, insertable = true, updatable = false)
    val id: Int,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "group_id", nullable = true, insertable = false, updatable = false)
    val groupId: Int,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "user_id", nullable = true, insertable = false, updatable = false)
    val userId: Int,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "who_invited", nullable = true, insertable = false, updatable = false)
    val whoInvited: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @get:Access(AccessType.FIELD)
    @get:JoinColumn(name = "group_id", referencedColumnName = "id")
    val refGroupEntity: GroupEntity?,

    @ManyToOne(fetch = FetchType.LAZY)
    @get:Access(AccessType.FIELD)
    @get:JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore val refUserEntity: UserEntity?,

    @ManyToOne(fetch = FetchType.LAZY)
    @get:Access(AccessType.FIELD)
    @get:JoinColumn(name = "who_invited", referencedColumnName = "id")
    @JsonIgnore val refAccessEntity: AccessEntity?,

    @OneToMany(mappedBy = "refAccessEntity")
    @get:Access(AccessType.FIELD)
    @JsonIgnore
    val refAccessEntities: List<AccessEntity>?,

    @OneToMany(mappedBy = "refAccessEntity")
    @get:Access(AccessType.FIELD)
    @JsonIgnore val refCashAccountEntities: List<CashAccountEntity>?,

    ) {
    override fun toString(): String =
        "Entity of type: ${javaClass.name} ( " +
                "id = $id " +
                "groupId = $groupId " +
                "userId = $userId " +
                "whoInvited = $whoInvited " +
                ")"
}
