package clube.outs.application.service;

import clube.outs.application.dto.UsuarioRequest;
import clube.outs.application.dto.UsuarioResponse;
import clube.outs.application.dto.ViaCepResponse;
import clube.outs.application.entity.Usuario;
import clube.outs.application.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Usuario salvarEnderecoNoUsuario(Integer usuarioId, String cep) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        ViaCepResponse response = viaCepService.obterEnderecoPorCep(cep);
        usuario.setLogradouro(response.getLogradouro());
        usuario.setBairro(response.getBairro());
        usuario.setLocalidade(response.getLocalidade());
        usuario.setUf(response.getUf());

        return usuarioRepository.save(usuario);
    }


    public List<Usuario> listarUsuariosOrdenadosPorUf() {
        return usuarioRepository.findAllByOrderByUfAsc();
    }

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

        // Chama a API ViaCEP para buscar o endereço baseado no CEP
        ViaCepResponse response = viaCepService.obterEnderecoPorCep(cep);
        usuario.setLogradouro(response.getLogradouro());
        usuario.setBairro(response.getBairro());
        usuario.setLocalidade(response.getLocalidade());
        usuario.setUf(response.getUf());

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
