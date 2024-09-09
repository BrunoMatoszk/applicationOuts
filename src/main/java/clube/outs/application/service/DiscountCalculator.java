package clube.outs.application.service;

import clube.outs.application.dto.DiscountRequest;
import clube.outs.application.dto.DiscountResponse;
import clube.outs.application.strategy.discount.TicketPriceStrategy;
import clube.outs.application.type.TicketType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DiscountCalculator {

    private final Map<TicketType, TicketPriceStrategy> strategies;

    @Autowired
    public DiscountCalculator(List<TicketPriceStrategy> strategies) {
        this.strategies = strategies.stream()
                .collect(Collectors.toMap(TicketPriceStrategy::getTicketType, Function.identity()));
    }

    public DiscountResponse calcularDesconto(DiscountRequest request) {
        TicketType ticketType;
        try {
            ticketType = TicketType.valueOf(request.getTipoDesconto().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de desconto inválido.");
        }

        TicketPriceStrategy discountStrategy = strategies.get(ticketType);
        if (discountStrategy == null) {
            throw new IllegalArgumentException("Tipo de desconto não encontrado.");
        }

        // Aplicar o desconto
        Double valorFinal = discountStrategy.getTicketPrice();

        // Retornar a resposta com o valor final
        return new DiscountResponse(valorFinal);
    }
}

