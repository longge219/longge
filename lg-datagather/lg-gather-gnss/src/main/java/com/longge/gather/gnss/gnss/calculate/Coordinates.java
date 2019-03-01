package com.longge.gather.gnss.gnss.calculate;
import com.longge.gather.gnss.gnss.constant.GnssConstants;
import org.ejml.simple.SimpleMatrix;
/**
 * Description:坐标系统转换对象
 * User: jianglong
 * Date: 2018年7月11号
 */
public class Coordinates{
	
	//空间坐标--以地球为中心的，地球固定的（X，Y，Z）
	private SimpleMatrix ecef = null; 
	
	//大地坐标---经度（lam）、纬度（phi）、高度（h）
	private SimpleMatrix geod = null; 

	//本地系统坐标----需要指定原点局部坐标（东、北、上）
	private SimpleMatrix enu; 
	
	//观测时间
	private Time refTime = null;

	protected Coordinates(){
		ecef = new SimpleMatrix(3, 1);
		geod = new SimpleMatrix(3, 1);
		enu = new SimpleMatrix(3, 1);
	}
	
    /**获取空间坐标实例*/
	public static Coordinates globalXYZInstance(double x, double y, double z){
		Coordinates c = new Coordinates();
		c.setXYZ(x, y, z);
		return c;
	}
	
	/**根据大地坐标获取空间坐标*/
	public static Coordinates globalGeodInstance( double lat, double lon, double alt ){
		Coordinates c = new Coordinates();
		c.setGeod( lat, lon, alt);
		c.computeECEF();
		if( !c.isValidXYZ() ){
			throw new RuntimeException("无效的ECEF: " + c);
		}
		return c;
	}
	
	/**根据空间坐标获取本地坐标系统实例*/
	public static Coordinates globalENUInstance(SimpleMatrix ecef){
		Coordinates c = new Coordinates();
		c.enu = ecef.copy();
		return c;
	}

	/**大地坐标转空间坐标*/
	public void computeECEF() {
		final double  a = GnssConstants.WGS84_SEMI_MAJOR_AXIS;//长轴
		final double finv = 298.257223563d;
		double dphi = this.geod.get(1);
		double dlambda = this.geod.get(0);
		double h = this.geod.get(2);
		// 计算degree-to-radian因素
		double dtr = Math.PI/180;
		// 计算离心率平方
		double esq = (2-1/finv)/finv;
		double sinphi = Math.sin(dphi*dtr);
		// 在主垂线中计算曲率半径
		double N_phi = a/Math.sqrt(1-esq*sinphi*sinphi);
		// P是Z轴的距离
		double P = (N_phi + h)*Math.cos(dphi*dtr);
		double Z = (N_phi*(1-esq) + h) * sinphi;
		double X = P*Math.cos(dlambda*dtr);
		double Y = P*Math.sin(dlambda*dtr);
		this.ecef.set(0, 0, X );
		this.ecef.set(1, 0, Y );
		this.ecef.set(2, 0, Z );
	}

	/**获取大地坐标纬度*/
	public double getGeodeticLatitude(){
		if(this.geod==null) computeGeodetic();
		return this.geod.get(1);
	}
	/**获取大地坐标高程*/
	public double getGeodeticHeight(){
		if(this.geod==null) computeGeodetic();
		return this.geod.get(2);
	}
	
	/**获取大地坐标经度*/
	public double getGeodeticLongitude(){
		if(this.geod==null) computeGeodetic();
		return this.geod.get(0);
	}
	
    /**空间坐标转大地坐标*/
	public void computeGeodetic() {
		double X = this.ecef.get(0);
		double Y = this.ecef.get(1);
		double Z = this.ecef.get(2);
		double a = GnssConstants.WGS84_SEMI_MAJOR_AXIS;//长轴
		double e = GnssConstants.WGS84_ECCENTRICITY;//离心率
		//半径计算
		double r = Math.sqrt(Math.pow(X, 2) + Math.pow(Y, 2) + Math.pow(Z, 2));
		// 地心经度
		double lamGeoc = Math.atan2(Y, X);
		// 地心纬度
		double phiGeoc = Math.atan(Z / Math.sqrt(Math.pow(X, 2) + Math.pow(Y, 2)));
		// 大地坐标的计算
		double psi = Math.atan(Math.tan(phiGeoc) / Math.sqrt(1 - Math.pow(e, 2)));
		double phiGeod = Math.atan((r * Math.sin(phiGeoc) + Math.pow(e, 2) * a
									/ Math.sqrt(1 - Math.pow(e, 2)) * Math.pow(Math.sin(psi), 3))
									/ (r * Math.cos(phiGeoc) - Math.pow(e, 2) * a * Math.pow(Math.cos(psi), 3)));
		double lamGeod = lamGeoc;
		double N = a / Math.sqrt(1 - Math.pow(e, 2) * Math.pow(Math.sin(phiGeod), 2));
		double h = r * Math.cos(phiGeoc) / Math.cos(phiGeod) - N;
		this.geod.set(0, 0, Math.toDegrees(lamGeod));
		this.geod.set(1, 0, Math.toDegrees(phiGeod));
		this.geod.set(2, 0, h);
	}

	/**获取空间坐标X*/
	public double getX(){
		return ecef.get(0);
	}
	/**获取空间坐标Y*/
	public double getY(){
		return ecef.get(1);
	}
	/**获取空间坐标Z*/
	public double getZ(){
		return ecef.get(2);
	}

    /**计算本地系统*/
	public void computeLocal(Coordinates target) {
		if(this.geod==null) computeGeodetic();
		SimpleMatrix R = rotationMatrix(this);
		enu = R.mult(target.minusXYZ(this));
	}

	/**计算本地系统E*/
	public double getE(){
		return enu.get(0);
	}
	
	/**计算本地系统N*/
	public double getN(){
		return enu.get(1);
	}
	/**计算本地系统U*/
	public double getU(){
		return enu.get(2);
	}

	public void setENU(double e, double n, double u){
		this.enu.set(0, 0, e);
		this.enu.set(1, 0, n);
		this.enu.set(2, 0, u);
	}

	public void setXYZ(double x, double y, double z){
		this.ecef.set(0, 0, x);
		this.ecef.set(1, 0, y);
		this.ecef.set(2, 0, z);
	}
	public void setGeod( double lat, double lon, double alt ){
		this.geod.set(1, 0, lat);
		this.geod.set(0, 0, lon);
		this.geod.set(2, 0, alt);
	}
	public void setPlusXYZ(SimpleMatrix sm){
		this.ecef.set(ecef.plus(sm));
	}
	public void setSMMultXYZ(SimpleMatrix sm){
		this.ecef = sm.mult(this.ecef);
	}

	public SimpleMatrix minusXYZ(Coordinates coord){
		return this.ecef.minus(coord.ecef);
	}
	
	/**校验XYZ坐标*/
	public boolean isValidXYZ(){
		return (this.ecef != null && this.ecef.elementSum() != 0 
        && !Double.isNaN(this.ecef.get(0)) && !Double.isNaN(this.ecef.get(1)) && !Double.isNaN(this.ecef.get(2))
        && !Double.isInfinite(this.ecef.get(0)) && !Double.isInfinite(this.ecef.get(1)) && !Double.isInfinite(this.ecef.get(2))
        && ( ecef.get(0) != 0 && ecef.get(1)!=0 && ecef.get(2)!= 0 )
		    );
	}

	/**克隆Coordinates对象*/
	public Object clone(){
		Coordinates c = new Coordinates();
		c.ecef = this.ecef.copy();
		c.enu = this.enu.copy();
		c.geod = this.geod.copy();
		return c;
	}
	
	/**克隆Coordinates对象到目标对象*/
	public void cloneInto(Coordinates c){
		c.ecef = this.ecef.copy();
		c.enu = this.enu.copy();
		c.geod = this.geod.copy();
	}

	/**用于从全局转换到本地参考系统的旋转矩阵（反之亦然）*/
	public static SimpleMatrix rotationMatrix(Coordinates origin) {
		double lam = Math.toRadians(origin.getGeodeticLongitude());
		double phi = Math.toRadians(origin.getGeodeticLatitude());
		double cosLam = Math.cos(lam);
		double cosPhi = Math.cos(phi);
		double sinLam = Math.sin(lam);
		double sinPhi = Math.sin(phi);
		double[][] data = new double[3][3];
		data[0][0] = -sinLam;
		data[0][1] = cosLam;
		data[0][2] = 0;
		data[1][0] = -sinPhi * cosLam;
		data[1][1] = -sinPhi * sinLam;
		data[1][2] = cosPhi;
		data[2][0] = cosPhi * cosLam;
		data[2][1] = cosPhi * sinLam;
		data[2][2] = sinPhi;
		SimpleMatrix R = new SimpleMatrix(data);
		return R;
	}
	
	public String toString(){
		String lineBreak = System.getProperty("line.separator");
		String out= String.format( "Coord ECEF: X:"+getX()+" Y:"+getY()+" Z:"+getZ()+lineBreak +
		"       ENU: E:"+getE()+" N:"+getN()+" U:"+getU()+lineBreak +
		"      GEOD: Lon:"+getGeodeticLongitude()+" Lat:"+getGeodeticLatitude()+" H:"+getGeodeticHeight()+lineBreak +
		"      http://maps.google.com?q=%3.4f,%3.4f" + lineBreak, getGeodeticLatitude(), getGeodeticLongitude() );
		return out;
	}

	public Time getRefTime() {
		return refTime;
	}

	public void setRefTime(Time refTime) {
		this.refTime = refTime;
	}
	
}
