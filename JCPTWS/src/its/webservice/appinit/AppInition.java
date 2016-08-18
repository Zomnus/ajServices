/**
 * Copyright(c) SUPCON 2008-2011. 浙江浙大中控信息技术有限公司
 */

package its.webservice.appinit;


import its.webservice.activemq.ServiceTest;
import its.webservice.common.AppInitConstants;
import its.webservice.config.SystemConfig;
import its.webservice.dao.ApplicationDao;
import its.webservice.socket.SendAlarmInfo;
import its.webservice.socket.SendLiveInfo;
import its.webservice.socket.SendMQInfo;
import its.webservice.socket.SendReWritePicInfo;
import its.webservice.util.ItsUtility;

import java.lang.reflect.Field;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * 系统名称：智能交通WebService服务(ITSWebService)
 * 所属模块：系统初始化
 * 功能描述：系统启动时初始化
 * 文件名：its.webservice.control.AppInitController.java
 * 版本信息：1.00
 * 
 * 开发部门：研发中心
 * 创建者： lzk
 * 创建时间：Feb 14, 2011 1:15:35 PM
 * 修改者： lzk
 * 修改时间：Feb 14, 2011 1:15:35 PM
 */

public class AppInition implements InitializingBean,ApplicationContextAware{
	
	/** 获取Log4j实例 */
    protected Logger log = Logger.getLogger(AppInition.class.getName());
	
    private ApplicationDao applicationDao;
    
	public ApplicationDao getApplicationDao() {
		return applicationDao;
	}

	public void setApplicationDao(ApplicationDao applicationDao) {
		this.applicationDao = applicationDao;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
	//	log.debug("App.afterPropertiesSet init Start .....................");
	//	SendLiveInfo liveInfo = new SendLiveInfo();
	//	liveInfo.start();
	//	log.error("实时过车监听线程启动……");
    //	SendAlarmInfo alarmInfo = new SendAlarmInfo();
    //	alarmInfo.start();
    //	log.error("实时报警监听线程启动……");
	//	log.debug("App.afterPropertiesSet init End .....................");
		
		SendMQInfo mqSend=new SendMQInfo();
		new Thread(mqSend).start();
		log.error("实时过车带图片发送MQ启动……");
		
		log.error("重写图片线程启动");
		SendReWritePicInfo reWritePicInfo = new SendReWritePicInfo();
		new Thread(reWritePicInfo).start();
		
	}
    
	protected void initialize() {
	    log.debug("AppInition.initialize() Start...");
	    
	    /** 初始化数据字典 */
    	AppInitConstants.DICTIONARY_LIST = applicationDao.getDictionary();
	    
	  //初始化路口列表, 在路口变动时, 需要及时重设该变量的值
    	AppInitConstants.ALL_CROSS_MAP = applicationDao.getAllCrossList();
	    
	    /** 初始化系统配置信息 */
	    try {
	    	log.info("智能交通WebService服务初始化系统配置信息开始......");
	    	//数据库获得配置信息Map
	    	Map configMap = applicationDao.getAppConfigMap();
	    	//获得AppInitConstants所有属性字段
	    	Field fieldList[] = AppInitConstants.class.getDeclaredFields();
	    	log.debug("初始化AppInitConstants信息......");
	    	for(int i = 0; i < fieldList.length; i++){
	    		Field field = fieldList[i];
	    		//log.debug("field.toString===================="+field.getName());
	    		//当数据库字段与对象属性字段相同
	    		if(configMap.containsKey(field.getName())){
	    			Class type = field.getType();
	    			log.debug("field.getType()========="+type);
	    			//将指定对象变量上此 Field 对象表示的字段设置为指定的新值
	    			if(type.isInstance("")){
	    				field.set(AppInitConstants.class,configMap.get(field.getName()));
	    			}else if(type.toString().indexOf("int") != -1
                            || type.toString().indexOf("Integer") != -1){
	    				field.set(AppInitConstants.class,Integer.parseInt((String) configMap.get(field.getName())));
	    			}else if(type.toString().indexOf("Double") != -1
                            || type.toString().indexOf("double") != -1){
	    				field.set(AppInitConstants.class,(Double.valueOf((String)configMap.get(field.getName()))));
	    			}else if(type.toString().indexOf("Date") != -1){
	    				field.set(AppInitConstants.class,(ItsUtility.convertToDate((String)configMap.get(field.getName()),"yyyy-MM-dd")));
	    			}else if(type.toString().indexOf("Float") != -1
                            || type.toString().indexOf("float") != -1){
	    				field.set(AppInitConstants.class,(Float.valueOf((String)configMap.get(field.getName()))));
	    			}else if(type.toString().indexOf("String") != -1){
	    				field.set(AppInitConstants.class,(String)configMap.get(field.getName()));
	    			}else if(type.isInstance(Long.decode("1"))){
	    				field.set(AppInitConstants.class,(Long.valueOf((String)configMap.get(field.getName()))));
	    			}
	    			log.debug(field.toString()+"="+field.get(AppInitConstants.class));
	    		}
	    	}
	    	//从配置文件读取，add by Tony Lin on 2013-05-16
	    	if(!"".equals(SystemConfig.getConfigInfomation("app.socketserverip"))){
	    		AppInitConstants.SOCKET_PROXY_SERVER_IP = SystemConfig.getConfigInfomation("app.socketserverip");
	    	}
	    	if(!"".equals(SystemConfig.getConfigInfomation("app.socketserverport"))){
	    		AppInitConstants.SOCKET_PROXY_SERVER_PORT = Integer.parseInt(SystemConfig.getConfigInfomation("app.socketserverport"));
	    	}
	    	AppInitConstants.resource  = SystemConfig.getTgsConfigInfomation();  // by lvhua 2013-6-27  add
	    	log.info("智能交通WebService服务初始化系统配置信息结束......"); 
	    } catch(Exception e) {
	    	log.error("智能交通WebService服务初始化系统配置信息错误："+e.toString());
	    }
	    
	    log.debug("AppInition.initialize() End.");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		AppInitConstants.APPLICATIONCONTEXT = applicationContext;
		
	}

}
