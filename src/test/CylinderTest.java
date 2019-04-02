package test;

import geometries.Cylinder;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;

public class CylinderTest {

    //check Cylinder normal
    @Test
    public void getNormal() {
        Cylinder cy = new Cylinder(2,new Ray(new Point3D(2, 0, 0), new Vector(0, 1, 0)),10);
        assertEquals(new Vector(-1,0,0).normal(),cy.getNormal(new Point3D(0,3,0)));
        assertEquals(new Vector(0,1,0).normal(),cy.getNormal(new Point3D(1,5,0)));
        assertEquals(new Vector(0,-1,0).normal(),cy.getNormal(new Point3D(3,-5,0)));
    }

    //check 0 radius
    @Test(expected = IllegalArgumentException.class)
    public void radiusZeroExc(){
        Cylinder cy = new Cylinder(0,new Ray(new Point3D(1, 2, 3), new Vector(10, 10, 10)),10);
    }

    //check negative radius
    @Test(expected = IllegalArgumentException.class)
    public void radiusNegativeExc(){
        Cylinder cy = new Cylinder(-1,new Ray(new Point3D(1, 2, 3), new Vector(10, 10, 10)),10);
    }

    //check 0 height
    @Test(expected = IllegalArgumentException.class)
    public void heightZeroExc(){
        Cylinder cy = new Cylinder(5,new Ray(new Point3D(1, 2, 3), new Vector(10, 10, 10)),0);
    }

    //check negative height
    @Test(expected = IllegalArgumentException.class)
    public void heightNegativeExc(){
        Cylinder cy = new Cylinder(5,new Ray(new Point3D(1, 2, 3), new Vector(10, 10, 10)),-10);
    }
}