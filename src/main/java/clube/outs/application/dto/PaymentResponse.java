package clube.outs.application.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentResponse {
    private String status;
    private String responseBody;

    public PaymentResponse(String status) {
        this.status = status;
    }

    public PaymentResponse() {
    }
}
