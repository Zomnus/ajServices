/**
 * Copyright(c) SUPCON 2008-2011. 浙江浙大中控信息技术有限公司
 */

package its.webservice.service;

import its.webservice.activemq.MessageSender;
import its.webservice.common.AppInitConstants;
import its.webservice.config.SystemConfig;
import its.webservice.dao.VehPassDao;
import its.webservice.entity.PicInfo;
import its.webservice.entity.WbRecord;
import its.webservice.util.FtpUtil;
import its.webservice.util.HttpPicSaver;
import its.webservice.util.ItsUtility;
import its.webservice.util.PassPostUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import javax.activation.DataHandler;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.xfire.util.Base64;

import com.sun.org.apache.bcel.internal.generic.CPInstruction;


/**
 * 系统名称：智能交通WebService服务(ITSWebService)
 * 所属模块：过车数据写入
 * 功能描述：过车数据写入接口实现类
 * 文件名：com.supconit.its.service.impl.WriteVehInfoServiceImpl.java
 * 版本信息：1.00
 * 
 * 开发部门：研发中心
 * 创建者： lzk
 * 创建时间：Jan 25, 2011 4:16:13 PM
 * 修改者： lzk
 * 修改时间：Jan 25, 2011 4:16:13 PM
 */

public class VehInfoServiceImpl implements VehInfoService {
	
	/** 获取Log4j实例 */
    protected Logger log = Logger.getLogger(VehInfoServiceImpl.class.getName());
    
    private VehPassDao vehPassDao;
    
	public VehPassDao getVehPassDao() {
		return vehPassDao;
	}

	public void setVehPassDao(VehPassDao vehPassDao) {
		this.vehPassDao = vehPassDao;
	}
	
	/*
	 * 图片服务器存储图片路劲前缀，如D:/picserver
	 * 该值可在配置文件中设置
	 */
	private String imaPath = "";
	
	public String getImaPath() {
		return imaPath;
	}

	public void setImaPath(String imaPath) {
		this.imaPath = imaPath;
	}
	
	/**
	 * 图片服务器FTP前缀，用于统一保存过车图片，如ftp://its:its@10.10.76.75:21
	 * 该值可在配置文件中设置
	 */
	private String ftpPre = "";
	
	public String getFtpPre() {
		return ftpPre;
	}

	public void setFtpPre(String ftpPre) {
		this.ftpPre = ftpPre;
	}
	
	//FTP虚拟路径
	String virtualRoute = "";
	
    //protected Socket socket;
    
	 //PrintWriter pw;
	

	/**
	 * 初始化信息写入
	 * @param deviceCode
	 * @param deviceKey
	 * @return
	 */
	public String InitWriteVehInfo(String deviceCode,String deviceKey){
		log.debug("WriteVehInfoServiceImpl.InitWriteVehInfo()  Start......");
		String returnStr = "";
		
		/*if("".equals(deviceCode)){
			return "卡口编号不能为空，请检查写入数据项！";
		}
		if("".equals(deviceKey)){
			return "卡口编号对应密钥不能为空，请检查写入数据项！";
		}
		//判断卡口平台是否添加该卡口编号
		if(!vehPassDao.hasAddedCode(deviceCode)){
			return "卡口编号("+deviceCode+")尚未在卡口管理平台添加相关信息，请先联系管理员进行添加再进行数据写入！";
		}
		try{
			//验证卡口密钥是否合法
			if(deviceKey.equals(ItsUtility.getMD5String(deviceCode+"supcon"))){
				//判断是否已经过初始化验证
				if(AppInitConstants.ALL_CODE_KEY_MAP.containsKey(deviceCode)){
					//return "卡口编号("+deviceCode+")对应的密钥("+deviceKey+")已经过初始化注册，无需再次初始化注册。";
					return "";
				}else{
					AppInitConstants.ALL_CODE_KEY_MAP.put(deviceCode, deviceKey);
				}
			}else{
				return "卡口编号("+deviceCode+")对应的密钥("+deviceKey+")错误，请联系管理员重新获得密钥！";
			}
		
			boolean isInsert = vehPassDao.InitWriteVehInfo(deviceCode, deviceKey);
			if(isInsert){
				log.debug("卡口编号:"+deviceCode+"初始化注册成功！");
			}else{
				returnStr = "卡口编号"+deviceCode+"初始化注册失败，请联系管理员！";
			}
		}catch(Exception e){
			log.error("卡口编号"+deviceCode+"初始化注册失败。" + e.getMessage());
			returnStr = "卡口编号"+deviceCode+"初始化注册失败，请联系管理员！" + e.getMessage();
		}*/
		
		log.debug("WriteVehInfoServiceImpl.InitWriteVehInfo()  End......");
		return returnStr;
	}

	/**
	 * 过车数据写入接口服务
	 * @param vehInfo
	 * @return String
	 */
	public String WriteVehicleInfo(String SBBH,String FXBH,String HPHM,String HPZL,String JGSJ,String CLSD,String HPYS,
			String CSYS,String CLLX,String TZTP,String QMTP,String HPTP,String CDH,String XS) {
	    long start = System.currentTimeMillis();																					
        long end;																																		
		long total = 0;	
		
		log.debug("WriteVehInfoServiceImpl.WriteVehicleInfo()  Start......");
		String returnStr = "";
		log.debug("HPHM:" + HPHM);
		log.debug("JGSJ:" + JGSJ);
		log.debug("HPYS:" + HPYS);
		log.debug("SBBH:" + SBBH);
		log.debug("CDH:" + CDH);
		log.debug("CLSD:" + CLSD);
		log.debug("XS:" +XS);
		
		//判断该路口编号是否经过初始化，即是否进行密钥校对
		/*if(!AppInitConstants.ALL_CODE_KEY_MAP.containsKey(vehInfo.getJgdd())){
			return "该路口未进行初始化验证，请先进行InitWriteVehInfo初始化注册，再进行数据写入！";
		}*/
		
		//判断一些必填项是否写入
		if("".equals(HPHM)){
			return "号牌号码(HPHM)不能为空，请检查写入数据项！";
		}
		if("".equals(JGSJ)){
			return "过车时间(JGSJ)不能为空，请检查写入数据项！";
		}
		//判断过车时间格式是否正确
		//if(!ItsUtility.isValidDate(vehInfo.getJgsj())){
		//	return "过车时间(JGSJ:"+vehInfo.getJgsj()+")格式错误，请检查写入数据项！";
		//}
		Date jgsj = ItsUtility.StringToDate(JGSJ,"yyyy-MM-dd HH:mm:ss");
		log.debug("jgsj:"+jgsj);
		if(jgsj == null){
			return "过车时间(JGSJ:"+JGSJ+")格式错误，请检查写入数据项！";
		}
		if("".equals(HPYS)){
			return "号牌颜色(HPYS)不能为空，请检查写入数据项！";
		}
		if("".equals(SBBH)){
			return "设备编号(SBBH)不能为空，请检查写入数据项！";
		}
		if("".equals(CDH)){
			return "车道号(CDH)不能为空，请检查写入数据项！";
		}
		try{
			boolean isInsert = vehPassDao.WriteVehicleInfo(SBBH,FXBH,HPHM,HPZL,JGSJ,CLSD,HPYS,CSYS,CLLX,TZTP,QMTP,HPTP,CDH,XS);
			//boolean isInsert = true;
			if(isInsert){
				log.debug("过车数据写入成功！");
			}else{
				returnStr = "过车数据写入失败，请检查数据字段是否正确！";
			}
		}catch(Exception e){
			log.error("写入过车数据失败：" + e.getMessage());
			return returnStr = "写入过车数据失败，请检查各数据输入项格式是否正确！" + e.getMessage();
			
		}
		
		//过车实时数据和布控报警  Added by Tony Lin on 2012-5-10
		try{
			//过滤掉历史数据报警,1小时内  Added by Tony Lin on 2012-7-25
			Date now = new Date();
			long n = (now.getTime() - jgsj.getTime())/(60*60*1000);//n < 2 表示时间延迟小于2小时
			if("1".equals(AppInitConstants.IS_NEED_BKBJ)){//是否具有布控报警功能，1有，0无  Modified by Tony Lin on 2012-5-14
				//实时过车数据发送
			//	sendLiveMessage(HPHM,SBBH,JGSJ,FXBH,CDH,HPZL,CLSD,TZTP,QMTP,HPTP);
				
				//布控校验报警
				//校验是否为布控信息，包括黑名单和布控信息
				if(n < 2){//n < 2 表示时间差距小于2小时
		            Map<String,String> alarmMap = vehPassDao.checkAlarmInfo(JGSJ, SBBH, HPHM, CSYS, HPZL, CLLX);
		            if("true".equals(alarmMap.get("alarm"))){//发送布控报警，数据格式，编码都需转化成中文
		           // 	sendAlarmmessge(alarmMap.get("bkid"),HPHM,HPZL,SBBH,CDH,FXBH,JGSJ,CLSD,TZTP,QMTP,HPTP,HPYS,alarmMap.get("bk_type_no"),alarmMap.get("ifsend"),alarmMap.get("phoneno"));
		            	//写入报警数据  Added by Tony Lin on 2012-6-4
		            	vehPassDao.insertBkAlarmVeh(SBBH, FXBH, HPHM, HPZL, JGSJ, CLSD, HPYS, CSYS, CLLX, TZTP, QMTP, HPTP, CDH, XS, alarmMap.get("bkid"), alarmMap.get("bk_type_no"));
		            }
				}
			}
		}catch(Exception e){
			log.error("过车数据报警失败：" + e.getMessage());
			//returnStr = "过车数据报警失败，请检查各数据输入项格式是否正确！" + e.getMessage();
		}
		
		//写入超速违法数据至WFCLB added by Tony Lin on 2011-09-27
		//取消原先数据库job调用存储过程方式
		//取消此方式判断，卡口过车违法数据写入需调用WriteSurveilInfo()   Modified by Tony Lin on 2011-12-26
		/*if("".equals(AppInitConstants.DEPLOY_PLACE)){
			int clxs = 50;
			int csbl = 0;
			try{
				clxs = Integer.parseInt(XS);
				csbl = Math.round((Integer.parseInt(CLSD) - clxs)*100/clxs);
			}catch(Exception e){
				
			}
			log.debug("csbl=" + csbl);
			if(csbl > 10){//超速10%以上写入违法数据
				try{
					//将违法数据写入数据库(WFSJB_YSH)
					boolean isInsert = vehPassDao.insertWFCLB(SBBH,FXBH,HPHM,HPZL,JGSJ,CLSD,HPYS,CSYS,CLLX,TZTP,QMTP,HPTP,CDH,XS);
					if(isInsert){
						log.debug("违法超速过车数据写入成功！");
					}else{
						returnStr = "违法超速过车数据写入失败，请检查数据字段是否正确！";
					}
				}catch(Exception e){
					log.error("写入违法超速过车数据失败：" + e.getMessage());
					returnStr = "写入违法超速过车数据失败，请检查各数据输入项格式是否正确！" + e.getMessage();
				}
			}
		}*/
		
		log.debug("WriteVehInfoServiceImpl.WriteVehicleInfo()  End......");
		
		end = System.currentTimeMillis();
		total = end - start;
		long sec = total / 1000;		//导入时间精确到秒
		log.debug("本次数据写入总共耗时： " + sec / 60 + " 分 " + sec % 60 + " 秒"+total%1000+"毫秒。 ");
		return returnStr;
	}
	
	/**
	 * 过车数据(带图片)写入接口服务
	 * 
	 * @return String
	 */
	public String WriteVehicleInfoWithPhoto(String SBBH,String FXBH,String HPHM,String HPZL,
			String JGSJ,String CLSD,String HPYS,String CSYS,String CLLX,String TZTP,String QMTP,String HPTP,String TPTYPE,String CDH,String XS){
		log.debug("WriteVehInfoServiceImpl.WriteVehicleInfoWithPhoto()  Start......");
		
		log.debug("HPHM:" + HPHM);
		log.debug("JGSJ:" + JGSJ);
		log.debug("HPYS:" + HPYS);
		log.debug("SBBH:" + SBBH);
		log.debug("CDH:" + CDH);
		log.debug("CLSD:" + CLSD);
		log.debug("XS:" +XS);
	    long start = System.currentTimeMillis();																					
        long end;																																		
		long total = 0;	
		 
		String returnStr = "";
		
		
		//判断一些必填项是否写入
		if("".equals(HPHM)){
			return "号牌号码(HPHM)不能为空，请检查写入数据项！";
		}
		if("".equals(JGSJ)){
			return "过车时间(JGSJ)不能为空，请检查写入数据项！";
		}
		Date jgsj = ItsUtility.StringToDate(JGSJ,"yyyy-MM-dd HH:mm:ss");
		if(jgsj == null){
			return "过车时间(JGSJ:"+JGSJ+")格式错误，请检查写入数据项！";
		}
		if("".equals(HPYS)){
			return "号牌颜色(HPYS)不能为空，请检查写入数据项！";
		}
		if("".equals(SBBH)){
			return "设备编号(SBBH)不能为空，请检查写入数据项！";
		}
		if("".equals(CDH)){
			return "车道号(CDH)不能为空，请检查写入数据项！";
		}
		if("".equals(TPTYPE)){
			TPTYPE = "jpg";
		}
		
		if ("未识别".equals(HPHM)) {
			HPHM="无法识别";
		}
		
		
		
		String tztpFtpPath = "";
		String qmtpFtpPath = "";
		String hptpFtpPath = "";
		String fileNewPath = "";
		
		//根据配置文件随机选择   Added by Tony Lin on 2013-06-14
		//Modified by Tony Lin on 2013-06-14
		Map<String,String> ftpMap = ItsUtility.getRandomImgPath("kk");
		String imaPath = ftpMap.get("ImagePath");
		String virtualRoute = ftpMap.get("VirtualRoute");
		String ftpPre = ftpMap.get("ftpPre");
		if("".equals(imaPath)){
			imaPath = AppInitConstants.IMG_SERVER_ABS_FILE_PATH;
		}
		if("".equals(ftpPre)){
			ftpPre = AppInitConstants.IMG_SERVER_FTP_PRE;
		}
		if("".equals(virtualRoute)){
			virtualRoute = AppInitConstants.IMG_SERVER_FILE_PATH;
		}
		
		String gaFtpPre = SystemConfig.getConfigInfomation("gaFtpPre");
		if("".equals(gaFtpPre)){
			gaFtpPre = AppInitConstants.IMG_SERVER_FTP_PRE;
		}
		String gaTztpFtpPath = "";
		String gaQmtpFtpPath = "";
		String gaHptpFtpPath = "";
		String isWriteGA = SystemConfig.getConfigInfomation("isWriteGA");
		
		 //mowangzhong
		String tztpHttpPath="";
	//	String qmtpHttpPath="";
		String hptpHttpPath="";
		String tztpHttpGaPath="";
		String hptpHttpGaPath="";
		try{
			FileOutputStream fos = null;
			File file = null;
			byte[] bytes = null;
			String cltpName = "";
			//++++++
			HttpPicSaver httpPicSaver = new HttpPicSaver(AppInitConstants.HTTP_KK_PIC_IP_ZW, AppInitConstants.HTTP_PIC_PORT);
			String zwurl = "http://"+AppInitConstants.HTTP_KK_PIC_IP_ZW+":"+AppInitConstants.HTTP_PIC_PORT + "/images";
			String gaurl = "http://"+AppInitConstants.HTTP_KK_PIC_IP_GA+":"+AppInitConstants.HTTP_PIC_PORT + "/images";
			
				try {
					
					//将过车图片存放在本地或者FTP上，过车数据写入数据库并记录过车图片地址
					//过车图片保存目录路劲为：日期(天)/设备编号/方向/车道号/图片名
					if(null != TZTP &&  !"".equals(TZTP)){//处理第一个文件
						//cltpName = SBBH + "_" +  ItsUtility.DateToString(jgsj, "yyyyMMddHHmmss") + "_" + CDH + "_" + "TZTP." + TPTYPE;
						//Modified by Tony Lin on 2012-8-1
						cltpName = String.valueOf(start) + "_" + "TZTP." + TPTYPE;
						fileNewPath =  AppInitConstants.IMG_SERVER_ABS_FILE_PATH_KK + "/"+ ItsUtility.DateToString(jgsj, "yyyyMMdd") + "/" + SBBH + "/" + FXBH + "/" + CDH + "/";
						try {
							file = new File(imaPath  + fileNewPath + cltpName);   
							//如果该文件已存在，则直接返回
							/*if(file.isFile()){
								returnStr = "过车数据图片（"+imaPath  + fileNewPath + cltpName+"）已存在，请请勿重复上传数据！";
								return returnStr;
							}*/
							//如果文件夹不存在，则先创建文件夹
							if(file.isDirectory()){
								log.debug("the directory is exists!");
							}else{
								file.getParentFile().mkdirs();
								//filePath.createNewFile();
								log.debug("新建目录："+file+" 成功");
							}
							
							bytes = Base64.decode(TZTP);   
							fos = new FileOutputStream(file);   
							fos.write(bytes);   
							fos.flush();   
							fos.close();
						} catch (Exception e) {
							PicInfo picInfo = new PicInfo(imaPath  + fileNewPath + cltpName,TZTP);
							AppInitConstants.picInfoQueue.put(picInfo);
							log.error("写入过车图片失败：" + SBBH + "_" + HPHM + "_" + JGSJ + "; error:" + e.getMessage());
						} 
			        	tztpFtpPath = ftpPre + virtualRoute + fileNewPath +cltpName;
			        	gaTztpFtpPath = gaFtpPre + virtualRoute + fileNewPath +cltpName;
			        	//**********************************************************************************************************************
			        	//HttpPicSaver httpPicSaver = new HttpPicSaver(AppInitConstants.HTTP_KK_PIC_IP_ZW, AppInitConstants.HTTP_PIC_PORT);
						//String zwurl = "http://"+AppInitConstants.HTTP_KK_PIC_IP_ZW+":"+AppInitConstants.HTTP_PIC_PORT + "/images";
					
						//卡口图片存放到http 图片服务器
						//处理第一个
						cltpName = SBBH + "_" +  ItsUtility.DateToString(jgsj, "yyyyMMddHHmmss") + "_" + FXBH + "_" + CDH + "_" + "tztp." + TPTYPE;
						//对图片流进行转码
						bytes = Base64.decode(TZTP);
						//将图片保存到图片服务器 并返回图片保存地址
						String picKey =httpPicSaver.save(bytes, cltpName);
						tztpHttpPath = zwurl+picKey; //专网http 图片地址
						tztpHttpGaPath=gaurl+picKey; //公安网http 图片地址 
						//System.out.println("+tztpHttpPath"+tztpHttpPath);
					}
		        	if(null != QMTP && !"".equals(QMTP)){//处理第二个文件
		        		//Modified by Tony Lin on 2012-8-1
						cltpName = String.valueOf(start) + "_" + "QMTP." + TPTYPE;
		        		fileNewPath = AppInitConstants.IMG_SERVER_ABS_FILE_PATH_KK  + "/"+  ItsUtility.DateToString(jgsj, "yyyyMMdd") + "/" + SBBH + "/" + FXBH + "/" + CDH + "/";
						try {
							file = new File(imaPath  + fileNewPath + cltpName);   
							//如果文件夹不存在，则先创建文件夹
							if(file.isDirectory()){
								log.debug("the directory is exists!");
							}else{
								file.getParentFile().mkdirs();
								log.debug("新建目录："+file+" 成功");
							}
							
							bytes = Base64.decode(QMTP);   
							fos = new FileOutputStream(file);   
							fos.write(bytes);   
							fos.flush();   
							fos.close();
						} catch (Exception e) {
							PicInfo picInfo = new PicInfo(imaPath  + fileNewPath + cltpName,QMTP);
							AppInitConstants.picInfoQueue.put(picInfo);
							log.error("写入过车图片失败：" + SBBH + "_" + HPHM + "_" + JGSJ + "; error:" + e.getMessage());
						} 
			        	qmtpFtpPath = ftpPre + virtualRoute+ fileNewPath  +cltpName;
			        	gaQmtpFtpPath = gaFtpPre + virtualRoute + fileNewPath +cltpName;
			        	
		        	}else{
		        		qmtpFtpPath = tztpFtpPath;
		        		gaQmtpFtpPath = gaTztpFtpPath;
		        	}
		        	if(null != HPTP && !"".equals(HPTP)){//处理第三个文件
		        		//Modified by Tony Lin on 2012-8-1
						cltpName = String.valueOf(start) + "_" + "HPTP." + TPTYPE;
		        		fileNewPath =  AppInitConstants.IMG_SERVER_ABS_FILE_PATH_KK  + "/"+  ItsUtility.DateToString(jgsj, "yyyyMMdd") + "/" + SBBH + "/" + FXBH + "/" + CDH + "/";
						try {
							file = new File(imaPath  + fileNewPath + cltpName);   
							//如果文件夹不存在，则先创建文件夹
							if(file.isDirectory()){
								log.debug("the directory is exists!");
							}else{
								file.getParentFile().mkdirs();
								//filePath.createNewFile();
								log.debug("新建目录："+file+" 成功");
							}
							
							bytes = Base64.decode(HPTP);
							fos = new FileOutputStream(file);   
							fos.write(bytes);   
							fos.flush();   
							fos.close();
						} catch (Exception e) {
							PicInfo picInfo = new PicInfo(imaPath  + fileNewPath + cltpName,HPTP);
							AppInitConstants.picInfoQueue.put(picInfo);
							log.error("写入过车图片失败：" + SBBH + "_" + HPHM + "_" + JGSJ + "; error:" + e.getMessage());
						} 
			        	hptpFtpPath = ftpPre + virtualRoute  + fileNewPath +cltpName;
			        	gaHptpFtpPath = gaFtpPre + virtualRoute  + fileNewPath +cltpName;
			        	
			        	
			        	//卡口图片存放到hptp 图片服务器
						//处理第三个
						cltpName = SBBH + "_" +  ItsUtility.DateToString(jgsj, "yyyyMMddHHmmss") + "_" + FXBH + "_" + CDH + "_" + "hptp." + TPTYPE;
						//对图片流进行转码
						bytes = Base64.decode(HPTP);
						//将图片保存到图片服务器 并返回图片保存地址
						String picKey =httpPicSaver.save(bytes, cltpName);
						hptpHttpPath = zwurl+picKey; //专网http 图片地址
						hptpHttpGaPath= gaurl+picKey; //公安网http图片服务器地址
			        	       				        	
		        	}
		        	//fos.close();   
		        } catch (Exception e) {
		        	log.error("写入过车图片失败：" + SBBH + "_" + HPHM + "_" + JGSJ + "; error:" + e.getMessage());
		        	//取消图片错误限制，允许过无图片车数据写入 Added by Tony Lin on 2012-4-28
		        	returnStr = "过车数据图片写入失败，请检确保图片数据是否正确！" + e;
		        } finally{
		             if (fos != null){
		                 try{
		                	 fos.flush(); 
		                     fos.close();
		                 }
		                 catch (Exception e){
		                	 log.error(e.getMessage());
		                	 e.printStackTrace();
		                 }
		             }
		         }
	        
			if(!StringUtils.isEmpty(SystemConfig.getBMDConfigInfomation(HPHM+"_"+HPZL))){
				//白名单
				vehPassDao.WriteBMDVehicleInfoWithPhoto(SBBH,FXBH,HPHM,HPZL,JGSJ,CLSD,HPYS,CSYS,CLLX,tztpFtpPath,qmtpFtpPath,hptpFtpPath,CDH,XS);
			}else{
				//推送给大数据，不入oracle start
				boolean isInsert = vehPassDao.WriteVehicleInfoWithPhoto(SBBH,FXBH,HPHM,HPZL,JGSJ,CLSD,HPYS,CSYS,CLLX,tztpHttpPath,hptpHttpPath,hptpHttpPath,CDH,XS);
				
				if ("无法识别".equals(HPHM)) {
					HPHM="-";
				}		
				//System.out.println("****************"+FXBH+"*******************");
				String result=PassPostUtil.postClientUploadTgs(AppInitConstants.uploadForBigDate.toString(), "999999", 
						SBBH, SBBH, HPHM, HPZL, HPYS,CLLX,
						JGSJ, FXBH, CDH, CLSD,"1", "1", "2","",
						CSYS, "000,000,000,000", "000,000,000,000", "000,000,000,000",
						"0",tztpHttpPath);
			    String code = result.substring(result.indexOf(":")+1,result.indexOf(","));
			    //推送到大数据-end
				if("0".endsWith(code)){ 
					System.out.println("过车数据写入大数据成功！+++++++++++++++++");
					if ("未识别".equals(HPHM)) {
						HPHM="车牌";
					}
					//转换车牌颜色提供给海康
					if(HPYS=="01"){
						HPYS="3";
					}else if(HPYS=="02"){
						HPYS="4";
					}else if(HPYS=="03"||HPYS=="04"){
						HPYS="5";
					}else if(HPYS=="23"){
						HPYS="2";
					}else{
						HPYS="1";
					}
				     String mqMessage="1"+"$"+SBBH+"$"+CDH+"$"+HPHM+"$"+HPYS+"$"+HPZL+"$"+JGSJ+"$"+CLSD+"$"+CLLX+"$"+"1"+"$"+CSYS+"$"+hptpHttpPath+"$"+tztpHttpPath+"$"+"0"+"$"+"1"+"$"+""+"$"+"";
				     boolean offer = AppInitConstants.sendMQQueue.offer(mqMessage);
				     if (offer) {
				    	 log.debug("+++++++++带图片过车数据发送MQ队列成功，队列长度："+AppInitConstants.sendMQQueue.size()+"+++++++++++");
					}
				}else{
					returnStr = "过车数据写入失败，请检查数据字段是否正确！";
				}
				if("1".equals(isWriteGA)){ //写入到临时表同步到公安网
					//通过ftp 地址方式同步
					boolean isInsertGa = vehPassDao.WriteVehicleInfoWithPhotoTemp(SBBH,FXBH,HPHM,HPZL,JGSJ,CLSD,HPYS,CSYS,CLLX,gaTztpFtpPath,gaQmtpFtpPath,gaHptpFtpPath,CDH,XS);
					//通过 http 地址方式同步
					//boolean isInsertGa = vehPassDao.WriteVehicleInfoWithPhotoTemp(SBBH,FXBH,HPHM,HPZL,JGSJ,CLSD,HPYS,CSYS,CLLX,tztpHttpGaPath,tztpHttpGaPath,hptpHttpGaPath,CDH,XS);
					
					if(isInsertGa){
						log.debug("过车数据写入临时表成功！");
						
					}else{
						returnStr = "过车数据写入临时表失败，请检查数据字段是否正确！";
					}
				}
			}
			
		}catch(Exception e){
			log.error("写入过车数据失败：" + SBBH + "_" + HPHM + "_" + JGSJ + "; error:" + e.getMessage());
			returnStr = "写入过车数据失败，请检查各数据输入项格式是否正确！" + e.getMessage();
			return returnStr;
		}
		
		
		//写入超速违法数据至WFSJB_YSH added by Tony Lin on 2011-09-27
		boolean isCS = false;
		//卡式电警超速数据过滤掉, added by Tony Lin on 2012-04-10
		//判断该设备是否需要采集违法超速数据 added by Tony Lin on 2012-5-4
		/*if(vehPassDao.isNeedWfcj(SBBH)){
		}*/
		try{
				if(vehPassDao.isNeedWfcj(SBBH)){//判断该设备是否需要采集违法超速数据 added by Tony Lin on 2012-5-4
					
					if("anji".equals(AppInitConstants.DEPLOY_PLACE)){//安吉超速标准为：实测 - 限速 > 10
						if((Integer.parseInt(CLSD) - Integer.parseInt(XS)) > 10){
							isCS = true;
						}
					}else{//按超速比率 > 10%
						int clxs = 50;
						int csbl = 0;
						try{
							clxs = Integer.parseInt(XS);
							csbl = Math.round((Integer.parseInt(CLSD) - clxs)*100/clxs);
						}catch(Exception e){
							
						}
						if(csbl > 10){
							isCS = true;
						}
						log.debug("csbl=" + csbl);
					}
				}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		
		if(isCS){//超速写入违法数据
			try{
				//将违法数据写入数据库(WFSJB_YSH)
				boolean isInsert = vehPassDao.insertWFCLB(SBBH,FXBH,HPHM,HPZL,JGSJ,CLSD,HPYS,CSYS,CLLX,tztpFtpPath,qmtpFtpPath,hptpFtpPath,CDH,XS);
				if(isInsert){
					log.debug("违法超速过车数据写入成功！");
				}else{
					//returnStr = "违法超速过车数据写入失败，请检查数据字段是否正确！";
				}
				if("1".equals(isWriteGA)){
					boolean isInsertGa = vehPassDao.insertWFCLBTemp(SBBH,FXBH,HPHM,HPZL,JGSJ,CLSD,HPYS,CSYS,CLLX,gaTztpFtpPath,gaQmtpFtpPath,gaHptpFtpPath,CDH,XS);
					if(isInsertGa){
						log.debug("违法超速数据写入临时表成功！");
						
					}else{
						returnStr = "违法超速过车数据写入临时表失败，请检查数据字段是否正确！";
					}
				}
			}catch(Exception e){
				log.error("写入违法超速过车数据失败：" + e.getMessage());
				returnStr = "写入违法超速过车数据失败，请检查各数据输入项格式是否正确！" + e.getMessage();
				//return returnStr;
			}
		 }
		
		end = System.currentTimeMillis();
		total = end - start;
		long sec = total / 1000;		//导入时间精确到秒
		log.error("本次数据写入总共耗时： " + sec / 60 + " 分 " + sec % 60 + " 秒"+total%1000+"毫秒。 ");
		
		log.debug("WriteVehInfoServiceImpl.WriteVehicleInfoWithPhoto()  End......");
		return returnStr;
	}
	
	/**
	 * 查询远程服务器时间
	 * @return
	 */
	public String QueryServerTime(){
		log.debug("WriteVehInfoServiceImpl.QueryServerTime()  Start......");
		String strServerDate = vehPassDao.getSysdate();
		if("".equals(strServerDate)){
			Date date = new Date();
			strServerDate = ItsUtility.DateToString(date, "yyyy-MM-dd HH:mm:ss");
		}
		log.debug("strServerDate:" + strServerDate);
		log.debug("WriteVehInfoServiceImpl.QueryServerTime()  End......");
		return strServerDate;
	}
	

	/**
	 * 违法数据(包括卡口、电警)写入接口
	 * @param SBBH
	 * @param FXBH
	 * @param HPHM
	 * @param HPZL
	 * @param JGSJ
	 * @param CLSD
	 * @param HPYS
	 * @param CSYS
	 * @param CLLX
	 * @param CDH
	 * @param XS
	 * @param Cltp1
	 * @param Cltp2
	 * @param Cltp3
	 * @param CltpType
	 * @param CJFS
	 * @return
	 */
	public String WriteSurveilInfo(String SBBH,String FXBH,String HPHM,String HPZL,String JGSJ,String CLSD,String HPYS,String CSYS,
			String CLLX,String CDH,String XS,String Cltp1,String Cltp2,String Cltp3,String CltpType,String VideoFile,String VideoType,String CJFS){
		log.debug("WriteVehInfoServiceImpl.WriteSurveilInfo()  Start......");
	    long start = System.currentTimeMillis();																					
        long end;																																		
		long total = 0;	
		
		String returnStr = "";
		
		if("".equals(SBBH)){
			return "设备编号(SBBH)不能为空，请检查写入数据项！";
		}
		if("".equals(HPHM)){
			return "号牌号码(HPHM)不能为空，请检查写入数据项！";
		}
		if("".equals(JGSJ)){
			return "过车时间(JGSJ)不能为空，请检查写入数据项！";
		}
		if("".equals(CDH)){
			return "车道编号(CDH)不能为空，请检查写入数据项！";
		}
		if("".equals(HPYS)){
			return "号牌颜色(HPYS)不能为空，请检查写入数据项！";
		}
		if("".equals(Cltp1)){
			return "违法图片1(Cltp1)不能为空，请检查写入数据项！";
		}
		//设置图片默认后缀名
		if("".equals(CltpType)){
			CltpType = "jpg";
		}
		//设置录像默认后缀名
		if("".equals(VideoType)){
			VideoType = "mp4";
		}
		
		Date checkJgsj = ItsUtility.StringToDate(JGSJ,"yyyy-MM-dd HH:mm:ss");
		log.debug("jgsj:"+checkJgsj);
		if(checkJgsj == null){
			return "过车时间(JGSJ:"+JGSJ+")格式错误，请检查写入数据项！";
		}
		JGSJ = ItsUtility.DateToString(checkJgsj, "yyyyMMddHHmmss");
		FileOutputStream fos = null;
		File file = null;
		byte[] bytes = null;
		String cltpName = "";
		String fileNewPath = "";
		String cltp1FtpPath = "";
		String cltp2FtpPath = "";
		String cltp3FtpPath = "";
		String videoFtpPath = "";
		
		if("".equals(imaPath)){
			imaPath = AppInitConstants.IMG_SERVER_ABS_FILE_PATH;
		}
		if("".equals(ftpPre)){
			ftpPre = AppInitConstants.IMG_SERVER_FTP_PRE;
		}
		//根据配置文件随机选择   Added by Tony Lin on 2013-06-14
		//Modified by Tony Lin on 2013-06-14
		
		Map<String,String> ftpMap = ItsUtility.getRandomImgPath("wf");
		String imaPath = ftpMap.get("ImagePath");
		String  virtualRoute = ftpMap.get("VirtualRoute");
		String  ftpPre = ftpMap.get("ftpPre");
		if("".equals(imaPath)){
			imaPath = AppInitConstants.IMG_SERVER_ABS_FILE_PATH;
		}
		if("".equals(ftpPre)){
			ftpPre = AppInitConstants.IMG_SERVER_FTP_PRE;
		}
		if("".equals(virtualRoute)){
			virtualRoute = AppInitConstants.IMG_SERVER_FILE_PATH;
		}
		String gaFtpPre = SystemConfig.getConfigInfomation("gaFtpPre");
		if("".equals(gaFtpPre)){
			gaFtpPre = AppInitConstants.IMG_SERVER_FTP_PRE;
		}
		
		
		String gaCltp1FtpPath = "";
		String gaCltp2FtpPath = "";
		String gaCltp3FtpPath = "";
		String gaVideoFtpPath = "";
		String isWriteGA = SystemConfig.getConfigInfomation("isWriteGA");
		try{
			try {
				/*
				HttpPicSaver httpPicSaver = new HttpPicSaver(AppInitConstants.HTTP_WF_PIC_IP_ZW, AppInitConstants.HTTP_PIC_PORT);
				String zwurl = "http://"+AppInitConstants.HTTP_WF_PIC_IP_ZW+":"+AppInitConstants.HTTP_PIC_PORT + "/images";
				String gaurl = "http://"+AppInitConstants.HTTP_WF_PIC_IP_GA+":"+AppInitConstants.HTTP_PIC_PORT + "/images";
				//电警违法图片存放到http 图片服务器
				if(null != Cltp1 && !"".equals(Cltp1)){//处理第一个
					cltpName = CJFS + "_" + SBBH + "_" +  ItsUtility.DateToString(checkJgsj, "yyyyMMddHHmmss") + "_" + FXBH + "_" + CDH + "_" + "1." + CltpType;
					//对图片流进行转码
					bytes = Base64.decode(Cltp1);
					//将图片保存到图片服务器 并返回图片保存地址
					String picKey =httpPicSaver.save(bytes, cltpName);
					cltp1FtpPath = zwurl+picKey;
					gaCltp1FtpPath =gaurl+picKey;
				}
				
				if( null!= Cltp2  && !"".equals(Cltp2)){//处理第二个文件
					cltpName = CJFS + "_" + SBBH + "_" +  ItsUtility.DateToString(checkJgsj, "yyyyMMddHHmmss") + "_" + FXBH + "_"  + CDH + "_" + "2." + CltpType;
					//对图片流进行转码
					bytes = Base64.decode(Cltp2);
					//将图片保存到图片服务器 并返回图片保存地址
					String picKey =httpPicSaver.save(bytes, cltpName);
					cltp2FtpPath = zwurl+picKey;
					gaCltp2FtpPath =gaurl+picKey;
				}
				if(null != Cltp3 && !"".equals(Cltp3)){//处理第三个文件
	        		cltpName = CJFS + "_" + SBBH + "_" +  ItsUtility.DateToString(checkJgsj, "yyyyMMddHHmmss") + "_" + FXBH + "_"  + CDH + "_" + "3." + CltpType;
	        		//对图片流进行转码
					bytes = Base64.decode(Cltp3);
					//将图片保存到图片服务器 并返回图片保存地址
					String picKey =httpPicSaver.save(bytes, cltpName);
					cltp3FtpPath = zwurl+picKey;
					gaCltp3FtpPath =gaurl+picKey;
				}
				*/
	//******************************************************************************************			
		
				//将违法图片存放在本地或者FTP上，违法数据写入数据库并记录违法图片地址
				//违法图片录像保存目录路劲为：日期(天)/设备编号/图片或录像名
				
				if(null != Cltp1 && !"".equals(Cltp1)){//处理第一个文件
					cltpName = CJFS + "_" + SBBH + "_" +  ItsUtility.DateToString(checkJgsj, "yyyyMMddHHmmss") + "_" + FXBH + "_" + CDH + "_" + "1." + CltpType;
					fileNewPath = AppInitConstants.IMG_SERVER_ABS_FILE_PATH_WF  + "/"+  ItsUtility.DateToString(checkJgsj, "yyyyMMdd") + "/" + SBBH + "/";
					try {
						file = new File(imaPath  + fileNewPath + cltpName);  
						//if(file.isFile()){
						//	returnStr = "违法数据图片（"+imaPath  + fileNewPath + cltpName+"）已存在，请请勿重复上传数据！";
						//	return returnStr;
						//}
						//如果文件夹不存在，则先创建文件夹
						if(file.isDirectory()){
							log.debug("the directory is exists!");
						}else{
							file.getParentFile().mkdirs();
							//filePath.createNewFile();
							log.debug("新建目录："+file+" 成功");
						}
						
						bytes = Base64.decode(Cltp1);   
						fos = new FileOutputStream(file);   
						fos.write(bytes);   
						fos.flush();   
						fos.close();
					} catch (Exception e) {
						PicInfo picInfo = new PicInfo(imaPath  + fileNewPath + cltpName,Cltp1);
						AppInitConstants.picInfoQueue.put(picInfo);
						log.error("写入违法图片失败：" + e.getMessage());
					} 
		        	cltp1FtpPath = ftpPre + virtualRoute + fileNewPath +cltpName;
		        	gaCltp1FtpPath = gaFtpPre + virtualRoute + fileNewPath +cltpName;
				}
	        	if( null!= Cltp2  && !"".equals(Cltp2)){//处理第二个文件
	        		cltpName = CJFS + "_" + SBBH + "_" +  ItsUtility.DateToString(checkJgsj, "yyyyMMddHHmmss") + "_" + FXBH + "_"  + CDH + "_" + "2." + CltpType;
					fileNewPath =  AppInitConstants.IMG_SERVER_ABS_FILE_PATH_WF + "/"+  ItsUtility.DateToString(checkJgsj, "yyyyMMdd") + "/" + SBBH + "/";
					try {
						file = new File(imaPath  + fileNewPath + cltpName);   
//						if(file.isFile()){
//							returnStr = "违法数据图片（"+imaPath  + fileNewPath + cltpName+"）已存在，请请勿重复上传数据！";
//							return returnStr;
//						}
						//如果文件夹不存在，则先创建文件夹
						if(file.isDirectory()){
							log.debug("the directory is exists!");
						}else{
							file.getParentFile().mkdirs();
							//filePath.createNewFile();
							log.debug("新建目录："+file+" 成功");
						}
						
						bytes = Base64.decode(Cltp2);   
						fos = new FileOutputStream(file);   
						fos.write(bytes);   
						fos.flush();   
						fos.close();
					} catch (Exception e) {
						PicInfo picInfo = new PicInfo(imaPath  + fileNewPath + cltpName,Cltp2);
						AppInitConstants.picInfoQueue.put(picInfo);
						log.error("写入违法图片失败：" + e.getMessage());
					} 
		        	cltp2FtpPath = ftpPre + virtualRoute  + fileNewPath +cltpName;
		        	gaCltp2FtpPath = gaFtpPre + virtualRoute  + fileNewPath +cltpName;
	        	}
	        	if(null != Cltp3 && !"".equals(Cltp3)){//处理第三个文件
	        		cltpName = CJFS + "_" + SBBH + "_" +  ItsUtility.DateToString(checkJgsj, "yyyyMMddHHmmss") + "_" + FXBH + "_"  + CDH + "_" + "3." + CltpType;
					fileNewPath = AppInitConstants.IMG_SERVER_ABS_FILE_PATH_WF + "/"+   ItsUtility.DateToString(checkJgsj, "yyyyMMdd") + "/" + SBBH + "/";
					try {
						file = new File(imaPath  + fileNewPath + cltpName);   
//						if(file.isFile()){
//							returnStr = "违法数据图片（"+imaPath  + fileNewPath + cltpName+"）已存在，请请勿重复上传数据！";
//							return returnStr;
//						}
						//如果文件夹不存在，则先创建文件夹
						if(file.isDirectory()){
							log.debug("the directory is exists!");
						}else{
							file.getParentFile().mkdirs();
							//filePath.createNewFile();
							log.debug("新建目录："+file+" 成功");
						}
						
						bytes = Base64.decode(Cltp3);
						fos = new FileOutputStream(file);   
						fos.write(bytes);   
						fos.flush();   
						fos.close();
					} catch (Exception e) {
						PicInfo picInfo = new PicInfo(imaPath  + fileNewPath + cltpName,Cltp3);
						AppInitConstants.picInfoQueue.put(picInfo);
						log.error("写入违法图片失败：" + e.getMessage());
					} 
		        	cltp3FtpPath = ftpPre + virtualRoute  + fileNewPath +cltpName;
		        	gaCltp3FtpPath = gaFtpPre + virtualRoute  + fileNewPath +cltpName;
	        	}
	        	if(null != VideoFile && !"".equals(VideoFile)){//处理录像文件
	        		cltpName = CJFS + "_" + SBBH + "_" +  ItsUtility.DateToString(checkJgsj, "yyyyMMddHHmmss") + "_" + FXBH + "_" + CDH + "." + VideoType;
					fileNewPath = AppInitConstants.IMG_SERVER_ABS_FILE_PATH_WF + "/"+   ItsUtility.DateToString(checkJgsj, "yyyyMMdd") + "/" + SBBH + "/";
					try {
						file = new File(imaPath  + fileNewPath + cltpName);   
						//if(file.isFile()){
//							returnStr = "违法数据录像（"+imaPath  + fileNewPath + cltpName+"）已存在，请请勿重复上传数据！";
//							return returnStr;
//						}
						//如果文件夹不存在，则先创建文件夹
						if(file.isDirectory()){
							log.debug("the directory is exists!");
						}else{
							file.getParentFile().mkdirs();
							//filePath.createNewFile();
							log.debug("新建目录："+file+" 成功");
						}
						
						bytes = Base64.decode(VideoFile);
						fos = new FileOutputStream(file);   
						fos.write(bytes);   
						fos.flush();   
						fos.close();
						
					} catch (Exception e) {
						PicInfo picInfo = new PicInfo(imaPath  + fileNewPath + cltpName,VideoFile);
						AppInitConstants.picInfoQueue.put(picInfo);
						log.error("写入违法图片失败：" + e.getMessage());
					} 
		        	videoFtpPath = ftpPre + virtualRoute  + fileNewPath +cltpName;
		        	gaVideoFtpPath = gaFtpPre + virtualRoute  + fileNewPath +cltpName;
	        	}
	        	
	        	
	        } catch (Exception e) {
	        	log.error("写入违法图片失败：" + e.getMessage());
	        	returnStr = "违法数据图片写入失败，请检确保图片数据是否正确！" + e;
	        	return returnStr;
	        } finally{
	             if (fos != null) {
	                 try {
	                	 fos.flush(); 
	                     fos.close();
	                 }
	                 catch (Exception e){
	                 }
	             }
	         }
	        //将数据写入数据库(WFSJB_YSH)
			boolean isInsert = vehPassDao.WriteSurveilInfo(SBBH,FXBH,HPHM,HPZL,JGSJ,CLSD,HPYS,CSYS,CLLX,CDH,XS,cltp1FtpPath,cltp2FtpPath,cltp3FtpPath,CltpType,videoFtpPath,VideoType,CJFS);
			if(isInsert){
				log.debug("违法数据写入成功！");
			}else{
				returnStr = "违法数据写入失败，请检查数据字段是否正确！";
				return returnStr;
			}
			if("1".equals(isWriteGA)){
				//将数据写入数据库临时表(WFSJB_YSH_TEMP)
				boolean isInsertTemp = vehPassDao.WriteSurveilInfoTemp(SBBH,FXBH,HPHM,HPZL,JGSJ,CLSD,HPYS,CSYS,CLLX,CDH,XS,gaCltp1FtpPath,gaCltp2FtpPath,gaCltp3FtpPath,CltpType,gaVideoFtpPath,VideoType,CJFS);
				if(isInsertTemp){
					log.debug("违法数据写入临时表成功！");
				}else{
					returnStr = "违法数据写入临时表失败，请检查数据字段是否正确！";
					return returnStr;
				}
			}
		}catch(Exception e){
			log.error("写入违法数据失败：" + e.getMessage());
			returnStr = "写入违法数据失败，请检查各数据输入项格式是否正确！" + e.getMessage();
			return returnStr;
		}
		
		log.debug("WriteVehInfoServiceImpl.WriteSurveilInfo()  End......");
		
		end = System.currentTimeMillis();
		total = end - start;
		long sec = total / 1000;		//导入时间精确到秒
		log.debug("本次数据写入总共耗时： " + sec / 60 + " 分 " + sec % 60 + " 秒"+total%1000+"毫秒。 ");
		return returnStr;
	}
	
	
	/**
	  * 新违法数据(包括卡口、电警)写入接口，增加RedLightTime、RedLightDuration、ViolationType
	 * @param SBBH
	 * @param FXBH
	 * @param HPHM
	 * @param HPZL
	 * @param JGSJ
	 * @param CLSD
	 * @param HPYS
	 * @param CSYS
	 * @param CLLX
	 * @param CDH
	 * @param XS
	 * @param Cltp1
	 * @param Cltp2
	 * @param Cltp3
	 * @param CltpType
	 * @param CJFS
	 * @param RedLightTime
	 * @param RedLightDuration
	 * @param ViolationType
	 * @param check 
	 * @return
	 * String wfdd,String wfdz, String check,String jyr,String jysj,String cjr,String cjjg 针对违停新加
	 */
	public String WriteSurveilInfoExt(String SBBH,String FXBH,String HPHM,String HPZL,String JGSJ,String CLSD,String HPYS,String CSYS,
			String CLLX,String CDH,String XS,String Cltp1,String Cltp2,String Cltp3,String CltpType,String VideoFile,String VideoType,String CJFS,
			String RedLightTime,String RedLightDuration,String ViolationType,String wfdd,String wfdz, String check,String jyr,String jysj,String cjr,String cjjg){
		log.debug("WriteVehInfoServiceImpl.WriteSurveilInfoExt()  Start......");
	    long start = System.currentTimeMillis();																					
        long end;																																		
		long total = 0;	
		
		String returnStr = "";
		String fxbh = "";
		String clsd = "";
		String xsz = "";
		String dwxh = ""; // 设备点位序号
		if("".equals(SBBH)){
			return "设备编号(SBBH)不能为空，请检查写入数据项！";
		}
		if("".equals(HPHM)){
			return "号牌号码(HPHM)不能为空，请检查写入数据项！";
		}
		if("".equals(JGSJ)){
			return "过车时间(JGSJ)不能为空，请检查写入数据项！";
		}
		if("".equals(CDH)){
			return "车道编号(CDH)不能为空，请检查写入数据项！";
		}
		if("".equals(HPYS)){
			return "号牌颜色(HPYS)不能为空，请检查写入数据项！";
		}
		if("".equals(Cltp1)){
			return "违法图片1(Cltp1)不能为空，请检查写入数据项！";
		}
		//设置图片默认后缀名
		if("".equals(CltpType)){
			CltpType = "jpg";
		}
		//设置录像默认后缀名
		if("".equals(VideoType)){
			VideoType = "mp4";
		}
		
		Date checkJgsj = ItsUtility.StringToDate(JGSJ,"yyyy-MM-dd HH:mm:ss");
		log.debug("jgsj:"+checkJgsj);
		if(checkJgsj == null){
			return "过车时间(JGSJ:"+JGSJ+")格式错误，请检查写入数据项！";
		}
		JGSJ = ItsUtility.DateToString(checkJgsj, "yyyyMMddHHmmss");
		log.debug("JGSJ-VIEW:"+JGSJ);
		FileOutputStream fos = null;
		File file = null;
		byte[] bytes = null;
		String cltpName = "";
		String fileNewPath = "";
		String cltp1FtpPath = "";
		String cltp2FtpPath = "";
		String cltp3FtpPath = "";
		String videoFtpPath = "";
		//上虞上传信电
		InputStream input  = null;
		String fxName = "";
		String lkName = "";
		String xc_path = "";
		
		
		if("".equals(imaPath)){
			imaPath = AppInitConstants.IMG_SERVER_ABS_FILE_PATH;
		}
		if("".equals(ftpPre)){
			ftpPre = AppInitConstants.IMG_SERVER_FTP_PRE;
		}
		//根据配置文件随机选择   Added by Tony Lin on 2013-06-14
		//Modified by Tony Lin on 2013-06-14
		
		Map<String,String> ftpMap = ItsUtility.getRandomImgPath("wf");
		String imaPath = ftpMap.get("ImagePath");
		String virtualRoute = ftpMap.get("VirtualRoute");
		String ftpPre = ftpMap.get("ftpPre");
		if("".equals(imaPath)){
			imaPath = AppInitConstants.IMG_SERVER_ABS_FILE_PATH;
		}
		if("".equals(ftpPre)){
			ftpPre = AppInitConstants.IMG_SERVER_FTP_PRE;
		}
		if("".equals(virtualRoute)){
			virtualRoute = AppInitConstants.IMG_SERVER_FILE_PATH;
		}
		
		String gaFtpPre = SystemConfig.getConfigInfomation("gaFtpPre");
		if("".equals(gaFtpPre)){
			gaFtpPre = AppInitConstants.IMG_SERVER_FTP_PRE;
		}
		
		String gaCltp1FtpPath = "";
		String gaCltp2FtpPath = "";
		String gaCltp3FtpPath = "";
		String gaVideoFtpPath = "";
		String isWriteGA = SystemConfig.getConfigInfomation("isWriteGA");
		
		/*
		
		if(AppInitConstants.DEPLOY_PLACE.equals("yuhuan")){  // by lvhua add 2013-05-20
			if(StringUtils.isNotEmpty(FXBH)){
				if(FXBH.equals("01")){
					fxbh = "3";
				}else if(FXBH.equals("02")){
					fxbh = "4";
				}else if(FXBH.equals("03")){
					fxbh = "2";
				}else{
					fxbh = "1";
				}
			}
			if(StringUtils.isNotEmpty(CLSD)){
				if(CLSD.length()==1){
					clsd = "00"+CLSD;
				}else if(CLSD.length()==2){
					clsd = "0"+CLSD;
				}else{
					clsd =  CLSD;
				}
			}
			if(StringUtils.isNotEmpty(XS)){
				if(XS.length()==1){
					xsz = "00"+XS;
				}else if(XS.length()==2){
					xsz = "0"+XS;
				}else{
					xsz =  XS;
				}
			}
			try {
				dwxh = AppInitConstants.resource.getString(SBBH);
			} catch (Exception e) {
				log.error("不存在该设备编号:"+SBBH);
			}
		}
		*/
		
		try{
			try {
				/*
				HttpPicSaver httpPicSaver = new HttpPicSaver(AppInitConstants.HTTP_WF_PIC_IP_ZW, AppInitConstants.HTTP_PIC_PORT);
				String zwurl = "http://"+AppInitConstants.HTTP_WF_PIC_IP_ZW+":"+AppInitConstants.HTTP_PIC_PORT + "/images";
				String gaurl = "http://"+AppInitConstants.HTTP_WF_PIC_IP_GA+":"+AppInitConstants.HTTP_PIC_PORT + "/images";
				//电警违法图片存放到http 图片服务器
				if(null != Cltp1 && !"".equals(Cltp1)){//处理第一个
					cltpName = CJFS + "_" + SBBH + "_" +  ItsUtility.DateToString(checkJgsj, "yyyyMMddHHmmss") + "_" + FXBH + "_" + CDH + "_" + "1." + CltpType;
					//对图片流进行转码
					bytes = Base64.decode(Cltp1);
					//将图片保存到图片服务器 并返回图片保存地址
					String picKey =httpPicSaver.save(bytes, cltpName);
					cltp1FtpPath = zwurl+picKey;
					gaCltp1FtpPath =gaurl+picKey;
				}
				
				if( null!= Cltp2  && !"".equals(Cltp2)){//处理第二个文件
					cltpName = CJFS + "_" + SBBH + "_" +  ItsUtility.DateToString(checkJgsj, "yyyyMMddHHmmss") + "_" + FXBH + "_"  + CDH + "_" + "2." + CltpType;
					//对图片流进行转码
					bytes = Base64.decode(Cltp2);
					//将图片保存到图片服务器 并返回图片保存地址
					String picKey =httpPicSaver.save(bytes, cltpName);
					cltp2FtpPath = zwurl+picKey;
					gaCltp2FtpPath =gaurl+picKey;
				}
				if(null != Cltp3 && !"".equals(Cltp3)){//处理第三个文件
	        		cltpName = CJFS + "_" + SBBH + "_" +  ItsUtility.DateToString(checkJgsj, "yyyyMMddHHmmss") + "_" + FXBH + "_"  + CDH + "_" + "3." + CltpType;
	        		//对图片流进行转码
					bytes = Base64.decode(Cltp3);
					//将图片保存到图片服务器 并返回图片保存地址
					String picKey =httpPicSaver.save(bytes, cltpName);
					cltp3FtpPath = zwurl+picKey;
					gaCltp3FtpPath =gaurl+picKey;
				}
				*/
				
				//*************************************************************old
				
				//将违法图片存放在本地或者FTP上，违法数据写入数据库并记录违法图片地址
				//违法图片录像保存目录路劲为：日期(天)/设备编号/图片或录像名
				if(null != Cltp1  && !"".equals(Cltp1)){//处理第一个文件
					//cltpName = (CJFS + "_" + SBBH + "_" +  ItsUtility.DateToString(checkJgsj, "yyyyMMddHHmmss") + "_" + FXBH + "_" + CDH + "_" + "1." + CltpType);
					//fileNewPath = AppInitConstants.IMG_SERVER_ABS_FILE_PATH_WF  + "/"+  ItsUtility.DateToString(checkJgsj, "yyyyMMdd") + "/" + SBBH + "/";
					//fileNewPath = AppInitConstants.IMG_SERVER_ABS_FILE_PATH_WF + "/"+   ItsUtility.DateToString(checkJgsj, "yyyyMMdd") + "/" + SBBH + "/";
					 // 部署地是玉环 目录结够  ：“时间\路口\文件名”  反之相反 by lvhua 2013-05-20
					fileNewPath = AppInitConstants.IMG_SERVER_ABS_FILE_PATH_WF  + "/"+(!AppInitConstants.DEPLOY_PLACE.equals("yuhuan")?(ItsUtility.DateToString(checkJgsj, "yyyyMMdd") + "/" + SBBH + "/"):(SBBH + "/"+ItsUtility.DateToString(checkJgsj, "yyyyMMddHH")+ "/"));
					cltpName = (!AppInitConstants.DEPLOY_PLACE.equals("yuhuan")?(CJFS + "_" + SBBH + "_" +  ItsUtility.DateToString(checkJgsj, "yyyyMMddHHmmss") + "_" + FXBH + "_" + CDH + "_" + "1." + CltpType):(AppInitConstants.DEPLOY_PLACE_NO +"1"+dwxh+fxbh+JGSJ+CDH+xsz+clsd+"."+ CltpType));
					try {
						file = new File(imaPath  + fileNewPath + cltpName);  
//						if(file.isFile()){
//							returnStr = "违法数据图片（"+imaPath  + fileNewPath + cltpName+"）已存在，请请勿重复上传数据！";
//							return returnStr;
//						}
						//如果文件夹不存在，则先创建文件夹
						if(file.isDirectory()){
							log.debug("the directory is exists!");
						}else{
							file.getParentFile().mkdirs();
							//filePath.createNewFile();
							log.debug("新建目录："+file+" 成功");
						}
						
						bytes = Base64.decode(Cltp1); 
						fos = new FileOutputStream(file); 
						fos.write(bytes);   
						fos.flush();   
						fos.close();
					} catch (Exception e) {
						PicInfo picInfo = new PicInfo(imaPath  + fileNewPath + cltpName,Cltp1);
						AppInitConstants.picInfoQueue.put(picInfo);
						log.error("写入违法图片失败：" + e.getMessage());
					} 
		        	cltp1FtpPath = ftpPre + virtualRoute + fileNewPath +cltpName;
		        	gaCltp1FtpPath = gaFtpPre + virtualRoute + fileNewPath +cltpName;
				}
	        	if(null != Cltp2 &&   !"".equals(Cltp2)){//处理第二个文件
	        		//cltpName = CJFS + "_" + SBBH + "_" +  ItsUtility.DateToString(checkJgsj, "yyyyMMddHHmmss") + "_" + FXBH + "_"  + CDH + "_" + "2." + CltpType;
	        		//fileNewPath = AppInitConstants.IMG_SERVER_ABS_FILE_PATH_WF + "/"+   ItsUtility.DateToString(checkJgsj, "yyyyMMdd") + "/" + SBBH + "/";
					 // 部署地是玉环 目录结够  ：“时间\路口\文件名”  反之相反 by lvhua 2013-05-20
					fileNewPath = AppInitConstants.IMG_SERVER_ABS_FILE_PATH_WF  + "/"+(!AppInitConstants.DEPLOY_PLACE.equals("yuhuan")?(ItsUtility.DateToString(checkJgsj, "yyyyMMdd") + "/" + SBBH + "/"):(SBBH + "/"+ItsUtility.DateToString(checkJgsj, "yyyyMMddHH")+ "/"));
					cltpName = (!AppInitConstants.DEPLOY_PLACE.equals("yuhuan")?(CJFS + "_" + SBBH + "_" +  ItsUtility.DateToString(checkJgsj, "yyyyMMddHHmmss") + "_" + FXBH + "_" + CDH + "_" + "2." + CltpType):(AppInitConstants.DEPLOY_PLACE_NO +"1"+dwxh+fxbh+JGSJ+CDH+xsz+clsd+"_2."+ CltpType));
					try {
						file = new File(imaPath  + fileNewPath + cltpName);   
//						if(file.isFile()){
//							returnStr = "违法数据图片（"+imaPath  + fileNewPath + cltpName+"）已存在，请请勿重复上传数据！";
//							return returnStr;
//						}
						//如果文件夹不存在，则先创建文件夹
						if(file.isDirectory()){
							log.debug("the directory is exists!");
						}else{
							file.getParentFile().mkdirs();
							//filePath.createNewFile();
							log.debug("新建目录："+file+" 成功");
						}
						
						bytes = Base64.decode(Cltp2);   
						fos = new FileOutputStream(file);   
						fos.write(bytes);   
						fos.flush();   
						fos.close();
					} catch (Exception e) {
						PicInfo picInfo = new PicInfo(imaPath  + fileNewPath + cltpName,Cltp2);
						AppInitConstants.picInfoQueue.put(picInfo);
						log.error("写入违法图片失败：" + e.getMessage());
					} 
		        	cltp2FtpPath = ftpPre + virtualRoute  + fileNewPath +cltpName;
		        	gaCltp2FtpPath = gaFtpPre + virtualRoute + fileNewPath +cltpName;
		        	if("shangyu".equals(AppInitConstants.DEPLOY_PLACE)){
		        		log.debug("byte2_length---------"+bytes.length);
		        		input = new  ByteArrayInputStream(bytes);
		        		String filename = JGSJ+"-000B.jpg";
		        		boolean isUpload = FtpUtil.UploadFileByIp(AppInitConstants.FTP_HOST_NAME_XC, input, xc_path, filename);
		        		if(isUpload){
		        			log.debug("上传信电FTP图片2："+filename+" 成功!");
		        		}else{
		        			log.debug("上传信电FTP图片2："+filename+" 失败!");
		        		}
		        		input.close();
		        	}
	        	}
	        	if(null != Cltp3 && !"".equals(Cltp3)){//处理第三个文件
	        		//cltpName = CJFS + "_" + SBBH + "_" +  ItsUtility.DateToString(checkJgsj, "yyyyMMddHHmmss") + "_" + FXBH + "_"  + CDH + "_" + "3." + CltpType;
	        		//fileNewPath = AppInitConstants.IMG_SERVER_ABS_FILE_PATH_WF + "/"+   ItsUtility.DateToString(checkJgsj, "yyyyMMdd") + "/" + SBBH + "/";
					 // 部署地是玉环 目录结够  ：“时间\路口\文件名”  反之相反 by lvhua 2013-05-20
					fileNewPath = AppInitConstants.IMG_SERVER_ABS_FILE_PATH_WF  + "/"+(!AppInitConstants.DEPLOY_PLACE.equals("yuhuan")?(ItsUtility.DateToString(checkJgsj, "yyyyMMdd") + "/" + SBBH + "/"):(SBBH + "/"+ItsUtility.DateToString(checkJgsj, "yyyyMMddHH")+ "/"));
					cltpName = (!AppInitConstants.DEPLOY_PLACE.equals("yuhuan")?(CJFS + "_" + SBBH + "_" +  ItsUtility.DateToString(checkJgsj, "yyyyMMddHHmmss") + "_" + FXBH + "_" + CDH + "_" + "3." + CltpType):(AppInitConstants.DEPLOY_PLACE_NO +"1"+dwxh+fxbh+JGSJ+CDH+xsz+clsd+"_3."+ CltpType));
					try {
						file = new File(imaPath  + fileNewPath + cltpName);   
//						if(file.isFile()){
//							returnStr = "违法数据图片（"+imaPath  + fileNewPath + cltpName+"）已存在，请请勿重复上传数据！";
//							return returnStr;
//						}
						//如果文件夹不存在，则先创建文件夹
						if(file.isDirectory()){
							log.debug("the directory is exists!");
						}else{
							file.getParentFile().mkdirs();
							//filePath.createNewFile();
							log.debug("新建目录："+file+" 成功");
						}
						
						bytes = Base64.decode(Cltp3);
						fos = new FileOutputStream(file);   
						fos.write(bytes);   
						fos.flush();   
						fos.close();
					} catch (Exception e) {
						PicInfo picInfo = new PicInfo(imaPath  + fileNewPath + cltpName,Cltp3);
						AppInitConstants.picInfoQueue.put(picInfo);
						log.error("写入违法图片失败：" + e.getMessage());
					} 
		        	cltp3FtpPath = ftpPre + virtualRoute  + fileNewPath +cltpName;
		        	gaCltp3FtpPath = gaFtpPre + virtualRoute + fileNewPath +cltpName;
	        	}
	        	if(null != VideoFile &&  !"".equals(VideoFile)){//处理录像文件
	        		//cltpName = CJFS + "_" + SBBH + "_" +  ItsUtility.DateToString(checkJgsj, "yyyyMMddHHmmss") + "_" + FXBH + "_" + CDH + "." + VideoType;
					//fileNewPath = AppInitConstants.IMG_SERVER_ABS_FILE_PATH_WF + "/"+   ItsUtility.DateToString(checkJgsj, "yyyyMMdd") + "/" + SBBH + "/";
					 // 部署地是玉环 目录结够  ：“时间\路口\文件名”  反之相反 by lvhua 2013-05-20
					fileNewPath = AppInitConstants.IMG_SERVER_ABS_FILE_PATH_WF  + "/"+(!AppInitConstants.DEPLOY_PLACE.equals("yuhuan")?(ItsUtility.DateToString(checkJgsj, "yyyyMMdd") + "/" + SBBH + "/"):(SBBH + "/"+ItsUtility.DateToString(checkJgsj, "yyyyMMddHH")+ "/"));
					cltpName = (!AppInitConstants.DEPLOY_PLACE.equals("yuhuan")?(CJFS + "_" + SBBH + "_" +  ItsUtility.DateToString(checkJgsj, "yyyyMMddHHmmss") + "_" + FXBH + "_" + CDH + "_" + "." + VideoType):(AppInitConstants.DEPLOY_PLACE_NO +"1"+dwxh+fxbh+JGSJ+CDH+xsz+clsd+"."+ VideoType));
					try {
						file = new File(imaPath  + fileNewPath + cltpName);   
//						if(file.isFile()){
//							returnStr = "违法数据录像（"+imaPath  + fileNewPath + cltpName+"）已存在，请请勿重复上传数据！";
//							return returnStr;
//						}
						//如果文件夹不存在，则先创建文件夹
						if(file.isDirectory()){
							log.debug("the directory is exists!");
						}else{
							file.getParentFile().mkdirs();
							//filePath.createNewFile();
							log.debug("新建目录："+file+" 成功");
						}
						
						bytes = Base64.decode(VideoFile);
						fos = new FileOutputStream(file);   
						fos.write(bytes);   
						fos.flush();   
						fos.close();
						
					} catch (Exception e) {
						PicInfo picInfo = new PicInfo(imaPath  + fileNewPath + cltpName,VideoFile);
						AppInitConstants.picInfoQueue.put(picInfo);
						log.error("写入违法图片失败：" + e.getMessage());
					} 
		        	videoFtpPath = ftpPre + virtualRoute  + fileNewPath +cltpName;
		        	gaVideoFtpPath = gaFtpPre + virtualRoute + fileNewPath +cltpName;
	        	}
	        	
	        	
	        } catch (Exception e) {
	        	log.error("写入违法图片失败：" + e.getMessage());
	        	returnStr = "违法数据图片写入失败，请检确保图片数据是否正确！" + e;
	        	return returnStr;
	        } finally{
	             if (fos != null) {
	                 try {
	                	 fos.flush(); 
	                     fos.close();
	                 }
	                 catch (Exception e){
	                	 e.printStackTrace();
	                 }
	             }
	         }
	        //将数据写入数据库(WFSJB_YSH) 
			//2017-2-23 add wfdd mowangzhong
			//2017-5-3 add is_check mowangzhong 
			//2017-5-5 add jyr jyss，cjr,cjjg mowanghzong
			boolean isInsert = vehPassDao.WriteSurveilInfoExt(SBBH,FXBH,HPHM,HPZL,JGSJ,CLSD,HPYS,CSYS,CLLX,CDH,XS,cltp1FtpPath,cltp2FtpPath,cltp3FtpPath,CltpType,videoFtpPath,VideoType,CJFS,RedLightTime,RedLightDuration,ViolationType,wfdd,wfdz,check,jyr,jysj,cjr,cjjg);
			if(isInsert){
				log.debug("违法数据写入成功！");
			}else{
				returnStr = "违法数据写入失败，请检查数据字段是否正确！";
				return returnStr;
			}
			if("1".equals(isWriteGA)){
				boolean isInsertTemp = vehPassDao.WriteSurveilInfoExtTemp(SBBH,FXBH,HPHM,HPZL,JGSJ,CLSD,HPYS,CSYS,CLLX,CDH,XS,gaCltp1FtpPath,gaCltp2FtpPath,gaCltp3FtpPath,CltpType,gaVideoFtpPath,VideoType,CJFS,RedLightTime,RedLightDuration,ViolationType,wfdd,wfdz,check,jyr,jysj,cjr,cjjg);
				if(isInsertTemp){
					log.debug("违法数据写入临时表成功！");
				}else{
					returnStr = "违法数据写入临时表失败，请检查数据字段是否正确！";
					return returnStr;
				}
			}
			
		}catch(Exception e){
			log.error("写入违法数据失败：" + e.getMessage());
			returnStr = "写入违法数据失败，请检查各数据输入项格式是否正确！" + e.getMessage();
			return returnStr;
		}
		
		log.debug("WriteVehInfoServiceImpl.WriteSurveilInfoExt()  End......");
		
		end = System.currentTimeMillis();
		total = end - start;
		long sec = total / 1000;		//导入时间精确到秒
		log.info("本次数据写入总共耗时： " + sec / 60 + " 分 " + sec % 60 + " 秒"+total%1000+"毫秒。 ");
		return returnStr;
	}
	
}
