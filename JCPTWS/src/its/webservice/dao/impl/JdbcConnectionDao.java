//-------------------------------------------------------------
//$Id: JdbcConnectionDao.java, v 1.5 2010/01/13 13:06:18
//$Exp Copyright(c) SUPCON 2008-2010. All Rights Reserved.
//-------------------------------------------------------------
package its.webservice.dao.impl;

import its.webservice.common.BaseRowMapper;
import its.webservice.dao.ConnectionDao;
import its.webservice.entity.ConnectionedBean;
import its.webservice.entity.InsertConnectionedBean;
import its.webservice.util.TimeUtil;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

//*****************************************************************************
//用户名            交警
//系统名           智能交通集成平台
//子系统名          运行维护
//描述              PING 电脑的连接状态相关处理DAO实现类
/**
 * JdbcConnectionDao DAO Class<br>
 *
 * @author SUPCON
 * @version Version 1.0
 */
//更新履历
//2010－01－13：创建
//*****************************************************************************
public class JdbcConnectionDao extends JdbcTemplate implements ConnectionDao {

    
    
	/**
     * @method 检测ip是否重复JdbcDao
     * @author LvHua
     * @param  ip     
     * @return ConnectionedBean
     * @date   2011-07-18
    */ 
	@SuppressWarnings("unchecked")
	public  ConnectionedBean  getConnectionBeanById(String ip,String port){
			 logger.debug("JdbcConnectionDao.getConnectionBeanById() Start...");
			 StringBuffer selectSql = new StringBuffer("SELECT  IP, PORT   FROM   COMPUTER_CONNECTIONS  WHERE ");
		     selectSql.append(" IP  = ? AND PORT = ? ");
			 logger.debug(selectSql.toString());
			 List<ConnectionedBean>  connectionedList  =  this.query(selectSql.toString() ,new Object[]{ip,port},new int[]{Types.VARCHAR,Types.VARCHAR},  new BaseRowMapper(ConnectionedBean.class));
			 logger.debug("JdbcConnectionDao.getConnectionBeanById() End...");
			 if(null!= connectionedList  && connectionedList.size()>0){
					return connectionedList.get(0);
		     }else
		            return null;
	}
	
	/**
     * 取得所有设备连接状态CONNECTED信息
     * @author lvhua
     * param  table
     * @TIME  2011-11-23 
     * @return Map
     */
    @SuppressWarnings("unchecked")
    public List<ConnectionedBean> getAllDeviceInfoList() {
        logger.debug("JdbcConnectionDao.getAllDeviceInfoList() Start...");
           List<ConnectionedBean> connectedInfoList = null;
           StringBuffer  selectSql = new  StringBuffer("");     
	       selectSql.append( " SELECT A.ID, A.NAME, A.IP, A.PORT, A.IDS, A.CONNECTED  ,A.TABLENAME FROM ( " );
	       selectSql.append( " SELECT ID,   NAME , IP , PORT ,  IDS ,  CONNECTED ,TABLENAME FROM( " );
	       selectSql.append( " SELECT PC_NO AS ID,NAME, IP, PORT, NULL AS IDS , STATUS_FLAG AS CONNECTED ,  " ); 
	       selectSql.append( " '0' as TABLENAME " );  
	       selectSql.append( " FROM COMPUTER_CONNECTIONS   WHERE STATUS_FLAG = '0' " );
	       selectSql.append("  ORDER BY TO_NUMBER(PC_NO) DESC ) " );
	       selectSql.append( " UNION   " );
	       selectSql.append( " SELECT ID,   NAME , IP,  PORT ,  IDS ,  CONNECTED   ,TABLENAME  FROM( " );
	       selectSql.append( " SELECT T.DEVICE_ID AS ID  ,T.DEVICE_NAME AS NAME ,T.IP_ADDRESS AS IP,T.PORT AS PORT, T.DEVICE_CODE AS IDS,T.COMMUNICATE_STATUS  AS CONNECTED ,  " );
	       selectSql.append( " '4' as TABLENAME " );  
	       selectSql.append( " FROM  DEVICE_CCTV_INFO  T)   " );
	       selectSql.append( " UNION   " );
	       selectSql.append( " SELECT ID,   NAME , IP , PORT ,  IDS ,  CONNECTED   ,TABLENAME  FROM( " );
	       selectSql.append( " SELECT T.DEVICE_ID AS ID  ,T.DEVICE_NAME AS NAME ,T.IP_ADDRESS AS IP,T.PORT AS PORT, T.DEVICE_CODE AS IDS,T.COMMUNICATE_STATUS  AS CONNECTED , " );
	       selectSql.append( " '1' as TABLENAME " );  
	       selectSql.append( " FROM  DEVICE_EPS_INFO  T)  " );
	       selectSql.append( " UNION    " );
	       selectSql.append( " SELECT ID,   NAME , IP , PORT ,  IDS ,  CONNECTED   ,TABLENAME  FROM( " );
	       selectSql.append( " SELECT T.DEVICE_ID AS ID  ,T.DEVICE_NAME AS NAME ,T.IP_ADDRESS AS IP,T.PORT AS PORT, T.DEVICE_CODE AS IDS,T.COMMUNICATE_STATUS  AS CONNECTED,  " );
	       selectSql.append( " '6' as TABLENAME " );  
	       selectSql.append( " FROM  DEVICE_LED_INFO  T)   " );
	       selectSql.append( " UNION    " );
	       selectSql.append( " SELECT ID,   NAME , IP  ,PORT ,  IDS ,  CONNECTED   ,TABLENAME  FROM( " );
	       selectSql.append( " SELECT T.DEVICE_ID AS ID  ,T.DEVICE_NAME AS NAME ,T.IP_ADDRESS AS IP,NULL AS PORT, T.DEVICE_CODE AS IDS,T.COMMUNICATE_STATUS  AS CONNECTED , " ); 
	       selectSql.append( " '7' as TABLENAME " );  
	       selectSql.append( " FROM  DEVICE_SIGNAL_INFO  T)   " );
	       selectSql.append( " UNION   " ); 
	       selectSql.append( " SELECT ID,   NAME , IP , PORT ,  IDS ,  CONNECTED  ,TABLENAME  FROM( " );
	       selectSql.append( " SELECT T.DEVICE_ID AS ID  ,T.DEVICE_NAME AS NAME ,T.IP_ADDRESS AS IP,T.PORT AS PORT, T.DEVICE_CODE AS IDS,T.COMMUNICATE_STATUS  AS CONNECTED , " ); 
	       selectSql.append( " '2' as TABLENAME " );  
	       selectSql.append( " FROM  DEVICE_TGS_INFO  T) " );
	       selectSql.append( "  ) A      " );    
	       selectSql.append("  WHERE　   1=1   ORDER BY  A.TABLENAME  asc  ");
	    logger.debug("JdbcConnectionDao.getAllDeviceInfoList() sql="+selectSql.toString());
	    connectedInfoList = (List<ConnectionedBean>) this.query(selectSql.toString(), new BaseRowMapper(ConnectionedBean.class));
        logger.debug("JdbcConnectionDao.getAllDeviceInfoList() End.");
        return connectedInfoList;
    }
    
    
   
	
	/**
	*@method 批量插入设备网络检测数据信息JdbcDao
	*@author LvHua
	*@param  List<InsertConnectionedBean>   
    *@return boolean
    *@date   2012-03-05
    */
	public boolean  addDeviceNetWorkInfo(final List <InsertConnectionedBean> connectionedBeanList ){
		    logger.debug("JdbcConnectionDao.addDeviceNetWorkInfo() Start...");
		    StringBuffer insertIntoDeviceNetWorkSql  = new StringBuffer();
		    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			insertIntoDeviceNetWorkSql.append(" INSERT INTO  YW_DEVICE_NETWORK_HISTROY (DEVICE_CODE,CJ_TIME," +
					"STATUS,  DEVICE_TYPE,CHECK_SOURCE    )" //lvhua 2012-06-01 add    CHECK_SOURCE  字段
	                 +"VALUES(?,?,?,?,?) "); 
		 
			 BatchPreparedStatementSetter setterSchemeLed=new BatchPreparedStatementSetter(){
				 public int getBatchSize() {
					return connectionedBeanList.size();
				 }
			       public void setValues(PreparedStatement ps,int i){
			       try{
			       InsertConnectionedBean  insertConnectionedBean  = connectionedBeanList.get(i);
			       ps.setString(1, insertConnectionedBean.getDevice_code()); 
			       ps.setTimestamp (2,  TimeUtil.toSqlTimestampFromUtilDate(sdf.parse(insertConnectionedBean.getCj_time()))); 
			       ps.setString(3, insertConnectionedBean.getConnected());
			       ps.setString(4, insertConnectionedBean.getDeviceNameType());
			       ps.setString(5, insertConnectionedBean.getCheck_source()); //lvhua 2012-06-01 add
			       }
			       catch(Exception e){
			        e.printStackTrace();
			       }
			      }
			     };
		    int[]  returnSchemeLedKey =   this.batchUpdate(insertIntoDeviceNetWorkSql.toString(),setterSchemeLed);  
		    logger.debug("insertIntoDeviceNetWorkSql:   "+insertIntoDeviceNetWorkSql);
		    logger.debug("JdbcConnectionDao.addDeviceNetWorkInfo() End.");
		    if(returnSchemeLedKey.length>0 ){
				return true;
		    }else{
				return false;
		    }
	}
    
	 
	 
	
    
    /**
	*@method 批量插入设备网络在线信息JdbcDao
	*@author LvHua
	*@param  List<InsertConnectionedBean>   
    *@return boolean
    *@date   2012-06-26
    */
	public boolean  addDeviceOnLine(final List <InsertConnectionedBean> connectionedBeanList ,String msg){
		    logger.debug("JdbcConnectionDao.addDeviceOnLine() Start...");
		    StringBuffer insertIntoDeviceOnLineSql  = new StringBuffer();
		    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
		    insertIntoDeviceOnLineSql.append(" INSERT INTO  DEVICE_ONLINE_TJ (DEVICE_CODE,DEVICE_NAME,DEVICE_ONLINE,ONLINE_DATE )" //lvhua 2012-06-01 add    CHECK_SOURCE  字段
	                 +"VALUES(?,?,?,?) "); 
		 
			 BatchPreparedStatementSetter setterSchemeLed=new BatchPreparedStatementSetter(){
				 public int getBatchSize() {
					return connectionedBeanList.size();
				 }
			       public void setValues(PreparedStatement ps,int i){
			       try{
			       InsertConnectionedBean  insertConnectionedBean  = connectionedBeanList.get(i);
			       ps.setString(1, insertConnectionedBean.getDevice_code()); 
			       ps.setString(2, insertConnectionedBean.getDevice_name());
			       ps.setString(3, insertConnectionedBean.getConnected());
			       ps.setTimestamp (4,  TimeUtil.toSqlTimestampFromUtilDate(sdf.parse(insertConnectionedBean.getCj_time()))); 
			       }
			       catch(Exception e){
			        e.printStackTrace();
			       }
			      }
			     };
		    int[]  returnSchemeLedKey =   this.batchUpdate(insertIntoDeviceOnLineSql.toString(),setterSchemeLed);  
		    
		    
		    // 插入短信消息表MOBILE_MESSAGE ;   by lvhua    2013-07-16 add  start....
		    if(StringUtils.isNotEmpty(msg)){
		    	int updateRow = 0;
				String sql = " insert into MOBILE_MESSAGE (TYPE_ID,MSG,W_DATE,STATUS)  values('1','"+msg+"',sysdate,'1')";
				 // 先更新所有数据为历史记录
				int  updateCount  =  this.update("UPDATE MOBILE_MESSAGE t SET t.STATUS ='0'  where t.TYPE_ID ='1' and t.send = '0'  ") ; 
		 
			    updateRow = this.update(sql);  // 更新数据为最新数据
			    
			    if(updateRow>0){
			       logger.debug("插入更新短信消息服务信息成功!");
			    	
			    }
		    }
			
		   //   by lvhua    2013-07-16 add  end....   
		    
		    logger.debug("insertIntoDeviceOnLineSql:   "+insertIntoDeviceOnLineSql);
		    logger.debug("JdbcConnectionDao.addDeviceOnLine() End.");
		    if(returnSchemeLedKey.length>0 ){
				return true;
		    }else{
				return false;
		    }
	}

	/**
     * @method 更新设备网络连接状态JdbcDao
     * @author LvHua
     * @date   2011-11-28
     * @param  selectSql     
     * @return boolean
  
    */ 
	public  boolean  updateDeviceStatus(String selectSql){
			 logger.debug("JdbcConnectionDao.updateDeviceStatus() Start...");
			 logger.debug("updateDeviceStatus selectSql ="+selectSql);
			 int updateCount  =  this.update(selectSql) ;
			 logger.debug("JdbcConnectionDao.updateDeviceStatus() End...");
			 if(updateCount>0){
					return true;
		     }else
		            return false;
	}
	
	
   /**
	 * 插入更新短信消息服务信息
	 * @param msg
	 * @date   2013-07-16
	 * @return Boolean
	 */
	public boolean writeMobileMessage(String msg) {
		logger.debug("JdbcConnectionDao.writeMobileMessage() Start......");
	   // 插入短信消息表MOBILE_MESSAGE ;
		int updateRow = 0;
		String sql = " insert into MOBILE_MESSAGE (TYPE_ID,MSG,W_DATE,STATUS)  values('1','"+msg+"',sysdate,'1')";
		
		int  updateCount  =  this.update("UPDATE MOBILE_MESSAGE t SET t.STATUS ='0'  where t.TYPE_ID ='1' and t.send = '0'  ") ;
 
	    updateRow = this.update(sql);
	    
  
		logger.debug("JdbcConnectionDao.WriteMobileMessage() End......");
		return (updateRow == 1);
	}
    
}
