package com.longge.gather.gnss.ci.invok;
import java.util.ArrayList;
import java.util.List;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
/**
 * @Description:DLL调用接口
 * @create Author:jianglong
 * @create 2018-10-030
 */
  public interface DllHelper extends Library{
	  
	  static DllHelper instance = (DllHelper)Native.loadLibrary(Platform.isWindows() ? "XincRTK" : "XincRTK",DllHelper.class);
	 
	    public static DllHelper getInstance(){
	        return instance;
	    }
	  
	    /**加载DLL*/
	    //DllHelper instanceDll = (DllHelper) Native.synchronizedLibrary((DllHelper)Native.loadLibrary(Platform.isWindows() ? "RTKDll" : "RTKDll",DllHelper.class));

		/**卫星信息对象结构体*/
		public  class STARINFO extends Structure{
		    public STARINFO() { }
	        public STARINFO(Pointer p) { super(p); read(); 
	        }

			//指针结构体使用
	       public static class ByReference extends STARINFO implements Structure.ByReference{
	    	   public ByReference() { }
	    	   public ByReference(Pointer p) { super(p); }
	       };
	        
			//结构体属性值拷贝使用
	       public static class ByValue extends STARINFO implements Structure.ByValue{
	           public ByValue() { }
	            public ByValue(Pointer p) { super(p); }
	       };

			public byte ID; //卫星号
			
			public byte Freq; //频点号 0:L1  1:L2  2:L5  3：B1  4:B2  5:B3  6:G1  7:G2 
			
			public double PR; //伪距---单位:米
			
			public double Phase; //载波相位---单位:周
			
			public double Doppler; //多普勒---单位:HZ
			
			public byte  LLI; //卫星失锁标志0-7
			
			public double SNR; //信噪比1-9
			
			public double[] El  =  new  double[2]; //0:卫星俯仰角(度)  1:方位角(度)
			
			public double[] XYZ = new double[3]; //卫星坐标(米)
			 
			public  double[]  Vel = new double[3]; //卫星速度(米/秒)
			
			public double Iono; //电离层延迟(米)
 
			@Override
		    protected List<String> getFieldOrder() {
				List<String> Field = new ArrayList<String>();
				Field.add("ID");
				Field.add("Freq");
				Field.add("PR");
				Field.add("Phase");
				Field.add("Doppler");
				Field.add("LLI");
				Field.add("SNR");
				Field.add("El");
				Field.add("XYZ");
				Field.add("Vel");
				Field.add("Iono");
				return Field;
			}
        }
	  /**原始数据结构体定义*/
	  public  class ORIGINALDATA extends Structure{
		  
		    public ORIGINALDATA() { }
	        public ORIGINALDATA(Pointer p) { super(p); read(); }
		  
	        //指针结构体使用
		    public static class ByReference extends ORIGINALDATA implements Structure.ByReference{
		    	   public ByReference() { }
		    	   public ByReference(Pointer p) { super(p); }
		    };
			  
		   //结构体属性值拷贝使用
	       public static class ByValue extends ORIGINALDATA implements Structure.ByValue{
	           public ByValue() { }
	            public ByValue(Pointer p) { super(p); }
	       };
			 
	         public short PosMod; //定位模式   0:Null   1:L1   2:L2   4:L5   8:B1    16:B2   32:B3    64:G1    128:G2
			 	
	         public short Dynamic; //动态模式   0：静态  1：动态
	         
	         public short[] week = new short[3];  //GPS周，北斗周，glonass周
	         
	         public short IonFlag; //电离层参数标记 1：有
			 	
	         public double[] desecond = new double[3]; //周内秒   0:GPS   1:BD   2:Glonass
			 	
	         public byte satNum; //卫星数
			 	
	         public double[] XYZ = new double[3]; //接收机位置
			 	
	         public double[]  BLH = new double[3]; //接收机位置
			 	
	        public  STARINFO[] StarInfor = (STARINFO[])new STARINFO().toArray(128); //卫星信息 MAX_STAR_NUM=128颗卫星  
	         
		   @Override
		   protected List<String> getFieldOrder() {
					List<String> Field = new ArrayList<String>();
					Field.add("PosMod");
					Field.add("Dynamic");
					Field.add("week");
					Field.add("IonFlag");
					Field.add("desecond");
					Field.add("satNum");
					Field.add("XYZ");
					Field.add("BLH");
					Field.add("StarInfor");
					return Field;
		    }
		}
	  
	  /**原始卫星星历结构体*/
	  public  class EPHDATA extends Structure{
		  
		    public EPHDATA() { }
	        public EPHDATA(Pointer p) { super(p); read(); }
		  
	        //指针结构体使用
		    public static class ByReference extends EPHDATA implements Structure.ByReference{
		    	   public ByReference() { }
		    	   public ByReference(Pointer p) { super(p); }
		    };
			  
		   //结构体属性值拷贝使用
	       public static class ByValue extends EPHDATA implements Structure.ByValue{
	           public ByValue() { }
	            public ByValue(Pointer p) { super(p); }
	       };
			 
	   	   public  int nSat; //卫星号
	   	   public  int nIode;        //IODE
		   public int  nIodc;        //IODC
		   public int nSva;         //SV accuracy (URA index)
		   public int nSvh;         //SV health (0:ok)
		   public int nWeek;        //GPS/QZS: gps week, GAL: galileo week
		   public int nCode;        //GPS/QZS: code on L2, GAL/CMP: data sources
		   public int nFlag;        //GPS/QZS: L2 P data flag, CMP: nav type
		   public double dblToe;    //Toe,Toc,T_trans
		   public double dblToc;
		   public double dblTtr;    
		  //SV orbit parameters
		  public double dblA;
		  public double dblE;
		  public double dblI0;
		  public double dblOMG0;
		  public double dblOmg;
		  public double dblM0;
		  public double dblDeln;
		  public double dblOMGd;
		  public double dblIdot;
		  public double dblCrc;
		  public double dblCrs;
		  public double dblCuc;
		  public double dblCus;
		  public double dblCic;
		  public double dblCis;
		  public double dblToes;   //Toe (s) in week
		  public double dblFit;    //fit interval (h)
		  public double dblAf0;     //SV clock parameters (af0,af1,af2)
		  public double dblAf1;
		  public double dblAf2;   
		  public double[] arrTgd = new double[4];      /* group delay parameters */
		/* GPS/QZS:tgd[0]=TGD */
		/* GAL    :tgd[0]=BGD E5a/E1,tgd[1]=BGD E5b/E1 */
		/* CMP    :tgd[0]=BGD1,tgd[1]=BGD2 */
		public double dblAdot;   //Adot,ndot for CNAV
		public double dblNdot;
	         
		   @Override
		   protected List<String> getFieldOrder() {
					List<String> Field = new ArrayList<String>();
					Field.add("nSat");
					Field.add("nIode");
					Field.add("nIodc");
					Field.add("nSva");
					Field.add("nSvh");
					Field.add("nWeek");
					Field.add("nCode");
					Field.add("nFlag");
					Field.add("dblToe");
					Field.add("dblToc");
					Field.add("dblTtr");
					Field.add("dblA");
					Field.add("dblE");
					Field.add("dblI0");
					Field.add("dblOMG0");
					Field.add("dblOmg");
					Field.add("dblM0");
					Field.add("dblDeln");
					Field.add("dblOMGd");
					Field.add("dblIdot");
					Field.add("dblCrc");
					Field.add("dblCrs");
					Field.add("dblCuc");
					Field.add("dblCus");
					Field.add("dblCic");
					Field.add("dblCis");
					Field.add("dblToes");
					Field.add("dblFit");
					Field.add("dblAf0");
					Field.add("dblAf1");
					Field.add("dblAf2");
					Field.add("arrTgd");
					Field.add("dblAdot");
					Field.add("dblNdot");
					return Field;
		    }
		}
	  
		/**原始数据入参结构体定义*/
	  public  class NAVDATA2 extends Structure{
		  
		    public NAVDATA2() { }
	        public NAVDATA2(Pointer p) { super(p); read(); }
		  
	        //指针结构体使用
		    public static class ByReference extends NAVDATA2 implements Structure.ByReference{
		    	   public ByReference() { }
		    	   public ByReference(Pointer p) { super(p); }
		    };
			  
		   //结构体属性值拷贝使用
	       public static class ByValue extends NAVDATA2 implements Structure.ByValue{
	           public ByValue() { }
	            public ByValue(Pointer p) { super(p); }
	       };
	       public  int nNum;  //星历个数
	       public  EPHDATA[] ehpData = (EPHDATA[])new EPHDATA().toArray(128); //星历数据 MAX_STAR_NUM=128颗卫星  
		   @Override
		   protected List<String> getFieldOrder() {
					List<String> Field = new ArrayList<String>();
					Field.add("nNum");
					Field.add("ehpData");
					return Field;
		    }
		}
	  
	  /**输出信息结构体定义*/
	  public static class CALOUTPUT extends Structure{
		    public CALOUTPUT() {}
		    
	        public CALOUTPUT(Pointer p) { super(p); read(); }
		  
	        //指针结构体使用
		    public static class ByReference extends CALOUTPUT implements Structure.ByReference{
		    	   public ByReference() { }
		    	   public ByReference(Pointer p) { super(p); }
		    }
			  
		   //结构体属性值拷贝使用
	       public static class ByValue extends CALOUTPUT implements Structure.ByValue{
	           public ByValue() { }
	           public ByValue(Pointer p) { super(p); }
	       }
	     	public int DDFlag;		                //计算成功 0失败 1成功
	    	public double[] BaseLineVect = new double[3];         //载波相位基线向量(XYZ)
	    	public double[] BaseLineVectQ = new double[6];        //基线分量协方差值
	    	public int numv;                       //观测值改正数个数
	    	public double[] BaseLineV = new double[192]; //观测值改正数
	    	public double Amb_Ratio;               //基线解算模糊度比率
	    	public int namb;                       //模糊度个数
	    	public double[] Amb_Value = new double[192]; //基线解算模糊度值
	    	public double	BaselineLength;
	    	public double[]		ENUVect = new double[3];
	    	public double[]	DDPRLineVec = new double[3];
	         
			@Override
		    protected List<String> getFieldOrder() {
				List<String> Field = new ArrayList<String>();
				Field.add("DDFlag");
				Field.add("BaseLineVect");
				Field.add("BaseLineVectQ");
				Field.add("numv");
				Field.add("BaseLineV");
				Field.add("Amb_Ratio");
				Field.add("namb");
				Field.add("Amb_Value");
				Field.add("BaselineLength");
				Field.add("ENUVect");
				Field.add("DDPRLineVec");
				return Field;
			}
		}
	  
		 //声明DLL的函数
	    public int CalculateRTK2(ORIGINALDATA.ByReference BaseData, ORIGINALDATA.ByReference RoveData, NAVDATA2.ByReference navData, CALOUTPUT.ByReference OutPut, short Index);
	    
	}
