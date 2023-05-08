package com.example.stripeIntro.Controllers;

import com.example.stripeIntro.Dto.CreatePaymentDto;
import com.example.stripeIntro.Dto.CreatePaymentResponseDto;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
