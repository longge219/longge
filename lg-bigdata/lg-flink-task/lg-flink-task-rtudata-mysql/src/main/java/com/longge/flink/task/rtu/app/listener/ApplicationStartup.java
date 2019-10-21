package com.longge.flink.task.rtu.app.listener;
import com.longge.flink.task.rtu.app.start.ServerStart;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
/**
 * @description 监听应用启动完成后触发事件
 * @author jianglong
 * @create 2019-10-17
 */
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if(event.getApplicationContext().getParent() == null){
            //启动服务
            ApplicationContext applicationContext = event.getApplicationContext();
            ServerStart serverStart =  applicationContext.getBean(ServerStart.class);
            serverStart.start();
        }
    }
}
