package com.khsa.kafka.tasks;

import com.khsa.kafka.engine.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

@Component
public class SendMessageTask {
    private final Logger logger = LoggerFactory.getLogger(SendMessageTask.class);

    private final Producer producer;

    public SendMessageTask(Producer producer) {
        this.producer = producer;
    }

    // run every 3 sec
    @Scheduled(fixedRateString = "3000")
    public void send() throws ExecutionException, InterruptedException {

        String dateTime = LocalDateTime.now().toString();

        ListenableFuture<SendResult<String, String>> listenableFuture = this.producer.sendMessage("INPUT_DATA", "IN_KEY", dateTime);

        SendResult<String, String> result = listenableFuture.get();
        logger.info(String.format("Produced: message: %s\ntopic: %s, offset: %d, partition: %d, value size: %d", dateTime, result.getRecordMetadata().topic(),
                result.getRecordMetadata().offset(),
                result.getRecordMetadata().partition(), result.getRecordMetadata().serializedValueSize()));
    }

    // run every 5 sec
    @Scheduled(fixedRateString = "5000")
    public void sendTest() throws ExecutionException, InterruptedException {

        String dateTime = LocalDateTime.now().toString();

        ListenableFuture<SendResult<String, String>> listenableFuture = this.producer.sendMessage("TEST_DATA", "IN_KEY", dateTime);

        SendResult<String, String> result = listenableFuture.get();
        logger.info(String.format("Produced: message: %s\ntopic: %s, offset: %d, partition: %d, value size: %d", dateTime, result.getRecordMetadata().topic(),
                result.getRecordMetadata().offset(),
                result.getRecordMetadata().partition(), result.getRecordMetadata().serializedValueSize()));
    }
}
