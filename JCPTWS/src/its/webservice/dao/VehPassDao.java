/**
 * Copyright(c) SUPCON 2008-2011. 浙江浙大中控信息技术有限公司
 */

package its.webservice.dao;

import java.util.List;
import java.util.Map;

import its.webservice.entity.WbRecord;
import its.webservice.service.BlackNameInfo;
import its.webservice.service.DeviceStatInfo;
import its.webservice.service.VehicleInfo;

/**
 * 系统名称：智能交通WebService服务(ITSWebService)
 * 所属模块：过车数据导入
 * 功能描述：过车数据导入数据库接口
 * 文件名：com.supconit.its.dao.VehPassDao.java
 * 版本信息：1.00
 * 
 * 开发部门：研发中心
 * 创建者： lzk
 * 创建时间：Jan 25, 2011 4:20:13 PM
 * 修改者： lzk
 * 修改时间：Jan 25, 2011 4:20:13 PM
 */

public interface VehPassDao {
	
	/**
	 * 过车数据写入接口服务
	 * @param 
	 * @return String
	 */
	public boolean WriteVehicleInfo(String SBBH,String FXBH,String HPHM,String HPZL,String JGSJ,String CLSD,String HPYS,String CSYS,String CLLX,String TZTP,String QMTP,String HPTP,String CDH,String XS);
	
	/**
	 * 过车数据(带图片)写入接口服务
	 * 
	 * @return String
	 */
	public boolean WriteVehicleInfoWithPhoto(String SBBH,String FXBH,String HPHM,String HPZL,String JGSJ,String CLSD,String HPYS,String CSYS,String CLLX,String TZTPFtpPath,String QMTPFtpPath,String HPTPFtpPath,String CDH,String XS);
	
	/**
	 * 布控报警数据写入接口服务
	 * @param vehInfo
	 * @return String
	 */
	public boolean WriteAlarmVehInfo(VehicleInfo vehInfo);
	
	/**
	 * 黑名单数据写入接口服务
	 * @param blackNameInfo
	 * @return
	 */
	public boolean WriteBlackName(BlackNameInfo blackNameInfo);
	
	/**
	 * 获得所有设备编号/密钥Map
	 * @return Map<deviceCode,deviceKey>
	 */
	public Map<String,String> getAllDeviceKey();
	
	/**
	 * 初始化卡口写入过车数据
	 * @param deviceCode
	 * @param deviceKey
	 * @return
	 */
	public boolean InitWriteVehInfo(String deviceCode,String deviceKey);
	
	/**
	 * 判断卡口平台是否已添加该路口
	 * @param deviceCode
	 * @return
	 */
	public boolean hasAddedCode(String deviceCode);
	
	/**
	 * 判断是否已存在该黑名单
	 * @param cph
	 * @return
	 */
	public boolean hasThisBlackName(String cph);
	
	/**
	 * 设备状态写入接口服务
	 * @return
	 */
	public boolean WriteDeviceStat(DeviceStatInfo deviceStatInfo);
	
	/**
	 * 获得所有路口信息（ID,NAME）
	 * @return
	 */
	public List<Map<String,String>> getAllCross();
	
	/**
	 * 获取车辆信息
	 * @param passID
	 * @return
	 */
	public List<Map<String,String>> getVehicleDetail(String passID);
	
	/**
	 * 获取卡口详情信息
	 * @param crossID
	 * @return
	 */
	public List<Map<String,String>> getCrossDetail(String crossID);
	
	/**
	 * 获取统计信息
	 * @return
	 */
	public List<Map<String,String>> getStatInfo(String hphm,String clzl,String hpys,String startTime,String endTime,String crossID,String fxList);

	/**
	 * 根据查询条件获得总数
	 * @param hphm
	 * @param cllx
	 * @param hpys
	 * @param startTime
	 * @param endTime
	 * @param crossID
	 * @return
	 */
	public int getPassTotolCount(String hphm, String cllx, String hpys,
			String startTime, String endTime, String crossID);
	
	
	/**
	 * 写入嫌疑车牌
	 * @return
	 */
	public boolean insertFaultCp(VehicleInfo vehInfo);
	
	/**
	 * 根据行政辖区查询卡口
	 * @param canton
	 * @return
	 */
	public List<Map<String,String>> getCrossByCanton(String canton);
	
	/**
	 * 写入违法车辆数据
	 * @param 
	 * @return
	 */
	public boolean insertWFCLB(String SBBH,String FXBH,String HPHM,String HPZL,String JGSJ,String CLSD,String HPYS,String CSYS,String CLLX,String TZTP,String QMTP,String HPTP,String CDH,String XS);
	/*
	 * 写入违法车辆数据
	 * @param 
	 * @return
	 */
	 
	public boolean insertWFCLBTemp(String SBBH,String FXBH,String HPHM,String HPZL,String JGSJ,String CLSD,String HPYS,String CSYS,String CLLX,String TZTP,String QMTP,String HPTP,String CDH,String XS);
	
	 /**
	 * 
	 *@方法名称:insertHbcToWFCLB
	 *@输    入: 
	 *@输    出：
	 *@作    者: 张晓宇
	 *@创建日期:2013-8-8
	 *@方法描述:  黄标车写入违法数据表
	 */
	public boolean insertHbcToWFCLB(String SBBH,String FXBH,String HPHM,String HPZL,String JGSJ,String CLSD,String HPYS,
			String CSYS,String CLLX,String TZTP,String QMTP,String HPTP,String CDH,String XS);
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
	public boolean WriteSurveilInfo(String SBBH,String FXBH,String HPHM,String HPZL,String JGSJ,String CLSD,String HPYS,String CSYS,String CLLX,String CDH,String XS,String Cltp1,String Cltp2,String Cltp3,String CltpType,String VideoFile,String VideoType,String CJFS);
	
	public boolean WriteSurveilInfoExt(String SBBH,String FXBH,String HPHM,String HPZL,String JGSJ,String CLSD,String HPYS,String CSYS,String CLLX,String CDH,String XS,String Cltp1,String Cltp2,String Cltp3,String CltpType,String VideoFile,String VideoType,String CJFS,String RedLightTime,String RedLightDuration,String ViolationType);
	
	/**
	 * 检测是否为布控车辆
	 * @param jgsj
	 * @param sbbh
	 * @param hphm
	 * @param csys
	 * @param hpzl
	 * @param cllx
	 * @return
	 */
	public Map<String,String> checkAlarmInfo(String jgsj, String sbbh, String hphm, String csys, String hpzl, String cllx);
	
	/**
	 * 判断是否需要违法采集
	 * @param sbbh
	 * @return
	 */
	public boolean isNeedWfcj(String sbbh);
	
	/**
	 * 写入布控报警信息
	 * @param SBBH
	 * @param FXBH
	 * @param HPHM
	 * @param HPZL
	 * @param JGSJ
	 * @param CLSD
	 * @param HPYS
	 * @param CSYS
	 * @param CLLX
	 * @param TZTP
	 * @param QMTP
	 * @param HPTP
	 * @param CDH
	 * @param XS
	 * @param BKID
	 * @param BK_TYPE_ID
	 * @return
	 */
	public boolean insertBkAlarmVeh(String SBBH,String FXBH,String HPHM,String HPZL,
			String JGSJ,String CLSD,String HPYS,String CSYS,String CLLX,String TZTP,String QMTP,String HPTP,String CDH,String XS,String BKID,String BK_TYPE_ID);
	
	/**
	 * add by pgh
	 * @param wbReco
	 * @return
	 */
	public int insertWbRecord(WbRecord wbReco);
	/**
	 *@方法名称:isHbc
	 *@输    入: 
	 *@输    出：
	 *@作    者: 张晓宇
	 *@创建日期:2013-8-8
	 *@方法描述: 判断是否黄标车  
	 */
	public boolean isHbc(String cph);
	
	/**
	 *@方法名称:isInWfsj
	 *@输    入: 
	 *@输    出：
	 *@作    者: 张晓宇
	 *@创建日期:2013-7-22
	 *@方法描述: 判断24小时内违法数据是否包含 
	 */
	public boolean isInWfsj(String hphm);
	/**
	 *@方法名称:getSysdate
	 *@输    入: 
	 *@输    出：
	 *@作    者: 张晓宇
	 *@创建日期:2013-7-26
	 *@方法描述:  获取系统时间
	 *
	 */
	public String getSysdate();
	
	/**
	 *@方法名称:getEpsNameByCode
	 *@输    入: 
	 *@输    出：
	 *@作    者: 张晓宇
	 *@创建日期:2013-10-25
	 *@方法描述:  根据设备编号获取设备信息
	 */
	public String getDeviceNameByCode(String sbbh);
	
	public String getTgsNameByCode(String sbbh);

	public boolean WriteVehicleInfoWithPhotoTemp(String SBBH,String FXBH,String HPHM,String HPZL,String JGSJ,String CLSD,String HPYS,String CSYS,String CLLX,String TZTPFtpPath,String QMTPFtpPath,String HPTPFtpPath,String CDH,String XS);

	/**
	 * 违法数据(包括卡口、电警)写入接口(临时表)
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
	public boolean WriteSurveilInfoTemp(String SBBH,String FXBH,String HPHM,String HPZL,String JGSJ,String CLSD,String HPYS,String CSYS,String CLLX,String CDH,String XS,String Cltp1,String Cltp2,String Cltp3,String CltpType,String VideoFile,String VideoType,String CJFS);

	public boolean WriteSurveilInfoExtTemp(String SBBH,String FXBH,String HPHM,String HPZL,String JGSJ,String CLSD,String HPYS,String CSYS,String CLLX,String CDH,String XS,String Cltp1,String Cltp2,String Cltp3,String CltpType,String VideoFile,String VideoType,String CJFS,String RedLightTime,String RedLightDuration,String ViolationType);

	public boolean WriteBMDVehicleInfoWithPhoto(String SBBH,String FXBH,String HPHM,String HPZL,String JGSJ,String CLSD,String HPYS,String CSYS,String CLLX,String TZTPFtpPath,String QMTPFtpPath,String HPTPFtpPath,String CDH,String XS);
	
}
