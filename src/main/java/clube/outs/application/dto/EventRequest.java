package clube.outs.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class EventRequest {
    private String nome;
    private String genero;
    private LocalDate dataEvento;
    private String lineUp;
    private List<TicketRequest> ingressos;
}
