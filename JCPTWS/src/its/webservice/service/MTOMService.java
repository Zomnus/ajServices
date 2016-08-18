/**
 * Copyright(c) SUPCON 2008-2011. 浙江浙大中控信息技术有限公司
 */

package its.webservice.service;

import javax.activation.DataHandler;
import javax.jws.WebParam;
import javax.xml.bind.annotation.XmlMimeType;

/**
 * 系统名称：智能交通集成平台(ITSWS)
 * 所属模块：XXXXXXXXXXXXXX
 * 功能描述：XXXXXXXXXXXXXX
 * 文件名：its.webservice.service.MTOMService.java
 * 版本信息：1.00
 * 
 * 开发部门：研发中心
 * 创建者： lzk
 * 创建时间：2011-10-14 下午1:34:40
 * 修改者： lzk
 * 修改时间：2011-10-14 下午1:34:40
 */

public interface MTOMService {
	
	public void upload(@WebParam(name = "fileName") String fileName,

			@XmlMimeType("*/*") @WebParam(name = "fileDataHandler") DataHandler dataHandler);

}
