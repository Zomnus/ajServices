package its.webservice.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class PingNetWork {
	// The default daytime port 445 135 139, 要建立局域网必须开放这三个端口,
	// 因此默认要ping的服务器均开放了445端口
	static int DAYTIME_PORT = 445;
	
    protected static Logger log = Logger.getLogger(PingNetWork.class.getName());


	public static void main(String[] args) {
		try {
			PingNetWork.validateServerConnections("10.10.76.71", "345","ping");
			PingNetWork.validateServerConnections("10.10.76.75", "3000","telnet");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 验证网络连接状态[ping telnet]
	 * @param  host String
	 * @param  port  String
	 * @param  type  String
	 * @return boolean 测试成功返回true, 失败false
	 * @author LVHUA 
	 * @date   2012-02-27
	 */
	public static boolean validateServerConnections(String host, String port, String type) throws InterruptedException, IOException {
		Socket server = null;
		InetAddress address = null;
		InetSocketAddress picAddress = null;
		
		if (StringUtils.isNotEmpty(host)) {  	// 利用Socket的connect(SocketAddress endpoint, int  timeout)方法可以实现telnet的功能，如果catch到异常说明telnet失败
			try {
				server = new Socket();
				picAddress = new InetSocketAddress(host, Integer.valueOf(port));
				server.connect(picAddress, 5000);  // 等待5秒钟
				if (server.isConnected()) {
					log.debug(host + " Telnet成功!");
					server.close();
					return true;
				} else {
					server.close();
					log.debug(host + " Telnet失败!");
					return false;
				}
			} catch (UnknownHostException e) {
				log.debug(host + " Telnet失败!");
			} catch (IOException e) {
				log.debug(host + " Telnet失败!");
				return false;
			} finally {
				server.close();
			}
		}
		
	/*	if (StringUtils.isNotEmpty(host) && type.equals("ping")) {
			try {
				if (StringUtils.isNotEmpty(host) && type.equals("ping")) {
					address = InetAddress.getByName(host);
					if (address.isReachable(5000)) { //    等待5秒钟 利用InetAddress的isReachable方法可以实现ping的功能
						log.debug(host + " Ping成功!");
						return true;
					} else {
						log.debug(host + " Ping失败!");
						return false;
					}
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
				log.debug(host + " Ping失败!");
			} catch (IOException e) {
				e.printStackTrace();
				log.debug(host + " Ping失败!");
			} finally {
				;
			}
		}*/
		return false;
	}

	
	/**
	 * 验证网络连接状态[ping telnet]
	 * @param  host String
	 * @param  port  String
	 * @param  type  String
	 * @return boolean 测试成功返回true, 失败false
	 * @author LVHUA 
	 * @date   2012-02-27
	 */
	public static  String  pingDeviceWork(String host, String port, String type) throws InterruptedException, IOException {
		Socket server = null;
		InetAddress address = null;
		InetSocketAddress picAddress = null;
		String  flag = "1";
		
		if (StringUtils.isNotEmpty(host)) {  	// 利用Socket的connect(SocketAddress endpoint, int  timeout)方法可以实现telnet的功能，如果catch到异常说明telnet失败
			try {
				server = new Socket();
				picAddress = new InetSocketAddress(host, Integer.valueOf(port));
				server.connect(picAddress, 5000);  // 等待5秒钟
				if (server.isConnected()) {
					server.close();
					return "0";
				} else {
					server.close();
					return "1";
				}
			} catch (UnknownHostException e) {
				//log.debug(host + " Telnet失败!");
				return "1";
			} catch (IOException e) {
				//log.debug(host + " Telnet失败!");
				return "1";
			} finally {
				server.close();
			}
		}
		
	/*	if (StringUtils.isNotEmpty(host) && type.equals("ping")) {
			try {
				if (StringUtils.isNotEmpty(host) && type.equals("ping")) {
					address = InetAddress.getByName(host);
					if (address.isReachable(5000)) { //    等待5秒钟 利用InetAddress的isReachable方法可以实现ping的功能
						log.debug(host + " Ping成功!");
						return "0";
					} else {
						log.debug(host + " Ping失败!");
						return "1";
					}
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
				//log.debug(host + " Ping失败!");
				return "1";
			} catch (IOException e) {
				e.printStackTrace();
				//log.debug(host + " Ping失败!");
				return "1";
			} finally {
				;
			}
		}*/
		return flag;
	}
}
