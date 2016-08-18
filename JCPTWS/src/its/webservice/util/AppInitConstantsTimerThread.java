package its.webservice.util;

import its.webservice.common.AppInitConstants;
import its.webservice.config.SystemConfig;
import its.webservice.dao.ApplicationDao;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Timer;

import org.apache.log4j.Logger;


public class AppInitConstantsTimerThread {
	/** 获取Log4j实例 */
    protected      Logger log = Logger.getLogger(AppInitConstantsTimerThread.class.getName());
    
  
    private ApplicationDao applicationDao;
    
	public ApplicationDao getApplicationDao() {
		return applicationDao;
	}

	public void setApplicationDao(ApplicationDao applicationDao) {
		this.applicationDao = applicationDao;
	}

	static  Timer timer = null;
	static  ReschedulableTimerTask task = null;
	public    void initialize() {
		timer = new Timer();  // 定时初始化AppInitConstants定时器
		   task= new ReschedulableTimerTask() {   
	         @Override  
	         public void run() {   
	        		initializeTimer();
	            }   
	        };   
	        timer.schedule(task, AppInitConstants.RE_APPINT_TIME *1000, AppInitConstants.RE_APPINT_TIME*1000); //定期执行一次   
	      }
	
    /**
    *定时初始化AppInitConstants服务
    *@author LVHUA
    *@since  2013-07-03
    *@return void
    */	
    public    void initializeTimer(){
    	    task.setPeriod(AppInitConstants.RE_APPINT_TIME*1000);    //修改定时器执行时间   
    	    log.debug("定时初始化AppInitConstants定时器开启.....");
    	    try {
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
    	    	
    	    } catch(Exception e) {
    	    	log.error("定时初始化智能交通WebService服务系统配置信息错误："+e.toString());
    	    }
		   
		}

}
