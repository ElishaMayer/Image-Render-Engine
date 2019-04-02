package test;

import geometries.Plane;
import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;

public class PlaneTest {

    //check plane normal
    @Test
    public void getNormal() {
        Plane plane = new Plane(new Point3D(2,0,0),new Point3D(0,2,0),new Point3D(0,0,0));
        assertEquals(new Vector(0,0,1),plane.getNormal(null));

        plane = new Plane(new Point3D(1,1,1),new Vector(1,2,3));
        assertEquals(new Vector(1,2,3).normal(),plane.getNormal(null));
    }

    //check invalid plane
    @Test(expected = IllegalArgumentException.class)
    public void InvalidPlane(){
        Plane plane = new Plane(new Point3D(0,2,0),new Point3D(2,0,0),new Point3D(2,0,0));
    }
}