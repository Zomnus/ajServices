/**
 * Copyright(c) SUPCON 2008-2012. 浙江浙大中控信息技术有限公司
 */

package its.webservice.service;

import its.webservice.common.AppInitConstants;
import its.webservice.config.SystemConfig;
import its.webservice.util.DeleteFileUtil;

import java.util.Calendar;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 * 系统名称：智能交通集成平台(JCPTWS)
 * 所属模块：集成平台图片文件管理
 * 功能描述：对卡口、电警等图片文件维护，删除过期数据
 * 文件名：its.webservice.service.FileMaintanceService.java
 * 版本信息：1.00
 * 
 * 开发部门：研发中心
 * 创建者： lzk
 * 创建时间：2011-10-27 下午1:17:45
 * 修改者： lzk
 * 修改时间：2011-10-27 下午1:17:45
 */

public class FileMaintanceServiceImpl implements FileMaintanceService {
	
	/** 获取Log4j实例 */
    protected Logger log = Logger.getLogger(FileMaintanceServiceImpl.class.getName());
	
	/**
	 * 删除历史过期图片
	 * @param dateStr
	 * @return
	 */
	public boolean deleteHistoryFile(String deleteFileDir) {
		log.debug("FileMaintanceServiceImpl.DeleteHistoryFile() invoked...");
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd");

		Date d = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		
		String filePath = "";
		
		//Added by Tony Lin on 2013-07-04
		int keep_kk_pic_days = AppInitConstants.SERVER_KEEP_KK_PIC_DAYS;
		int keep_wf_pic_days = AppInitConstants.SERVER_KEEP_DJ_PIC_DAYS;
		//int keep_other_pic_days=30;
		try{
			keep_kk_pic_days = Integer.parseInt(SystemConfig.getConfigInfomation("server_keep_kk_pic_days"));
			keep_wf_pic_days = Integer.parseInt(SystemConfig.getConfigInfomation("server_keep_wf_pic_days"));
			//keep_other_pic_days = Integer.parseInt(SystemConfig.getConfigInfomation("server_keep_other_pic_days"));
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		//删除电警图片
		log.debug("开始删除电警图片Start..............");
		cal.add(Calendar.DAY_OF_MONTH, -keep_wf_pic_days);
		//log.debug(sdf.format(cal.getTime()));
		for(int i = 1; i < 6; i++){//删除过期日期文件夹
			cal.add(Calendar.DAY_OF_MONTH, -1);
			//log.debug(sdf.format(cal.getTime()));
			filePath = deleteFileDir + AppInitConstants.IMG_SERVER_ABS_FILE_PATH_WF + "/" + sdf.format(cal.getTime());
			DeleteFileUtil.deleteDirectory(filePath);
			i++;
		}
		log.debug("结束删除电警图片End..............");
		
		//删除卡口图片
		log.debug("开始删除卡口图片Start..............");
		cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.DAY_OF_MONTH, -keep_kk_pic_days);
		//log.debug(sdf.format(cal.getTime()));
		for(int i = 1; i < 6; i++){//删除过期日期文件夹
			cal.add(Calendar.DAY_OF_MONTH, -1);
			//log.debug(sdf.format(cal.getTime()));
			filePath = deleteFileDir + AppInitConstants.IMG_SERVER_ABS_FILE_PATH_KK  + "/" + sdf.format(cal.getTime());
			DeleteFileUtil.deleteDirectory(filePath);
			i++;
		}
		log.debug("结束删除卡口图片End..............");
		
		//删除视频违法抓拍图片
		log.debug("开始删除视频违法抓拍图片Start..............");
		cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.DAY_OF_MONTH, -AppInitConstants.SERVER_KEEP_SP_PIC_DAYS);
		//log.debug(sdf.format(cal.getTime()));
		for(int i = 1; i < 6; i++){//删除过期日期文件夹
			cal.add(Calendar.DAY_OF_MONTH, -1);
			//log.debug(sdf.format(cal.getTime()));
			filePath = deleteFileDir + AppInitConstants.IMG_SERVER_ABS_FILE_PATH_SP  + "/" + sdf.format(cal.getTime());
			DeleteFileUtil.deleteDirectory(filePath);
			i++;
		}
		log.debug("开始删除视频违法抓拍图片End..............");
		
		//删除人工上传图片（测速设备、移动摄像）Added on 2012-5-9
		log.debug("开始删除人工上传图片Start..............");
		cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.DAY_OF_MONTH, -AppInitConstants.SERVER_KEEP_XC_PIC_DAYS);
		//log.debug(sdf.format(cal.getTime()));
		for(int i = 1; i < 6; i++){//删除过期日期文件夹
			cal.add(Calendar.DAY_OF_MONTH, -1);
			//log.debug(sdf.format(cal.getTime()));
			filePath = deleteFileDir + AppInitConstants.IMG_SERVER_ABS_FILE_PATH_XC  + "/" + sdf.format(cal.getTime());
			DeleteFileUtil.deleteDirectory(filePath);
			i++;
		}
		log.debug("开始删除人工上传图片End..............");
		
		log.debug("FileMaintanceServiceImpl.DeleteHistoryFile() invoked...");
		
		return true;
	}

}
