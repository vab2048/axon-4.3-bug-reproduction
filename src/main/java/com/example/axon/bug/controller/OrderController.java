package com.example.axon.bug.controller;

import com.example.axon.bug.api.messages.OrderCommandMessages.CreateOrderCommand;
import com.example.axon.bug.api.messages.OrderQueryMessages.FindLatestOrdersQuery;
import com.example.axon.bug.api.messages.OrderQueryMessages.FindOrderQuery;
import com.example.axon.bug.api.records.OrderId;
import com.example.axon.bug.query.model.OrderState;
import com.example.axon.bug.query.repository.OrderStateRepository;
import com.example.axon.bug.utils.OrderUtils;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
public class OrderController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;
    private final OrderStateRepository orderStateRepository;

    @Autowired
    public OrderController(
            CommandGateway commandGateway,
            QueryGateway queryGateway,
            OrderStateRepository orderStateRepository) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
        this.orderStateRepository = orderStateRepository;
    }

    @PostMapping("/create")
    public CreateOrderCommand createRandomOrder() {
        log.info("Creating a random order");

        // We create the command with random values for the id, parentage and bet.
        CreateOrderCommand createOrderCommand = OrderUtils.generateRandomCreateOrderCommand();

        // Send the command
        commandGateway.send(createOrderCommand);

        // Return the command that was issued so the caller knows the created order structure.
        return createOrderCommand;
    }

    @CrossOrigin
    @GetMapping("/getLatest")
    public CompletableFuture<List<OrderState>> getLatestOrders() {
        // If we have not been given an explicit number of orders use our default value.
        // Otherwise set it to the requested value.
        int numOrders = 1000;

        log.debug("getLatestOrders query called with numOrders: {}", numOrders);

        // Return results of the query.
        return queryGateway.query(
                new FindLatestOrdersQuery(numOrders),
                ResponseTypes.multipleInstancesOf(OrderState.class)
        );
    }

    @GetMapping("/get/{orderAggregateId}")
    public CompletableFuture<OrderState> getOrder(@PathVariable("orderAggregateId") String orderAggregateId) {
        log.debug("getOrder: {}", orderAggregateId);

        // We have the OrderId as a string but we need it as an OrderId
        OrderId orderId = OrderId.fromString(orderAggregateId);

        return queryGateway.query(
                new FindOrderQuery(orderId),
                ResponseTypes.instanceOf(OrderState.class)
        );
    }



}
