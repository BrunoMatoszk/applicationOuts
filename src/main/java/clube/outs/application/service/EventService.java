package clube.outs.application.service;

import clube.outs.application.dto.EventRequest;
import clube.outs.application.dto.EventResponse;
import clube.outs.application.dto.TicketRequest;
import clube.outs.application.entity.Event;
import clube.outs.application.entity.Ticket;
import clube.outs.application.repository.EventRepository;
import clube.outs.application.strategy.discount.*;
import clube.outs.application.type.TicketType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<EventResponse> listarEventos() {
        return eventRepository.findAll().stream()
                .map(EventResponse::new)
                .collect(Collectors.toList());
    }

    public EventResponse criarEvento(EventRequest eventRequest) {
        // Criar o evento a partir do request
        Event event = new Event(eventRequest);

        // Criar os ingressos dinamicamente
        List<Ticket> ingressos = new ArrayList<>();
        for (TicketRequest ticketRequest : eventRequest.getIngressos()) {
            TicketType type = ticketRequest.getTicketType();
            TicketPriceStrategy strategy = getStrategyByType(type);
            ingressos.add(new Ticket(type, strategy));
        }

        // Associar os ingressos ao evento
        event.setIngressos(ingressos);

        // Salvar o evento (sem salvar ingressos no banco)
        eventRepository.save(event);

        // Converter Event para EventResponse e calcular o preço total
        return new EventResponse(event);
    }

    private TicketPriceStrategy getStrategyByType(TicketType type) {
        switch (type) {
            case CASAL20:
                return new Casal20Ticket();
            case OPENBAR:
                return new OpenBarTicket();
            case CODIGO:
                return new CodigoTicket();
            case CASAL20_ANIVERSARIANTE:
                return new Casal20AniversarianteTicket();
            case OPENBAR_PROMO:
                return new OpenBarPromocaoTicket();
            case ANIVERSARIANTE:
                return new AniversarianteTicket();
            default:
                throw new IllegalArgumentException("Tipo de ingresso inválido: " + type);
        }
    }


    public EventResponse buscarEventoPorId(int id) {
        return eventRepository.findById(id)
                .map(EventResponse::new)
                .orElse(null);
    }

    public EventResponse atualizarEvento(int id, EventRequest eventoAtualizado) {
        if (!eventRepository.existsById(id)) {
            return null;
        }

        Event evento = eventRepository.findById(id).orElseThrow();
        evento.setNome(eventoAtualizado.getNome());

        return new EventResponse(eventRepository.save(evento));
    }

    public boolean deletarEvento(int id) {
        if (!eventRepository.existsById(id)) {
            return false;
        }

        eventRepository.deleteById(id);
        return true;
    }
}
