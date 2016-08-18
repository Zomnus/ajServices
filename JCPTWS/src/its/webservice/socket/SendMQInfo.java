package its.webservice.socket;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import its.webservice.activemq.MessageSender;
import its.webservice.common.AppInitConstants;
import its.webservice.common.ItsConstants;

/**
 *@author zhangxiaoqiang
 *@date 2015上午11:18:57
 *@description 发送MQ线程
 */
public class SendMQInfo implements Runnable{
	Logger log=Logger.getLogger(SendMQInfo.class);
	@Override
	public void run() {
		while(true){
			int mqQueueSize = AppInitConstants.sendMQQueue.size();
			if (mqQueueSize>0) {
				boolean a= MessageSender.sendMessage(AppInitConstants.sendMQQueue.poll());
				if (a) {
					log.debug("本次发送实时过车数据到MQ成功，队列长度："+mqQueueSize);
					log.error("本次发送实时过车数据到MQ成功，队列长度："+mqQueueSize);
				}
			}else if(mqQueueSize==ItsConstants.MAX_STAY_MSG){
				AppInitConstants.sendMQQueue.poll();
			}else {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
	}

}
