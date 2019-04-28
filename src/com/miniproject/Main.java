package com.miniproject;

import elements.Camera;
import geometries.*;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Home Work 3.
 * Written by:
 * Elisha Mayer ,319185997 ,elisja.mayer@gmail.com .
 * Menachem Natanson , 207134859, menachem.natanson@gmail.com
 */
public class Main {

    public static void main(String[] args) {
        ImageWriter image = new ImageWriter("Img_0001",499,499,499,499);
        Camera cam = new Camera(Point3D.ZERO,new Vector(0,1,0),new Vector(0,0,-1));
        Geometries geo = new Geometries(new Tube(54.96,new Ray(new Point3D(0,-100,-200),new Vector(1,1,-0.6))));
        geo.add(new Cylinder(54.96,new Ray(new Point3D(-10,90,-200),new Vector(0,1,0)),100));
        geo.add(new Sphere(50,new Point3D(-80,300,-200)));
        geo.add(new Triangle(new Point3D(50,-50,-101),new Point3D(200,0,-101),new Point3D(0,-200,-101)));

        for(int x = 0;x<499;x++){
            for(int y=0;y<499;y++){
                Ray ray = cam.constructRayThroughPixel(499,499,x,y,100,499,499);
                if(geo.findIntersections(ray).size()!=0)
                    image.writePixel(x,y,255,255,255);
                else if(isGrid(x,y,50,50))
                    image.writePixel(x,y,255,0,0);
                else
                    image.writePixel(x,y,0,0,0);

            }
        }
        image.writeToimage();
    }

    public static Boolean isGrid(int x, int y,int columnWidth ,int rowWidth){
        return (x+1)%columnWidth == 0 || (y+1)%rowWidth == 0;

    }

    public static Point3D closes(List<Point3D> list,Point3D p){
        if(list.size()==0)
            return null;
        if(list.size()==1)
            return list.get(0);
        Point3D pToReturn = list.get(0);
        for (Point3D point:list) {
            if(p.distance2(point)<p.distance2(pToReturn))
                pToReturn = point;
        }
        return pToReturn;
    }
}
