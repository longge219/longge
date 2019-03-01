package com.longge.gather.gnss.server.handler;
import java.util.Optional;
import javax.annotation.Resource;
import com.longge.gather.gnss.common.protocal.rtcm32.arp.Arp_1006;
import com.longge.gather.gnss.common.protocal.rtcm32.assistoperate.ephemeris.BDSEphemeris_1046;
import com.longge.gather.gnss.common.protocal.rtcm32.assistoperate.ephemeris.GpsEphemeris_1019;
import com.longge.gather.gnss.common.protocal.rtcm32.msm.data.Msm_4;
import com.longge.gather.gnss.common.protocal.wh.WhBDInoutcInfo;
import com.longge.gather.gnss.common.protocal.wh.WhInoutcInfo;
import com.longge.gather.gnss.server.service.ChannelService;
import com.longge.gather.gnss.server.service.ServerHandlerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
/**
 * @description 服务端协议处理
 * @author jianglong
 * @create 2018-07-10
 **/
@ChannelHandler.Sharable
@Component
public class ServerHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = LogManager.getLogger(ServerHandler.class);

	@Resource
	private ServerHandlerService serverHandlerServiceImpl;
	
	@Resource
	private ChannelService channelServiceImpl;


	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.info("建立新的Channel，缓存Channel：channelId [ " + ctx.channel().id().asLongText() + " ]");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		logger.info("Channel：channelId [ " + ctx.channel().id().asLongText() + " ]主动断开了连接.");
		channelServiceImpl.deleleChannel(ctx.channel().id().asLongText(),true);
	}

	/**
	 * 服务端业务接收到数据
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		 Optional.ofNullable(msg).ifPresent((value) -> doMessage(ctx,msg));
	}

	/**
	 * 服务端业务接收到数据
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	/**
	 * 服务端处理异常
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.info("服务端数据包解码出错------------------------------------");
		cause.printStackTrace();
		ctx.flush();
	}
	
	/**
	 * 超时处理
	 */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
           if(evt instanceof IdleStateEvent) {
        	   IdleStateEvent event = (IdleStateEvent) evt;
        		if (event.state() == IdleState.READER_IDLE) {
               	  //关闭连接
                  logger.info("Channel：channelId [ " + ctx.channel().id().asLongText() + " ]出现网络故障无数据上传");
            	  channelServiceImpl.deleleChannel(ctx.channel().id().asLongText(),true);
    			} 
            } else {
                 // 传递给下一个处理程序
                 super.userEventTriggered(ctx, evt);
            }
    }
	
	
	/**
	 * 处理报文消息
	 */
    private void doMessage(ChannelHandlerContext ctx, Object msg){
    	//会话ID
    	String channelId = ctx.channel().id().asLongText();
    	if(!(channelServiceImpl.hasChannel(channelId)) && !(msg instanceof Arp_1006)){
                logger.info("该接收机还未正确鉴权通过............");
                return;
    	}
    	String receiverId = channelServiceImpl.getChannel(channelId);
    	/**处理卫星观测数据MSM4*/
       if(msg instanceof Msm_4){
    		 try{
    			 Msm_4 msm4 = (Msm_4)msg;
    			 serverHandlerServiceImpl.doMsm4(msm4, receiverId);
    		 }catch(Exception e){
    			  logger.error("处理MSM_4业务出错");
    			  e.printStackTrace();
    		 }
    	 }
       /**处理GPS星历数据1019*/
       else if(msg instanceof GpsEphemeris_1019){
    		 try{
    			 GpsEphemeris_1019 ge1019 = (GpsEphemeris_1019)msg;
    			 serverHandlerServiceImpl.doGpsEphemeris1019(ge1019, receiverId);
    		 }catch(Exception e){
    			 logger.error("处理GpsEphemeris_1019业务出错");
    			 e.printStackTrace();
    		 }
    	 }
       /**处理BDS星历数据1042*/
       else if(msg instanceof BDSEphemeris_1046){
    		 try{
    			 BDSEphemeris_1046 bdsE1046 = (BDSEphemeris_1046)msg;
    			 serverHandlerServiceImpl.doBDSEphemeris1046(bdsE1046, receiverId);
    		 }catch(Exception e){
    			 logger.error("处理BDSEphemeris_1046业务出错");
    			 e.printStackTrace();
    		 }
    	 }
       /**收到接收机描述数据1006*/
       else if(msg instanceof Arp_1006 ){
    		   try{
    			   Arp_1006 arp1006 = (Arp_1006) msg;
    				//数据处理
    			   String channelID  = ctx.channel().id().asLongText();
    			   serverHandlerServiceImpl.doArp1006(channelID,arp1006);
    		   }catch(Exception e){
    			   logger.error("处理固定天线数据业务出错");
    			   e.printStackTrace();
    		   }
    	 }
       /**处理武汉导航院GPS电离层模型改正参数报文*/
       else if(msg instanceof WhInoutcInfo){
    		 WhInoutcInfo whInoutcInfo = (WhInoutcInfo) msg;
    		 try{
    			 serverHandlerServiceImpl.doWhGPSInoutcInfo(whInoutcInfo,receiverId);
    		 }catch(Exception e){
    			 logger.error("处理武汉导航院电GPS离层模型改正参数协议处理");
    			 e.printStackTrace();
    		 }
    	 }
       /**处理武汉导航院BD电离层模型改正参数报文*/
       else if(msg instanceof WhBDInoutcInfo){
    	   WhBDInoutcInfo whBDInoutcInfo = (WhBDInoutcInfo) msg;
    		 try{
    			 serverHandlerServiceImpl.doWhBDInoutcInfo(whBDInoutcInfo, receiverId);
    		 }catch(Exception e){
    			 logger.error("处理武汉导航院BD电离层模型改正参数协议处理");
    			 e.printStackTrace();
    		 }
    	 }
    }

}
