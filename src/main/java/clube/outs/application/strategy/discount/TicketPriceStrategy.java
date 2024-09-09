package clube.outs.application.strategy.discount;

import clube.outs.application.type.TicketType;

public interface TicketPriceStrategy {
    Double getTicketPrice();
    TicketType getTicketType();
}
