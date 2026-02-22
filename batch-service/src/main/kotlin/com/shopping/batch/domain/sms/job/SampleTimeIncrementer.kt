package com.shopping.batch.domain.sms.job

import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.JobParametersIncrementer
import org.springframework.stereotype.Component

@Component
class SampleTimeIncrementer : JobParametersIncrementer {

    override fun getNext(parameters: JobParameters?): JobParameters {
        return JobParametersBuilder(parameters!!)
            .addLong("time", System.currentTimeMillis(), true)
            .toJobParameters()
    }

}