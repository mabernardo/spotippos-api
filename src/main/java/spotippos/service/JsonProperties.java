package spotippos.service;

import spotippos.model.Property;

import java.util.List;

/**
 * Classe auxiliar utilizada pelo ObjectMapper para parsear o arquivo json de propriedades.
 *
 * @author Marcio Bernardo.
 */
final class JsonProperties {
    JsonProperties() { }

    private int totalProperties;
    private List<Property> properties;

    public int getTotalProperties() {
        return totalProperties;
    }

    public void setTotalProperties(int totalProperties) {
        this.totalProperties = totalProperties;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }
}
