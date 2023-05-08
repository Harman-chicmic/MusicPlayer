package com.example.dummySbrigit.Controllers;

import com.example.dummySbrigit.Dto.PaymentDto;
import com.stripe.Stripe;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class PaymentController {
    @Value("{stripe.api.key}")
    private String stripePublicKey;
    static class CreatePaymentResponse{
        private String clientSecret;
        public CreatePaymentResponse(String clientSecret){
            this.clientSecret=clientSecret;
        }
    }

    @GetMapping("/create-payment-intent")
    public CreatePaymentResponse createPaymentIntent(@RequestBody @Valid PaymentDto createPayment) throws StripeException, StripeException {
        PaymentIntentCreateParams createParams = new
                PaymentIntentCreateParams.Builder()
                .setCurrency("usd")
                .putMetadata("featureRequest", createPayment.getFeatureRequest())
                .setAmount(15 * 100L)
                .build();
        PaymentIntent intent = PaymentIntent.create(createParams);
        System.out.println(intent);
        return new CreatePaymentResponse(intent.getClientSecret());
    }

}
