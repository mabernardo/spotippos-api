package spotippos.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entidade das propriedades.
 *
 * @author Marcio Bernardo.
 */
public class Property {

    @NotNull
    private Integer id;

    @NotNull
    private String title;

    @NotNull
    private Integer price;

    @NotNull
    private String description;

    @NotNull
    @Size(max = 1400)
    @JsonProperty("lat")
    private Integer x;

    @NotNull
    @Size(max = 1000)
    @JsonProperty("long")
    private Integer y;

    @NotNull
    @Size(min = 1, max = 5)
    private Integer beds;

    @NotNull
    @Size(min = 1)
    private Integer baths;

    @NotNull
    @Size(min = 20, max = 240)
    private Integer squareMeters;

    private String[] provinces;

    /**
     * Construtor default. Necessário para o ObjectMapper.
     */
    public Property() { }

    /**
     * Construtor inicializando todos os membros da classe.
     * @param id id da propriedade.
     * @param title nome da propriedade.
     * @param price preco da propriedade.
     * @param description descrição da propriedade.
     * @param x coordenada x da propriedade.
     * @param y coordenada y da propriedade.
     * @param beds número de quartos.
     * @param baths número de banheiros.
     * @param squareMeters área em m2 da propriedade.
     */
    public Property(Integer id, String title, Integer price, String description, Integer x, Integer y, Integer beds,
                    Integer baths, Integer squareMeters, String[] provinces) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.x = x;
        this.y = y;
        this.beds = beds;
        this.baths = baths;
        this.squareMeters = squareMeters;
        this.provinces = provinces;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getBeds() {
        return beds;
    }

    public void setBeds(Integer beds) {
        this.beds = beds;
    }

    public Integer getBaths() {
        return baths;
    }

    public void setBaths(Integer baths) {
        this.baths = baths;
    }

    public Integer getSquareMeters() {
        return squareMeters;
    }

    public void setSquareMeters(Integer squareMeters) {
        this.squareMeters = squareMeters;
    }

    public String[] getProvinces() {
        return provinces;
    }

    public void setProvinces(String[] provinces) {
        this.provinces = provinces;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Property property = (Property) o;

        return id != null ? id.equals(property.id) : property.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
