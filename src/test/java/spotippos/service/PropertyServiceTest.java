package spotippos.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import spotippos.model.Property;

import javax.validation.ConstraintViolationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Classe de teste do service bean {@link PropertyService}
 *
 * @author Marcio Bernardo.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PropertyServiceTest {

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private PropertyService propertyService;

    @Before
    public void setup() throws IOException {
        String provincesJson = "{ \"Gode\" : { \"boundaries\" : { " +
                "\"upperLeft\": {\"x\": 0, \"y\": 1000 }, \"bottomRight\": { \"x\": 600, \"y\": 500 } } }, " +
                "\"Ruja\": { \"boundaries\" : { " +
                "\"upperLeft\": {\"x\": 400, \"y\": 1000 }, \"bottomRight\": { \"x\": 1100, \"y\": 500 } } }," +
                "\"Nova\": { \"boundaries\": { " +
                "\"upperLeft\": {\"x\": 800, \"y\": 500 }, \"bottomRight\": {\"x\": 1400, \"y\": 0 } } } }";

        String propertiesJson = "{\"totalProperties\": 8000, \"properties\": [ {" +
                "\"id\": 1, \"title\": \"Imóvel 1.\", \"price\": 643000, \"description\": \"Description 1.\"," +
                "\"lat\": 250, \"long\": 750, \"beds\": 3, \"baths\": 2, \"squareMeters\": 61 }, {" +
                "\"id\": 2, \"title\": \"Imóvel 2.\", \"price\": 949000, \"description\": \"Description 2.\"," +
                "\"lat\": 500, \"long\": 680, \"beds\": 4, \"baths\": 3, \"squareMeters\": 94 }, {" +
                "\"id\": 3, \"title\": \"Imóvel 3.\", \"price\": 1779000, \"description\": \"Description 3.\"," +
                "\"lat\": 1200, \"long\": 441, \"beds\": 5, \"baths\": 4, \"squareMeters\": 174 } ] }";

        InputStream provincesIn = new ByteArrayInputStream(provincesJson.getBytes(StandardCharsets.UTF_8));
        provinceService.loadData(provincesIn);

        InputStream propertiesIn = new ByteArrayInputStream(propertiesJson.getBytes(StandardCharsets.UTF_8));
        propertyService.loadData(propertiesIn);
    }

    @Test
    public void testLoadData() {
        Property p1 = new Property(1, "Imóvel 1.", 643000, "Description 1.",
                250, 750, 3, 2, 61, new ArrayList<>(Arrays.asList(new String[] {"Gode"})));
        Property p2 = new Property(2, "Imóvel 2.", 949000, "Description 2.",
                500, 680, 4, 3, 94, new ArrayList<>(Arrays.asList(new String[] {"Gode", "Ruja"})));
        Property p3 = new Property(3, "Imóvel 3.", 1779000, "Description 3.",
                1200, 441, 5, 4, 174, new ArrayList<>(Arrays.asList(new String[] {"Nova"})));

        List<Property> actual = propertyService.getProperties();
        assertTrue(actual.contains(p1));
        assertTrue(actual.contains(p2));
        assertTrue(actual.contains(p3));

        Property p4 = actual.get(0);
        assertEquals((Integer) 1, p4.getId());
        assertEquals("Imóvel 1.", p4.getTitle());
        assertEquals((Integer) 643000, p4.getPrice());
        assertEquals("Description 1.", p4.getDescription());
        assertEquals((Integer) 250, p4.getX());
        assertEquals((Integer) 750, p4.getY());
        assertEquals((Integer) 3, p4.getBeds());
        assertEquals((Integer) 2, p4.getBaths());
        assertEquals((Integer) 61, p4.getSquareMeters());
        assertArrayEquals(new String[] {"Gode"}, p4.getProvinces().toArray());
    }

    @Test
    public void testIdManipulation() {
        int currentId = propertyService.getCurrentId();
        int nextId = propertyService.getNextId();

        assertTrue(currentId > 0);
        assertNotEquals(nextId, currentId);
        assertTrue(currentId + 1 == nextId);

        propertyService.updateNextId(9999);
        assertEquals((Integer) 9999, (Integer) propertyService.getCurrentId());

        propertyService.updateNextId(1);
        assertEquals((Integer) 9999, (Integer) propertyService.getCurrentId());
    }

    @Test(expected = ConstraintViolationException.class)
    public void testAddInvalid() {
        Property p1 = new Property(0, "Imóvel X.", 643000, "Description 1.",
                2250, 750, 3, 2, 61, new ArrayList<>(Arrays.asList(new String[] {"Gode"})));

        propertyService.add(p1);
    }

    @Test
    public void testAddValid() {
        Property p1 = new Property(0, "Imóvel X.", 643000, "Description 1.",
                1250, 750, 3, 2, 61, new ArrayList<>(Arrays.asList(new String[] {"Gode"})));
        p1 = propertyService.add(p1);
        assertNotNull(p1);
        assertEquals((Integer) propertyService.getCurrentId(), p1.getId());
        assertEquals(p1, propertyService.findById(p1.getId()));
    }

    @Test
    public void testFindById() {
        Property p1 = new Property(1, "Imóvel 1.", 643000, "Description 1.",
                250, 750, 3, 2, 61, new ArrayList<>(Arrays.asList(new String[] {"Gode"})));

        assertEquals(p1, propertyService.findById(1));
        assertNull(propertyService.findById(999));
    }

    @Test
    public void testFindByArea() {
        assertEquals(0, propertyService.findByArea(0, 500, 250, 0).size());
        assertEquals(1, propertyService.findByArea(0, 1000, 400, 500).size());
        assertEquals(3, propertyService.findByArea(0, 1000, 1400, 0).size());
    }
}
