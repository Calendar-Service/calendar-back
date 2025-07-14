//package com.cs.calendarback.payment;
//
//import com.cs.calendarback.payment.dto.CancelPaymentRequest;
//import com.cs.calendarback.payment.dto.ConfirmPaymentRequest;
//import lombok.RequiredArgsConstructor;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.net.http.HttpResponse;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/api/v1/payments")
//public class PaymentController {
//
//    private final PaymentService paymentService;
//
//    private final TossPaymentClient tossPaymentClient;
//
//    @Value("${payment.secret-key}")
//    private String secretKey;
//
//    @PostMapping("/confirm")
//    public ResponseEntity<?> confirmPayment(@RequestBody ConfirmPaymentRequest request) throws Exception {
//        HttpResponse<String> stringHttpResponse = tossPaymentClient.requestConfirm(request);
//        int statusCode = stringHttpResponse.statusCode(); // 응답 코드
//        String responseBodyString = stringHttpResponse.body(); // 응답 본문 (JSON 문자열)
//
//        JSONParser parser = new JSONParser();
//        JSONObject responseBody = (JSONObject) parser.parse(responseBodyString);
//
//        if (statusCode == 200) {
//            try {
//                return ResponseEntity.ok(responseBody); // 정상 응답
//            } catch (Exception e) {
//                return ResponseEntity.status(500).body("결제 승인에는 성공했지만, 서버 내부 문제로 결제 데이터를 저장하지 못했습니다. 결제를 취소했습니다.");
//            }
//        } else {
//            // 결제가 실패한 경우
//            return ResponseEntity.status(statusCode).body(responseBody);
//        }
//
//    }
//
//    @PostMapping("/cancel")
//    public ResponseEntity<?> cancelPayment(@RequestBody CancelPaymentRequest request) throws IOException, InterruptedException {
//        HttpResponse<String> response = tossPaymentClient.requestCancel(request.paymentKey(), request.cancelReason());
//        //if(response.statusCode() == 200) tossPaymentService.changePaymentStatus(cancelPaymentRequest.paymentKey(), TossPaymentStatus.CANCELED);
//        return ResponseEntity.status(response.statusCode()).body(response.body());
//    }
//}
