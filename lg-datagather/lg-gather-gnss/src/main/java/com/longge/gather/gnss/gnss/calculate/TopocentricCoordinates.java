package com.longge.gather.gnss.gnss.calculate;
import org.ejml.simple.SimpleMatrix;
/**
 * @description 地平法计算--以地面上的某点为中心
 * @author jianglong
 * @create 2018-07-11
 **/
public class TopocentricCoordinates {

	private SimpleMatrix topocentric = new SimpleMatrix(3, 1); 

	/**
	 * 计算接收机与卫星的仰角，方位角，距离
	 * origin 接收机坐标
	 * target 卫星坐标
	 * */
	public TopocentricCoordinates computeTopocentric(Coordinates origin, Coordinates target) {
		//卫星空间坐标转换成站心坐标
		origin.computeLocal(target);
		double E = origin.getE();
		double N = origin.getN();
		double U = origin.getU();
		// 计算从原点到这个物体的水平距离
		double hDist = Math.sqrt(Math.pow(E, 2) + Math.pow(N, 2));
		// 如果这个物体在顶点
		if (hDist < 1e-20) {
			topocentric.set(0, 0, 0);
			topocentric.set(1, 0, 90);
		} else {
			topocentric.set(0, 0, Math.toDegrees(Math.atan2(E, N))); //方位角
			topocentric.set(1, 0, Math.toDegrees(Math.atan2(U, hDist)));//仰角
			//判断方位角是否为负
			if (topocentric.get(0) < 0){
				topocentric.set(0, 0, topocentric.get(0) + 360);
			}
				
		}
		topocentric.set( 2, 0, Math.sqrt(Math.pow(E, 2) + Math.pow(N, 2) + Math.pow(U, 2)));//距离
		return this;
	}
	
	/**
	 * 计算接收机与卫星的仰角，方位角，距离
	 * origin 接收机坐标
	 * target 卫星坐标
	 * */
	public TopocentricCoordinates computeTopocentricT(Coordinates origin, Coordinates target) {
		double  El, AZ;
		double xls, yls, zls;	
		double xaz, yaz, zaz;	
		double az, el;		
		double	north_x,north_y,north_z, east_x, east_y, up_x,   up_y,   up_z ;	
		double x,y,z,r,rxy;
		double PI=3.1415926535898;
	    double R2d=57.295779513;
		if( Math.abs(origin.getX())< 1.0E-8 && Math.abs(origin.getY())< 1.0E-8 && Math.abs(origin.getZ())< 1.0E-8 ){
			El = 0.0;
			AZ = 0.0;
			return null;
		}
		//用户坐标
		x = origin.getX();
		y = origin.getY();
		z = origin.getZ();
		r = Math.sqrt(x*x + y*y + z*z);	
		rxy =Math.sqrt(x*x + y*y);		
		El = 0.0;               
		AZ = 0.0;
		if ( rxy > 1.0E-8 ){
			//地心坐标系 到 地平坐标系 的旋转矩阵		
			north_x = -x/rxy * z/r;
			north_y = -y/rxy * z/r;
			north_z =  rxy / r;
			east_x  = -y / rxy;
			east_y  =  x / rxy;
			up_x    =  x / r;
			up_y    =  y / r;
			up_z    =  z / r;
			// 站心和卫星之间的坐标差，用以计算是否卫星在站心坐标XY平面下
			xls = target.getX() - x;
			yls = target.getY() - y;
			zls = target.getZ() - z;
			// 也就是先计算高度角
			zaz = (up_x * xls + up_y * yls + up_z * zls) / (Math.sqrt( xls * xls + yls * yls + zls * zls));
			// 这里不会发生zaz>1和<-1的情况，只有极端的情况
			if ( zaz >= 1.0 ){
				el = PI/2;
			}else if ( zaz <= -1.0 ){
				el = - PI/2;
			}else{
				el = Math.asin(zaz);
			}
			// 以下计算的是卫星在站心坐标中的XY坐标
			xaz = north_x * xls + north_y * yls + north_z * zls;
			yaz = east_x * xls + east_y * yls;
			if (xaz != 0.0 || yaz != 0.0){
				az = Math.atan2(yaz, xaz); 
				if( az < 0) az += 2*PI;
			}else{
				az = 0.0;
			}
				

			El = el*R2d;
			AZ =az*R2d;
			topocentric.set(0, 0,AZ);
			topocentric.set(1, 0,El);
			
		}
		return this;
	}
	
	
	/**方位角*/
	public double getAzimuth(){
		return topocentric.get(0);
	}
	/**仰角*/
	public double getElevation(){
		return topocentric.get(1);
	}
	/**距离*/
	public double getDistance(){
		return topocentric.get(2);
	}
}
