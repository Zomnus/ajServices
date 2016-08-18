/**
 * Copyright(c) SUPCON 2008-2011. 浙江浙大中控信息技术有限公司
 */

package its.webservice.socket;

import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;


/**
 * 系统名称：智能交通集成平台(SMSS)
 * 所属模块：车辆监控-布控过车数据
 * 功能描述：布控过车数据发送
 * 文件名：com.supconit.its.socketproxy.SendLivePassVehHandler.java
 * 版本信息：1.00
 * 
 * 开发部门：研发中心
 * 创建者： lzk
 * 创建时间：2011-12-12 上午10:15:09
 * 修改者： lzk
 * 修改时间：2011-12-12 上午10:15:09
 */

public class SendAlarmInfoHandler extends IoHandlerAdapter{
	
	 private final Logger log = Logger.getLogger(SendAlarmInfoHandler.class.getName());
	 
	//6000s
	//private final int IDLE = 6000;
	
	 /**
	     *  构造函数
	     */
	public SendAlarmInfoHandler(){
	}
	
	@Override
    public void exceptionCaught(IoSession session, Throwable cause)
            throws Exception {
        //session.close();
		//用于关闭IoSession，该操作也是异步的，参数指定true 表示立即关闭，否则就在所有的写操作都flush 之后再关闭。
        session.close(true);
        //log.debug("session occured exception, so close it.");
        log.debug("pass alarm client session occured exception, so close it." + cause.getMessage());
    }
	
	@Override
	public void sessionCreated(IoSession session) {
		// 
		log.debug("pass alarm Connect to Server ["+session.getServiceAddress().toString()+"] success.");
		//session.write("Connected!");
	}
	
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		String str = message.toString();
		log.debug("pass alarm Client receive Message ..." + str);
		log.debug("与服务端"+((InetSocketAddress) session.getServiceAddress()).getAddress().getHostAddress()+"连接成功！");
		
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		 log.debug("pass alarm client sessionClosed.");
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		log.debug("pass alarm client session idle, so disconnecting......");
        //session.close();
        session.close(true);
        log.debug("pass alarm client disconnected.");
	}
	
	// 
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		 log.debug("pass alarm message has Sent.");
		// disconnect an idle client
		//session.close();
    }
	
	@Override
    public void sessionOpened(IoSession session) throws Exception {
		log.debug("pass alarm client sessionOpened.");
        //void setIdleTime(IdleStatus status,int idleTime)：这个方法设置关联在通道上的读、写或者是读写事件在指定时间内未发生，该通道就进入空
		//闲状态。一旦调用这个方法，则每隔idleTime 都会回调过滤器、IoHandler 中的sessionIdle()
		//方法。
        //session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, IDLE);
    }

}
