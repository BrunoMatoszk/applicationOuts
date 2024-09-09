package clube.outs.application.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentRequest {
    private String orderId;
    private String customerName;
    private String paymentType;
    private String cardNumber;
    private String cardHolder;
    private String expirationDate;
    private String cvv;
    private String cardBrand;
    private int amount;
    private int installments;
    private String currency;
    private String pixKey;

}
