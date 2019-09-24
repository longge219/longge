package com.longge.gather.mqtt.common.util;
/**
 * @Description:基础类型与数组装换
 * @create Author:jianglong
 * @create Date:2019-09-09
 * @version V1.0
 */
public class ByteConvert {
	
  public static long bytesToLong(byte[] array) {
		return (array[0] & 0xFF) << 0 | (array[1] & 0xFF) << 8
				| (array[2] & 0xFF) << 16 | (array[3] & 0xFF) << 24
				| (array[4] & 0xFF) << 32 | (array[5] & 0xFF) << 40
				| (array[6] & 0xFF) << 48 | (array[7] & 0xFF) << 56;
	}
	public static int bytesToInt(byte[] b) {
		return (int)(b[3] & 0xFF)<<24 | (b[2] & 0xFF) << 16 | (b[1] & 0xFF) << 8 | (b[0] & 0xFF) << 0;
	}
	
	public static long bytesToUint(byte[] b) {
		return  (b[3] & 0xFF)<<24 | (b[2] & 0xFF) << 16 | (b[1] & 0xFF) << 8 | (b[0] & 0xFF) << 0;
	}

	/**小头排序*/
	public static short bytesToShort(byte[] b) {
		return (short) ((b[1] & 0xFF)<<8 | (b[0] & 0xFF) << 0);
	}

	public static int bytesToUshort(byte[] b) {
		return (b[1] & 0xFF)<<8 | (b[0] & 0xFF) << 0;
	}

	/**大头排序*/
	public static short bytesToShortS(byte[] b) {
		return (short) ((b[1] & 0xFF)<<0 | (b[0] & 0xFF) << 8);
	}

	public static int bytesToUShortS(byte[] b) {
		return ((b[1] & 0xFF)<<0 | (b[0] & 0xFF) << 8);
	}
	

	
	public static double bytesToDouble(byte[] array) {
		byte[] temp = new byte[array.length];
		int i =0;
		for(int j = array.length-1; j>=0 ;j--){
			temp[i] = array[j];
			i++;
		}
		 boolean[] booleanData= Bits.bytestoBits(temp);
		 long a  = Bits.bitsToULong(booleanData);
		 return Double.longBitsToDouble(a);
	}
}