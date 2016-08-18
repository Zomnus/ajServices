package its.webservice.util;

import its.webservice.common.AppInitConstants;
import its.webservice.dao.ConnectionDao;
import its.webservice.entity.ConnectionedBean;
import its.webservice.entity.InsertConnectionedBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


public class TimerThread {
	/** 获取Log4j实例 */
    protected      Logger log = Logger.getLogger(TimerThread.class.getName());
    
    static Map<String,String>  tableMap = new HashMap<String,String>();
    static{
     tableMap.put("0", "COMPUTER_CONNECTIONS");
     tableMap.put("1", "DEVICE_EPS_INFO");
     tableMap.put("2", "DEVICE_TGS_INFO");
     tableMap.put("4", "DEVICE_CCTV_INFO");
     tableMap.put("6", "DEVICE_LED_INFO");
     tableMap.put("7", "DEVICE_SIGNAL_INFO");
    } 
  
	private ConnectionDao connectionDao;
	
	public ConnectionDao getConnectionDao() {
		return connectionDao;
	}

	public void setConnectionDao(ConnectionDao connectionDao) {
		this.connectionDao = connectionDao;
	}

	static Timer timer = null;
	public    void checkDeviceStatus() {
		timer = new Timer();  // 定时检测网络定时器
	   /* long day =  24 * 60 * 60 * 1000; 
		Calendar calendar = Calendar.getInstance(); 
	    calendar.add(Calendar.DATE, 1); 
	    calendar.set(Calendar.HOUR_OF_DAY, 0); 
	    calendar.set(Calendar.MINUTE, 00); 
	    calendar.set(Calendar.SECOND, 0); */
		 
		timer.schedule(new TimerTask() {
				@Override
				public synchronized void run() {
				   checkDeviceStatusTimer();
				}
	     },AppInitConstants.TELNET_NETWORK_TIME *1000, AppInitConstants.TELNET_NETWORK_TIME*1000);
		 
	}
	
    /**
    *定时检测网络服务
    *@author LVHUA
    *@since  2013-06-27
    *@return void
    */	
    public    void checkDeviceStatusTimer(){
    	    if(AppInitConstants.NETWORK_OPEN.equals("1")) {
    	    log.debug("定时检测网络服务 定时器开启.....");
			log.debug("TimerThread.checkDeviceStatusJob()  Start......");
		    long start = System.currentTimeMillis();																					
	        long end;																																		
			long total = 0;	
			String status = "";
			String  updateSql = "";
			String  device_message_str ="";  // by  lvhua  2013-07-16 add
			List<ConnectionedBean> connectionedBeanList = null;
			List<InsertConnectionedBean>  insertConnectionedBeanList  = new ArrayList<InsertConnectionedBean>();
		    try{  
		        connectionedBeanList	 = connectionDao.getAllDeviceInfoList();
		        if(null!= connectionedBeanList  && connectionedBeanList.size()>0){
		        	for (ConnectionedBean connectionedBean : connectionedBeanList) {
		        		InsertConnectionedBean  insertConnectionedBean = new  InsertConnectionedBean();
		        		if(PingNetWork.validateServerConnections(connectionedBean.getIp(),  (null!= connectionedBean.getPort() && StringUtils.isNotEmpty(connectionedBean.getPort())?connectionedBean.getPort():"445"),   "")){
		        			status = "0";  // 网络通畅	
		        			insertConnectionedBean.setCj_time(TimeUtil.returnNowDate("yyyy-MM-dd hh:mm:ss")); // lvhua 2013-07-04
		        		}else{
		        			status = "1";  //   网络阻塞
		        			insertConnectionedBean.setCj_time(TimeUtil.returnNowDate("yyyy-MM-dd hh:mm:ss")); // lvhua 2013-07-04
		        			device_message_str += connectionedBean.getName()+",";
		        		} 
		        		updateSql =  getSelectTableName(connectionedBean.getTablename(),connectionedBean.getIp(),status);
		        		if(connectionDao.updateDeviceStatus(updateSql)){ //  数据库更新设备连接状态
	        				log.debug("IP:"+connectionedBean.getIp()+" 数据库网络连接状态字段更新成功!");  
	        			}else{
	        				log.error("IP:"+connectionedBean.getIp()+" 数据库网络连接状态字段更新失败!"); 
	        			}
		        	    insertConnectionedBean.setDevice_name(connectionedBean.getName());
		      			insertConnectionedBean.setDevice_code(connectionedBean.getTablename().equals("0")?connectionedBean.getId():connectionedBean.getIds());  // 设备code  lvhua 2013-07-04
		      			insertConnectionedBean.setDeviceNameType(connectionedBean.getTablename());  // lvhua 2013-07-04
		      			insertConnectionedBean.setConnected(status);   // lvhua 2013-07-04
		      			insertConnectionedBean.setCheck_source("系统");
		      			insertConnectionedBeanList.add(insertConnectionedBean);   //  存放设备网络历史检测信息至 List 集合  // lvhua 2013-07-04
			        } 
		        }
		        if(null!= device_message_str  && StringUtils.isNotEmpty(device_message_str)){
		        	device_message_str = "设备:"+ device_message_str.substring(0, device_message_str.length()-1) +"网络出现异常,请尽快检查谢谢!";
		        }                                            //  device_message_str  插入插入更新短信消息服务信息  by lvhua  2013-07-16
		        if(connectionDao.addDeviceOnLine(insertConnectionedBeanList,device_message_str) ){   // 插入设备网络检测历史记录信息  by lvhua  2013-07-04
		        	log.debug("插入设备在线网络检测历史记录成功! ");    
		        }else{
		            log.error("插入设备在线网络检测历史记录失败! "); 
		        }
		     
			} catch(Exception e){
				log.error( "网络检测失败 ！" + e.getMessage());
			}
			end = System.currentTimeMillis();
			total = end - start;
			long sec = total / 1000;		//导入时间精确到秒
			log.debug("本次定时检测总共耗时： " + sec / 60 + " 分 " + sec % 60 + " 秒"+total%1000+"毫秒。 ");
			log.debug("TimerThread.checkDeviceStatusJob()  End......");
		    }  else{
		    	log.debug("定时检测网络服务 定时器处于关闭状态!");
		    }
		}
    
    
    /**
     * 获取设备对应表名
     * @author lvhua
     * @param  tableNam, status
     * @TIME  2011-11-23 
     * @return Map
     */
    public   String getSelectTableName(String tableName,String ip,String status){
    	StringBuffer  returnTableNameStr = new StringBuffer("UPDATE ");
        Map<String,String>  deviceMap = new HashMap<String,String>(); 
        Iterator dtit = tableMap.entrySet().iterator();
        while(dtit.hasNext()){
            Map.Entry entry =(Map.Entry)dtit.next();
            if(null!=tableName && tableName.equals(entry.getKey().toString())){
            	if(tableName.startsWith("0")){
            		returnTableNameStr.append(entry.getValue() +" SET STATUS ='"+status+"'  WHERE　IP ='"+ip+"'" );
            	}else
            	returnTableNameStr.append(entry.getValue() +" SET COMMUNICATE_STATUS ='"+status+"' WHERE　IP_ADDRESS ='"+ip+"'"  );
            } 
          	 
     }
        log.debug("returnTableNameStr:"+returnTableNameStr.toString());   
    	return  returnTableNameStr.toString();
    }
    
    

}
