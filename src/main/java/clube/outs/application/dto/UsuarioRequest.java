package clube.outs.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UsuarioRequest {
    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private String cep;
    private LocalDate dataNascimento;

}
