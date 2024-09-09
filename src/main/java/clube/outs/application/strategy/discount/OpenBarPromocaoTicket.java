package clube.outs.application.strategy.discount;

import clube.outs.application.type.TicketType;

public class OpenBarPromocaoTicket implements TicketPriceStrategy {

    @Override
    public Double getTicketPrice() {
        return 55.00;
    }

    @Override
    public TicketType getTicketType() {
        return TicketType.OPENBAR_PROMO;
    }
}
