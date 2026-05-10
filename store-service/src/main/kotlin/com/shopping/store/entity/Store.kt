package com.shopping.store.entity

import com.common.jpa.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "store")
class Store(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    var id: Long? = null,

    @Column(name = "store_name")
    var name: String,

    @JoinColumn(name = "brand_id")
    @ManyToOne(fetch = FetchType.LAZY)
    var brand: Brand? = null,

    @Column(name = "postal_code")
    var postalCode: String,

    @Column(name = "address")
    var address: String,

    @Column(name = "detail_address")
    var detailAddress: String,

    @Column(name = "phone")
    var phone: String,

    @Column(name = "min_order_price")
    var minOrderPrice: Int,

    @Column(name = "estimated_delivery_time")
    var estimatedDeliveryTime: Int,

    @Column(name = "business_status")
    var businessStatus: BusinessStatus = BusinessStatus.PREPARING,

    @Column(name = "description")
    var description: String? = null,

    @Column(name = "is_use")
    var isUse: Boolean = true,

) : BaseEntity() {

    // 연관관계의 주인이 내가 아니다 라는 뜻
    // 읽기만 하고 수정,등록 불가
    @OneToMany(mappedBy = "store") // 연관관계 주인의 필드 이름
    val storeOperationHour = mutableListOf<StoreOperationHour>()

}
