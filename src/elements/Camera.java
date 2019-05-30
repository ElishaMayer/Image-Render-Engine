package elements;

import primitives.*;

/**
 * A Camera
 */
public class Camera {
    private Point3D _p0;
    private Vector _vUp;
    private Vector _vRight;
    private Vector _vTo;
    /* ********* Constructors ***********/
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
    /* ************* Getters/Setters *******/

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

    /* ************** Admin *****************/

    @Override
    public String toString() {
        return "Camera{" +
                "P0=" + _p0 +
                ", VUp=" + _vUp +
                ", VR=" + _vRight +
                ", vTo=" + _vTo +
                '}';
    }
    /* ************* Operations ***************/

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
        //check if the values are ok
        if(screenDistance <=0)
            throw new IllegalArgumentException("Zero or negative distance");
        if(nX <= 0 && nY <= 0)
            throw new IllegalArgumentException("Zero or negative pixels");
        if(i >= nX || i<0)
            throw new IllegalArgumentException("i out of range");
        if(j >= nY || j<0)
            throw new IllegalArgumentException("j out of range");

        Point3D pointC = _p0.add(_vTo.scale(screenDistance));
        double xToMove = (i - (nX - 1.0)/2.0);
        double yToMove = (j - (nY - 1.0)/2.0);
        Point3D pointIJ = pointC;

        //find the point on the plane
        if(!Util.isZero(xToMove))
            pointIJ = pointIJ.add(_vRight.scale(xToMove*screenWidth/nX));
        if(!Util.isZero(yToMove))
            pointIJ = pointIJ.add(_vUp.scale(-1*yToMove*screenHeight/nY));

        //create ray and return
        Vector vectorIJ = pointIJ.subtract(_p0);
        return  new Ray(pointIJ,vectorIJ);
    }

    /**
     * rotate the camera
     * @param x radians on the x
     * @param y radians on the y
     * @param z radians on the z
     */
    public void rotateXYZ(double x,double y,double z){
        Coordinate cos = new Coordinate(Math.cos(x));
        Coordinate sin = new Coordinate(Math.sin(x));
        Coordinate msin = new Coordinate(-Math.sin(x));
        Coordinate zero = new Coordinate(0);
        Coordinate one = new Coordinate(1);
        Coordinate[][] matrixX = {
                {cos, msin, zero},
                {sin, cos, zero},
                {zero, zero, one}};
        cos = new Coordinate(Math.cos(y));
        sin = new Coordinate(Math.sin(y));
        msin = new Coordinate(-Math.sin(y));
        Coordinate[][] matrixY = {
                {cos, zero, sin},
                {zero, one, zero},
                {msin, zero, cos}};
        cos = new Coordinate(Math.cos(z));
        sin = new Coordinate(Math.sin(z));
        msin = new Coordinate(-Math.sin(z));
        Coordinate[][] matrixZ = {
                {one, zero, zero},
                {zero, cos, msin},
                {zero, sin, cos}};
        Coordinate[][] matrix = multMatrixs(matrixX, matrixY);
        matrix = multMatrixs(matrix,matrixZ);
        _vRight = _vRight.multMatrixVector(matrix).normal();
        _vTo = _vTo.multMatrixVector(matrix).normal();
        _vUp = _vUp.multMatrixVector(matrix).normal();
    }

    /**
     * multiply two matrix's
     * @param matrix1 matrix 1
     * @param matrix2 matrix 2
     * @return [matrix1]x[matrix2]
     */
    private Coordinate[][] multMatrixs(Coordinate[][] matrix1,Coordinate[][] matrix2){
        Coordinate[][] matrix = new Coordinate[3][3];
        for(int i =0 ;i<3;i++) {
            for (int j = 0; j < 3; j++) {
                matrix[i][j] = new Coordinate(0);
            }
        }
        for(int i =0 ;i<3;i++){
            for(int j=0;j<3;j++){
                for(int w=0;w<3;w++){
                    matrix[i][j] = matrix[i][j].add(matrix1[i][w].multiply(matrix2[w][j]));
                }
            }
        }
        return matrix;
    }

}

