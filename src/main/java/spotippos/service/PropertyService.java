package spotippos.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import spotippos.model.Property;
import spotippos.model.Province;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.Validator;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service Bean respons&aacute;vel por carregar e fornecer informa&ccedil;&otilde;es sobre Propriedades.
 *
 * @author Marcio Bernardo.
 */
@Validated
@Service
public class PropertyService {
    @Value("${properties.file}")
    private String propertiesFile;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private Validator validator;

    private int currentId = 0;

    private Map<Integer, Property> properties;

    @PostConstruct
    public void init() throws IOException {
        loadData(new FileInputStream(propertiesFile));
    }

    /**
     * Carrega o json com as propriedades e mant&eacute;m o controle de Id atualizado.
     *
     * @param file arquivo contendo as propriedades.
     * @throws IOException caso n&atilde;o seja poss&iacute;vel carregar o arquivo.
     */
    synchronized void loadData(InputStream file) throws IOException {
        properties = new HashMap<>();
        ObjectMapper jsonMapper = new ObjectMapper();
        JsonProperties jsonProps = jsonMapper.readValue(file, JsonProperties.class);
        for (Property p : jsonProps.getProperties()) {
            p.setProvinces(getProvincesNames(provinceService.getProvinces(p.getX(), p.getY())));
            properties.put(p.getId(), p);
            updateNextId(p.getId());
        }
    }

    /**
     * Adiciona uma nova prov&iacute;ncia na lista.
     *
     * @param property Propriedade a ser inserida.
     * @return propriedade com o Id gerado.
     */
    public synchronized Property add(@Valid Property property) {
        property.setId(getNextId());
        property.setProvinces(getProvincesNames(provinceService.getProvinces(property.getX(), property.getY())));
        properties.put(property.getId(), new Property(property));

        return property;
    }

    /**
     * Busca uma propriedade a partir de seu Id.
     *
     * @param id id da propriedade.
     * @return Propriedade com o id especificado ou null caso não exista.
     */
    public Property findById(int id) {
        return properties.get(id);
    }

    public List<Property> findByArea(int ax, int ay, int bx, int by) {
        if (ax < 0 || ay < 0 || bx < 0 || by < 0)
            return null;
        return properties.values().stream()
                .filter((p) -> p.getX() >= ax && p.getX() <= bx && p.getY() <= ay && p.getY() >= by)
                .collect(Collectors.toList());
    }

    /**
     * Extrai o nome das prov&iacute;ncias da lista de prov&iacute;ncias.
     * @param provinces Lista de prov&iacute;ncias.
     * @return Lista de nomes.
     */
    private List<String> getProvincesNames(List<Province> provinces) {
        List<String> provincesNames = new ArrayList<>();
        for (Province v : provinces) {
            provincesNames.add(v.getName());
        }
        return provincesNames;
    }
    /**
     * Atualiza o id caso o informado no par&acirc;metro seja maior que o atual.
     *
     * @param id id a ser verificado e atualizado.
     */
    synchronized void updateNextId(int id) {
        if (id > currentId) {
            currentId = id;
        }
    }

    /**
     * Retorna e incrementa o pr&oacute;ximo Id a ser utilizado.
     *
     * @return pr&oacute;ximo Id a ser utilizado.
     */
    synchronized int getNextId() {
        return ++currentId;
    }

    /**
     * Retorna o &uacute;ltimo Id utilizado.

     * @return valor de currentId.
     */
    int getCurrentId() {
        return currentId;
    }

    /**
     * Getter da lista completa de propriedades.
     *
     * @return Set n&atilde;o modific&aacute;vel de propriedades.
     */
    public List<Property> getProperties() {
        return Collections.unmodifiableList(new ArrayList<>(properties.values()));
    }
}
