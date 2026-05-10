package com.shopping.store.entity

import com.common.jpa.entity.BaseEntity
import jakarta.persistence.*
import java.time.LocalTime

@Entity
@Table(name = "store_operation_hour")
class StoreOperationHour (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_operation_hour_id")
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    val store: Store,

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    val dayOfWeek: DayOfWeek,

    @Column(name = "open_time", nullable = false)
    var openTime: LocalTime,

    @Column(name = "close_time", nullable = false)
    var closeTime: LocalTime,

    @Column(name = "break_start")
    var breakStart: LocalTime,

    @Column(name = "break_end")
    var breakEnd: LocalTime,

    @Column(name = "is_day_off")
    var isDayOff: Boolean,
) : BaseEntity()