package com.longge.gather.gnss.utils;
/**
 * @Description:字符串处理
 * @create Author:jianglong
 * @create Date:2019-02-13
 */
public class StringUtil {
	
	/**判断一个字符串中包含1的数量*/
    public static int getContainOneNum(String str){
    	return str.length()-str.replaceAll("1", "").length();
    }
	
	/**获取字符串中为1的位置数组*/
	public static int[] getStrIndex(String str){
		int indexNum = getContainOneNum(str);
		if(indexNum == 0){
			return null;
		}else{
			int[] indexArray = new int[indexNum];
			int j =0;
			for(int i=0;i<str.length();i++){
				 if(str.charAt(i) =='1'){
					 indexArray[j] = i+1;
					 j++;
				 }
			}
			return indexArray;
		}
	}
	
	/**传入字符串大为max多位，不足用空格在后面补齐*/
	public static  String se(String in, int max){
		return sf(in,max,0);
	}
	
	/**传入字符串大为max-1多位，不足用空格在后面补齐*/
	public static String sf(String in, int max){
		return sf(in,max,1);
	}

	/**后面补空格*/
	public static String sf(String in, int max,int margin){
		if(in.length()==max-margin){
			while(in.length()<max) in +=" ";
			return in;
		}
		if(in.length()>max-margin){
			return in.substring(0, max-margin)+" ";
		}
		while(in.length()<max) in +=" ";

		return in;
	}

	
	/**传入字符串大为max-margin多位，不足用空格在前面补齐*/
	public static String spe(String in, int max){
		return sp(in, max,0);
	}
	
	public static String sp(String in, int max){
		return sp(in, max,1);
	}
	
	public static String sp(String in, int max,int margin){
		if(in.length()==max-margin){
			while(in.length()<max) in =" "+in;
			return in;
		}
		if(in.length()>max-margin){
			return in.substring(0, max-margin)+" ";
		}
		while(in.length()<max) in =" "+in;

		return in;
	}
}
