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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Classe responsável por carregar e fornecer informações sobre as Provincias.
 *
 * @author Marcio Bernardo
 */
@Service
public class ProvinceService {

    @Value("${provinces.file}")
    private String provincesFile;

    private List<Province> provinces;

    /**
     * Inicializa o service bean de províncias, carregando-as do arquivo de dados.
     *
     * @throws IOException caso não seja possível ler o arquivo de dados.
     */
    @PostConstruct
    public void init() throws IOException {
        loadData(new FileInputStream(provincesFile));
    }

     synchronized void loadData(InputStream file) throws IOException {
        provinces = new ArrayList<>();
        ObjectMapper jsonMapper = new ObjectMapper();
        @SuppressWarnings("unchecked") Map<String, Map<String, Map<String, Map<String, Integer>>>> provincesMap =
                jsonMapper.readValue(file, Map.class);
        provincesMap.forEach( (String k, Map<String, Map<String, Map<String, Integer>>> v) -> {
            int x1 = v.get("boundaries").get("upperLeft").get("x");
            int y1 = v.get("boundaries").get("upperLeft").get("y");
            int x2 = v.get("boundaries").get("bottomRight").get("x");
            int y2 = v.get("boundaries").get("bottomRight").get("y");
            provinces.add(new Province(k, new Boundary(x1, y1, x2, y2)));
        } );
    }

    /**
     * Retorna a(s) provícia(s) existente em uma determinada coordenada.
     *
     * @param x latitude da província.
     * @param y longitude da província.
     * @return Lista da(s) província(s) na coordenada informada.
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
     * Getter da lista de províncias.
     *
     * @return lista de províncias
     */
    public List<Province> getProvinces() {
        return provinces;
    }
}
