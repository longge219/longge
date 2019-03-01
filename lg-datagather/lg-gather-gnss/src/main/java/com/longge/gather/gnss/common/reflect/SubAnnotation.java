package com.longge.gather.gnss.common.reflect;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
@Retention(RUNTIME)
@Target(FIELD)
public @interface SubAnnotation {

	/**
	 * 类型
	 */
	public String type();

	/**
	 * 开始位
	 */
	public int startPos();
	
	/**
	 * 长度
	 */
	public int len();
	
	/**
	 * 备注
	 */
	public String mark();
	
	/**
	 * 类名
	 */
	public String className();
}
