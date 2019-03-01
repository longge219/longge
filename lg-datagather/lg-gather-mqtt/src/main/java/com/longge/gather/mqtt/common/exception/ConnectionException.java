package com.longge.gather.mqtt.common.exception;
/**
 * @description 连接异常
 * @author jianglong
 * @create 2019-03-01
 **/
public class ConnectionException extends  RuntimeException {

	private static final long serialVersionUID = -4244869151919296416L;

	public ConnectionException(String message) {
        super(message);
    }
}
