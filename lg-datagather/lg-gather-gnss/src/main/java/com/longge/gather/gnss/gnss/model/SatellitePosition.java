package com.longge.gather.gnss.gnss.model;
import com.longge.gather.gnss.gnss.calculate.Coordinates;
import org.ejml.simple.SimpleMatrix;
/**
 * @description 卫星位置
 * @author jianglong
 * @create 2018-07-11
 **/
public class SatellitePosition extends Coordinates {
	
  public static final SatellitePosition UnhealthySat = new SatellitePosition(0, 0, '0', 0, 0, 0); 

	private int satID; 

	private char satType;
	
	private double satelliteClockError; 

	private long unixTime;
	
	private boolean predicted;
	
	private boolean maneuver;
	
  private SimpleMatrix speed; 

	public SatellitePosition(long unixTime, int satID, char satType, double x, double y, double z) {
		super();
		this.unixTime = unixTime;
		this.satID = satID;
		this.satType = satType;
		this.setXYZ(x, y, z);
        this.speed = new SimpleMatrix(3, 1);
	}

	public int getSatID() {
		return satID;
	}

	public void setSatID(int satID) {
		this.satID = satID;
	}
	
	public char getSatType() {
		return satType;
	}

	public void setSatType(char satType) {
		this.satType = satType;
	}

	public double getSatelliteClockError() {
		return satelliteClockError;
	}

	public void setSatelliteClockError(double timeCorrection) {
		this.satelliteClockError = timeCorrection;
	}

	public long getUtcTime() {
		return unixTime;
	}

	public void setPredicted(boolean predicted) {
		this.predicted = predicted;
	}

	public boolean isPredicted() {
		return predicted;
	}

	public void setManeuver(boolean maneuver) {
		this.maneuver = maneuver;
	}

	public boolean isManeuver() {
		return maneuver;
	}

  public SimpleMatrix getSpeed() {
    return speed;
  }

  public void setSpeed( double xdot, double ydot, double zdot) {
    this.speed.set( 0, xdot );
    this.speed.set( 1, ydot );
    this.speed.set( 2, zdot );
  }
	
	public String toString(){
		return "X:"+this.getX()+" Y:"+this.getY()+" Z:"+getZ()+" clkCorr:"+getSatelliteClockError();
	}

	public Object clone(){
		SatellitePosition sp = new SatellitePosition(this.unixTime,this.satID, this.satType, this.getX(),this.getY(),this.getZ());
		sp.maneuver = this.maneuver;
		sp.predicted = this.predicted;
		sp.satelliteClockError = this.satelliteClockError;
    sp.setSpeed( speed.get(0), speed.get(1), speed.get(2));
		return sp;
	}
}
