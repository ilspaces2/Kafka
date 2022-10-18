package com.kafka.server.controller;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class KafkaServerController {

    private static final Logger log = LoggerFactory.getLogger(KafkaServerController.class);

    private final KafkaTemplate<Integer, String> template;

    private final Map<String, Integer> statistic = new ConcurrentHashMap<>();

    public KafkaServerController(KafkaTemplate<Integer, String> template) {
        this.template = template;
    }

    /**
     * Слушаем топик "kafka" и принимаем сообщения, складываем их в мапу
     * и дополнительно считаем статистику сообщений.
     * Если приходит сообщение "stat" то отправляем в топик "stats" (на Client) нашу мапу.
     */
    @KafkaListener(topics = "kafka")
    public void onApiCall(ConsumerRecord<Integer, String> input) {
        String value = input.value();
        statistic.put(value, statistic.getOrDefault(value, 0) + 1);
        log.info("Get and save input value: {}", value);
        if ("stat".equals(value)) {
            template.send("stats", statistic.toString());
            log.info("Send statistic to topic 'stats'.");
        }
    }
}
