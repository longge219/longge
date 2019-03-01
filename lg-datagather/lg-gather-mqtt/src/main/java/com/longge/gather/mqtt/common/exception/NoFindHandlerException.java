package com.longge.gather.mqtt.common.exception;
/**
 * @description 处理异常
 * @author jianglong
 * @create 2018-03-01
 **/
public class NoFindHandlerException extends  RuntimeException{
    
	private static final long serialVersionUID = 7537916869603226336L;

	public NoFindHandlerException(String message) {
        super(message);
    }
}
