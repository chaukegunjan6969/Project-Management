package com.Aadyy.projectManagement.Controller;


import com.Aadyy.projectManagement.Modal.PlanType;
import com.Aadyy.projectManagement.Modal.Subscription;
import com.Aadyy.projectManagement.Modal.User;
import com.Aadyy.projectManagement.services.SubscriptionService;
import com.Aadyy.projectManagement.services.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscription")
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserService userService;


    @GetMapping("/user")
    public ResponseEntity<Subscription> getUserSubscription(
            @RequestHeader("Authorization") String jwt
    ) throws  Exception
    {
        User user = userService.findUserProfilebyJwt(jwt);
        Subscription subscription = subscriptionService.getUserSubscription(user.getId());

        return  new ResponseEntity<>(subscription, HttpStatus.OK);
    }

    @PatchMapping("/upgrade")
    public ResponseEntity<Subscription> upgradeSubscription(
            @RequestHeader("Authorization") String jwt,
            @RequestParam PlanType planType
            ) throws  Exception
    {
        User user = userService.findUserProfilebyJwt(jwt);
        Subscription subscription = subscriptionService.upgradeSubscription(user.getId(),planType);

        return  new ResponseEntity<>(subscription, HttpStatus.OK);
    }
}
