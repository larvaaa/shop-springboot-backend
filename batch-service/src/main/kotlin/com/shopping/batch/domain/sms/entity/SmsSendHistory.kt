package com.shopping.batch.domain.sms.entity

import com.shopping.common.entity.BaseEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "sms_send_history")
class SmsSendHistory(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "send_id")
    val smsSend: SmsSend,

    val sendTime: LocalDateTime,

    @Enumerated(EnumType.STRING)
    val status: SendStatus = SendStatus.SUCCESS

) : BaseEntity()