package com.shopping.batch.domain.sms.service

import com.shopping.batch.domain.sms.dto.SmsRecipientDto
import com.shopping.batch.domain.sms.dto.SmsSendDto
import com.shopping.batch.domain.sms.entity.SendStatus
import com.shopping.batch.domain.sms.entity.SmsRecipient
import com.shopping.batch.domain.sms.entity.SmsSend
import com.shopping.batch.domain.sms.entity.SmsSendHistory
import com.shopping.batch.domain.sms.repository.SmsRecipientRepository
import com.shopping.batch.domain.sms.repository.SmsSendHistoryRepository
import com.shopping.batch.domain.sms.repository.SmsSendRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class SmsSendService(
    val smsSendRepository: SmsSendRepository,
    val smsRecipientRepository: SmsRecipientRepository,
    val smsSendHistoryRepository: SmsSendHistoryRepository,
) {

    fun sendSms(smsSendDto: SmsSendDto, recipientNumberList: List<SmsRecipientDto>, isReservation: Boolean) {

        if(smsSendDto.content.isEmpty()) {
            throw IllegalArgumentException("smsSendDto.content 은 필수 입니다.")
        } else if(smsSendDto.senderNumber.isEmpty()) {
            throw IllegalArgumentException("smsSendDto.senderNumber 은 필수 입니다.")
        }
        // 발송 정보 생성
        val smsSend = SmsSend(
            content = smsSendDto.content,
            senderNumber = smsSendDto.senderNumber,
            firstSendTime = LocalDateTime.now(),
            lastSendTime = LocalDateTime.now(),
            status = SendStatus.SENDING
        )
        // 발송정보 저장
        smsSendRepository.save(smsSend)

        // 발송 이력 생성
        val smsSendHistory = SmsSendHistory(
            smsSend = smsSend,
            sendTime = LocalDateTime.now(),
            status = SendStatus.SENDING
        )
        // 발송이력 저장
        smsSendHistoryRepository.save(smsSendHistory)

        // 수신자 생성
        val recipientList: List<SmsRecipient> = recipientNumberList.map { SmsRecipient(
            smsSend = smsSend,
            recipientNumber = it.recipientNumber,
            sendTime = LocalDateTime.now(),
            status = SendStatus.SENDING
        ) }

        // 수신자목록 저장
        for(recipient in recipientList) {
            smsRecipientRepository.save(recipient)
        }

        // 메시지 비동기 발송 kafka 호출

    }

    fun reserveSms(smsSendDto: SmsSendDto, recipientNumberList: List<SmsRecipientDto>) {

        if(smsSendDto.content.isEmpty()) {
            throw IllegalArgumentException("smsSendDto.content 은 필수 입니다.")
        } else if(smsSendDto.senderNumber.isEmpty()) {
            throw IllegalArgumentException("smsSendDto.senderNumber 은 필수 입니다.")
        }

        if(smsSendDto.reservationTime == null) {
            throw IllegalArgumentException("smsSendDto.reservationTime 이 없습니다.")
        }
        // 발송정보 생성
        val smsSend = SmsSend(
            content = smsSendDto.content,
            senderNumber = smsSendDto.senderNumber,
            reservationTime = smsSendDto.reservationTime,
            status = SendStatus.READY,
        )
        // 발송정보 저장
        smsSendRepository.save(smsSend)

        // 수신자 생성
        val recipientList: List<SmsRecipient> = recipientNumberList.map { SmsRecipient(
            smsSend = smsSend,
            recipientNumber = it.recipientNumber,
            status = SendStatus.READY,
        ) }
        // 수신자목록 저장
        for(recipient in recipientList) {
            smsRecipientRepository.save(recipient)
        }
    }

    fun delete(smsSend: SmsSend) = smsSendRepository.delete(smsSend)

    fun findEntityById(id: Long): SmsSend? = smsSendRepository.findEntity(id)

    fun findSmsInfo(): List<SmsSendDto> = smsSendRepository.findSmsInfo()

    fun findSmsInfoList(id: Long): List<SmsSendDto> = smsSendRepository.findSmsInfoList(id)

//    fun selectSample(id: Long): List<SmsSendDto> = smsSendRepository.selectSample(id)
}