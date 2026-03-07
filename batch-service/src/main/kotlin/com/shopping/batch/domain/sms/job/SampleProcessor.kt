package com.shopping.batch.domain.sms.job

import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

@Component
@StepScope
class SampleProcessor : ItemProcessor<String, String> {

    override fun process(item: String): String {

//        if(item.contains("3_60")) throw Exception("문제발생!!!!!!!!!")
        if(item.equals("2_16") || item.equals("3_10")) throw IllegalStateException()
        return "${item}_processed"
    }

}