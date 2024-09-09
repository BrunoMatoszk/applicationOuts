package clube.outs.application.service;

import clube.outs.application.dto.PaymentRequest;
import clube.outs.application.dto.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentProcessor {

    private final PaymentService paymentService;

    @Autowired
    public PaymentProcessor(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public PaymentResponse processPayment(PaymentRequest request) {
        return paymentService.processPayment(request);
    }

}




