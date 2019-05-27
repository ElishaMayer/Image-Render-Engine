package primitives;

public class Material {
    private double _kD;
    private double _kS;
    private int _nShininess;

    /* ********* Constructors ***********/

    /**
     *  a new Material
     *
     * @param _kD
     * @param _kS
     * @param _nShininess
     */
    public Material(double _kD, double _kS, int _nShininess) {
        this._kD = _kD;
        this._kS = _kS;
        this._nShininess = _nShininess;
    }

    /**
     * default constructor
     */
    public Material(){
        _kD = 0;
        _kS = 0;
        _nShininess = 0;
    }

    /**
     * copy constructor
     *
     * @param material
     */
    public Material(Material material){
        _nShininess = material._nShininess;
        _kS = material._kS;
        _kD = material._kD;
    }

    /* ************* Getters/Setters *******/

    /**
     * get KD
     *
     * @return KD
     */
    public double getKD() {
        return _kD;
    }

    /**
     * get KS
     *
     * @return KS
     */
    public double getKS() {
        return _kS;
    }

    /**
     * get NShininess
     *
     * @return NShininess
     */
    public int getNShininess() {
        return _nShininess;
    }

    @Override
    public String toString() {
        return "M{" +
                "kD=" + _kD +
                ", kS=" + _kS +
                ", nSh=" + _nShininess +
                '}';
    }
}
