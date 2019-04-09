package com.longge.gather.gnss.server.reflect;

public class ClassProcess extends ClassMap {
	public ClassProcess(){
		init();
	}
	
	public Class<?> getClassByTag(Integer tag) {
		ClassHandler handlerClass=map.get(tag);
		return handlerClass.getClazz();
	}
	
	public boolean verifyTag(Integer tag) {
		ClassHandler handlerClass=map.get(tag);
		if(handlerClass !=null){
			return true;	
			}else{
				return false;
			}
	}

}
