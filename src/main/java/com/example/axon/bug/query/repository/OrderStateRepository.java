package com.example.axon.bug.query.repository;


import com.example.axon.bug.query.model.OrderState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderStateRepository extends JpaRepository<OrderState, Long> {

    Optional<OrderState> findByOrderAggregateId(UUID orderAggregateId);

}
