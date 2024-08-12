package com.Aadyy.projectManagement.services;

import com.Aadyy.projectManagement.Modal.PlanType;
import com.Aadyy.projectManagement.Modal.Subscription;
import com.Aadyy.projectManagement.Modal.User;
import com.Aadyy.projectManagement.Repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SubscriptionServiceImpl implements  SubscriptionService{

    @Autowired
    private  UserService userService;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Override
    public void createSubscription(User user) {

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setSubscriptionStartDate(LocalDate.now());
        subscription.setGetSubscriptionEndDate(LocalDate.now().plusMonths(12));
        subscription.setValid(true);
        subscription.setPlantype(PlanType.FREE);

        subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription getUserSubscription(Long userId) throws Exception {
        Subscription subscription =  subscriptionRepository.findByUserId(userId);

        if(!isValid(subscription))
        {
            subscription.setPlantype(PlanType.FREE);
            subscription.setGetSubscriptionEndDate(LocalDate.now().plusMonths(12));
            subscription.setSubscriptionStartDate(LocalDate.now());
        }

        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription upgradeSubscription(Long userId, PlanType plantype) {
        Subscription subscription = subscriptionRepository.findByUserId(userId);
        subscription.setPlantype(plantype);
        subscription.setSubscriptionStartDate(LocalDate.now());

        if(plantype.equals(PlanType.ANNUALLY))
        {
            subscription.setGetSubscriptionEndDate(LocalDate.now().plusMonths(12));
        }
        else
        {
            subscription.setGetSubscriptionEndDate(LocalDate.now().plusMonths(1)); ;

        }

        return  subscriptionRepository.save(subscription);
    }

    @Override
    public boolean isValid(Subscription subscription) {

        if(subscription.getPlantype().equals(PlanType.FREE))
        {
            return  true;
        }

        LocalDate endDate = subscription.getGetSubscriptionEndDate();
        LocalDate  currentDate = LocalDate.now();

        return endDate.isAfter(currentDate) || endDate.isEqual(currentDate);
    }
}
