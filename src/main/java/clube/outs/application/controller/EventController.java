package clube.outs.application.controller;

import clube.outs.application.dto.EventRequest;
import clube.outs.application.dto.EventResponse;
import clube.outs.application.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/eventos")
public class EventController {

    @Autowired
    private EventService eventoService;

    @GetMapping
    public ResponseEntity<List<EventResponse>> listar() {
        List<EventResponse> eventos = eventoService.listarEventos();

        if (eventos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(eventos);
    }

    @PostMapping
    public ResponseEntity<EventResponse> cadastrar(@RequestBody EventRequest novoEvento) {
        EventResponse eventoCriado = eventoService.criarEvento(novoEvento);
        return ResponseEntity.status(201).body(eventoCriado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> buscaPorId(@PathVariable int id) {
        EventResponse evento = eventoService.buscarEventoPorId(id);

        if (evento == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(evento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> atualizar(@PathVariable int id, @RequestBody EventRequest eventoAtualizado) {
        EventResponse evento = eventoService.atualizarEvento(id, eventoAtualizado);

        if (evento == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(evento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        boolean deletado = eventoService.deletarEvento(id);

        if (!deletado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
