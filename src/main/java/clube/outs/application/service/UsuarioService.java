package clube.outs.application.service;

import clube.outs.application.dto.Endereco;
import clube.outs.application.dto.UsuarioRequest;
import clube.outs.application.dto.UsuarioResponse;
import clube.outs.application.entity.Usuario;
import clube.outs.application.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ViaCepService viaCepService;

    public List<UsuarioResponse> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioResponse::new)
                .collect(Collectors.toList());
    }

    public UsuarioResponse cadastrarUsuario(UsuarioRequest novoUsuario, String cep) {
        // Verificar se o e-mail ou CPF já estão cadastrados
        Optional<Usuario> emailExistente = usuarioRepository.findByEmail(novoUsuario.getEmail());
        Optional<Usuario> cpfExistente = usuarioRepository.findByCpf(novoUsuario.getCpf());

        if (emailExistente.isPresent() || cpfExistente.isPresent()) {
            throw new IllegalArgumentException("Usuário com e-mail ou CPF já cadastrado.");
        }

        // Instanciar o objeto Usuario com os dados do DTO (exceto o endereço)
        Usuario usuario = new Usuario(novoUsuario);

        // Salvar o usuário no banco de dados e retornar a resposta
        return new UsuarioResponse(usuarioRepository.save(usuario));
    }


    public UsuarioResponse buscarUsuarioPorId(int id) {
        return usuarioRepository.findById(id)
                .map(UsuarioResponse::new)
                .orElse(null);
    }

    public UsuarioResponse atualizarUsuario(int id, UsuarioRequest usuarioAtualizado) {
        if (!usuarioRepository.existsById(id)) {
            return null;
        }

        Usuario usuario = new Usuario(usuarioAtualizado);
        usuario.setId(id);
        return new UsuarioResponse(usuarioRepository.save(usuario));
    }

    public boolean deletarUsuario(int id) {
        if (!usuarioRepository.existsById(id)) {
            return false;
        }

        usuarioRepository.deleteById(id);
        return true;
    }

    public List<UsuarioResponse> buscarPorDataNascimento(LocalDate dataNascimento) {
        return usuarioRepository.findByDataNascimentoGreaterThan(dataNascimento).stream()
                .map(UsuarioResponse::new)
                .collect(Collectors.toList());
    }
}
