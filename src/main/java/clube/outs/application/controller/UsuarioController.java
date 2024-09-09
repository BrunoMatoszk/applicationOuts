package clube.outs.application.controller;

import clube.outs.application.dto.UsuarioRequest;
import clube.outs.application.dto.UsuarioResponse;
import clube.outs.application.entity.Usuario;
import clube.outs.application.service.UsuarioService;
import clube.outs.application.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ViaCepService viaCepService;


    @PostMapping("/{id}/endereco")
    public Usuario salvarEndereco(@PathVariable Integer id, @RequestParam String cep) {
        return usuarioService.salvarEnderecoNoUsuario(id, cep);
    }

    @GetMapping("/ordenados")
    public List<Usuario> listarUsuariosOrdenadosPorUf() {
        return usuarioService.listarUsuariosOrdenadosPorUf();
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listar() {
        List<UsuarioResponse> usuarios = usuarioService.listarUsuarios();

        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> cadastrar(@RequestBody UsuarioRequest novoUsuario, @RequestParam String cep) {
        try {
            UsuarioResponse usuarioCriado = usuarioService.cadastrarUsuario(novoUsuario, cep);
            return ResponseEntity.status(201).body(usuarioCriado);
        } catch (Exception e) {
            // Log o erro e retorne um erro interno do servidor
            System.err.println("Erro ao cadastrar usuário: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscaPorId(@PathVariable int id) {
        UsuarioResponse usuario = usuarioService.buscarUsuarioPorId(id);

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> atualizar(@PathVariable int id, @RequestBody UsuarioRequest usuarioAtualizado) {
        UsuarioResponse usuario = usuarioService.atualizarUsuario(id, usuarioAtualizado);

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        boolean deletado = usuarioService.deletarUsuario(id);

        if (!deletado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filtro-data")
    public ResponseEntity<List<UsuarioResponse>> porData(@RequestParam LocalDate dataNascimento) {
        List<UsuarioResponse> usuarios = usuarioService.buscarPorDataNascimento(dataNascimento);

        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(usuarios);
    }
}
