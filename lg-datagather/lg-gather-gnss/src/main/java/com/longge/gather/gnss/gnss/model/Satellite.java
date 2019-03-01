package com.longge.gather.gnss.gnss.model;
/**
 * Description:卫星抽象实体类
 * User: jianglong
 * Date: 2018年6月5号
 */
public class Satellite {
	
	    private  char satType;  //导航系统类型
	
		private int satID; //卫星编号
		
	    public Satellite(char satType,int satID) {
	    	this.satType=satType;
			this.satID = satID;
		}
	    
		@Override
		public String toString() {
			return "Satellite [satID=" + satID + ", satType=" + satType + "]";
		}
		
		@Override
		public int hashCode() {
			return satType + satID;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Satellite other = (Satellite) obj;
			if (satID != other.satID)
				return false;
			if (satType != other.satType)
				return false;
			return true;
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
}
