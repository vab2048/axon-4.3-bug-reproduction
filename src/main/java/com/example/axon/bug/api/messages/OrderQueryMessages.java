package com.example.axon.bug.api.messages;


import com.example.axon.bug.api.records.OrderId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

public class OrderQueryMessages {

    /**
     * Find the order with the specific ID.
     */
    @Value @Builder @AllArgsConstructor
    @NoArgsConstructor(force = true, access = AccessLevel.PRIVATE) // Unfortunately is needed to play nice with Jackson
    public static class FindOrderQuery {
        // The ID of the order we want to find.
        OrderId orderId;
    }

    /**
     * Find the latest 'n' orders as specified in the argument.
     */
    @Value @JsonSerialize @AllArgsConstructor
    @NoArgsConstructor(force = true, access = AccessLevel.PRIVATE) // Unfortunately is needed to play nice with Jackson
    public static class FindLatestOrdersQuery {
        int numberOrders;
    }

}
