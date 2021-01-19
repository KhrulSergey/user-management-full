package com.khsa.kafka.engine;

import com.khsa.usermanagement.domain.model.analytic.Event;
import com.khsa.usermanagement.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

@Service
@ConditionalOnProperty(value = "example.kafka.consumer-enabled", havingValue = "true")
public class Consumer {

    private final Logger logger = LoggerFactory.getLogger(Producer.class);

    private EventService eventService;

    @Autowired
    public Consumer(EventService eventService) {
        this.eventService = eventService;
    }

    @KafkaListener(topics = {"INPUT_DATA"})
    public void consume(final @Payload String message,
                        final @Header(KafkaHeaders.OFFSET) Integer offset,
                        final @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
                        final @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                        final @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                        final @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts,
                        final Acknowledgment acknowledgment
    ) {
        logger.error(String.format("#### -> Consumed message -> TIMESTAMP: %d, Message:%s\noffset: %d, key: %s, partition: %d, topic: %s", ts, message, offset, key, partition, topic));
        acknowledgment.acknowledge();

        Event newEvent = new Event();
        newEvent.setMessage(message);
        newEvent.setCreatedDate(LocalDateTime.ofInstant(Instant.ofEpochMilli(ts),
                TimeZone.getDefault().toZoneId()));
        eventService.add(newEvent);
    }

    @KafkaListener(topics = {"TEST_DATA"})
    public void consumeTest(final @Payload String message,
                        final @Header(KafkaHeaders.OFFSET) Integer offset,
                        final @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
                        final @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                        final @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                        final @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts,
                        final Acknowledgment acknowledgment
    ) {
        logger.error(String.format("#### -> Consumed message test-> TIMESTAMP: %d, Message:%s\noffset: %d, key: %s, partition: %d, topic: %s", ts, message, offset, key, partition, topic));
        acknowledgment.acknowledge();

        Event newEvent = new Event();
        newEvent.setMessage(message);
        newEvent.setCreatedDate(LocalDateTime.ofInstant(Instant.ofEpochMilli(ts),
                TimeZone.getDefault().toZoneId()));
        eventService.add(newEvent);
    }
}

