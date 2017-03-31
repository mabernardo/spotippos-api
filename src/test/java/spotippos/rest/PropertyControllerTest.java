package spotippos.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import spotippos.model.Property;
import spotippos.service.PropertyService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.endsWith;


/**
 * Classe de teste da Controller de Propriedades.
 *
 * @author Marcio Bernardo.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PropertyController.class)
public class PropertyControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PropertyService propertyService;

    @Test
    public void testFindById() throws Exception {
        Property p1 = new Property(666, "Imóvel X.", 643000, "Description 1.",
                1250, 750, 3, 2, 61, new ArrayList<>(Arrays.asList(new String[] {"Gode"})));

        given(this.propertyService.findById(666)).willReturn(p1);
        this.mvc.perform(get("/properties/666").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(666)))
                .andExpect(jsonPath("$.title", is("Imóvel X.")))
                .andExpect(jsonPath("$.price", is(643000)))
                .andExpect(jsonPath("$.description", is("Description 1.")))
                .andExpect(jsonPath("$.lat", is(1250)))
                .andExpect(jsonPath("$.long", is(750)))
                .andExpect(jsonPath("$.beds", is(3)))
                .andExpect(jsonPath("$.baths", is(2)))
                .andExpect(jsonPath("$.squareMeters", is(61)))
                .andExpect(jsonPath("$.provinces", hasSize(1)))
                .andExpect(jsonPath("$.provinces[0]", is("Gode")));

        // Teste 404
        given(this.propertyService.findById(9999)).willReturn(null);
        this.mvc.perform(get("/properties/9999").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFindByArea() throws Exception {
        Property p1 = new Property(2, "Imóvel 2.", 949000, "Description 2.",
                500, 680, 4, 3, 94, new ArrayList<>(Arrays.asList(new String[] {"Gode", "Ruja"})));
        Property p2 = new Property(3, "Imóvel 3.", 1779000, "Description 3.",
                1200, 441, 5, 4, 174, new ArrayList<>(Arrays.asList(new String[] {"Nova"})));
        List<Property> properties = new ArrayList<>();
        properties.add(p1);
        properties.add(p2);

        given(this.propertyService.findByArea(100,100,200,0)).willReturn(properties);
        this.mvc.perform(get("/properties?ax=100&ay=100&bx=200&by=0").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.foundProperties", is(2)))
                .andExpect(jsonPath("$.properties", hasSize(2)))
                .andExpect(jsonPath("$.properties[0].id", is(2)))
                .andExpect(jsonPath("$.properties[0].title", is("Imóvel 2.")))
                .andExpect(jsonPath("$.properties[0].price", is(949000)))
                .andExpect(jsonPath("$.properties[0].description", is("Description 2.")))
                .andExpect(jsonPath("$.properties[0].lat", is(500)))
                .andExpect(jsonPath("$.properties[0].long", is(680)))
                .andExpect(jsonPath("$.properties[0].beds", is(4)))
                .andExpect(jsonPath("$.properties[0].baths", is(3)))
                .andExpect(jsonPath("$.properties[0].squareMeters", is(94)))
                .andExpect(jsonPath("$.properties[0].provinces", hasSize(2)))
                .andExpect(jsonPath("$.properties[0].provinces[0]", is("Gode")))
                .andExpect(jsonPath("$.properties[0].provinces[1]", is("Ruja")))
                .andExpect(jsonPath("$.properties[1].id", is(3)))
                .andExpect(jsonPath("$.properties[1].title", is("Imóvel 3.")))
                .andExpect(jsonPath("$.properties[1].price", is(1779000)))
                .andExpect(jsonPath("$.properties[1].description", is("Description 3.")))
                .andExpect(jsonPath("$.properties[1].lat", is(1200)))
                .andExpect(jsonPath("$.properties[1].long", is(441)))
                .andExpect(jsonPath("$.properties[1].beds", is(5)))
                .andExpect(jsonPath("$.properties[1].baths", is(4)))
                .andExpect(jsonPath("$.properties[1].squareMeters", is(174)))
                .andExpect(jsonPath("$.properties[1].provinces", hasSize(1)))
                .andExpect(jsonPath("$.properties[1].provinces[0]", is("Nova")));

        // Nenhum encontrado.
        given(this.propertyService.findByArea(0,0,0,0)).willReturn(Collections.emptyList());
        this.mvc.perform(get("/properties?ax=0&ay=0&bx=0&by=0").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.foundProperties", is(0)))
                .andExpect(jsonPath("$.properties", hasSize(0)));
    }

    @Test
    public void testAddProperty() throws Exception {
        Property p1 = new Property(666, "Imóvel X.", 643000, "Description 1.",
                1250, 750, 3, 2, 61, new ArrayList<>(Arrays.asList(new String[] {"Gode"})));

        ObjectMapper jsonMapper = new ObjectMapper();
        String json = jsonMapper.writeValueAsString(p1);

        given(this.propertyService.add(p1)).willReturn(p1);
        this.mvc.perform(post("/properties").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(header().string("location", endsWith("/properties/666")))
                .andExpect(jsonPath("$.id", is(666)))
                .andExpect(jsonPath("$.title", is("Imóvel X.")))
                .andExpect(jsonPath("$.price", is(643000)))
                .andExpect(jsonPath("$.description", is("Description 1.")))
                .andExpect(jsonPath("$.lat", is(1250)))
                .andExpect(jsonPath("$.long", is(750)))
                .andExpect(jsonPath("$.beds", is(3)))
                .andExpect(jsonPath("$.baths", is(2)))
                .andExpect(jsonPath("$.squareMeters", is(61)))
                .andExpect(jsonPath("$.provinces", hasSize(1)))
                .andExpect(jsonPath("$.provinces[0]", is("Gode")));

        // Dados não válidos
        Property invalid = new Property(666, null, null, null, -1, 2000, 10, 20, 25, null);
        json = jsonMapper.writeValueAsString(invalid);

        given(this.propertyService.add(p1)).willReturn(p1);
        this.mvc.perform(post("/properties").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
