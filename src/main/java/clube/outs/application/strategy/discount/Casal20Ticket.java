package clube.outs.application.strategy.discount;

import clube.outs.application.type.TicketType;

public class Casal20Ticket implements TicketPriceStrategy {

    @Override
    public Double getTicketPrice() {
        return 85.00;
    }

    @Override
    public TicketType getTicketType() {
        return TicketType.CASAL20;
    }
}
