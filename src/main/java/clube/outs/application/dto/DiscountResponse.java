package clube.outs.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountResponse {
    private Double valorFinal;

    public DiscountResponse(Double valorFinal) {
        this.valorFinal = valorFinal;
    }

}
