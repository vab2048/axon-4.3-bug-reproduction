package com.example.axon.bug.utils;

import com.example.axon.bug.api.enums.OrderBehaviour;
import com.example.axon.bug.api.messages.OrderCommandMessages.CreateOrderCommand;
import com.example.axon.bug.api.messages.OrderCommandMessages.OrderCreatedEvent;
import com.example.axon.bug.api.records.CorrelationId;
import com.example.axon.bug.api.records.OrderId;
import com.example.axon.bug.api.records.OrderMetadata;
import lombok.experimental.UtilityClass;

import java.security.SecureRandom;
import java.time.Instant;

@UtilityClass
public class OrderUtils {

    /**
     * Our random generator for the class.
     */
    private static final SecureRandom random = new SecureRandom();

    /**
     * Return a randomly generated OrderMetadata instance.
     */
    public static OrderMetadata generateRandomOrderMetadataInstance() {
        return OrderMetadata.builder()
                .orderCreationInstant(Instant.now())
                .orderId(new OrderId())
                .correlationId1(new CorrelationId())
                .correlationId2(new CorrelationId())
                .correlationId3(new CorrelationId())
                .build();
    }

    /**
     * Return a newly generated CreateOrderCommand with fields
     * populated with randomly generated instances.
     * This is useful in tests.
     */
    public static CreateOrderCommand generateRandomCreateOrderCommand() {
        // Create instances of the correct type for our CreateOrderCommand
        OrderMetadata orderMetadata = generateRandomOrderMetadataInstance();

        OrderBehaviour orderBehaviour = randomEnumValue(OrderBehaviour.class);

        // Return the event
        return new CreateOrderCommand(orderMetadata.getOrderId(), orderMetadata, orderBehaviour);
    }

    /**
     * Return a newly generated random OrderCreatedEvent.
     */
    public static OrderCreatedEvent generateRandomOrderCreatedEvent() {
        // Create the order parentage and the bet
        OrderMetadata orderMetadata = generateRandomOrderMetadataInstance();

        OrderBehaviour orderBehaviour = randomEnumValue(OrderBehaviour.class);

        // Return the event
        return new OrderCreatedEvent(orderMetadata.getOrderId(), orderMetadata, orderBehaviour);
    }


    /**
     * Given an enum class this will return a random enum value for that enum..
     */
    public static <T extends Enum<?>> T randomEnumValue(Class<T> clazz){
        if (clazz.getEnumConstants().length == 0) {
            throw new IllegalArgumentException("Attempting to create RandomEnumValue for enum with no values defined");
        }

        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
}
