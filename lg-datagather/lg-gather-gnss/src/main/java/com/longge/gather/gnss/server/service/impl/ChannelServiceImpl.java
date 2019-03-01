package com.longge.gather.gnss.server.service.impl;
import java.util.Date;
import com.longge.gather.gnss.server.channel.ChannelManager;
import com.longge.gather.gnss.server.service.ChannelService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
/**
 * @description channel管理业务处理接口实现
 * @author jianglong
 * @create 2018-09-06
 **/
@Service
public class ChannelServiceImpl implements ChannelService {
	
	//channel管理单例
	private static ChannelManager channelManager = ChannelManager.INSTANCE;
	
	private static final Logger logger = LogManager.getLogger(ChannelServiceImpl.class); 

	
	/**
	 * 判定是否已存储连接
	 * @param channelId
	 */
	public boolean  hasChannel(String channelId){
		return channelManager.hasChannel(channelId);
	}
	
	/**
	 * 缓存当前正在工作的有效的Channel
	 * @param channelId
	 * @param: receiverId
	 */
	@Override
	public void cacheWorkingChannel(String channelId, String siteNo) {
		channelManager.addChannel(channelId, siteNo);
	}

	/**
	 * 根据channelId查找接收机ID
	 * @param channelId
	 * @return receiverId
	 */
	@Override
	public String getChannel(String channelId){
		return channelManager.getChannel(channelId);
	}

	/**
	 * 移除Channel
	 * @param channelId
	 * @param: receiverId
	 */
	@Override
	public void deleleChannel(String channelId){
	    //删除缓存数据
		channelManager.deleteChannel(channelId);
	}

	/**
	 * 移除所有Channel
	 */
	@Override
	public void deleteAllChannels() {
		channelManager.deleteAllChannels();
	}

	/**
	 * 当前已缓存的Channel数量
	 */
	@Override
	public int channelSize() {
		return channelManager.channelSize();
	}

}
