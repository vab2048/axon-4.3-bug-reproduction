package com.example.axon.bug.api.records;

import lombok.*;

import java.time.Instant;

/**
 * Metadata we may want to store about the order.
 */
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE) // Unfortunately is needed to play nice with Jackson
@Value @Builder @AllArgsConstructor
public class OrderMetadata {
    Instant orderCreationInstant;
    OrderId orderId;
    CorrelationId correlationId1;
    CorrelationId correlationId2;
    CorrelationId correlationId3;
}
