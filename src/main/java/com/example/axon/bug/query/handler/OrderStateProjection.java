package com.example.axon.bug.query.handler;

import com.example.axon.bug.api.messages.OrderCommandMessages.OrderCreatedEvent;
import com.example.axon.bug.api.messages.OrderQueryMessages.FindLatestOrdersQuery;
import com.example.axon.bug.api.messages.OrderQueryMessages.FindOrderQuery;
import com.example.axon.bug.query.model.OrderState;
import com.example.axon.bug.query.repository.OrderStateRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.SequenceNumber;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


@Service @Slf4j
public class OrderStateProjection {

    private final OrderStateRepository orderRepository;

    @Autowired
    public OrderStateProjection(OrderStateRepository orderRepository)
    {
        this.orderRepository = orderRepository;
    }

    //////////////////////////////////////////////////////////////////
    //                   @EventHandler methods
    //////////////////////////////////////////////////////////////////
    @EventHandler
    public void on(OrderCreatedEvent event, @SequenceNumber Long aggregateVersion) {
        log.trace("Projecting (aggregateVersion: {}): {}", aggregateVersion, event);

        // Create the new projection entity (since this is an OrderCreatedEvent).
        OrderState orderStateProjection = new OrderState();
        orderStateProjection.setOrderAggregateId(event.getOrderId().asUUID());
        orderStateProjection.setOrderMetadata(event.getOrderMetadata());
        orderStateProjection.setOrderBehaviour(event.getOrderBehaviour());

        // Save the entity to the DB.
        orderRepository.save(orderStateProjection);
    }

    //////////////////////////////////////////////////////////////////
    //                   @QueryHandler methods
    //////////////////////////////////////////////////////////////////
    @QueryHandler
    public OrderState handle(FindOrderQuery query) {
        log.debug("Handling query: {}", query);

        // Grab the UUID of the requested order.
        UUID orderAggregateUUID = UUID.fromString(query.getOrderId().getId());

        // Return the OrderEntity with the requested UUID or throw an exception signifying
        // it does not exist.
        return orderRepository.findByOrderAggregateId(orderAggregateUUID)
                .orElseThrow(() -> {
                    String s = "Order with id '%s' not found";
                    return new IllegalArgumentException(String.format(s, orderAggregateUUID.toString()));
                });
    }

    /**
     * Retrieve the latest `N` order entities in a list which is kept
     * in ascending order with respect to the entity IDs
     * e.g. 10, 11, 12.
     */
    @QueryHandler
    public List<OrderState> handle(FindLatestOrdersQuery query) {
        log.debug("Handling query: {}", query);
        // We retrieve the latest 'N' orders by ordering DESCENDING. However, these will be returned in descending
        // order with respect to the ID i.e. 12, 11, 10, etc. which is not the final form of what we want to return.
        // The args passed to the page request are for the: page to retrieve, size of page, sort definition.
        Pageable pageDefinition = PageRequest.of(0, 1_000, Sort.by(Sort.Direction.DESC, "id"));
        List<OrderState> orders = orderRepository.findAll(pageDefinition).getContent();

        // We want orders with ascending ID e.g. 10, 11, 12, so we create a reversed list and return it.
        ArrayList<OrderState> orderEntities = new ArrayList<>(orders);
        Collections.reverse(orderEntities);

        return orderEntities;
    }



}
