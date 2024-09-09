package clube.outs.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViaCepResponse {
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;
    private Boolean erro;

    public ViaCepResponse() {
    }

    public ViaCepResponse(ViaCepResponse viaCep) {
        this.logradouro = viaCep.getLogradouro();
        this.localidade = viaCep.getLocalidade();
        this.uf = viaCep.getUf();
        this.erro = viaCep.getErro();
    }
}
