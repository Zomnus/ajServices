/**
 * Copyright(c) SUPCON 2008-2011. 浙江浙大中控信息技术有限公司
 */

package its.webservice.service;

/**
 * 系统名称：智能交通集成平台(JCPTWS)
 * 所属模块：集成平台图片文件管理
 * 功能描述：对卡口、电警等图片文件维护，删除过期数据
 * 文件名：its.webservice.service.FileMaintanceService.java
 * 版本信息：1.00
 * 
 * 开发部门：研发中心
 * 创建者： lzk
 * 创建时间：2011-10-27 下午1:28:42
 * 修改者： lzk
 * 修改时间：2011-10-27 下午1:28:42
 */

public interface FileMaintanceService {
	
	/**
	 * 删除历史过期图片
	 * @param dateStr
	 * @return
	 */
	public boolean deleteHistoryFile(String deleteFileDir);

}
