package com.example.dummySbrigit.Controllers;

import com.example.dummySbrigit.Dto.CreatePaymentDto;
import com.example.dummySbrigit.Dto.CreatePaymentResponseDto;
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


    @PostMapping("/create-payment-intent")
    public CreatePaymentResponseDto createPaymentIntent(@RequestBody @Valid CreatePaymentDto createPayment) throws StripeException, StripeException {
        PaymentIntentCreateParams createParams = new
                PaymentIntentCreateParams.Builder()
                .setCurrency("INR")
//                .putMetadata("featureRequest", createPayment.getFeatureRequest())
                .setAmount(15* 100L)
                .build();
        PaymentIntent intent = PaymentIntent.create(createParams);
        return new CreatePaymentResponseDto(intent.getClientSecret());
    }

}