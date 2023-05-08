package com.example.stripeIntro.Dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data public class CreatePaymentDto {
    @SerializedName("items")
    Object[]items;

}
