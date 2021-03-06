package com.tradeshift.spring.rabbit.example.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.tradeshift.amqp.rabbit.handlers.RabbitTemplateHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class SomeResource {

    @Value("${spring.rabbitmq.custom.some-event.exchange}")
    private String exchangeSomeEvent;

    @Value("${spring.rabbitmq.custom.some-event.queueRoutingKey}")
    private String routingKeySomeEvent;

    private final RabbitTemplateHandler rabbitTemplateHandler;

    @Autowired
    SomeResource(RabbitTemplateHandler rabbitTemplateHandler) {
        this.rabbitTemplateHandler = rabbitTemplateHandler;
    }

    @GetMapping("/sendMessageDefault/{message}")
    public ResponseEntity sendMessageToDefaultVirtualHost(@PathVariable("message") final String message) {
        rabbitTemplateHandler.getRabbitTemplate("some-event").convertAndSend(exchangeSomeEvent, routingKeySomeEvent, message);
        return ResponseEntity.ok("Message sent");
    }

}
