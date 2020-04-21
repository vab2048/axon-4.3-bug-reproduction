package com.example.axon.bug.command;


import com.example.axon.bug.api.enums.OrderBehaviour;
import com.example.axon.bug.api.messages.OrderCommandMessages.CreateOrderCommand;
import com.example.axon.bug.api.messages.OrderCommandMessages.OrderCreatedEvent;
import com.example.axon.bug.api.records.OrderId;
import com.example.axon.bug.api.records.OrderMetadata;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate @Slf4j
public class Order {
    /* *************************************************************
     *                      Instance Variables
     * *************************************************************/
    @AggregateIdentifier private OrderId orderId;
    private OrderMetadata orderMetadata;
    private OrderBehaviour orderBehaviour;

    /* *************************************************************
     *                   Constructors/Order Creation
     * *************************************************************/
    public Order() {}

    @CommandHandler
    public Order(CreateOrderCommand cmd) {
        log.debug("{}", cmd);
        apply(new OrderCreatedEvent(cmd.getOrderId(), cmd.getOrderMetadata(), cmd.getOrderBehaviour()));
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        log.debug("{}", event);
        orderId = event.getOrderId();
        orderMetadata = event.getOrderMetadata();
        orderBehaviour = event.getOrderBehaviour();
    }
}
