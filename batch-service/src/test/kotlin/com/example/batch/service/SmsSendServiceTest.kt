package com.example.batch.service

import com.shopping.batch.domain.sms.dto.SmsRecipientDto
import com.shopping.batch.domain.sms.dto.SmsSendDto
import com.shopping.batch.domain.sms.entity.SendStatus
import com.shopping.batch.domain.sms.service.SmsSendService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import kotlin.test.Test

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Rollback(false)
class SmsSendServiceTest {

    @Autowired
    lateinit var smsSendService: SmsSendService

    private val logger = KotlinLogging.logger {}


    @Test
    fun selectSampleTest() {
//        val findlist = smsSendService.selectSample(7)
//
//        for(dto in findlist) {
//            logger.info("dto = $dto")
//        }

    }

    @Test
    fun selectTest() {

        val now = LocalDateTime.now()
        val sendInfos: List<SmsSendDto> = smsSendService.findSmsInfoList(7)

        val first = sendInfos[0]
        logger.info("asis status = ${first.status}")
        first.status = SendStatus.SUCCESS
        logger.info("tobe status = ${first.status}")

    }

    @Test
    fun saveTest() {

        val sendInfo = SmsSendDto(
            content = "안녕하세요. 테스트123",
            senderNumber = "01011112222",
            reservationTime = LocalDateTime.of(2025, 12, 21, 17, 39)
        )

        val numberList = listOf("01011111111", "01022222222", "01033333333")
        val recipientList = mutableListOf<SmsRecipientDto>()

        for(number in numberList) {
            recipientList.add(
                SmsRecipientDto(
                    recipientNumber = number,
                )
            )
        }

        smsSendService.reserveSms(sendInfo, recipientList)

    }

}