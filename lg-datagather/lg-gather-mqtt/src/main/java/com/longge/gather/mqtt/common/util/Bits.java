package com.longge.gather.mqtt.common.util;
/**
 * Description:bits处理工具类
 * User: jianglong
 * Date: 2019-09-09
 */
public class Bits {

	/**bits转有符号int*/
	public static int bitsToInt(boolean[] bits) {
		//负数
        if(bits[0]){
           //取反
        	for(int i = bits.length-1; i > 0; i--){
        		if(bits[i]){
        			bits[i] =false;
        		}else{
        			bits[i] =true;
        		}
        	}
        	//加1
    		int result = 0;
    		int pow2 = 1;
    		for (int i = bits.length-1; i > 0; i--) {
    			if (bits[i]) {
    				result = result + pow2;
    			}
    			pow2 = pow2 * 2;
    		}
    		return -(result+1);
        }else{
    		int result = 0;
    		int pow2 = 1;
    		for (int i = bits.length-1; i > 0; i--) {
    			if (bits[i]) {
    				result = result + pow2;
    			}
    			pow2 = pow2 * 2;
    		}
    		return result;
        }

	}
	
	
	/**bits转无符号int*/
	public static int bitsToUInt(boolean[] bits) {
		int result = 0;
		int pow2 = 1;
		for (int i = bits.length-1; i >= 0; i--) {
			if (bits[i]) {
				result = result + pow2;
			}
			pow2 = pow2 * 2;
		}
		return result;
	}
	
	/**bits转有符号long*/
	public static long bitsToLong(boolean[] bits) {
		//负数
		if(bits[0]){
	           //取反
	        	for(int i = bits.length-1; i > 0; i--){
	        		if(bits[i]){
	        			bits[i] =false;
	        		}else{
	        			bits[i] =true;
	        		}
	        	}
	        //加1
    		long result = 0;
    		long pow2 = 1;
    		for (int i = bits.length-1; i > 0; i--) {
    			if (bits[i]) {
    				result = result + pow2;
    			}
    			pow2 = pow2 * 2;
    		}
    		return -(result+1);
		}else{
			long result = 0;
			long pow2 = 1;
			for (int i = bits.length-1; i > 0; i--) {
				if (bits[i]) {
					result = result + pow2 ;
				}
				pow2 = pow2 * 2;
			}
			return result;
		}

	}
	
	/**bits转无符号long*/
	public static long bitsToULong(boolean[] bits) {
		long result = 0;
		long pow2 = 1;
		for (int i = bits.length-1; i >= 0; i--) {
			if (bits[i]) {
				result = result + pow2 ;
			}
			pow2 = pow2 * 2;
		}
		return result;
	}

	
	/** bit转long */
	public static long bitsTwoComplement(boolean[] bits) {
		long result;
		if (!bits[0]) {
			result = bitsToUInt(bits);
		} else {
			boolean[] b = new boolean[bits.length];
			for (int i = 0; i < bits.length; i++) {
				b[i] = !bits[i];
			}
			result = -1 * (bitsToUInt(b) + 1);
		}

		return result;
	}
	
	/** bit转string(0,1) */
	public static String bitsToStr(boolean[] b) {
		String result = "";
		for (int i = 0; i < b.length; i++) {
			if (b[i]) {
				result = result.concat("1");
			} else {
				result = result.concat("0");
			}
		}
		return result;
	}
	
	public static boolean[] strToBits(String str){
		boolean[] res = new boolean[str.length()];
		for(int i=0; i<str.length(); i++){
			res[i] = charToBit(str.charAt(i));
		}
		return res;
	}
	
	/**char转bit*/
	public static  boolean charToBit(char a){
		if(a == '1'){
			return true;
		}else{
			return false;
		}
	}

	/** int转str(0,1)*/
	public static String byteToStr(int b) {
		return bitsToStr(rollByteToBits(b));
	}

	/**比较两个位数组的长度和位*/
	public static boolean compare(boolean[] b1, boolean[] b2) {
		boolean result = b1.length != 0 && (b1.length == b2.length);
		int i = 0;
		while (result && i < b1.length) {
			result = (b1[i] == b2[i]);
			i++;
		}
		return result;
	}

	/** 将两个位数组放入一个新的数组中 */
	public static boolean[] concat(boolean[] b1, boolean[] b2) {
		boolean[] result = new boolean[b1.length + b2.length];
		for (int i = 0; i < b1.length; i++) {
			result[i] = b1[i];
		}
		for (int i = 0; i < b2.length; i++) {
			result[i + b1.length] = b2[i];
		}
		return result;
	}

	/**把整个位数组复制到一个新的位数组中*/
	public static boolean[] copy(boolean[] b) {
		return subset(b, 0, b.length);
	}


	/** 将一个字节转换成bits*/
	public static boolean[] byteToBits(byte b, int length) {
		int in = getUInt(b);
		boolean[] result = new boolean[length];
		for (int i = 0; i < length; i++) {
			result[length - 1 - i] = (in % 2 == 1);
			in = in / 2;
		}
		return result;
	}

	/** 将一个字节数组转换成bits*/
	public static boolean[] bytestoBits(byte[] b){
		boolean[] result = new boolean[0];
		for (int i = 0; i < b.length; i++) {
			boolean[] temp  = byteToBits(b[i],8);
			result = concat(result,temp);
		}
		return result;
	}

	/**int 转bits*/
	public static boolean[] intToBits(int in, int length) {
		boolean[] result = new boolean[length];
		for (int i = 0; i < length; i++) {
			result[length - 1 - i] = (in % 2 == 1);
			in = in / 2;
		}
		return result;
	}

	/**int 转 bits*/
	public static boolean[] rollByteToBits(int in) {
		boolean[] result = new boolean[8];
		for (int i = 7; i > -1; i--) {
			result[i] = (in % 2 == 1);
			in = in / 2;
		}
		return result;
	}

	/**从位数组中复制一个子集到一个新的位数组*/
	public static boolean[] subset(boolean[] b, int start, int length) {
		boolean[] result;

		if (start >= b.length || start + length > b.length) {
			result = null;
			throw new ArrayIndexOutOfBoundsException(
					        "Invalid subset: exceeds length of " + b.length
							+ ":\nstart of subset: " + start
							+ ", length of subset: " + length);
		} else {
			result = new boolean[length];
			for (int i = 0; i < length; i++) {
				result[i] = b[start + i];
			}
		}
		return result;
	}

	public static String subsetBin(boolean[] b, int start, int length) {
		String result = "b://";
		if (start >= b.length || start + length > b.length) {
			result = null;
			throw new ArrayIndexOutOfBoundsException(
					        "Invalid subset: Succseeds length of " + b.length
							+ ":\nstart of subset: " + start
							+ ", length of subset: " + length);
		} else {
			for (int i = 0; i < length; i++) {
				if (b[start + i])
					result += "1";
				else
					result += "0";

			}
		}
		return result;
	}

	/**bits转byte[]*/
	public static byte[] tobytes(boolean[] bits) {
		byte[] bytes = new byte[bits.length / 8];
		int indice = 0;
		for (int i = 0; i < bits.length / 8; i++) {
			bytes[i] = (byte) bitsToUInt(subset(bits, indice, 8));
			indice += 8;
		}
		return bytes;
	}

	public static int getUInt(byte b){
		return b>=0?b:256+b;
	}

	public static double byteToIEEE754Double(byte l[]){
		long bits = 0;
		for(int i=l.length-1;i>=0;i--){
			bits = bits << 8;
			 bits = bits | getUInt(l[i]);
		}
		return Double.longBitsToDouble(bits);
	}
	
	public static float byteToIEEE754Float(byte l[]){
		int bits = 0;
		for(int i=l.length-1;i>=0;i--){
			bits = bits << 8;
			bits = bits | getUInt(l[i]);
		}
		return Float.intBitsToFloat(bits);
	}
	
	public static long byteToLong(byte l[]){
		long bits = 0;
		for(int i=l.length-1;i>=0;i--){
			bits = bits << 8;
			bits = bits | getUInt(l[i]);
		}
		return bits;
	}
	
	public static int byteToInt(byte l[]){
		int bits = 0;
		for(int i=l.length-1;i>=0;i--){
			bits = bits << 8;
			bits = bits | getUInt(l[i]);
		}

		return bits;
	}
	
	public static double byteToIEEE754DoubleBigEndian(byte l[]){
		long bits = 0;
		for(int i=0;i<l.length;i++){
			bits = bits << 8;
			 bits = bits | getUInt(l[i]);
		}
		return Double.longBitsToDouble(bits);
	}
	
	public static float byteToIEEE754FloatBigEndian(byte l[]){
		int bits = 0;
		for(int i=0;i<l.length;i++){
			bits = bits << 8;
			bits = bits | getUInt(l[i]);
		}
		return Float.intBitsToFloat(bits);
	}
	
	public static long byteToLongBigEndian(byte l[]){
		int bits = 0;
		for(int i=0;i<l.length;i++){
			bits = bits << 8;
			bits = bits | getUInt(l[i]);
		}
		return bits;
	}
	
	public static int byteToIntBigEndian(byte l[]){
		int bits = 0;
		for(int i=0;i<l.length;i++){
			bits = bits << 8;
			bits = bits | getUInt(l[i]);
		}
		return bits;
	}

	/**将指定的字节数从一个字节数组转换成可读的gex string*/
    public static String toHexString(byte[] byteArray, int length)
    {
        StringBuffer buffer = new StringBuffer();
        for(int i = 0; i < length; i++)
        {
            buffer.append(toHex(byteArray[i]));
        }
        return buffer.toString();
    }

    /**byte 转hexString */
    public static String toHex(byte b)
    {
        Integer I = new Integer((b << 24) >>> 24);
        int i = I.intValue();

        if (i < (byte)16)
        {
            return "0" + Integer.toString(i,16).toUpperCase();
        }
        return Integer.toString(i,16).toUpperCase();
    }
    
}
