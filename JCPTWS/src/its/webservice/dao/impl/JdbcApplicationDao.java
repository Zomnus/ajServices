// $Id: JdbcApplicationDao.java, v 1.5 2008/10/22 11:41:18
// $Exp Copyright(c) SUPCON 2008-2011. All Rights Reserved.
// -----------------------------------------------------------------
package its.webservice.dao.impl;

import its.webservice.common.BaseRowMapper;
import its.webservice.dao.ApplicationDao;
import its.webservice.entity.CrossBean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;



//*****************************************************************************
//用户名            交警
//系统名           智能交通集成平台
//子系统名          共通
//描述              在Spring启动时, 初始化(数据来自数据库)一些系统共用的集合.
/**
 * Application DAO Implementation Class<br>
 * 
 * @author lzk
 * @version Version 1.0
 */
//更新履历
//2008－10－22：创建
//*****************************************************************************
public class JdbcApplicationDao extends JdbcTemplate implements ApplicationDao {

    
    /**
     * 获得系统配置信息
     * 
     * Tony Lin Added on 2008-11-07
     * @return Map 包含库表APP_CONFIG所有key，value键值对
     */
    public Map getAppConfigMap(){
    	logger.debug("JdbcApplicationDao.getAppConfigMap() invoked...");
    	String sql = "select t.key,t.value from app_config t ";
    	Map<String,String> configMap = new HashMap<String,String>();
    	List rowList = this.queryForList(sql);
		Iterator rowIterator = rowList.iterator();
        while (rowIterator.hasNext()) {
            Map rowMap = (Map)rowIterator.next();
            configMap.put(rowMap.get("KEY").toString(),rowMap.get("VALUE") == null ? "" : rowMap.get("VALUE").toString());
        }
    	
    	return configMap;
    }
    
	/**
	 * 获得所有路口列表
	 * @return List<CrossBean>
	 */
    @SuppressWarnings("unchecked")
	public Map<String,String> getAllCrossList() {
		logger.debug("JdbcApplicationDao.getAllCrossList() start...");

		String sql = "\n SELECT DEVICE_CODE,DEVICE_NAME \n"            + 
                       "  FROM DEVICE_TGS_INFO \n"        + 
                       "  UNION SELECT DEVICE_CODE,DEVICE_NAME FROM DEVICE_EPS_INFO WHERE DEVICE_MODE = '1' \n";
		
		Map<String,String> crossMap = new HashMap<String,String>();
    	List rowList = this.queryForList(sql);
    	if(null!=rowList && rowList.size()>0){
    		Iterator rowIterator = rowList.iterator();
            while (rowIterator.hasNext()) {
                Map rowMap = (Map)rowIterator.next();
                crossMap.put(rowMap.get("DEVICE_CODE").toString(),rowMap.get("DEVICE_NAME") == null ? "" : rowMap.get("DEVICE_NAME").toString());
            }
    	}		
		logger.debug("JdbcApplicationDao.getAllCrossList() End.");
		return crossMap;
	}
    
    /**
     * 查询a_code表对应所有的typeId和map（code，name）的键值对
     * 
     * @author Tony Lin Added on 2008-11-03
     * @return List
     */
    public List getDictionary(){
    	logger.debug("JdbcApplicationDao.getDictionary() invoked...");
    	String sql = "select t.type_id, t.code, t.name from a_code t order by t.type_id,t.code ";

    	return (List)this.queryForList(sql);
    }

}
