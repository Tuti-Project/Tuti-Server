package com.tuti.payment.controller;

import com.tuti.auth.config.AuthenticatedMemberId;
import com.tuti.common.advice.response.ApiResponse;
import com.tuti.payment.service.PaymentService;
import com.tuti.payment.service.request.PaymentCallbackRequest;
import com.tuti.payment.service.request.PaymentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/payment")
    public ApiResponse<PaymentRequest> paymentPage(@AuthenticatedMemberId Long memberId) {
        return ApiResponse.ok(paymentService.findRequestDto(memberId));
    }

    @PostMapping("/payment")
    public ApiResponse<Void> validationPayment(@RequestBody PaymentCallbackRequest request) {
        paymentService.paymentByCallback(request);
        return ApiResponse.ok();
    }
}