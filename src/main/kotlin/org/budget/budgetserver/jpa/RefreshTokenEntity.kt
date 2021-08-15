package org.budget.budgetserver.jpa

import com.fasterxml.jackson.annotation.JsonIgnore
import java.sql.Date
import javax.persistence.*

@Entity
@Table(name = "tb_refresh_token", schema = "public", catalog = "Budget")
data class RefreshTokenEntity(
    @get:Id
    @get:Access(AccessType.FIELD)
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    @get:Column(name = "id", nullable = false, insertable = false, updatable = false)
    var id: Int = 0,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    var userId: Int = 0,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "token", nullable = false)
    var token: String = "",

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "expire_date", nullable = false)
    var expireDate: Date,

    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:JoinColumn(name = "user_id", referencedColumnName = "id")
    @get:Access(AccessType.FIELD)
    @JsonIgnore
    var refUserEntity: UserEntity? = null,
)