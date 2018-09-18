/**
 * Copyright(c) SUPCON 2008-2011. 浙江浙大中控信息技术有限公司
 */

package its.webservice.service;


/**
 * 系统名称：智能交通WebService服务(ITSWebService)
 * 所属模块：过车数据写入
 * 功能描述：过车数据写入接口
 * 文件名：com.supconit.its.service.WriteVehInfoService.java
 * 版本信息：1.00
 * 
 * 开发部门：研发中心
 * 创建者： lzk
 * 创建时间：Jan 25, 2011 4:05:06 PM
 * 修改者： lzk
 * 修改时间：Jan 25, 2011 4:05:06 PM
 */

public interface VehInfoService {
	
	/**
	 * 初始化信息写入
	 * @param deviceCode
	 * @param deviceKey
	 * @return
	 */
	public String InitWriteVehInfo(String deviceCode,String deviceKey);
	
	/**
	 * 过车数据(带图片)写入接口服务
	 * 
	 * @return String
	 */
	public String WriteVehicleInfoWithPhoto(String SBBH,String FXBH,String HPHM,String HPZL,String JGSJ,String CLSD,String HPYS,String CSYS,String CLLX,String TZTP,String QMTP,String HPTP,String TPTYPE,String CDH,String XS);
	

	/**
	 * 查询远程服务器时间
	 * @return
	 */
	public String QueryServerTime();
	
	
	
	/**
	 * 新违法数据(包括卡口、电警)写入接口，增加RedLightTime、RedLightDuration、ViolationType
	 */
	public String WriteSurveilInfoExt(String SBBH,String FXBH,String HPHM,String HPZL,String JGSJ,String CLSD,
			String HPYS,String CSYS,String CLLX,String CDH,String XS,String Cltp1,String Cltp2,String Cltp3,
			String CltpType,String VideoFile,String VideoType,String CJFS,String RedLightTime,
			String RedLightDuration,String ViolationType,String wfdd,String wfdz,
			String check,String jyr,String jysj,String cjr,String cjjg);
	
	}
