package spotippos.rest;

import java.util.List;

/**
 * Template de retorno de buscas lista de resultados.
 *
 * @author Marcio Bernardo.
 */
public class RestListResponse<T> {
    final private int foundProperties;
    final private List<T> properties;

    RestListResponse(List<T> properties) {
        this.foundProperties = properties.size();
        this.properties = properties;
    }

    public int getFoundProperties() {
        return foundProperties;
    }

    public List<T> getProperties() {
        return properties;
    }
}
