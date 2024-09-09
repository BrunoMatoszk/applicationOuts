package clube.outs.application.dto;

import clube.outs.application.entity.Event;
import clube.outs.application.strategy.discount.TicketPriceStrategy;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class EventResponse {
    private Integer id;
    private String nome;
    private String genero;
    private LocalDate dataEvento;
    private String lineUp;
    private Double valorTotal;
    private List<TicketPriceStrategy> ticketPriceStrategies;

    public EventResponse(Event evento) {
        this.id = evento.getId();
        this.nome = evento.getNome();
        this.genero = evento.getGenero();
        this.dataEvento = evento.getDataEvento();
        this.lineUp = evento.getLineUp();
    }
}

