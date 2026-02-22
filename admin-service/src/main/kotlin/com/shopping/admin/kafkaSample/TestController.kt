package com.shopping.admin.kafkaSample

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestController(
    private val testService: TestService
) {

    @GetMapping("/send")
    fun send(message: String): String {
        testService.send("test-topic", message)
        return "sent message $message"
    }
}