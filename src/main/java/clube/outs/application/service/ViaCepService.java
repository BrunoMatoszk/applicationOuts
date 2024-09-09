package clube.outs.application.service;

import clube.outs.application.dto.ViaCepResponse;
import clube.outs.application.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ViaCepService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public ViaCepResponse obterEnderecoPorCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        RestTemplate restTemplate = new RestTemplate();
        ViaCepResponse response = restTemplate.getForObject(url, ViaCepResponse.class);

        if (response == null || response.getErro() != null) {
            throw new IllegalArgumentException("CEP inválido");
        }

        return response;
    }
}
