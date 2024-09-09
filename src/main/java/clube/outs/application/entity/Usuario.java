package clube.outs.application.entity;

import clube.outs.application.dto.UsuarioRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String email;
    private String cpf;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;
    private String senha;
    private LocalDate dataNascimento;

    // Construtor padrão sem argumentos (necessário para o JPA)
    public Usuario() {
    }

    // Construtor com todos os parâmetros
    public Usuario(UsuarioRequest request) {
        this.nome = request.getNome();
        this.email = request.getEmail();
        this.cpf = request.getCpf();
        this.senha = request.getSenha();
        this.dataNascimento = request.getDataNascimento();
    }

}
