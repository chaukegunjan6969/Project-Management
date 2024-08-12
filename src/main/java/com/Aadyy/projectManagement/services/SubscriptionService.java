package com.Aadyy.projectManagement.services;

import com.Aadyy.projectManagement.Modal.PlanType;
import com.Aadyy.projectManagement.Modal.Subscription;
import com.Aadyy.projectManagement.Modal.User;

public interface SubscriptionService {

    void createSubscription(User user);

    Subscription getUserSubscription(Long userId)throws  Exception;

    Subscription upgradeSubscription(Long userId, PlanType plantype);

    boolean isValid(Subscription subscription);
}
