package test;

import org.junit.Test;
import primitives.Vector;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VectorTest {

    @org.junit.Test
    public void add() {
        Vector vec1 = new Vector(1, 1, 1);
        Vector vec2 = new Vector(3, -2, 5);
        assertEquals(new Vector(4, -1, 6), vec1.add(vec2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addExc() {
        Vector vec1 = new Vector(1, 1, 1);
        Vector vec2 = new Vector(-1, -1, -1);
        vec1.add(vec2);
    }

    @Test
    public void subtract() {
        Vector vec1 = new Vector(1, 1, 1);
        Vector vec2 = new Vector(3, -2, 5);
        assertEquals(new Vector(-2, 3, -4), vec1.subtract(vec2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void subtractExc() {
        Vector vec1 = new Vector(1, 1, 1);
        Vector vec2 = new Vector(1, 1, 1);
        vec1.subtract(vec2);
    }

    @Test
    public void scale() {
        Vector vec1 = new Vector(1, 1, 1);
        assertEquals(new Vector(2, 2, 2), vec1.scale(2));
        assertEquals(new Vector(-2, -2, -2), vec1.scale(-2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void scaleExc() {
        Vector vec1 = new Vector(1, 1, 1);
        vec1.scale(0);
    }

    @org.junit.Test
    public void dot_product() {
        Vector vec1 = new Vector(1, 1, 1);
        Vector vec2 = new Vector(3, -2, 5);
        assertEquals(6, vec1.dot_product(vec2));
    }

    @org.junit.Test
    public void cross_product() {
        Vector vec1 = new Vector(1, 1, 1);
        Vector vec2 = new Vector(3, -2, 5);
        assertEquals(new Vector(7, -2, -5), vec1.cross_product(vec2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void cross_productExc() {
        Vector vec1 = new Vector(1, 1, 1);
        Vector vec2 = new Vector(2, 2, 2);
        vec1.cross_product(vec2);
    }

    @org.junit.Test
    public void length() {
        Vector vec1 = new Vector(0, -3, 4);
        Vector vec2 = new Vector(3, 0, -4);
        Vector vec3 = new Vector(-3, 4, 0);
        assertEquals(5, vec1.length());
        assertEquals(5, vec2.length());
        assertEquals(5, vec3.length());
    }

    @org.junit.Test
    public void length2() {
        Vector vec1 = new Vector(0, -3, 4);
        Vector vec2 = new Vector(3, 0, -4);
        Vector vec3 = new Vector(-3, 4, 0);
        assertEquals(25, vec1.length2());
        assertEquals(25, vec2.length2());
        assertEquals(25, vec3.length2());
    }

    @org.junit.Test
    public void normal() {
        Vector vec1 = new Vector(0, -3, 4);
        Vector vec2 = new Vector(3, 0, -4);
        Vector vec3 = new Vector(-3, 4, 0);
        assertEquals(new Vector(0, -3.0 / 5.0, 4.0 / 5.0), vec1.normal());
        assertEquals(new Vector(3.0 / 5.0, 0, -4.0 / 5.0), vec2.normal());
        assertEquals(new Vector(-3.0 / 5.0, 4.0 / 5.0, 0), vec3.normal());
    }
}