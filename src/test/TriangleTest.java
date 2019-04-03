package test;

import geometries.Plane;
import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;

public class TriangleTest {

    /**
     * check triangle normal,
     * test for {@link Triangle#getNormal getNormal} method
     */
    @Test
    public void getNormal() {
        Triangle tri = new Triangle(new Point3D(2,0,0),new Point3D(0,2,0),new Point3D(0,0,0));
        assertEquals(new Vector(0,0,1),tri.getNormal(null));
    }

}