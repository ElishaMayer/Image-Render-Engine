package test;

import geometries.Sphere;
import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;

public class SphereTest {

    /**
     * check sphere normal,
     * test for {@link Sphere#getNormal getNormal} method
     */
    @Test
    public void getNormal() {
        Sphere sp = new Sphere(Math.sqrt(3),new Point3D(1,1,1));
        assertEquals(new Vector(1,1,1).normal(),sp.getNormal(new Point3D(2,2,2)));
    }

    /**
     * check 0 radius,
     * test for {@link Sphere#Sphere Sphere} constructor
     */
    @Test(expected = IllegalArgumentException.class)
    public void radiusZeroExc(){
        Sphere sp = new Sphere(0,new Point3D(1,1,1));
    }

    /**
     * check negative radius,
     * test for {@link Sphere#Sphere Sphere} constructor
     */
    @Test(expected = IllegalArgumentException.class)
    public void radiusNegativeExc(){
        Sphere sp = new Sphere(-1,new Point3D(1,1,1));
    }
}