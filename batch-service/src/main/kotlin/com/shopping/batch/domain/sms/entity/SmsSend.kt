package com.shopping.batch.domain.sms.entity

import com.common.jpa.entity.BaseEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "sms_send")
class SmsSend (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "send_id")
    var id: Long? = null,

    val content: String,

    val senderNumber: String,

    val firstSendTime: LocalDateTime? = null,

    val lastSendTime: LocalDateTime? = null,

    @Enumerated(EnumType.STRING)
    val status: SendStatus = SendStatus.READY,

    val reservationTime: LocalDateTime? = null,

    @OneToMany(mappedBy = "smsSend")
    val smsRecipients: List<SmsRecipient> = mutableListOf(),

) : BaseEntity()