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
    public  static int width = 1920;
    public static int height = 1080;
    public static double zoom=0.75;
    public static Point3D startP = new Point3D(0,0,0);

    public static void main(String[] args) {
        ImageWriter image = new ImageWriter("images\\IMG_0001",width,height,width,height);
        Camera cam = new Camera(startP,new Vector(0,1,0),new Vector(0,0,-1));
        Geometries geo = new Geometries(new Tube(54.96,new Ray(new Point3D(0,-150,-200),new Vector(1,1,-1.5))));
        geo.add(new Cylinder(54.96,new Ray(new Point3D(-10,90,-240),new Vector(0.2,1,-0.4)),100));
        geo.add(new Sphere(60,new Point3D(120,0,-240)));
        geo.add(new Triangle(new Point3D(-50,0,-150),new Point3D(-150,0,-150),new Point3D(-50,-100,-150)));

        double b=0,s=0;
        for(int x = 0;x<width;x++){
            for(int y=0;y<height;y++){
                Ray ray = cam.constructRayThroughPixel(width,height,x,y,150,width-width*zoom,height-height*zoom);
                if(geo.findIntersections(ray).size()!=0) {
                    double dis = closes(geo.findIntersections(ray),startP).point.distance(startP);
                    //int c = (int)( (dis/255));
                  //  int c = (int) (255*30/dis*4.5);
                    dis/=1.3;
                    if(dis>b)
                        b=dis;
                    if(dis<s)
                        s=dis;
                    if(dis>255)
                       dis=255;
                    if(dis<0)
                        dis=0;
                    int c= (int) dis;
                    c=255-c;
                    image.writePixel(x, y, 240,c,c);
                }
                else if(isGrid(x,y,100,100))
                    image.writePixel(x,y,0,255,0);
                else
                    image.writePixel(x,y,0,0,0);

            }
        }
        image.writeToimage();
    }

    public static Boolean isGrid(int x, int y,int columnWidth ,int rowWidth){
        return (x+1)%columnWidth == 0 || (y+1)%rowWidth == 0;

    }

    public static Intersectable.GeoPoint closes(List<Intersectable.GeoPoint> list, Point3D p){
        if(list.size()==0)
            return null;
        if(list.size()==1)
            return list.get(0);
        Intersectable.GeoPoint pToReturn = list.get(0);
        for (Intersectable.GeoPoint point:list) {
            if(p.distance2(point.point)<p.distance2(pToReturn.point))
                pToReturn = point;
        }
        return pToReturn;
    }
}
