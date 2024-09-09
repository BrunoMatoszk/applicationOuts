package clube.outs.application.dto;

import clube.outs.application.entity.Usuario;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class UsuarioResponse {
    private Integer id;
    private String nome;
    private String email;
    private String cpf;
    private LocalDate dataNascimento;

    public UsuarioResponse(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.cpf = usuario.getCpf();
        this.dataNascimento = usuario.getDataNascimento();
    }

}
