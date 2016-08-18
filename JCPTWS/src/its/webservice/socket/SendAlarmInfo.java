/**
 * Copyright(c) SUPCON 2008-2011. 浙江浙大中控信息技术有限公司
 */

package its.webservice.socket;

import its.webservice.common.AppInitConstants;
import java.net.InetSocketAddress;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;


/**
 * 系统名称：智能交通集成平台(SMSS)
 * 所属模块：XXXXXXXXXXXXXX
 * 功能描述：XXXXXXXXXXXXXX
 * 文件名：com.supconit.its.socketproxy.SendLivePassVeh.java
 * 版本信息：1.00
 * 
 * 开发部门：研发中心
 * 创建者： lzk
 * 创建时间：2011-12-12 上午10:09:31
 * 修改者： lzk
 * 修改时间：2011-12-12 上午10:09:31
 */

public class SendAlarmInfo extends Thread {
	
	private final static Logger log = Logger.getLogger(SendAlarmInfo.class.getName());
	
	boolean isStillConn = false;
	
	String alarmInfo = "";
	
	int sleeptime = 1;
	
	NioSocketConnector connector = null;
	DefaultIoFilterChainBuilder chain = null;
	ConnectFuture cf = null;
	
	public SendAlarmInfo(){
	}
	
	
	public void run(){
		
		connector = new NioSocketConnector();
		chain = connector.getFilterChain();
		chain.addLast( "logger", new LoggingFilter() );
		chain.addLast( "codec", new ProtocolCodecFilter( new TextLineCodecFactory()) );
		connector.setHandler(new SendAlarmInfoHandler());
		connector.setConnectTimeoutMillis(500);
		cf = connector.connect(new InetSocketAddress(AppInitConstants.SOCKET_PROXY_SERVER_IP, AppInitConstants.SOCKET_PROXY_SERVER_PORT));//建立连接 
		cf.awaitUninterruptibly();//等待连接创建完成 
		//若在指定时间内没连接成功，则抛出异常
		if(cf.isDone()){
			try {
				while  (cf.getSession().isConnected()) {
					while  (!AppInitConstants.vehAlarmInfoQueue.isEmpty() && AppInitConstants.vehAlarmInfoQueue.size()>0) {
						log.debug("Alarm queue remain size : " + AppInitConstants.vehAlarmInfoQueue.size());
						alarmInfo = AppInitConstants.vehAlarmInfoQueue.take();
						cf.getSession().write(alarmInfo);//发送消息 
					}
					try {
						Thread.sleep( 500 );
						//log.debug("报警队列空闲，等待中……");
					} catch (InterruptedException e) {
						//e.printStackTrace();
						log.error("send alarm thread sleep error："+e.getMessage());
					}
					sleeptime = 1;
				}
			} catch (Exception e) {
				//e.printStackTrace();
				log.error("send alarm connect to SMSS error："+e.getMessage());
				isStillConn = false;
				connector.dispose(); 
			}
		}
		while(true){
			try{
				isStillConn = cf.getSession().isConnected();
				log.debug("send alarm is connect?"+cf.getSession().isConnected());
			}catch(Exception e){
				//e.printStackTrace();
				log.debug("send alarm connect to smss failed ..." + e.getMessage());
				isStillConn = false;
				connector.dispose(); 
			}
			if(!isStillConn){
				log.debug("send alarm restart connect to smss at " + new Date());
				try {
					Thread.sleep( 60000*sleeptime );
					sleeptime++;
					if(sleeptime >= 60)
						sleeptime = 60;
					//log.debug("send alarm wait connect to smss...");
				} catch (InterruptedException e) {
					//e.printStackTrace();
					log.error("send alarm connect to SMSS failed："+e.getMessage());
				}
				
				this.run();
			}else{
				sleeptime = 1;
			}
			
		}
		
	}
	
	
	public static void main(String[] args) {
		// 创建客户端连接器. 
		//NioSocketConnector connector = new NioSocketConnector(); 
		IoConnector connector = new NioSocketConnector(); 
		connector.getFilterChain().addLast( "logger", new LoggingFilter() ); 
		//connector.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new TextLineCodecFactory( Charset.forName( "UTF-8" )))); //设置编码过滤器
		//TextLineCodecFactory:只要发现哪个字节里存放的是ASCII 的10、13 字符（\r、\n），就认为之前的字节就是一个字符串（默认使用UTF-8 编码）。
		connector.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new TextLineCodecFactory()));
		//connector.setConnectTimeout(30); 
		connector.setConnectTimeoutMillis(500);
		//SendLiveInfoHandler slpvh = new SendLiveInfoHandler();
		connector.setHandler(new SendLiveInfoHandler());//设置事件处理器  
		ConnectFuture cf = connector.connect(new InetSocketAddress(AppInitConstants.SOCKET_PROXY_SERVER_IP, AppInitConstants.SOCKET_PROXY_SERVER_PORT));//建立连接 
		cf.awaitUninterruptibly();//等待连接创建完成 
		cf.getSession().write("test");//发送消息 
		cf.getSession().getCloseFuture().awaitUninterruptibly();//等待连接断开 
		connector.dispose(); 
		
	}

}
