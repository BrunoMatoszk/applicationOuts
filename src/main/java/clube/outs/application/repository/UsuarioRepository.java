package clube.outs.application.repository;

import clube.outs.application.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    List<Usuario> findByDataNascimentoGreaterThan(LocalDate data);

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByCpf(String cpf);

    List<Usuario> findAllByOrderByUfAsc();

}
