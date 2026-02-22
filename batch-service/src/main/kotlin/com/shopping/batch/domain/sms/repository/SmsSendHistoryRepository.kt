package com.shopping.batch.domain.sms.repository

import com.shopping.batch.domain.sms.entity.SmsSendHistory
import org.springframework.data.jpa.repository.JpaRepository

interface SmsSendHistoryRepository : JpaRepository<SmsSendHistory, Long> {



}