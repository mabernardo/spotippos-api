package spotippos.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Classe de test da classe {@link Property}.
 *
 * @author Marcio Bernardo.
 */
public class PropertyTest {

    @Test
    public void testProvince() {
        Property p1 = new Property(100, "Imóvel em Reykjavik", 750000, "Imóvel com vista para um vulcão ativo.",
                100, 1500, 3, 2, 220, new ArrayList<>(Arrays.asList(new String[] {"Kjosarysla", "Gullbringsysla"})));
        Property p2 = new Property(101, "Imóvel em Helsinki", 600000, "Próximo à Universidade de Helsinki",
                1000, 1200, 4, 3, 200, new ArrayList<>(Arrays.asList(new String[] {"Uusimaa"})));
        Property p3 = new Property(100, "Imóvel 3", 150000, "Imóvel 3",
                150, 500, 2, 1, 80, null);

        assertNotEquals(p1, p2);
        assertEquals(p1, p3);

        assertEquals((Integer) 100, p1.getId());
        assertEquals("Imóvel em Reykjavik", p1.getTitle());
        assertEquals((Integer) 750000, p1.getPrice());
        assertEquals("Imóvel com vista para um vulcão ativo.", p1.getDescription());
        assertEquals((Integer) 100, p1.getX());
        assertEquals((Integer) 1500, p1.getY());
        assertEquals((Integer) 3, p1.getBeds());
        assertEquals((Integer) 2, p1.getBaths());
        assertEquals((Integer) 220, p1.getSquareMeters());
        assertArrayEquals(new String[]{"Kjosarysla", "Gullbringsysla"}, p1.getProvinces().toArray());
    }
}
