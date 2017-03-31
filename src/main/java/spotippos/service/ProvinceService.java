package spotippos.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import spotippos.model.Boundary;
import spotippos.model.Province;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Classe respons&aacute;vel por carregar e fornecer informa&ccedil;&otilde;es sobre as Provincias.
 *
 * @author Marcio Bernardo
 */
@Service
public class ProvinceService {

    @Value("${provinces.file}")
    private String provincesFile;

    private List<Province> provinces;

    /**
     * Inicializa o service bean de prov&iacute;ncias, carregando-as do arquivo de dados.
     *
     * @throws IOException caso n&atilde;o seja poss&iacute;vel ler o arquivo de dados.
     */
    @PostConstruct
    public void init() throws IOException {
        loadData(new FileInputStream(provincesFile));
    }

    /**
     * Carrega o json com as prov&iacute;ncias.
     *
     * @param file arquivo contendo as prov&iacute;ncias.
     * @throws IOException caso n&atilde;o seja poss&iacute;vel carregar o arquivo.
     */
     synchronized void loadData(InputStream file) throws IOException {
        provinces = new ArrayList<>();
        ObjectMapper jsonMapper = new ObjectMapper();
        @SuppressWarnings("unchecked") Map<String, Map<String, Map<String, Map<String, Integer>>>> provincesMap =
                jsonMapper.readValue(file, Map.class);
        provincesMap.forEach( (k, v) -> {
            int x1 = v.get("boundaries").get("upperLeft").get("x");
            int y1 = v.get("boundaries").get("upperLeft").get("y");
            int x2 = v.get("boundaries").get("bottomRight").get("x");
            int y2 = v.get("boundaries").get("bottomRight").get("y");
            provinces.add(new Province(k, new Boundary(x1, y1, x2, y2)));
        } );
    }

    /**
     * Retorna a(s) prov&iacute;cia(s) existente em uma determinada coordenada.
     *
     * @param x latitude da prov&iacute;ncia.
     * @param y longitude da prov&iacute;ncia.
     * @return Lista da(s) prov&iacute;ncia(s) na coordenada informada.
     */
    public List<Province> getProvinces(int x, int y) {
        return provinces.stream()
                .filter((b) ->
                        x >= b.getBoundaries().upperLeft.x && x <= b.getBoundaries().bottomRight.x
                                && y <= b.getBoundaries().upperLeft.y && y >= b.getBoundaries().bottomRight.y
                )
                .collect(Collectors.toList());
    }

    /**
     * Getter da lista de prov&iacute;ncias.
     *
     * @return lista de prov&iacute;ncias
     */
    public List<Province> getProvinces() {
        return Collections.unmodifiableList(provinces);
    }
}
