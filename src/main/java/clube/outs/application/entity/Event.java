package clube.outs.application.entity;

import clube.outs.application.dto.EventRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String genero;
    private LocalDate dataEvento;
    private String lineUp;
    @Transient
    private List<Ticket> ingressos;

    // Construtor sem argumentos (necessário para JPA)
    public Event() {
    }

    // Construtor para inicialização a partir de EventRequest
    public Event(EventRequest request) {
        this.nome = request.getNome();
        this.genero = request.getGenero();
        this.dataEvento = request.getDataEvento();
        this.lineUp = request.getLineUp();
    }
}
