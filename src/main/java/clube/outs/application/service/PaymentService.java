package clube.outs.application.service;

import clube.outs.application.dto.PaymentRequest;
import clube.outs.application.dto.PaymentResponse;

public interface PaymentService {
    PaymentResponse processPayment(PaymentRequest paymentRequest);
}
