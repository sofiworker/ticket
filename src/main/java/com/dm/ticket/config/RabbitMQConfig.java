package com.dm.ticket.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    private static final String DEAD_LETTERS_QUEUE_NAME = "dead_letters_queue_name_order";

    private static final String DEAD_LETTERS_EXCHANGE_NAME = "dead_letters_exchange_name_order";

    private static final String QUEUE_NAME = "test_dlx_queue_name_order";

    private static final String EXCHANGE_NAME = "test_dlx_exchange_name_order";

    private static final String ROUTING_KEY = "order.pay";

    /**
     * 声明死信队列、死信交换机、绑定队列到死信交换机
     * 建议使用FanoutExchange广播式交换机
     */
    @Bean
    public Queue deadLettersQueue() {
        return new Queue(DEAD_LETTERS_QUEUE_NAME);
    }

    @Bean
    public FanoutExchange deadLettersExchange() {
        return new FanoutExchange(DEAD_LETTERS_EXCHANGE_NAME);
    }

    @Bean
    public Binding deadLettersBinding() {
        return BindingBuilder.bind(deadLettersQueue()).to(deadLettersExchange());
    }

    /**
     * 声明普通队列，并指定相应的备份交换机、死信交换机
     */
    @Bean
    public Queue queue() {
        Map<String, Object> arguments = new HashMap<>(10);
        //指定死信发送的Exchange
        arguments.put("x-dead-letter-exchange", DEAD_LETTERS_EXCHANGE_NAME);
        return new Queue(QUEUE_NAME, true, false, false, arguments);
    }

    @Bean
    public Exchange exchange() {
        return new DirectExchange(EXCHANGE_NAME, true, false, null);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(ROUTING_KEY).noargs();
    }
}
