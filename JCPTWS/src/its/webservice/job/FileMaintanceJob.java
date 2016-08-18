/**
 * Copyright(c) SUPCON 2008-2012. 浙江浙大中控信息技术有限公司
 */

package its.webservice.job;


import org.apache.log4j.Logger;

import its.webservice.common.AppInitConstants;
import its.webservice.config.SystemConfig;
import its.webservice.service.FileMaintanceService;
import its.webservice.util.ItsUtility;

/**
 * 系统名称：智能交通集成平台(JCPTWS)
 * 所属模块：文件管理模块
 * 功能描述：文件管理job
 * 文件名：its.webservice.job.FileMaintanceJob.java
 * 版本信息：1.00
 * 
 * 开发部门：研发中心
 * 创建者： lzk
 * 创建时间：2012-3-2 上午9:59:46
 * 修改者： lzk
 * 修改时间：2012-3-2 上午9:59:46
 */

public class FileMaintanceJob {
	
	/** 获取Log4j实例 */
    protected static Logger log = Logger.getLogger(FileMaintanceJob.class.getName());
	
	public FileMaintanceService getFileMaintanceService() {
		return fileMaintanceService;
	}

	public void setFileMaintanceService(FileMaintanceService fileMaintanceService) {
		this.fileMaintanceService = fileMaintanceService;
	}

	private FileMaintanceService fileMaintanceService;
	
	/*
	 * 图片服务器存储图片路劲前缀，如D:/picserver
	 * 该值在配置文件中传入
	 */
	private String imaPath = "";
	
	public String getImaPath() {
		return imaPath;
	}

	public void setImaPath(String imaPath) {
		this.imaPath = imaPath;
	}

	/**
	 * 过期图片文件删除
	 */
	public void deleteHistoryFile(){
		
		String currentTimeStr = ItsUtility.getCurrentFormattedDateTime();
        log.info("过期图片文件删除任务开始: Started at: " + currentTimeStr + "......");
        
        long start = System.currentTimeMillis();
		long total = 0;
		
		try{
			if("".equals(imaPath)){
				imaPath = AppInitConstants.IMG_SERVER_ABS_FILE_PATH;
			}
			fileMaintanceService.deleteHistoryFile(imaPath);
			
			//获取所有存储路径前缀  Added by Tony Lin on 2013-06-14
			//处理违法图片
			int EnabledwfImagePathNum = Integer.parseInt(SystemConfig.getConfigInfomation("EnabledwfImagePathNum"));
			for(int i = 1; i <= EnabledwfImagePathNum; i++){
				fileMaintanceService.deleteHistoryFile(SystemConfig.getConfigInfomation("wfImagePath" + i));
			}
			//处理卡口图片
			int EnabledkkImagePathNum = Integer.parseInt(SystemConfig.getConfigInfomation("EnabledkkImagePathNum"));
			for(int i = 1; i <= EnabledkkImagePathNum; i++){
				fileMaintanceService.deleteHistoryFile(SystemConfig.getConfigInfomation("kkImagePath" + i));
			}
			
		}catch(Exception e){
			log.error("过期图片文件删除任务失败，本次执行结束，错误信息如下："+e.getMessage());
		}
		
		long end = System.currentTimeMillis();
		total = end - start;
		long sec = total / 1000;
		
		currentTimeStr = ItsUtility.getCurrentFormattedDateTime();
		
		log.info("过期图片文件删除任务结束：Ended at "+ currentTimeStr + "......");
		log.info("本次任务总共耗时： " + sec / 60 + " 分 " + sec % 60 + " 秒" + total%1000 + "毫秒。");
		
	}
	
}
