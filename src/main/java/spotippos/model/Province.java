package spotippos.model;

/**
 * POJO imutável das Provincias, contendo o nome e seus limites.
 *
 * @author Marcio Bernardo
 */
public class Province {

    private final String name;
    private final Boundary boundaries;

    /**
     * Construtor com todos os atribuitos do objeto.
     * @param name nome da província.
     * @param boundaries limites da província.
     */
    Province(String name, Boundary boundaries) {
        this.name = name;
        this.boundaries = boundaries;
    }

    /**
     * Getter do nome da província
     * @return String nome da província
     */
    String getName() {
        return name;
    }

    /**
     * Getter dos limites da província.
     * @return Boundary limites da província.
     */
    Boundary getBoundaries() {
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
