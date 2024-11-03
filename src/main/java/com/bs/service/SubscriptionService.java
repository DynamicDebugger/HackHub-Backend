package com.bs.service;

import com.bs.model.PlanType;
import com.bs.model.Subscription;
import com.bs.model.User;

public interface SubscriptionService {

    Subscription createSubscription(User user);
    Subscription getUserSubscription(Long userID) throws Exception;

    Subscription upgradeSubscription(Long userId, PlanType planType);

    boolean isValid(Subscription subscription);
}
