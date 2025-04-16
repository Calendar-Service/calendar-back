package com.cs.calendarback.payment.dto;

public record ConfirmPaymentRequest(String orderId, String amount, String paymentKey) {

}
