package test;

import geometries.Tube;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;

public class TubeTest {

    /**
     * check tube normal,
     * test for {@link Tube#getNormal getNormal} method
     */
    @Test
    public void getNormal() {
        Tube tb = new Tube(1,new Ray(new Point3D(1, 0, 0), new Vector(0, 1, 0)));
        assertEquals(new Vector(-1,0,0).normal(),tb.getNormal(new Point3D(0,3,0)));
        assertEquals(new Vector(1,0,0).normal(),tb.getNormal(new Point3D(2,3,0)));
        assertEquals(new Vector(0,0,1).normal(),tb.getNormal(new Point3D(1,-1,1)));
        assertEquals(new Vector(0,0,-1).normal(),tb.getNormal(new Point3D(1,0,-1)));
    }

    /**
     * check 0 radius,
     * test for {@link Tube#Tube Tube} constructor
     */
    @Test(expected = IllegalArgumentException.class)
    public void radiusZeroExc(){
        Tube tb = new Tube(0,new Ray(new Point3D(1, 2, 3), new Vector(10, 10, 10)));
    }

    /**
     * check negative radius,
     * test for {@link Tube#Tube Tube} constructor
     */
    @Test(expected = IllegalArgumentException.class)
    public void radiusNegativeExc(){
        Tube tb = new Tube(-1,new Ray(new Point3D(1, 2, 3), new Vector(10, 10, 10)));
    }
}