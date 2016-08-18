// -------------------------------------------------------------
// $Id: ConnectionDao.java, v 1.5 2010/01/13 14:23:33
// $Exp Copyright(c) SUPCON 2008-2010. All Rights Reserved.
// -------------------------------------------------------------
package its.webservice.dao;

import its.webservice.entity.ConnectionedBean;
import its.webservice.entity.InsertConnectionedBean;

import java.util.List;

//*****************************************************************************
//用户名            交警
//系统名           智能交通集成平台
//子系统名          运行维护管理
//描述              PING 电脑的连接状态相关处理Dao接口
/**
 * ConnectionDao DAO service interface<br>
 * 
 * @author SUPCON
 * @version Version 1.0
 */
//更新履历
//2010－01－13：创建
//*****************************************************************************
public interface ConnectionDao {

    
	
	
	/**
     * 取得所有设备连接状态CONNECTED信息
     * @author lvhua
     * @TIME  2011-11-23 
     * @return Map
     */
    @SuppressWarnings("unchecked")
    public List<ConnectionedBean> getAllDeviceInfoList();
    
    
    /**
     * @method 更新设备网络连接状态
     * @author LvHua
     * @param  selectSql     
     * @return boolean
     * @date   2011-11-28
    */ 
	public  boolean  updateDeviceStatus(String selectSql);
	
	    
	 
       
    /**
	*@method 批量插入设备网络检测数据信息
	*@author LvHua
	*@param  List<InsertConnectionedBean>   
    *@return boolean
    *@date   2012-03-05
    */
	public boolean  addDeviceNetWorkInfo(final List <InsertConnectionedBean> connectionedBeanList );
	
	/**
	*@method 批量插入设备网络在线信息
	*@author LvHua
	*@param  List<InsertConnectionedBean>   
    *@return boolean
    *@date   2012-06-26
    */
	public boolean  addDeviceOnLine(final List <InsertConnectionedBean> connectionedBeanList ,String msg );
	
	
	 /**
	 * 插入更新短信消息服务信息
	 * @param msg
	 * @date  2013-07-16
	 * @return Boolean
	 */
	public boolean writeMobileMessage(String msg);

}
