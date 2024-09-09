package clube.outs.application.controller;

import clube.outs.application.dto.PaymentRequest;
import clube.outs.application.dto.PaymentResponse;
import clube.outs.application.service.PaymentProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentProcessor paymentProcessor;

    @Autowired
    public PaymentController(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    @PostMapping("/process")
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest paymentRequest) {
        if (paymentRequest == null || paymentRequest.getPaymentType() == null) {
            return ResponseEntity.badRequest().body(new PaymentResponse("Invalid payment request"));
        }

        PaymentResponse response = paymentProcessor.processPayment(paymentRequest);
        return ResponseEntity.ok(response);
    }
}
