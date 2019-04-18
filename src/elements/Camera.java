package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

/**
 * A Camera
 */
public class Camera {
    Point3D _p0;
    Vector _vUp;
    Vector _vRight;
    Vector _vTo;
    /********** Constructors ***********/
    /**
     * A new Camera. vUp and vTo need to be orthogonal
     * @param p0 camera location
     * @param vUp vector up
     * @param vTo vector to
     */
    public Camera(Point3D p0, Vector vUp, Vector vTo) {
        if(!Util.isZero(vUp.dotProduct(vTo)))
            throw new IllegalArgumentException("Vectors are not orthogonal");
        _p0 = new Point3D(p0);
        _vUp = vUp.normal();
        _vTo = vTo.normal();
        _vRight = vTo.crossProduct(vUp).normal();
    }
    /************** Getters/Setters *******/

    /**
     * Get camera location
     * @return p0
     */
    public Point3D getP0() {
        return _p0;
    }

    /**
     * Get vector Up
     * @return vUP
     */
    public Vector getVUp() {
        return _vUp;
    }

    /**
     * Get vector right
     * @return vRight
     */
    public Vector getVRight() {
        return _vRight;
    }

    /**
     * Get vector to
     * @return vTo
     */
    public Vector getVTo() {
        return _vTo;
    }

    /*************** Admin *****************/

    @Override
    public String toString() {
        return "Camera{" +
                "P0=" + _p0 +
                ", VUp=" + _vUp +
                ", VR=" + _vRight +
                ", vTo=" + _vTo +
                '}';
    }
    /************** Operations ***************/

    /**
     * Get a ray that goes through pixel in plane
     * @param nX Number of pixels on X
     * @param nY Number of pixels on Y
     * @param i Pixel location on the X
     * @param j Pixel location on the Y
     * @param screenDistance Distance of the plane from camera
     * @param screenWidth Screen Width
     * @param screenHeight Screen Height
     * @return Ray that goes through pixel
     */
    public Ray constructRayThroughPixel(int nX,int nY,int i,int j ,double screenDistance ,double screenWidth , double screenHeight){
        Point3D pointC = _p0.add(_vTo.scale(screenDistance));
        Vector vecX = _vRight.scale((i - (nX - 1)/2)*screenWidth/nX);
        Vector vecY = _vUp.scale((j - (nY - 1)/2)*screenHeight/nY);
        Point3D pointIJ = pointC.add(vecX.subtract(vecY));
        Vector vectorIJ = pointIJ.subtract(pointC);
        return  new Ray(_p0,vectorIJ.normal());
    }
}
