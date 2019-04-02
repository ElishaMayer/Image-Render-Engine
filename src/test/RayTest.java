package test;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class RayTest {

    //check that vector is normalized
    @org.junit.Test
    public void newRay() {
        Ray ray = new Ray(new Point3D(1, 2, 3), new Vector(10, 10, 10));
        double num = 10/Math.sqrt(300);
        Vector vec = new Vector(num, num, num);
        assertEquals(vec,ray.getVector());
    }

    //check that vector 0 is not accepted
    @Test(expected = IllegalArgumentException.class)
    public void vecZero(){
        Ray ray = new Ray(new Point3D(1,1,1),new Vector(0,0,0));
    }
}
