package com.longge.gather.gnss.server.channel;
import java.util.concurrent.ConcurrentHashMap;
/**
 * @description channel管理
 * @author jianglong
 * @create 2018-07-10
 **/
public enum ChannelManager {

	INSTANCE;

	// 全局channel缓存
	private static ConcurrentHashMap<String, String> channels = new ConcurrentHashMap<>();
	
	//判定是否已缓存连接
	public boolean  hasChannel(String channelId){
		return channels.containsKey(channelId);
	}

	/** 根据channelId查找receiverId*/
	public String getChannel(String channelId) {
		return channels.get(channelId);
	}

	/** 移除channel */
	public void deleteChannel(String channelId) {
		channels.remove(channelId);
	}
	
	/**
	 * 移除所有Channel
	 */
	public void deleteAllChannels() {
		channels.clear();
	};

	/**添加channel*/
	public void addChannel(String channelId, String siteNo) {
		channels.put(channelId, siteNo);
	}
	
	/**
	 * 当前已缓存的Channel数量
	 * @return
	 */
	public int channelSize() {
		return channels.size();
	}

}
