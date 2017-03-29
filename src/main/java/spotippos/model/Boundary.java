package spotippos.model;

import java.awt.Point;

/**
 * Classe que define as fronteiras de uma província.
 *
 * @author Marcio Bernardo
 */
public class Boundary {
    public final Point upperLeft;
    public final Point bottomRight;

    /**
     * Construtor com as coordenadas da fronteira.
     *
     * @param x1 coordenada x do ponto superior esquerdo.
     * @param y1 coordenada y do ponto superior esquerdo.
     * @param x2 coordenada x do ponto inferior direito.
     * @param y2 coordenada y do ponto inferior direito.
     */
    public Boundary(int x1, int y1, int x2, int y2) {
        upperLeft = new Point(x1, y1);
        bottomRight = new Point(x2, y2);
    }

    /**
     * Construtor com os pontos superior esquerdo e inferior direito.
     *
     * @param upperLeft ponto superior esquerdo.
     * @param bottomRight ponto inferior direito.
     */
    public Boundary(Point upperLeft, Point bottomRight) {
        this.upperLeft = upperLeft;
        this.bottomRight = bottomRight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Boundary boundary = (Boundary) o;

        return (upperLeft != null ? upperLeft.equals(boundary.upperLeft) : boundary.upperLeft == null) &&
               (bottomRight != null ? bottomRight.equals(boundary.bottomRight) : boundary.bottomRight == null);
    }

    @Override
    public int hashCode() {
        int result = upperLeft != null ? upperLeft.hashCode() : 0;
        result = 31 * result + (bottomRight != null ? bottomRight.hashCode() : 0);
        return result;
    }
}
