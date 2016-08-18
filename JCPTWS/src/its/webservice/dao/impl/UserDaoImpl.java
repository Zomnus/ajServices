/**
 * Copyright(c) SUPCON 2008-2011. 浙江浙大中控信息技术有限公司
 */

package its.webservice.dao.impl;

import its.webservice.dao.UserDao;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * 系统名称：智能交通WebService服务(ITSWebService)
 * 所属模块：系统管理
 * 功能描述：用户管理数据库接口实现类
 * 文件名：com.supconit.its.dao.impl.UserDaoImpl.java
 * 版本信息：1.00
 * 
 * 开发部门：研发中心
 * 创建者： lzk
 * 创建时间：Jan 25, 2011 3:10:35 PM
 * 修改者： lzk
 * 修改时间：Jan 25, 2011 3:10:35 PM
 */

public class UserDaoImpl extends JdbcTemplate implements UserDao {

	/** 获取Log4j实例 */
    protected Logger log = Logger.getLogger(UserDaoImpl.class.getName());
	
	public int test(String id) {
		log.debug("UserDaoImpl.test Start.......");
		String sql = "select count(*) from its_user where id = " + id;
		int rowNum  = this.queryForInt(sql);
		
		log.debug("UserDaoImpl.test End.......");
		return rowNum;
	}

}
