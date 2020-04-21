package com.example.axon.bug.api.records;
import lombok.Value;
import org.axonframework.common.IdentifierFactory;

import java.util.UUID;

@Value
public class OrderId {

    private final String id;

    private OrderId(String s) {
        this.id = s;
    }
    public static OrderId fromString(String s) { return new OrderId(s); } // Call private constructor to create instance.
    public OrderId() {
        // We utilise Axon's IdentifierFactory to generate a unique ID.
        // By default this will create a random UUID.
        this.id = IdentifierFactory.getInstance().generateIdentifier();
    }

    public UUID asUUID() {
        return UUID.fromString(id);
    }

    @Override
    public String toString() {
        return "OrderId(" + id + ")";
    }
}

