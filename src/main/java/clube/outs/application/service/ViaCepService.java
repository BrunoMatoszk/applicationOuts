package clube.outs.application.service;

import clube.outs.application.dto.Endereco;
import clube.outs.application.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ViaCepService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Endereco> buscarEnderecosPorLocalidade(String uf, String localidade, String logradouro) {
        RestTemplate restTemplate = new RestTemplate();

        String url = String.format("https://viacep.com.br/ws/%s/%s/%s/json/", uf, localidade, logradouro);

        Endereco[] enderecosArray = restTemplate.getForObject(url, Endereco[].class);

        return Arrays.asList(enderecosArray);
    }

    public void ordenarPorLogradouro(List<Endereco> enderecos) {
        for (int i = 0; i < enderecos.size() - 1; i++) {
            int indMenor = i;
            for (int j = i + 1; j < enderecos.size(); j++) {
                if (enderecos.get(j).getLogradouro().compareToIgnoreCase(enderecos.get(indMenor).getLogradouro()) < 0) {
                    indMenor = j;
                }
            }
            Endereco temp = enderecos.get(indMenor);
            enderecos.set(indMenor, enderecos.get(i));
            enderecos.set(i, temp);
        }
    }

}
