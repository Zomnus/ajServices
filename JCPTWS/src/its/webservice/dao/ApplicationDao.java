// -------------------------------------------------------------
// $Id: ApplicationDao.java, v 1.5 2008/10/22 11:41:18
// $Exp Copyright(c) SUPCON 2008-2011. All Rights Reserved.
// -------------------------------------------------------------
package its.webservice.dao;

import its.webservice.entity.CrossBean;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



//*****************************************************************************
//用户名            交警
//系统名           智能交通集成平台
//子系统名          共通
//描述              在Spring启动时, 初始化(数据来自数据库)一些系统共用的集合.
/**
 * Application DAO service interface<br>
 * 
 * @author Tony Lin
 * @version Version 1.0
 */
//更新履历
//2008－10－22：创建
//*****************************************************************************
public interface ApplicationDao {

    /**
     * 获得系统配置信息
     * 
     * Tony Lin Added on 2008-11-07
     * @return Map 包含库表APP_CONFIG所有key，value键值对
     */
    public Map getAppConfigMap();
    
	/**
	 * 获得所有路口列表
	 * @return
	 */
	public Map<String,String> getAllCrossList();
	
    /**
     * 查询a_code表对应所有的typeId和map（code，name）的键值对
     * 
     * @author Tony Lin Added on 2008-11-03
     * @return Map
     */
    public List getDictionary();

}
