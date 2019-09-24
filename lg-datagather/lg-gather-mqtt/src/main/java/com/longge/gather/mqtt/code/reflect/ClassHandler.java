package com.longge.gather.mqtt.code.reflect;

public class ClassHandler {
	
	public ClassHandler() {
		super();
	}
	public ClassHandler(Class<?> clazz) {
		super();
		this.clazz = clazz;
	}
   
	private Class<?> clazz; 
	
   
	public Class<?> getClazz() {
	   return clazz;
	}
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	} 

}
