package com.Aadyy.projectManagement.Repository;

import com.Aadyy.projectManagement.Modal.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {

    Subscription findByUserId(Long userid);
}
