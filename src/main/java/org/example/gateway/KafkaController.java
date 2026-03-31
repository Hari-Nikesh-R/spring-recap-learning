package org.example.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {
    @Autowired
    private KafkaProducer producer;

    @PostMapping("/kafka")
    public String triggerKafka(@RequestParam("message") String message) {
        producer.sendMessage(message);
        return "Successfully triggered kafka";
    }
}
