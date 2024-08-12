package com.Aadyy.projectManagement.Controller;

import com.Aadyy.projectManagement.Modal.PlanType;
import com.Aadyy.projectManagement.Modal.User;
import com.Aadyy.projectManagement.Response.PaymentLinkResponse;
import com.Aadyy.projectManagement.services.UserService;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Value("${razorpay.api.key")
    private  String apiKey;

    @Value("${razorpay.api.secret")
    private String apiSecret;

    @Autowired
    private UserService userService;

    @PostMapping("/{planType}")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(
            @PathVariable PlanType planType,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfilebyJwt(jwt);
        int amount = 799*100;
        if(planType.equals(PlanType.ANNUALLY))
        {
            amount=amount*12;
            amount=(int) (amount*0.7);
        }

            RazorpayClient razorpay =new RazorpayClient(apiKey,apiSecret);

            JSONObject paymentLinkRequest= new JSONObject();
            paymentLinkRequest.put("amount", amount);
            paymentLinkRequest.put("currency","INR");

            JSONObject customer = new JSONObject();
            customer.put("name",user.getFullname());
            customer.put("email",user.getEmail());

            paymentLinkRequest.put("customer", customer);

            JSONObject notify = new JSONObject();
            notify.put("email",true);
            paymentLinkRequest.put("notify", notify);

            paymentLinkRequest.put("CallBack_url","http://localhost:5173/upgrade_plan/sucess?planType"+planType );

            PaymentLink payment =razorpay.paymentLink.create(paymentLinkRequest);

           String paymentLinkId = payment.get("id");
           String  paymentLinkUrl = payment.get("short_url");

            PaymentLinkResponse res = new PaymentLinkResponse();
            res.setPayment_link_id(paymentLinkId);
            res.setPayment_link_url(paymentLinkUrl);

            return  new ResponseEntity<>(res, HttpStatus.CREATED);

    }
}
