package com.shopping.batch.domain.sms.entity

import com.shopping.common.entity.BaseEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "sms_recipient")
class SmsRecipient (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipient_id")
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "send_id")
    var smsSend: SmsSend,

    val recipientNumber: String,

    val sendTime: LocalDateTime? = null,

    @Enumerated(EnumType.STRING)
    val status: SendStatus = SendStatus.READY

) : BaseEntity()