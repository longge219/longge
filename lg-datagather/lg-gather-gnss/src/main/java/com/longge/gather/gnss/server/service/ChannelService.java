package com.longge.gather.gnss.server.service;
/**
 * @description channel管理业务处理接口
 * @author jianglong
 * @create 2018-09-06
 **/
public interface ChannelService {
	
	/**
	 * 判定是否已存储连接
	 * @param channelId
	 */
	public boolean  hasChannel(String channelId);
	
	/**
	 * 缓存当前正在工作的有效的Channel
	 * @param channelId
	 * @param receiverId
	 */
	public void cacheWorkingChannel(String channelId, String receiverId);
	
	/**
	 * 根据channelId查找接收机ID
	 * @param channelId
	 * @return receiverId
	 */
	public String getChannel(String channelId);
	
	/**
	 * 移除Channel
	 * @param channelId
	 */
	public void deleleChannel(String channelId);
	
	/**
	 * 服务端停止服务时，移除所有Channel
	 */
	public void deleteAllChannels();
	
	/**
	 * 当前已缓存的Channel数量
	 */
	public int channelSize();
}
