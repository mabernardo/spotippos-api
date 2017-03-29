package spotippos.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Classe de teste da classe {@link Boundary}.
 *
 * @author Marcio Bernardo.
 */
public class BoundaryTest {

    @Test
    public void testBoundary() {
        Boundary b1 = new Boundary(1,2,10,20);
        Boundary b2 = new Boundary(1,2,10,20);
        Boundary b3 = new Boundary(1000,1000,2000,2000);

        assertEquals(b1, b2);
        assertNotEquals(b1, b3);
        assertEquals(1, b1.upperLeft.x);
        assertEquals(2, b1.upperLeft.y);
        assertEquals(10, b1.bottomRight.x);
        assertEquals(20, b1.bottomRight.y);
    }
}
