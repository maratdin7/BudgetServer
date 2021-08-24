package org.budget.budgetserver.jpa

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "tb_user_to_group_token", schema = "public", catalog = "Budget")
data class UserToGroupTokenEntity(
    @get:Id
    @get:Access(AccessType.FIELD)
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    @get:Column(name = "id", nullable = false, insertable = true, updatable = false)
    var id: Int = 0,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    var userId: Int = 0,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "group_id", nullable = false, insertable = false, updatable = false)
    var groupId: Int = 0,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "token", nullable = false)
    var token: String = "",

    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:Access(AccessType.FIELD)
    @get:JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore val refUserEntity: UserEntity,

    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:Access(AccessType.FIELD)
    @get:JoinColumn(name = "group_id", referencedColumnName = "id")
    @JsonIgnore val refGroupEntity: GroupEntity,

    ) {
    override fun toString(): String =
        "Entity of type: ${javaClass.name} ( " +
                "id = $id " +
                "userId = $userId " +
                "groupId = $groupId " +
                "token = $token " +
                ")"
}

