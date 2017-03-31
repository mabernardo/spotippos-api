package spotippos.model;

/**
 * POJO imut&aacute;vel das Provincias, contendo o nome e suas fronteiras.
 *
 * @author Marcio Bernardo
 */
public class Province {

    private final String name;
    private final Boundary boundaries;

    /**
     * Construtor com todos os atribuitos do objeto.
     * @param name nome da prov&iacute;ncia.
     * @param boundaries limites da prov&iacute;ncia.
     */
    public Province(String name, Boundary boundaries) {
        this.name = name;
        this.boundaries = boundaries;
    }

    /**
     * Getter do nome da prov&iacute;ncia
     * @return String nome da prov&iacute;ncia
     */
    public String getName() {
        return name;
    }

    /**
     * Getter dos limites da prov&iacute;ncia.
     * @return Boundary limites da prov&iacute;ncia.
     */
    public Boundary getBoundaries() {
        return boundaries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Province province = (Province) o;

        return name != null ? name.equals(province.name) : province.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
