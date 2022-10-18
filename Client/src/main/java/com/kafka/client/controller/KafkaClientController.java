package com.kafka.client.controller;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class KafkaClientController {

    private static final Logger log = LoggerFactory.getLogger(KafkaClientController.class);

    private final KafkaTemplate<Integer, String> template;

    public KafkaClientController(KafkaTemplate<Integer, String> template) {
        this.template = template;
    }

    /**
     * Принимаем сообщение в параметре по ресту.
     * Отправляем сообщение в топик 'kafka'
     */
    @GetMapping("/send")
    public void sendMsg(@RequestParam(name = "message") String message) {
        template.send("kafka", message);
        log.info("Send message to topic 'kafka'");
    }

    /**
     * Слушаем топик 'stats' и принимаем входящие сообщения.
     */
    @KafkaListener(topics = "stats")
    public void listenStatistic(ConsumerRecord<Integer, String> input) {
        log.info("Get statistic: {}", input.value());
    }
}
