package clube.outs.application.entity;

import clube.outs.application.strategy.discount.TicketPriceStrategy;
import clube.outs.application.type.TicketType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ticket {
    private TicketType tipo;
    private TicketPriceStrategy strategy;

    public Ticket(TicketType tipo, TicketPriceStrategy strategy) {
        this.tipo = tipo;
        this.strategy = strategy;
    }

    public double calcularPreco() {
        return strategy.getTicketPrice();
    }
}

