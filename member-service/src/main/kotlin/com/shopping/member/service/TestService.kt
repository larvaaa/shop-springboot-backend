package com.shopping.member.service
//
//import org.springframework.kafka.annotation.KafkaListener
//import org.springframework.kafka.core.KafkaTemplate
//import org.springframework.stereotype.Service
//
//@Service
//class TestService(
//    private val kafkaTemplate: KafkaTemplate<String, String>
//) {
//
//    fun send(topic: String, message: String) {
//        kafkaTemplate.send(topic, message)
//        println("sent message: $message, topic: $topic")
//    }
//
//    @KafkaListener(topics = ["test-topic"], groupId = "test-group")
//    fun listen(message: String) {
//        println("receive message: $message")
//    }
//
//}
//
