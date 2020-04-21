package com.example.axon.bug.api.records;

import lombok.Value;
import org.axonframework.common.IdentifierFactory;


/**
 * Some random correlation ID class.
 */
@Value
public class CorrelationId {
    String id;

    public CorrelationId() {
        // We utilise Axon's IdentifierFactory to generate a unique ID.
        // By default, this will create a random UUID as a string.
        this.id = IdentifierFactory.getInstance().generateIdentifier();
    }

    @Override
    public String toString() {
        return "CorrelationId1(" + id + ")";
    }
}


