package spotippos.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Classe de teste da classe {@link Province}.
 *
 * @author Marcio Bernardo.
 */
public class ProvinceTest {

    @Test
    public void testProvince() {
        Province p1 = new Province("British Columbia", new Boundary(1, 1, 100, 100));
        Province p2 = new Province("Quebec", new Boundary(101, 101, 200, 200));
        Province p3 = new Province("Quebec", new Boundary(111, 111, 222, 222));

        assertNotEquals(p1, p2);
        assertEquals(p2, p3);
        assertEquals("British Columbia", p1.getName());
        assertEquals(new Boundary(1, 1, 100, 100), p1.getBoundaries());
        assertNotEquals(p1.getBoundaries(), p2.getBoundaries());
    }
}
