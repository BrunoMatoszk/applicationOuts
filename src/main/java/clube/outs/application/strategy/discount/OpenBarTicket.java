package clube.outs.application.strategy.discount;

import clube.outs.application.type.TicketType;

public class OpenBarTicket implements TicketPriceStrategy {

    @Override
    public Double getTicketPrice() {
        return 65.00;
    }

    @Override
    public TicketType getTicketType() {
        return TicketType.OPENBAR;
    }
}
