package com.longge.gather.gnss.app.listener;
import com.longge.gather.gnss.app.init.SystemBean;
import com.longge.gather.gnss.server.start.ServerStart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
/**
 * @description 监听应用启动完成后触发事件
 * @author jianglong
 * @create 2018-07-31
 */
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {
	
	private static final Logger logger = LogManager.getLogger(ApplicationStartup.class);
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		if(event.getApplicationContext().getParent() == null){
			ApplicationContext  applicationContext = event.getApplicationContext();
			SystemBean systemBean = applicationContext.getBean(SystemBean.class);
			if(systemBean.getSocketType().equals("server")){
				logger.info("以服务端方式启动采集程序............");
				ServerStart serverStart =  applicationContext.getBean(ServerStart.class);
				serverStart.start();
			}else{
				logger.error("socket类型配置出错................."+systemBean.getSocketType());
			}

		}
	}

}
