package com.cs.calendarback.payment.dto;

public record CancelPaymentRequest(String paymentKey, String cancelReason) {
}
