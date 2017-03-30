package spotippos.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import spotippos.model.Boundary;
import spotippos.model.Province;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Classe de teste da classe {@link ProvinceService}
 *
 * @author Marcio Bernardo.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProvinceServiceTest {

    @Autowired
    private ProvinceService provinceService;

    @Before
    public void setup() throws IOException {
        String provincesJson = "{ \"Gode\" : { \"boundaries\" : { " +
                "\"upperLeft\": {\"x\": 0, \"y\": 1000 }, \"bottomRight\": { \"x\": 600, \"y\": 500 } } }, " +
                "\"Ruja\": { \"boundaries\" : { " +
                "\"upperLeft\": {\"x\": 400, \"y\": 1000 }, \"bottomRight\": { \"x\": 1100, \"y\": 500 } } }," +
                "\"Nova\": { \"boundaries\": { " +
                "\"upperLeft\": {\"x\": 800, \"y\": 500 }, \"bottomRight\": {\"x\": 1400, \"y\": 0 } } } }";
        InputStream in = new ByteArrayInputStream(provincesJson.getBytes(StandardCharsets.UTF_8));
        provinceService.loadData(in);
    }

    @Test
    public void testLoadData() {
        Province p1 = new Province("Gode", new Boundary(0, 1000, 600, 500));
        Province p2 = new Province("Ruja", new Boundary(400, 1000, 1100, 500));
        Province p3 = new Province("Nova", new Boundary(800, 500, 1400, 0));

        List<Province> actual = provinceService.getProvinces();

        assertEquals(p1, actual.get(0));
        assertEquals(p2, actual.get(1));
        assertEquals(p3, actual.get(2));

        assertEquals(p1.getBoundaries(), actual.get(0).getBoundaries());
        assertEquals(p2.getBoundaries(), actual.get(1).getBoundaries());
        assertEquals(p3.getBoundaries(), actual.get(2).getBoundaries());
    }

    @Test
    public void testGetProvinces() {
        List<Province> result;
        result = provinceService.getProvinces(250, 750);
        assertEquals(1, result.size());
        assertEquals("Gode", result.get(0).getName());

        result = provinceService.getProvinces(750, 750);
        assertEquals(1, result.size());
        assertEquals("Ruja", result.get(0).getName());

        result = provinceService.getProvinces(500, 750);
        assertEquals(2, result.size());
        assertEquals("Gode", result.get(0).getName());
        assertEquals("Ruja", result.get(1).getName());

        result = provinceService.getProvinces(250, 250);
        assertEquals(0, result.size());
    }
}
