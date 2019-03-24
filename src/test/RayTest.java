package test;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class RayTest {

    @org.junit.Test
    public void add() {
        Ray ray = new Ray(new Point3D(1,2,3), new Vector(1,1,1));
        Vector vec = new Vector(3,-2,5);
        assertEquals(new Ray(new Point3D(1,2,3), new Vector(4,-1,6)),ray.add(vec));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addExc(){
        Ray ray = new Ray(new Point3D(1,2,3), new Vector(1,1,1));
        Vector vec = new Vector(-1,-1,-1);
        ray.add(vec);
    }

    @Test
    public void subtract() {
        Ray ray = new Ray(new Point3D(1,2,3), new Vector(1,1,1));
        Vector vec = new Vector(3,-2,5);
        assertEquals(new Ray(new Point3D(1,2,3), new Vector(-2,3,-4)),ray.subtract(vec));
    }

    @Test(expected = IllegalArgumentException.class)
    public void subtractExc(){
        Ray ray = new Ray(new Point3D(1,2,3), new Vector(1,1,1));
        Vector vec = new Vector(1,1,1);
        ray.subtract(vec);
    }

    @Test
    public void scale() {
        Ray ray = new Ray(new Point3D(1,2,3), new Vector(1,1,1));
        assertEquals(new Ray(new Point3D(1,2,3), new Vector(2,2,2)),ray.scale(2));
        assertEquals(new Ray(new Point3D(1,2,3), new Vector(-2,-2,-2)),ray.scale(-2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void scaleExc(){
        Ray ray = new Ray(new Point3D(1,2,3), new Vector(1,1,1));
        ray.scale(0);
    }
}
