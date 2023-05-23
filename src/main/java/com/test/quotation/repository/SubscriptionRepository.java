package com.test.quotation.repository;

import com.test.quotation.model.entity.Subscription;
import com.test.quotation.repository.base.CustomJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends CustomJpaRepository<Subscription, Long> {
}
