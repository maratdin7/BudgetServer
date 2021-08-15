package org.budget.budgetserver.jpa

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "tb_local_exchange", schema = "public", catalog = "Budget")
data class LocalExchangeEntity(
    @get:Id
    @get:Access(AccessType.FIELD)
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    @get:Column(name = "id", nullable = false)
    val id: Int?,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "sender_id", nullable = true, insertable = false, updatable = false)
    val senderId: Int?,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "receiver_id", nullable = true, insertable = false, updatable = false)
    val receiverId: Int?,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "sent", nullable = false)
    val sent: Double?,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "date", nullable = false)
    val date: java.sql.Date?,

    @get:Basic
    @get:Access(AccessType.FIELD)
    @get:Column(name = "comment", nullable = true)
    val comment: String?,

    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:Access(AccessType.FIELD)

    @get:JoinColumn(name = "sender_id", referencedColumnName = "id")
    @JsonIgnore val refCashAccountEntitySend: CashAccountEntity?,

    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:Access(AccessType.FIELD)

    @get:JoinColumn(name = "receiver_id", referencedColumnName = "id")
    @JsonIgnore val refCashAccountEntityReceive: CashAccountEntity?,

    ) {
    override fun toString(): String =
        "Entity of type: ${javaClass.name} ( " +
                "id = $id " +
                "senderId = $senderId " +
                "reciverId = $receiverId " +
                "sent = $sent " +
                "date = $date " +
                "comment = $comment " +
                ")"

}

