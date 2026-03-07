package com.shopping.batch.domain.sms.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.shopping.batch.domain.sms.dto.QSmsSendDto
import com.shopping.batch.domain.sms.dto.SmsSendDto
import com.shopping.batch.domain.sms.entity.QSmsRecipient.smsRecipient
import com.shopping.batch.domain.sms.entity.QSmsSend.smsSend
import com.shopping.batch.domain.sms.entity.SmsRecipient
import com.shopping.batch.domain.sms.entity.SmsSend
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

interface SmsSendRepository : JpaRepository<SmsSend, Long>, SmsSendRepositoryCustom {

    @Query("""
       select sr
         from SmsRecipient sr
        where sr.id = :id
    """)
    fun findEntityById(@Param("id") id: Long): SmsRecipient

    @Query("""
        select count(1) 
          from SmsSend s 
         where s.reservationTime <= :time 
           and s.status = 'READY'
    """)
    fun countSendInfo(time: LocalDateTime): Int

    @Query("""
        select new com.shopping.batch.domain.sms.dto.SmsSendDto(
            ss.id,
            ss.content,
            ss.senderNumber,
            null,
            null,
            ss.status,
            ss.reservationTime,
            sr.id,
            sr.recipientNumber
        )
          from SmsSend ss
          join ss.smsRecipients sr
         order by sr.id
    """)
    fun findSmsInfo(): List<SmsSendDto>

}

interface SmsSendRepositoryCustom {
    fun findSmsInfoList(id: Long): List<SmsSendDto>
    fun updateSmsRecipient(dtos: List<SmsSendDto>)
    fun findEntity(id: Long): SmsSend?
}

@Repository
class SmsSendRepositoryCustomImpl (
    private val jPAQueryFactory: JPAQueryFactory
) : SmsSendRepositoryCustom {

    override fun findEntity(id: Long): SmsSend? {
        return jPAQueryFactory
            .select(
                smsSend
            )
            .from(smsSend)
            .where(smsSend.id.eq(id))
            .fetchOne()
    }

    override fun findSmsInfoList(id: Long): List<SmsSendDto> {

         return jPAQueryFactory
             .select(
                 QSmsSendDto(
                     smsSend.id,
                     smsSend.content,
                     smsSend.senderNumber,
                     smsSend.firstSendTime,
                     smsSend.lastSendTime,
                     smsSend.status,
                     smsSend.reservationTime,
                     smsRecipient.id,
                     smsRecipient.recipientNumber
                 )
             )
             .from(smsSend)
             .join(smsSend.smsRecipients, smsRecipient)
             .where(
                 smsSend.id.eq(id)
             )
             .fetch()
    }



    override fun updateSmsRecipient(dtos: List<SmsSendDto>) {
        val updateClause = jPAQueryFactory.update(smsRecipient)
            .where(smsRecipient.smsSend.id.eq(2))

    }
}