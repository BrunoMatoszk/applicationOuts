package clube.outs.application.dto;

import clube.outs.application.type.TicketType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketRequest {
    private TicketType ticketType;
}

