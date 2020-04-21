package com.example.axon.bug.api.messages;

import com.example.axon.bug.api.enums.OrderBehaviour;
import com.example.axon.bug.api.records.OrderId;
import com.example.axon.bug.api.records.OrderMetadata;
import lombok.*;
import org.axonframework.commandhandling.RoutingKey;


public class OrderCommandMessages {

    /**
     * Create a new Order aggregate instance.
     */
    @Value @Builder @AllArgsConstructor
    @NoArgsConstructor(force = true, access = AccessLevel.PRIVATE) // Unfortunately is needed to play nice with Jackson
    public static class CreateOrderCommand {
        // The unique ID that the new order will be assigned.
        @RoutingKey
        OrderId orderId;

        // Some metadata associated with the order.
        OrderMetadata orderMetadata;

        // Some behaviour associated with the order.
        OrderBehaviour orderBehaviour;
    }

    @Value @Builder @AllArgsConstructor
    @NoArgsConstructor(force = true, access = AccessLevel.PRIVATE) // Unfortunately is needed to play nice with Jackson
    public static class OrderCreatedEvent {
        OrderId orderId;
        OrderMetadata orderMetadata;
        OrderBehaviour orderBehaviour;
    }

}
