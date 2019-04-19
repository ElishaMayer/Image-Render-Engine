package test;

import elements.Camera;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;

public class CameraTest {

    @Test
    public void constructRayThroughPixel() {
        Camera cam = new Camera(new Point3D(0,0,0),new Vector(0,1,0),new Vector(0,0,-1));
        Ray r1 = new Ray(new Point3D(0,0,0),new Vector(-3,3,-1));
        Ray r2 = new Ray(new Point3D(0,0,0),new Vector(0,3,-1));
        Ray r3 = new Ray(new Point3D(0,0,0),new Vector(3,3,-1));
        Ray r4 = new Ray(new Point3D(0,0,0),new Vector(-3,0,-1));
        Ray r5 = new Ray(new Point3D(0,0,0),new Vector(0,0,-1));
        Ray r6 = new Ray(new Point3D(0,0,0),new Vector(3,0,-1));
        Ray r7 = new Ray(new Point3D(0,0,0),new Vector(-3,-3,-1));
        Ray r8 = new Ray(new Point3D(0,0,0),new Vector(0,-3,-1));
        Ray r9 = new Ray(new Point3D(0,0,0),new Vector(3,-3,-1));



        assertEquals(r1,cam.constructRayThroughPixel(3,3,0,0,1,9,9));
        assertEquals(r2,cam.constructRayThroughPixel(3,3,1,0,1,9,9));
        assertEquals(r3,cam.constructRayThroughPixel(3,3,2,0,1,9,9));
        assertEquals(r4,cam.constructRayThroughPixel(3,3,0,1,1,9,9));
        assertEquals(r5,cam.constructRayThroughPixel(3,3,1,1,1,9,9));
        assertEquals(r6,cam.constructRayThroughPixel(3,3,2,1,1,9,9));
        assertEquals(r7,cam.constructRayThroughPixel(3,3,0,2,1,9,9));
        assertEquals(r8,cam.constructRayThroughPixel(3,3,1,2,1,9,9));
        assertEquals(r9,cam.constructRayThroughPixel(3,3,2,2,1,9,9));


    }

    @Test
    public void ctor(){
        Camera cam = new Camera(new Point3D(0,0,0),new Vector(1,0,0),new Vector(0,0,-1));
        assertEquals("Check the third vector",cam.getVRight(),new Vector(0,-1,0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void ctorExc(){
        Camera cam = new Camera(new Point3D(0,0,0),new Vector(1,1,1),new Vector(0,0,-1));
    }
}