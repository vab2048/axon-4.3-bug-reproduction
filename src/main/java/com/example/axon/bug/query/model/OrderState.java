package com.example.axon.bug.query.model;


import com.example.axon.bug.api.enums.OrderBehaviour;
import com.example.axon.bug.api.records.OrderMetadata;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.UUID;

/**
 * This is the query model entity representing the current state of an Order aggregate.
 *
 * Quick reference for FAQs regarding JPA annotations:
 * - @TypeDef and @Type(type = "jsonb")
 *   - See: https://vladmihalcea.com/how-to-map-json-objects-using-generic-hibernate-types/
 *   - Essentially these are required to map to the postgres JSONB type
 * - @Enumerated(EnumType.STRING)
 *   - Store the enum as its string value (rather than its ordinal value)
 */
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor @Builder
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)  // For using the Postgres JSONB type within the entity
@Entity @Table(name = OrderState.TABLE_NAME, schema = OrderState.SCHEMA_NAME)
public class OrderState {

    // The (fully qualified) table name for this entity. It is static so will not be persisted.
    public static final String SCHEMA_NAME = "projections";
    public static final String TABLE_NAME = "buggy_projection_state";
    public static final String FQ_TABLE_NAME = SCHEMA_NAME + "." + TABLE_NAME;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Column(name = "id")                                         private Long id;

    @Column(unique = true, name = "aggregate_id", nullable = false)  private UUID orderAggregateId;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb",
            nullable = false, name = "order_metadata")               private OrderMetadata orderMetadata;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_behaviour")                                private OrderBehaviour orderBehaviour;

}
