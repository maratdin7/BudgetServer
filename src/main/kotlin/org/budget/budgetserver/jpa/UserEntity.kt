package org.budget.budgetserver.jpa

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "tb_user", schema = "public", catalog = "Budget")
data class UserEntity(
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    @get:Access(AccessType.FIELD)
    @get:Column(name = "id", nullable = false, insertable = true, updatable = false)
    var id: Int = 0,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "name", nullable = false)
    var name: String,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "password", nullable = false)
    @JsonIgnore
    var password: String,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "enable", updatable = true, nullable = false)
    @JsonIgnore
    var enable: Boolean = false,

    @get:Access(AccessType.FIELD)
    @get:OneToMany(mappedBy = "refUserEntity")
    @JsonIgnore
    var refAccessEntities: List<AccessEntity>? = null,

    @get:Access(AccessType.FIELD)
    @get:OneToMany(mappedBy = "refUserEntity")
    @JsonIgnore
    var refRefreshTokenEntities: List<RefreshTokenEntity>? = null,

    @get:Access(AccessType.FIELD)
    @get:OneToMany(mappedBy = "refUserEntity")
    @JsonIgnore
    var refResetPasswordTokenEntities: List<ResetPasswordTokenEntity>? = null,

    @get:Access(AccessType.FIELD)
    @get:OneToMany(mappedBy = "refUserEntity")
    @JsonIgnore
    var refUserToGroupTokenEntity: List<UserToGroupTokenEntity>? = null,

    ) {
    override fun toString(): String =
        "Entity of type: ${javaClass.name} ( " +
                "id = $id " +
                "name = $name " +
                "password = $password " +
                ")"
}

