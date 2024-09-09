package clube.outs.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountRequest {
    private Double valorOriginal;
    private String tipoDesconto;

    public DiscountRequest(Double valorOriginal, String tipoDesconto) {
        this.valorOriginal = valorOriginal;
        this.tipoDesconto = tipoDesconto;
    }
}
