package test;

import org.junit.Test;
import primitives.Vector;

import static org.junit.Assert.*;

public class VectorTest {

    @org.junit.Test
    public void add() {

    }

    @Test
    public void subtract() {
    }

    @Test
    public void scale() {
        Vector vec1 = new Vector(1,1,1);
        assertEquals(new Vector(2,2,2),vec1.scale(2));
        assertEquals(new Vector(-2,-2,-2),vec1.scale(-2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void scaleExc(){
        Vector vec1 = new Vector(1,1,1);
        vec1.scale(0);

    }

    @org.junit.Test
    public void dot_product() {
    }

    @org.junit.Test
    public void cross_product() {
    }

    @org.junit.Test
    public void length() {
    }

    @org.junit.Test
    public void normal() {
    }
}