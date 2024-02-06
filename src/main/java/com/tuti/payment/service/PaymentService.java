package com.tuti.payment.service;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import com.tuti.member.domain.Member;
import com.tuti.member.domain.repository.MemberRepository;
import com.tuti.member.service.exception.MemberNotFoundException;
import com.tuti.payment.service.request.PaymentCallbackRequest;
import com.tuti.payment.service.request.PaymentRequest;
import com.tuti.payment.domain.Payments;
import com.tuti.payment.domain.repository.PaymentRepository;
import com.tuti.payment.domain.vo.PaymentStatus;
import com.tuti.payment.service.exception.PaymentsFailException;
import com.tuti.payment.service.exception.PaymentsNotFoundException;
import com.tuti.payment.service.exception.PaymentsSpoofingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final MemberRepository memberRepository;
    private final IamportClient iamportClient;

    public PaymentRequest findRequestDto(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        return PaymentRequest.builder()
                .buyerName(member.getName())
                .buyerEmail(member.getEmail().getValue())
                .paymentPrice(9900L)
                .itemName("퍼스널 브랜딩")
                .orderUid(UUID.randomUUID().toString())
                .build();
    }

    public void paymentByCallback(PaymentCallbackRequest request) {
        try {
            // 결제 단건 조회(아임포트)
            IamportResponse<Payment> iamportResponse = iamportClient.paymentByImpUid(request.getPaymentUid());
            Payments payments = paymentRepository.findByOrderUid(request.getOrderUid()).orElseThrow(PaymentsNotFoundException::new);

            // 결제 완료가 아니면
            checkPaymentStatus(iamportResponse.getResponse().getStatus());

            // 실 결제 금액
            int amountPaid = iamportResponse.getResponse().getAmount().intValue();

            // 결제 금액 검증
            if(amountPaid != payments.getPrice()) {
                payments.changeStatus(PaymentStatus.SPOOF);

                // 결제금액 위변조로 의심되는 결제금액을 취소(아임포트)
                iamportClient.cancelPaymentByImpUid(new CancelData(iamportResponse.getResponse().getImpUid(), true, new BigDecimal(amountPaid)));

                throw new PaymentsSpoofingException();
            }

            payments.processSuccess(request.getPaymentUid());

        } catch (IamportResponseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkPaymentStatus(String status) {
        if (!status.equals("paid")) {
            throw new PaymentsFailException();
        }
    }
}