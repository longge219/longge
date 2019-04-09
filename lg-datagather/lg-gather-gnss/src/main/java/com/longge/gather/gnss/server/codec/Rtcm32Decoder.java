package com.longge.gather.gnss.server.codec;
import com.longge.gather.gnss.server.reflect.ClassProcess;
import com.longge.gather.gnss.server.reflect.SubAnnotation;
import com.longge.gather.gnss.utils.Bits;
import com.longge.gather.gnss.utils.ByteConvert;
import com.longge.gather.gnss.utils.StringUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @Description:自定义RTCM3.2协议解码器
 * @create Author:jianglong
 * @create Date:2018-06-12
 * @version V1.0
 */
public class Rtcm32Decoder extends ByteToMessageDecoder {
	
	//日志
	private static final Logger logger = LoggerFactory.getLogger(Rtcm32Decoder.class);
	
	//反射工具类
	private static ClassProcess classProcess  = new ClassProcess();
	
	//数据包的二进制数组
	private  boolean[]  booleanData;
	
	//卫星掩码数量
	private int msmGnssSatNum;
	
	//信号掩码数量
	private int msmGnssSigNum;
	
	//单元掩码数
	private  int msmGnssEleNum;
	
	//msm卫星观测数据索引
	private  int msmGnssSatNumIndex;
	
	//msm单元数据索引
	private  int msmGnssCleNumIndex;
	
	//武汉协议长度
	private static Integer packetSize = 140;
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		//头三个字节
		byte[] wys = new byte[3];
		if(in.readableBytes() > 3){
			in.readBytes(wys);	
		}else{
			return;
		}
		//通过头三个字节来判定是武汉导航院协议还是RTCM协议
		if(checkWhProtocol(wys)){
			if (in.readableBytes() > packetSize - 3) {
				//消息头长度
				 in.readByte();
				 //消息ID
				 byte[] messageIdBytes = new byte[2];
				 in.readBytes(messageIdBytes);
				int messageId = ByteConvert.bytesToUshort(messageIdBytes);
				if(classProcess.verifyTag(messageId)){
					 Class<?> Class = classProcess.getClassByTag(messageId);
					 Object object = getObjectByBuffer(Class,in); 
					 out.add(object);
					 return ;
				}else{
					logger.info("武汉导航院未定义的协议头报文信息不处理");
				}

			}else{
				//报文长度小于报头最小长度(25字节) 内容不够，需要下一批发过来的内容
				in.readerIndex(in.readerIndex() - 3);
				return;
			}
			
		}
		/**正式解码标准RTCM协议数据*/
	   //处理RTCM半包问题
		byte[] packetLength = new byte[2];
		packetLength[0] = wys[1];
		packetLength[1] = wys[2];
		short length = ByteConvert.bytesToShortT(packetLength);
		if(length > in.readableBytes()){
			in.readerIndex(in.readerIndex() - 3);
			return;
		}
		//解码
		byte[] data = new byte[in.readableBytes()];
		in.readBytes(data);
		booleanData= Bits.bytestoBits(data);
		Object object =doDecode(booleanData);
		//清理数据
		booleanData  = null;
		out.add(object);
		/**正式解码标准RTCM协议数据完成*/
		return;
	}
	
	/** 
	  * 解码RTCM协议
	  */	
	private  Object doDecode(boolean[]  data) throws Exception{
		logger.info("收到的数据包-------"+ Bits.bitsToStr(data));
		//报文头
	    int head = Bits.bitsToUInt(Bits.subset(data, 0, 12));
	    logger.info("协议头" + head);
		if(classProcess.verifyTag(head)){
			 Class<?> Class = classProcess.getClassByTag(head);
			 Object object = getObjectByRtcmObject(Class,data); 
			 return object;
		}else{
			logger.info("未定义的协议头报文信息不处理"+head);
		}
		return null;
	 }
	
	   /**
	    * 根据类中注释解码得到每个类的实例
	    */
     private  Object getObjectByRtcmObject(Class<?> clazz, boolean[]  data) throws Exception{
			//实例化类
	    	Object object  = clazz.newInstance();
	        //得到类中private 的属性
		    Field[] fields=clazz.getDeclaredFields();    
		      for(int i=0;i<fields.length;i++){
			      Field field = fields[i];
			   	  //得到属性对应的注释
			      SubAnnotation subAnnotation = field.getAnnotation(SubAnnotation.class);
			   	  if (subAnnotation != null) {
				   		field.setAccessible(true);
				   		Object fieldValue = getRtcmValues(subAnnotation.type(), subAnnotation.startPos(),subAnnotation.len(),subAnnotation.mark(),subAnnotation.className(), data);
				   		field.set(object, fieldValue);// 得到此属性设值
			      }
		      }
		       return object; 
	    }
	
	  /**
	    *根据属性注解获取属性值
	    */
	 private  Object getRtcmValues(String type,int startPos,int len,String mark,String className,boolean[]  valueData)throws Exception{
	        /**解码成int属性*/
	        if (type.equalsIgnoreCase("boolean")){
	        	if(mark.equalsIgnoreCase("sppctfByMsmGnssEleNum")){
	        		int sPos = 169 + msmGnssSatNum*msmGnssSigNum+18*msmGnssSatNum+41*msmGnssEleNum+msmGnssCleNumIndex;
	        		return   booleanData[sPos];
	        	}else{
	        		return   booleanData[startPos];
	        	}
	        	
	        }		     
	        /**解码成无符号int属性*/
	        else  if (type.equalsIgnoreCase("uint")){
	        	if(mark.equalsIgnoreCase("sgdmiByMsmGnssSatNumIndex")){
	        		int sPos = 169 + msmGnssSatNum*msmGnssSigNum + 8*msmGnssSatNumIndex;
	        		return Bits.bitsToUInt(Bits.subset(booleanData, sPos, len));
	        	}else if(mark.equalsIgnoreCase("sgdmrByMsmGnssSatNumIndex")){
	        		int sPos = 169 + msmGnssSatNum*msmGnssSigNum + 8*msmGnssSatNum+10*msmGnssSatNumIndex;
	        		return Bits.bitsToUInt(Bits.subset(booleanData, sPos, len));
	        	}else if(mark.equalsIgnoreCase("sppctfByMsmGnssEleNum")){
	        		int sPos = 169 + msmGnssSatNum*msmGnssSigNum+18*msmGnssSatNum+37*msmGnssEleNum+4*msmGnssCleNumIndex;
	        		return Bits.bitsToUInt(Bits.subset(booleanData, sPos, len));
	        	}else if(mark.equalsIgnoreCase("scByMsmGnssEleNum")){
	        		int sPos = 169 + msmGnssSatNum*msmGnssSigNum+18*msmGnssSatNum+42*msmGnssEleNum+6*msmGnssCleNumIndex;
	        		return Bits.bitsToUInt(Bits.subset(booleanData, sPos, len));
	        	}
	        	else{
	        		return Bits.bitsToUInt(Bits.subset(booleanData, startPos, len));
	        	}
	        	
	        	}
	        /**解码成有符号int属性*/
	        else  if(type.equalsIgnoreCase("int")){
	        	if(mark.equalsIgnoreCase("sprepByMsmGnssEleNum")){
	        		int sPos = 169 + msmGnssSatNum*msmGnssSigNum+18*msmGnssSatNum+15*msmGnssCleNumIndex;
	        		return Bits.bitsToInt(Bits.subset(booleanData, sPos, len));
	        	}else if(mark.equalsIgnoreCase("sphapByMsmGnssEleNum")){
	        		int sPos = 169 + msmGnssSatNum*msmGnssSigNum+18*msmGnssSatNum+15*msmGnssEleNum+22*msmGnssCleNumIndex;
	        		return Bits.bitsToInt(Bits.subset(booleanData, sPos, len));
	        	}
	        	else{
	        		return Bits.bitsToInt(Bits.subset(booleanData, startPos, len));
	        	}
	        }
	        /**解码成无符号long属性*/
	        else if(type.equalsIgnoreCase("ulong")){
	        	return Bits.bitsToULong(Bits.subset(booleanData, startPos, len));
	        }
	        /**解码成有符号long属性*/
	        else if(type.equalsIgnoreCase("long")){
	        	return Bits.bitsToLong(Bits.subset(booleanData, startPos, len));
	        }
	        /**解码成有符号String属性*/
	        else if(type.equalsIgnoreCase("String")){
	        	String value = "";
	        	//MSM确定卫星掩码数量
	        	if(mark.equals("msmGnssSatNum")){
	        		value = Bits.bitsToStr(Bits.subset(booleanData, startPos, len));
	        		msmGnssSatNum = StringUtil.getContainOneNum(value);
	        	}
	        	//MSM确定信号掩码数量
	        	else if(mark.equalsIgnoreCase("msmGnssSigNum")){
	        		value = Bits.bitsToStr(Bits.subset(booleanData, startPos, len));
	        		msmGnssSigNum =  StringUtil.getContainOneNum(value);
	        	}
	        	//MSM根据卫星和信号掩码数量确定单元掩码
	        	else if(mark.equalsIgnoreCase("msmGnssEleNum")){
	        		value = Bits.bitsToStr(Bits.subset(booleanData, startPos, msmGnssSatNum*msmGnssSigNum));
	        		msmGnssEleNum = StringUtil.getContainOneNum(value);
	        	}else{
	        		value = Bits.bitsToStr(Bits.subset(booleanData, startPos, len));
	        	}
	        	return value;
	        }
	       /**解码成array数组对象属性*/
	        else if(type.equalsIgnoreCase("array")){
	        	//MSM卫星数据的内容
	        	if(mark.equalsIgnoreCase("byMsmGnssSatNum")){
	        		Object[] objectArray  = new Object[msmGnssSatNum];
	        		for(int i=0 ;i<msmGnssSatNum;i++){
	        			msmGnssSatNumIndex = i;
	        			Class<?> clazz=Class.forName(className);
	        			objectArray[i] = getObjectByRtcmObject(clazz,booleanData);
	        		}
	        		return objectArray;
	        		
	        	}
	        	//信号数据的内容
	        	else if(mark.equalsIgnoreCase("byMsmGnssSigNum")){
	        		Object[] objectArray  = new Object[msmGnssEleNum];
	        		for(int i=0; i<msmGnssEleNum;i++){
	        			msmGnssCleNumIndex = i;
	        			Class<?> clazz=Class.forName(className);
	        			objectArray[i] = getObjectByRtcmObject(clazz,booleanData);
	        		}
	        		return objectArray;
	        	}else{
	        		logger.info("注解错误无法解析array标准的属性");
	        	}
	        }
	        /**解码成object对象属性*/
	        else if(type.equalsIgnoreCase("object")){
	        	Class<?> clazz=Class.forName(className);
	        	return getObjectByRtcmObject(clazz,booleanData);
	        }
	        /**解码没有标注类型*/
	    	 return null;
	    }
	 
	 /**判定是否为武汉导航院协议*/
	 private  boolean checkWhProtocol(byte[] startData){
		 if(startData[0] == (byte)0xAA && startData[1] == (byte)0x44 && startData[2] == (byte)0x12){
			 return true;
		 }
		 return false;
	 }
	 
	public Object getObjectByBuffer(Class<?> clazz, ByteBuf in) throws Exception {
			// 实例化类
			Object obj = clazz.newInstance();
			// 得到类中private 的属性
			Field[] fields = clazz.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				// 得到属性对应的注释
				Annotation ano = field.getAnnotation(SubAnnotation.class);
				if (ano != null) {
					SubAnnotation sub = (SubAnnotation) ano;
					field.setAccessible(true);
					Object v = getValues(sub.type(), sub.len(),sub.className(), in);
					if (v == null) {
						logger.error("取值错误");
					} else {
						field.set(obj, v);// 得到此属性设值
					}

				}
			}
			return obj;
		}
	public Object getValues(String type, int len, String className, ByteBuf in) throws Exception {
		if (type.equalsIgnoreCase("byte")) {
				return in.readByte();
		}else if (type.equalsIgnoreCase("short")) {
			byte[] wys = new byte[len];
			in.readBytes(wys);
			return ByteConvert.bytesToShort(wys);
		}else if (type.equalsIgnoreCase("ushort")) {
			byte[] wys = new byte[len];
			in.readBytes(wys);
			return ByteConvert.bytesToUshort(wys);
		}else if (type.equalsIgnoreCase("int")) {
			byte[] wys = new byte[len];
			in.readBytes(wys);
			return ByteConvert.bytesToInt(wys);
		}else if (type.equalsIgnoreCase("uint")) {
			byte[] wys = new byte[len];
			in.readBytes(wys);
			return ByteConvert.bytesToUint(wys);
		}else if (type.equalsIgnoreCase("long")) {
			byte[] wys = new byte[len];
			in.readBytes(wys);
			return ByteConvert.bytesToLong(wys);
		} else if (type.equalsIgnoreCase("double")) {
			byte[] wys = new byte[len];
			in.readBytes(wys);
			return ByteConvert.bytesToDouble(wys);
		}else if(type.equalsIgnoreCase("object")){
        	Class<?> clazz=Class.forName(className);
        	return getObjectByBuffer(clazz,in);
        }
		else {
			logger.info("没有找到对应的类型");
			return null;
		}
	}
	 
}
