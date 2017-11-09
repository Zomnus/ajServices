/**
 * Copyright(c) SUPCON 2008-2011. 浙江浙大中控信息技术有限公司
 */

package its.webservice.dao.impl;

import its.webservice.common.AppInitConstants;
import its.webservice.common.BaseRowMapper;
import its.webservice.dao.VehPassDao;
import its.webservice.entity.EpsInfoBean;
import its.webservice.entity.HbcInfo;
import its.webservice.entity.WbRecord;
import its.webservice.service.BlackNameInfo;
import its.webservice.service.DeviceStatInfo;
import its.webservice.service.VehicleInfo;
import its.webservice.util.ItsUtility;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;



/**
 * 系统名称：智能交通WebService服务(ITSWebService)
 * 所属模块：过车数据导入
 * 功能描述：过车数据导入数据库接口实现类
 * 文件名：com.supconit.its.dao.impl.VehPassDaoImpl.java
 * 版本信息：1.00
 * 
 * 开发部门：研发中心
 * 创建者： lzk
 * 创建时间：Jan 25, 2011 4:22:30 PM
 * 修改者： lzk
 * 修改时间：Jan 25, 2011 4:22:30 PM
 */

public class VehPassDaoImpl extends JdbcTemplate implements VehPassDao {
	
	/** 获取Log4j实例 */
    protected Logger log = Logger.getLogger(VehPassDaoImpl.class.getName());

    /**
	 * 过车数据写入接口服务
	 * @param vehInfo
	 * @return String
	 */
	public boolean WriteVehicleInfo(String SBBH,String FXBH,String HPHM,String HPZL,String JGSJ,String CLSD,String HPYS,
			String CSYS,String CLLX,String TZTP,String QMTP,String HPTP,String CDH,String XS) {
		log.debug("VehPassDaoImpl.WriteVehicleInfo() Start......");
		
		String sql = "insert into kk_tgs_pass_vehicle(fxbh,hphm,hpzl,jgsj,clsd," +
				"hpys,csys,cllx,tztp,qmtp,hptp,jgdd,cdh,xs) " +
				"values('"+FXBH+"','"+HPHM+
				"','"+HPZL+"',to_date('"+JGSJ+"','yyyy-mm-dd hh24:mi:ss'),"+CLSD+",'"+HPYS+
				"','"+CSYS+"','"+CLLX+"','"+TZTP+"','"+QMTP+
				"','"+HPTP+"','"+SBBH+"','"+CDH+"',"+XS+")";
		log.debug("WriteVehicleInfo.sql:" + sql);
		
		int updateRow = this.update(sql);
		
		log.debug("VehPassDaoImpl.WriteVehicleInfo() End......");
		return (updateRow == 1);
	}
	
	/**
	 * 过车数据(带图片)写入接口服务
	 * 
	 * @return String
	 */
	public boolean WriteVehicleInfoWithPhoto(String SBBH,String FXBH,String HPHM,String HPZL,String JGSJ,String CLSD,String HPYS,
			String CSYS,String CLLX,String TZTPFtpPath,String QMTPFtpPath,String HPTPFtpPath,String CDH,String XS){
		log.debug("VehPassDaoImpl.WriteVehicleInfoWithPhoto() Start......");
		
		String sql = "insert into kk_tgs_pass_vehicle(fxbh,hphm,hpzl,jgsj,clsd," +
				"hpys,csys,cllx,tztp,qmtp,hptp,jgdd,cdh,xs) " +
				"values('"+FXBH+"','"+HPHM+
				"','"+HPZL+"',to_date('"+JGSJ+"','yyyy-mm-dd hh24:mi:ss'),"+CLSD+",'"+HPYS+
				"','"+CSYS+"','"+CLLX+"','"+TZTPFtpPath+"','"+QMTPFtpPath+
				"','"+HPTPFtpPath+"','"+SBBH+"','"+CDH+"',"+XS+")";
		log.debug("WriteVehicleInfo.sql:" + sql);
		
		int updateRow = this.update(sql);
		
		log.debug("VehPassDaoImpl.WriteVehicleInfoWithPhoto() End......");
		return (updateRow == 1);
	}
	
	/**
	 * 布控报警数据写入接口服务
	 * @param vehInfo
	 * @return String
	 */
	public boolean WriteAlarmVehInfo(VehicleInfo vehInfo){
		log.debug("VehPassDaoImpl.WriteAlarmVehInfo() Start......");
		
		String sql = "insert into CAUGHT_PASS_VEHICLE(clbh,fxbh,hphm,hpzl,jgsj,clsd," +
				"hpys,csys,cllx,tztp,qmtp,hptp,jgdd,cdh,xs,csbz) " +
				"values('" + vehInfo.getClbh() + "','"+vehInfo.getFxbh()+"','"+vehInfo.getHphm()+
				"','"+vehInfo.getHpzl()+"',to_date('"+vehInfo.getJgsj()+"','yyyy-mm-dd hh24:mi:ss'),"+vehInfo.getClsd()+",'"+vehInfo.getHpys()+
				"','"+vehInfo.getCsys()+"','"+vehInfo.getCllx()+"','"+vehInfo.getTztp()+"','"+vehInfo.getQmtp()+
				"','"+vehInfo.getHptp()+"','"+vehInfo.getJgdd()+"','"+vehInfo.getCdh()+"',"+vehInfo.getXs()+",'"+vehInfo.getCsbz()+"')";
		log.debug("WriteVehicleInfo.sql:" + sql);
		
		int updateRow = this.update(sql);
		
		log.debug("VehPassDaoImpl.WriteAlarmVehInfo() End......");
		return (updateRow == 1);
	}
	
	/**
	 * 黑名单数据写入接口服务
	 * @param blackNameInfo
	 * @return
	 */
	public boolean WriteBlackName(BlackNameInfo blackNameInfo){
		log.debug("VehPassDaoImpl.WriteBlackName() Start......");
		
		String sql = "insert into black_name_list(cph,hpzl,clys,cllx,clgsd,zzg,zzcmc,fdjh,fdjxh,pl,gl,cz,cz_tel) " +
				"values('"+blackNameInfo.getCph()+"','"+blackNameInfo.getHpzl()+"','"+
				blackNameInfo.getClys()+"','"+blackNameInfo.getCllx()+"','"+blackNameInfo.getClgsd()+
				"','"+blackNameInfo.getZzg()+"','"+blackNameInfo.getZzcmc()+"','"+blackNameInfo.getFdjh()+
				"','"+blackNameInfo.getFdjxh()+
				"','"+blackNameInfo.getPl()+"','"+blackNameInfo.getGl()+"','"+blackNameInfo.getCz()+
				"','"+blackNameInfo.getCz_tel()+"')";
		
		log.debug("WriteBlackName.sql:" + sql);
		
		int insertRow = this.update(sql);
		
		log.debug("VehPassDaoImpl.WriteBlackName() End......");
		return (insertRow == 1);
	}
	
	/**
	 * 获得所有设备编号/密钥Map
	 * @return Map<deviceCode,deviceKey>
	 */
	@SuppressWarnings("unchecked")
	public Map<String,String> getAllDeviceKey(){
		log.debug("VehPassDaoImpl.getAllDeviceKey() Start......");
		
		String sql = "select DEVICE_CODE,DEVICE_KEY from device_code_key";
		List codeKeyList = this.queryForList(sql);
		Map<String,String> codeKeyMap = new HashMap<String,String>();
		
		Iterator rowIterator = codeKeyList.iterator();
        while (rowIterator.hasNext()) {
        	Map rowMap = (Map)rowIterator.next();
            codeKeyMap.put(rowMap.get("DEVICE_CODE").toString(),rowMap.get("DEVICE_KEY").toString());
        }
		
		log.debug("VehPassDaoImpl.getAllDeviceKey() End......");
		return codeKeyMap;
	}
	
	/**
	 * 初始化卡口写入过车数据
	 * @param deviceCode
	 * @param deviceKey
	 * @return
	 */
	public boolean InitWriteVehInfo(String deviceCode,String deviceKey){
		log.debug("VehPassDaoImpl.InitWriteVehInfo() Start......");
		
		String sql = "insert into device_code_key(device_code,device_key) values('"+deviceCode+"','"+deviceKey+"')";
		
		int insertRow = this.update(sql);
		
		log.debug("VehPassDaoImpl.InitWriteVehInfo() End......");
		return (insertRow == 1);
	}
	
	/**
	 * 判断卡口平台是否已添加该路口
	 * @param deviceCode
	 * @return
	 */
	public boolean hasAddedCode(String deviceCode){
		log.debug("VehPassDaoImpl.hasAddedCode() Start......");
		
		String sql = "select count(*) from cross where cross_no = '"+deviceCode+"'";
		log.debug("hasAddedCode.sql:" + sql);
		
		int row = this.queryForInt(sql);
		
		log.debug("VehPassDaoImpl.hasAddedCode() End......");
		return (row >= 1);
	}
	
	/**
	 * 判断是否已存在该黑名单
	 * @param cph
	 * @return
	 */
	public boolean hasThisBlackName(String cph){
		log.debug("VehPassDaoImpl.hasThisBlackName() Start......");
		
		String sql = "select count(*) from black_name_list where cph = '"+cph+"'";
		
		int row = this.queryForInt(sql);
		
		log.debug("VehPassDaoImpl.hasThisBlackName() End......");
		return (row >= 1);
	}
	
	/**
	 * 设备状态写入接口服务
	 * @return
	 */
	public boolean WriteDeviceStat(DeviceStatInfo deviceStatInfo){
		log.debug("VehPassDaoImpl.WriteDeviceStat() Start......");
		
		String sql = "insert into device_status_log(change_time,cross_no," +
				"device_type,device_code,device_status,description) " +
				"values(to_date('"+deviceStatInfo.getChange_time()+"','yyyy-mm-dd hh24:mi:ss'),'"+deviceStatInfo.getCross_no()+
				"','"+deviceStatInfo.getDevice_type()+"','"+deviceStatInfo.getDevice_code()+
				"','"+deviceStatInfo.getDevice_status()+"','"+deviceStatInfo.getDescription()+"')";
		
		log.debug("WriteBlackName.sql:" + sql);
		
		int insertRow = this.update(sql);
		
		log.debug("VehPassDaoImpl.WriteDeviceStat() End......");
		return (insertRow == 1);
	}
	
	/**
	 * 获得所有路口信息（ID,NAME）
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,String>> getAllCross(){
		log.debug("VehPassDaoImpl.getAllCross() Start......");
		
		String sql = "select cross_no,cross_name from cross ";
		
		List<Map<String,String>> crossList = this.queryForList(sql);
		
		log.debug("VehPassDaoImpl.getAllCross() End......");
		return crossList;
	}
	
	/**
	 * 获取车辆信息
	 * @param passID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,String>> getVehicleDetail(String passID){
		log.debug("VehPassDaoImpl.getVehicleDetail() Start......");
		
		String sql = "select to_char(t.id) id,\n" +
					 "       t.hphm,\n" + 
					 "       t.hpzl,\n" + 
					 "       t.tztp,\n" + 
					 "       t.hpys,\n" + 
					 "       t.hptp,\n" + 
					 "       t.jgdd,\n" + 
					 "       t.fxbh,\n" + 
					 "       to_char(t.jgsj, 'yyyy-mm-dd hh24:mi:ss') jgsj,\n" + 
					 "       to_char(t.clsd) clsd\n" + 
					 "  from tgs_pass_vehicle t where t.id = "+passID+"";
		log.debug("VehPassDaoImpl.getVehicleDetail().sql:" + sql);
		
		List<Map<String,String>> passInfoList = this.queryForList(sql);
		
		log.debug("VehPassDaoImpl.getVehicleDetail() End......");
		return passInfoList;
	}
	
	/**
	 * 获取卡口详情信息
	 * @param crossID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,String>> getCrossDetail(String crossID){
		log.debug("VehPassDaoImpl.getCrossDetail() Start......");
		
		String sql = "select t.cross_no,t.cross_name,t.address,to_char(t.cds) cds,fx_list,t.maintainer,t.builder,t.status from cross t where t.cross_no = '"+crossID+"'";
		log.debug("VehPassDaoImpl.getCrossDetail().sql:" + sql);
		
		List<Map<String,String>> crossList = this.queryForList(sql);
		
		log.debug("VehPassDaoImpl.getCrossDetail() End......");
		return crossList;
	}
	
	/**
	 * 获取统计信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,String>> getStatInfo(String hphm,String clzl,String hpys,String startTime,String endTime,String crossID,String fxList){
		log.debug("VehPassDaoImpl.getStatInfo() Start......");
		
		String sql = "select t.jgdd,to_char(count(t.jgsj)) count from tgs_pass_vehicle t where 1 = 1 ";
		String filter = "";
		if(!"".equals(hphm)){
			filter += "and t.hphm = '"+hphm+"' ";
		}
		if(!"".equals(clzl)){
			filter += "and t.cllx = '"+clzl+"' ";
		}
		if(!"".equals(hpys)){
			filter += "and t.hpys = '"+hpys+"' ";
		}
		//处理多个crossID,多个用,隔开
		if(!"".equals(crossID)){
			String[] crosses = crossID.split(",");
			filter += ItsUtility.getSqlOrString("t.jgdd",crosses);
		}
		if(!"".equals(startTime)){
			filter += " and t.jgsj >= to_date('"+startTime+"','yyyy-mm-dd hh24:mi:ss') ";
		}
		if(!"".equals(endTime)){
			filter += " and t.jgsj <= to_date('"+endTime+"','yyyy-mm-dd hh24:mi:ss') ";
		}
		//增加方向查询条件
		if(!"".equals(fxList)){
			String[] fx = fxList.split(",");
			filter += ItsUtility.getSqlOrString("t.fxbh",fx);
		}
		
		sql += filter;
		sql += " group by t.jgdd ";
		log.debug("VehPassDaoImpl.getStatInfo().sql:" + sql);
		
		List<Map<String,String>> jgddCountList = this.queryForList(sql);
		
		log.debug("VehPassDaoImpl.getStatInfo() End......");
		return jgddCountList;
	}
	
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
			String startTime, String endTime, String crossID){
		log.debug("VehPassDaoImpl.getPassTotolCount() Start......");
		String sql = "select to_char(count(t.jgsj)) count from tgs_pass_vehicle t where 1=1 ";
		String filter = "";
		if(!"".equals(hphm)){
			filter += "and t.hphm = '"+hphm+"' ";
		}
		if(!"".equals(cllx)){
			filter += "and t.cllx = '"+cllx+"' ";
		}
		if(!"".equals(hpys)){
			filter += "and t.hpys = '"+hpys+"' ";
		}
		//处理多个crossID,多个用,隔开
		if(!"".equals(crossID)){
			String[] crosses = crossID.split(",");
			filter += ItsUtility.getSqlOrString("t.jgdd",crosses);
		}
		if(!"".equals(startTime)){
			filter += " and t.jgsj >= to_date('"+startTime+"','yyyy-mm-dd hh24:mi:ss') ";
		}
		if(!"".equals(endTime)){
			filter += " and t.jgsj <= to_date('"+endTime+"','yyyy-mm-dd hh24:mi:ss') ";
		}
		sql += filter;
		log.debug("VehPassDaoImpl.getPassTotolCount().sql:" + sql);
		
		int totalCount = this.queryForInt(sql);
		
		log.debug("VehPassDaoImpl.getPassTotolCount() End......");
		return totalCount;
	}
	
	
	/**
	 * 写入嫌疑车牌
	 * @return
	 */
	public boolean insertFaultCp(VehicleInfo vehInfo){
		log.debug("VehPassDaoImpl.insertFaultCp() Start......");
		
		String sql = "insert into jcpb(hphm,jgsj,jgdd,fxbh,hpzl,cdh,clsd,tztp,qmtp,hptp,hpys) " +
				"values('"+vehInfo.getHphm()+"',to_date('"+vehInfo.getJgsj()+"','yyyy-mm-dd hh24:mi:ss'),'"+vehInfo.getJgdd()+
				"','"+vehInfo.getFxbh()+"','"+vehInfo.getHpzl()+"','"+vehInfo.getCdh()+
				"','"+vehInfo.getClsd()+"','"+vehInfo.getTztp()+"','"+vehInfo.getQmtp()+
				"','"+vehInfo.getHptp()+"','"+vehInfo.getHpys()+"')";
		
		log.debug("insertFaultCp.sql:" + sql);
		
		int insertRow = this.update(sql);
		
		log.debug("VehPassDaoImpl.insertFaultCp() End......");
		return (insertRow == 1);
	}
	
	/**
	 * 根据行政辖区查询卡口
	 * @param canton
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,String>> getCrossByCanton(String canton){
		log.debug("VehPassDaoImpl.getCrossByCanton() Start......");
		
		String sql = "select cross_no,cross_name,fx_list,canton,address,cds from cross where canton = '"+canton+"'";
		
		log.debug("VehPassDaoImpl.getCrossByCanton().sql:" + sql);
		
		List<Map<String,String>> crossList = this.queryForList(sql);
		
		log.debug("VehPassDaoImpl.getCrossByCanton() End......");
		return crossList;
	}
	
	/**
	 * 写入违法车辆数据WFSJB_YSH
	 * @param 
	 * @return
	 */
	public boolean insertWFCLB(String SBBH,String FXBH,String HPHM,String HPZL,String JGSJ,String CLSD,String HPYS,
			String CSYS,String CLLX,String TZTP,String QMTP,String HPTP,String CDH,String XS){
		log.debug("VehPassDaoImpl.insertWFCLB() Start......");
		String sql = "insert into WFSJB_YSH(HPHM,WFSJ,SBBH,FXBH,HPZL,HPYS,SCZ,CLFL,CSYS,ZPSTR1,ZPSTR2,ZPSTR3,CJFS,BZZ,CDH) " +
				"values('"+HPHM+"',to_date('"+JGSJ+"','yyyy-mm-dd hh24:mi:ss'),'"+SBBH+
				"','"+FXBH+"','"+HPZL+"','"+HPYS+
				"','"+CLSD+"','"+CLLX+"','"+CSYS+"','"+TZTP+"','"+QMTP+
				"','"+HPTP+"','2','"+XS+"','"+CDH+"')";
		
		log.debug("insertWFCLB.sql:" + sql);
		
		int insertRow = this.update(sql);
		
		log.debug("VehPassDaoImpl.insertWFCLB() End......");
		return (insertRow == 1);
	}
	
		/**
	 * 写入违法车辆数据WFSJB_YSH_TEMP
	 * @param 
	 * @return
	 */
	public boolean insertWFCLBTemp(String SBBH,String FXBH,String HPHM,String HPZL,String JGSJ,String CLSD,String HPYS,
			String CSYS,String CLLX,String TZTP,String QMTP,String HPTP,String CDH,String XS){
		log.debug("VehPassDaoImpl.insertWFCLB() Start......");
		String sql = "insert into WFSJB_YSH_TEMP(HPHM,WFSJ,SBBH,FXBH,HPZL,HPYS,SCZ,CLFL,CSYS,ZPSTR1,ZPSTR2,ZPSTR3,CJFS,BZZ,CDH) " +
				"values('"+HPHM+"',to_date('"+JGSJ+"','yyyy-mm-dd hh24:mi:ss'),'"+SBBH+
				"','"+FXBH+"','"+HPZL+"','"+HPYS+
				"','"+CLSD+"','"+CLLX+"','"+CSYS+"','"+TZTP+"','"+QMTP+
				"','"+HPTP+"','2','"+XS+"','"+CDH+"')";
		
		log.debug("insertWFCLBTemp.sql:" + sql);
		
		int insertRow = this.update(sql);
		
		log.debug("VehPassDaoImpl.insertWFCLBTemp() End......");
		return (insertRow == 1);
	}
	
	/**
	 *@方法名称:insertHbcToWFCLB
	 *@输    入: 
	 *@输    出：
	 *@作    者: 张晓宇
	 *@创建日期:2013-8-8
	 *@方法描述:  黄标车写入违法数据表
	 */
	public boolean insertHbcToWFCLB(String SBBH,String FXBH,String HPHM,String HPZL,String JGSJ,String CLSD,String HPYS,
			String CSYS,String CLLX,String TZTP,String QMTP,String HPTP,String CDH,String XS){
		log.debug("VehPassDaoImpl.insertHbcToWFCLB() Start......");
		String sql = "insert into WFSJB_YSH(HPHM,WFSJ,SBBH,FXBH,HPZL,HPYS,SCZ,CLFL,CSYS,ZPSTR1,ZPSTR2,ZPSTR3,CJFS,BZZ,CDH) " +
				"values('"+HPHM+"',to_date('"+JGSJ+"','yyyy-mm-dd hh24:mi:ss'),'"+SBBH+
				"','"+FXBH+"','"+HPZL+"','"+HPYS+
				"','"+CLSD+"','"+CLLX+"','"+CSYS+"','"+TZTP+"','"+QMTP+
				"','"+HPTP+"','6','"+XS+"','"+CDH+"')";
		
		log.debug("insertWFCLB.sql:" + sql);
		
		int insertRow = this.update(sql);
		
		log.debug("VehPassDaoImpl.insertHbcToWFCLB() End......");
		return (insertRow == 1);
	}
	
	/**
	 * 违法数据(包括卡口、电警)写入接口WFSJB_YSH
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
	 * @param VideoFile
	 * @param VideoType
	 * @param CJFS
	 * @return
	 */
	public boolean WriteSurveilInfo(String SBBH,String FXBH,String HPHM,String HPZL,String JGSJ,String CLSD,String HPYS,
			String CSYS,String CLLX,String CDH,String XS,String Cltp1Path,String Cltp2Path,String Cltp3Path,String CltpType,String VideoFilePath,String VideoType,String CJFS){
		log.debug("VehPassDaoImpl.WriteSurveilInfo() Start......");
		/*String insertKKWfxx = "insert into KK_WFCLB(hphm,wfsj,wfdd,bjfx,hpzl,hpys,clsd,cllx,csys,tztp,qmtp,hptp,sjly,xcxs,cdh) " +
				"values('"+HPHM+"',to_date('"+JGSJ+"','yyyy-mm-dd hh24:mi:ss'),'"+SBBH+"','"+FXBH+"','"+HPZL+"','"+HPYS+"','"+CLSD+"','"+CLLX+"','"+CSYS+"','"+Cltp1+"','"+Cltp2+"','"+Cltp3+"','4','"+XS+"','"+CDH+"')";
		String insertDJWfxx = "insert into EPS_DATA(SBBH,FXBH,HPHM,HPZL,JGSJ,CLSD,XS,HPYS,CSYS,CLLX,CDH,TP1,TP2,TP3) " +
				"values('"+SBBH+"','"+FXBH+"','"+HPHM+"','"+HPZL+"',to_date('"+JGSJ+"','yyyy-mm-dd hh24:mi:ss'),'"+CLSD+"','"+XS+"','"+HPYS+"','"+CSYS+"','"+CLLX+"','"+CDH+"','"+Cltp1+"','"+Cltp2+"','"+Cltp3+"')";
		int insertRow = 0;
		if("F".equalsIgnoreCase(CJFS)){//卡口违法数据
			log.debug("WriteSurveilInfo.insertKKWfxx= :" + insertKKWfxx);
			insertRow = this.update(insertKKWfxx);
		}else if("H".equalsIgnoreCase(CJFS)){//电子警察
			log.debug("WriteSurveilInfo.insertDJWfxx:" + insertDJWfxx);
			insertRow = this.update(insertDJWfxx);
		}*/
		
		int insertRow = 0;
		String insertSql = "";
		if("linan".equals(AppInitConstants.DEPLOY_PLACE)){
			String selectSql = "select device_id,device_code,device_name,road_name,cross_id from device_eps_info where device_code = '"+SBBH+"'";
			String wfdd = "";
			String lddm = "";
			String wfdz = "";
			List<EpsInfoBean> deviceList = this.query(selectSql.toString(),new BaseRowMapper(EpsInfoBean.class));
			if(deviceList.size()>0){
				EpsInfoBean epsInfo = deviceList.get(0);
				wfdd = epsInfo.getRoad_name();
				lddm = epsInfo.getCross_id();
				wfdz = epsInfo.getDevice_name();
			}
			insertSql = "insert into WFSJB_YSH(SBBH,FXBH,HPHM,HPZL,WFSJ,SCZ,BZZ,HPYS,CSYS,CLLX,CDH,ZPSTR1,ZPSTR2,ZPSTR3,SPDZ,CJFS,FZJG,TZRQ,XZQH,WFDD,LDDM,WFDZ,WFXW,JYSJ,IS_CHECK) " +
			" values('"+SBBH+"','"+FXBH+"','"+HPHM+"','"+HPZL+"',to_date('"+JGSJ+"','yyyy-mm-dd hh24:mi:ss'),'"+CLSD+"','"+XS+"','"+HPYS+"','"+CSYS+"','"+CLLX+"','"+CDH+"','"+Cltp1Path+"','"+Cltp2Path+"','"+Cltp3Path+"','"+VideoFilePath+"','"+CJFS+"','fzjg',sysdate,'"+AppInitConstants.DEPLOY_PLACE_NO+"','"+wfdd+"','"+lddm+"','"+wfdz+"','1625',sysdate,'1')";
		}else{
			insertSql = "insert into WFSJB_YSH(SBBH,FXBH,HPHM,HPZL,WFSJ,SCZ,BZZ,HPYS,CSYS,CLLX,CDH,ZPSTR1,ZPSTR2,ZPSTR3,SPDZ,CJFS) " +
			" values('"+SBBH+"','"+FXBH+"','"+HPHM+"','"+HPZL+"',to_date('"+JGSJ+"','yyyy-mm-dd hh24:mi:ss'),'"+CLSD+"','"+XS+"','"+HPYS+"','"+CSYS+"','"+CLLX+"','"+CDH+"','"+Cltp1Path+"','"+Cltp2Path+"','"+Cltp3Path+"','"+VideoFilePath+"','"+CJFS+"')";
		}
		
		log.debug("WriteSurveilInfo.insertSql= :" + insertSql);
		insertRow = this.update(insertSql);
		
		log.debug("VehPassDaoImpl.WriteSurveilInfo() Start......");
		return (insertRow == 1);
	}
	
	
	public boolean WriteSurveilInfoExt(String SBBH,String FXBH,String HPHM,String HPZL,String JGSJ,String CLSD,String HPYS,
			String CSYS,String CLLX,String CDH,String XS,String Cltp1Path,String Cltp2Path,String Cltp3Path,String CltpType,String VideoFilePath,String VideoType,String CJFS,
			String RedLightTime,String RedLightDuration,String ViolationType,
			String wfdd,String wfdz,String IsCheck,String jyr,String jysj,String cjr,String cjjg){
		log.debug("VehPassDaoImpl.WriteSurveilInfoExt() Start......");
		
		int insertRow = 0;
		String insertSql = "";
		insertSql = "insert into WFSJB_YSH(SBBH,FXBH,HPHM,HPZL,WFSJ,SCZ,BZZ,HPYS,CSYS,CLLX,CDH,ZPSTR1,ZPSTR2,ZPSTR3,SPDZ,CJFS,WFXW,HDLQSJ,HDCXSJ,WFDD,WFDZ,IS_CHECK,JYR,JYSJ,CJR,CJJG) " +
		" values('"+SBBH+"','"+FXBH+"','"+HPHM+"','"+HPZL+"',to_date('"+JGSJ+"','yyyy-mm-dd hh24:mi:ss'),'"+CLSD+"','"+XS+"','"+HPYS+"','"+CSYS+"','"+CLLX+"','"+CDH+"','"+Cltp1Path+"','"+Cltp2Path+"','"+Cltp3Path+"','"+VideoFilePath+"','"+CJFS+"','"+ViolationType+"',to_date('"+RedLightTime+"','yyyy-mm-dd hh24:mi:ss'),'"+RedLightDuration+"','"
				+ ""+wfdd+"','"+wfdz+"','"+IsCheck+"','"+jyr+"',to_date('"+jysj+"','yyyy-mm-dd hh24:mi:ss'),'"+cjr+"','"+cjjg+"')";
		
		log.debug("WriteSurveilInfo.insertSql= :" + insertSql);
		insertRow = this.update(insertSql);
		
		log.debug("VehPassDaoImpl.WriteSurveilInfoExt() Start......");
		return (insertRow == 1);
	}
	
	
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
	public Map<String,String> checkAlarmInfo(String jgsj, String sbbh, String hphm, String csys, String hpzl, String cllx){
//		log.debug("VehPassDaoImpl.checkAlarmInfo() Start......");
		Map<String,String> alarmMap = new HashMap<String,String>();
		//String bkSql = "SELECT a.id bkid, " + 
		//增加布控报警类型，added by Tony Lin on 2012-5-7
		String bkSql = "SELECT a.id bkid,a.bk_type_no " + 
							//增加短信报警接收信息  Added by Tony Lin on 2012-10-23 for 安吉
							" ,a.SEND_TYPE, a.RECEIVER_CP_NO "+
							
							"  FROM kk_blacklist_d a, kk_bkkd b " + 
							"  WHERE a.id = b.bk_id " + 
							"   AND a.bkzt = '1' " + 
							"   AND rownum=1 " +
							"   AND to_date('"+jgsj+"','yyyy-mm-dd hh24:mi:ss') BETWEEN a.bkkssj AND a.bkjzsj " + 
							//过滤掉历史数据报警,1小时内
							//"   AND to_date('"+jgsj+"','yyyy-mm-dd hh24:mi:ss') > (sysdate-1/24) " +
							"   AND b.cross_no = '"+sbbh+"' " + 
							"   AND '"+hphm+"' LIKE a.hphm " + 
							"   AND (a.csys = '"+csys+"' OR a.csys IS NULL) " + 
							"   AND (a.hpzl = '"+hpzl+"' OR a.hpzl IS NULL) " + 
							"   AND (a.cllx = '"+cllx+"' OR a.cllx IS NULL)";
		
		String blackNameSql = "";
		if("haiyan".equals(AppInitConstants.DEPLOY_PLACE)){//海盐版本支持模糊车牌匹配
			//增加白名单过滤比对
			blackNameSql = "select count(kk.cph) from kk_black_name_list kk where kk.cph like '_"+hphm.substring(1)+"' and not exists (select cph from kk_white_name_list where cph = '"+hphm+"') ";
		}else{
			//增加白名单过滤比对
			blackNameSql = "select count(kk.cph) from kk_black_name_list kk where kk.cph = '"+hphm+"' and not exists (select cph from kk_white_name_list where cph = '"+hphm+"') ";
		}
		
//		log.debug("bkSql:" + bkSql);
//		log.debug("blackNameSql:" + blackNameSql);
//		
		List alarmList = this.queryForList(bkSql);
		 Iterator rowIterator = alarmList.iterator();
         while (rowIterator.hasNext()) {
             Map rowMap = (Map)rowIterator.next();
             alarmMap.put("bkid", rowMap.get("BKID").toString());
            //增加布控报警类型，added by Tony Lin on 2012-5-7
             alarmMap.put("bk_type_no", rowMap.get("BK_TYPE_NO").toString());
           //增加短信报警接收信息  Added by Tony Lin on 2012-10-23 for 安吉
             alarmMap.put("ifsend", ItsUtility.removeNull(rowMap.get("SEND_TYPE")).toString());
             alarmMap.put("phoneno",  ItsUtility.removeNull(rowMap.get("RECEIVER_CP_NO")).toString());
         }
         
		int bks = this.queryForInt(blackNameSql);
		//黑名单报警     Added by Tony Lin on 2012-08-30
		if(bks > 0){
			alarmMap.put("bk_type_no", "BKLB015");
		}
		if(!alarmMap.isEmpty() || bks > 0){
			alarmMap.put("alarm", "true");
		}else{
			alarmMap.put("alarm", "false");
		}
		
//		log.debug("VehPassDaoImpl.checkAlarmInfo() End......");
		return alarmMap;
	}
	
	/**
	 * 判断是否需要违法采集
	 * @param sbbh
	 * @return
	 */
	public boolean isNeedWfcj(String sbbh){
		String sql = "select count(*) from device_tgs_info t where t.sfcj = '1' and t.device_code='"+sbbh+"'";
		int selecrRow = this.queryForInt(sql);
		
		return (selecrRow == 1);
	}
	
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
		String JGSJ,String CLSD,String HPYS,String CSYS,String CLLX,String TZTP,String QMTP,String HPTP,String CDH,String XS,String BKID,String BK_TYPE_ID){
		String sql = "INSERT INTO KK_ALARM_VEHICLE (" +
																					"fxbh, " +
																					"hphm," +
																					"hpzl," +
																					" jgsj," +
																					"clsd," +
																					"hpys," +
																					"csys," +
																					"cllx," +
																					"tztp," +
																					"qmtp," +
																					"hptp," +
																					" jgdd," +
																					"cdh," +
																					"bk_id," +
																					"bjlx) " +
																	      " values('"+FXBH+"','"+HPHM+"','"+HPZL+"',to_date('"+JGSJ+"','yyyy-mm-dd hh24:mi:ss'),'"+
																					      CLSD+"','"+HPYS+"','"+CSYS+"','"+CLLX+"','"+TZTP+"','"+QMTP+"','"+HPTP+"','"+SBBH+"','"+CDH+"',"+BKID+",'"+BK_TYPE_ID+"')";
		log.debug("insertBkAlarmVeh.sql:" + sql);
		int insertRow = this.update(sql);
		
		return (1 == insertRow);
	}
	
	/**
	 *@方法名称:getHbcList
	 *@输    入: 
	 *@输    出：
	 *@作    者: 张晓宇
	 *@创建日期:2013-7-22
	 *@方法描述: 获取黄标车列表 
	 *
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashMap<String, List<HbcInfo>> getHbcList(){
		LinkedHashMap<String, List<HbcInfo>> map = new LinkedHashMap<String, List<HbcInfo>>();
		String sql = "select id,cph,cllx,clxz,czxm,cjh,ccrq,sccjmc,zdzzl,bzlx,rllx from kk_hbc_list";
		List<HbcInfo> list = (List<HbcInfo>)this.query(sql,new BaseRowMapper(HbcInfo.class));
		String rqStr = ItsUtility.DateToString(new Date(), "yyyy-MM-dd");
		map.put(rqStr, list);
		return map;
	}
	
	/**
	 *@方法名称:isInWfsj
	 *@输    入: 
	 *@输    出：
	 *@作    者: 张晓宇
	 *@创建日期:2013-7-22
	 *@方法描述: 判断24小时内违法数据是否包含 
	 */
	public boolean isInWfsj(String hphm){
		boolean isIn = false;
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, -1);
		String preTime = ItsUtility.DateToString(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
		String nowTime = ItsUtility.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
		String sql = "select count(0) from wfsjb_ysh where t.wfsj<= to_date('"+nowTime+"','yyyy-mm-dd hh24:mi:ss') and t.wfsj > to_date('"+preTime+"','yyyy-mm-dd hh24:mi:ss') and hphm = '"+hphm+"'";
		int count = this.queryForInt(sql);
		if(count>0){
			isIn = true;
		}
		return isIn;
	}
	
	/**
	 *@方法名称:isHbc
	 *@输    入: 
	 *@输    出：
	 *@作    者: 张晓宇
	 *@创建日期:2013-8-8
	 *@方法描述: 判断是否黄标车  
	 */
	public boolean isHbc(String cph){
		boolean isIn = false;
		String sql = "select count(0) from kk_hbc_list where cph='"+cph+"'";
		int count = this.queryForInt(sql);
		if(count>0){
			isIn=true;
		}
		return isIn;
	}
	
	/**
	 *@方法名称:getSysdate
	 *@输    入: 
	 *@输    出：
	 *@作    者: 张晓宇
	 *@创建日期:2013-7-26
	 *@方法描述:  获取系统时间
	 *
	 */
	public String getSysdate(){
		String dateStr = "";
		String sql = "select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') as xtsj from dual";
		List list = this.queryForList(sql);
        Iterator rowIterator = list.iterator();
        while (rowIterator.hasNext()) {
            Map rowMap = (Map)rowIterator.next();
            if(null!=rowMap.get("xtsj")){
            	dateStr = rowMap.get("xtsj").toString();
            }
        }
        return dateStr;
	}
	/**
	 *@方法名称:insertWbRecord
	 *@输    入: 
	 *@输    出：
	 *@作    者: 张晓宇
	 *@创建日期:2013-9-22
	 *@方法描述:  微波数据写入
	 */
	public boolean insertWbRecord(String SBBH,String FXBH,String HPHM,String HPZL,String JGSJ,String CLSD,String HPYS,
			String CSYS,String CLLX,String TZTP,String QMTP,String HPTP,String CDH,String XS){
		log.debug("VehPassDaoImpl.insertHbcToWFCLB() Start......");
		String sql = "insert into WFSJB_YSH(HPHM,WFSJ,SBBH,FXBH,HPZL,HPYS,SCZ,CLFL,CSYS,ZPSTR1,ZPSTR2,ZPSTR3,CJFS,BZZ,CDH) " +
				"values('"+HPHM+"',to_date('"+JGSJ+"','yyyy-mm-dd hh24:mi:ss'),'"+SBBH+
				"','"+FXBH+"','"+HPZL+"','"+HPYS+
				"','"+CLSD+"','"+CLLX+"','"+CSYS+"','"+TZTP+"','"+QMTP+
				"','"+HPTP+"','6','"+XS+"','"+CDH+"')";
		
		log.debug("insertWFCLB.sql:" + sql);
		
		int insertRow = this.update(sql);
		
		log.debug("VehPassDaoImpl.insertHbcToWFCLB() End......");
		return (insertRow == 1);
	}

	@Override
	public int insertWbRecord(WbRecord wbReco) {
		// TODO Auto-generated method stub
		log.debug("VehPassDaoImpl.insertWbRecord() Start......");
		String sql = "insert into SJCJ_WBJC_DATA(ID,WBJC_CODE,CDH,SPEED_AVER,SPEED_LON_VEH,SPEED_MID_VEH, SPEED_SHT_VEH,VOLUME_VEH,VOLUME_LON_VEH,VOLUME_MID_VEH,VOLUME_SHT_VEH,OCCUPY_VEH, OCCUPY_LON_VEH,OCCUPY_MID_VEH,OCCUPY_SHT_VEH ,HEADWAY_TIME,GATHER_TIME) " +
				"values(seq_wbjc_data_id.nextval,'"+wbReco.getWbjc_code()+"','"+wbReco.getCdh()+
				"','"+wbReco.getSpeed_aver()+"','"+wbReco.getSpeed_lon_veh()+"','"+wbReco.getSpeed_mid_veh()+
				"','"+wbReco.getSpeed_sht_veh()+"','"+wbReco.getVolume_veh()+"','"+wbReco.getVolume_lon_veh()+"','"+wbReco.getVolume_mid_veh()+"','"+wbReco.getVolume_sht_veh()+
				"','"+wbReco.getOccupy_veh()+"','"+wbReco.getOccupy_lon_veh()+"','"+wbReco.getOccupy_mid_veh()+"','"+wbReco.getOccupy_sht_veh()+"','"+wbReco.getHeadway_time()+"',to_date('"+wbReco.getGather_time()+"','yyyy-mm-dd hh24:mi:ss'))";
		
		log.debug("insertWFCLB.sql:" + sql);
		
		int insertRow = this.update(sql);
		
		log.debug("VehPassDaoImpl.insertWbRecord() End......");
		return insertRow ;
		
	}
	
	/**
	 *@方法名称:getEpsNameByCode
	 *@输    入: 
	 *@输    出：
	 *@作    者: 张晓宇
	 *@创建日期:2013-10-25
	 *@方法描述:  根据设备编号获取设备信息
	 */
	public String getDeviceNameByCode(String sbbh){
//		log.debug("getdevicenamebycode___start........................");
		String devicename = "未知设备";
		String sql = "select device_name from device_eps_info where device_code='"+sbbh+"'";
//		log.debug("getEpsNameByCode___sql"+sql);
		List list = this.queryForList(sql);
        Iterator rowIterator = list.iterator();
        while (rowIterator.hasNext()) {
            Map rowMap = (Map)rowIterator.next();
            if(null!=rowMap.get("device_name")){
            	devicename = rowMap.get("device_name").toString();
            }
        }
//        log.debug("getdevicenamebycode___end........................");
        return devicename;
	}
	
	public String getTgsNameByCode(String sbbh){
		String devicename = "未知设备";
		String sql = "select device_name from device_tgs_info where device_code='"+sbbh+"'";
		List list = this.queryForList(sql);
        Iterator rowIterator = list.iterator();
        while (rowIterator.hasNext()) {
            Map rowMap = (Map)rowIterator.next();
            if(null!=rowMap.get("device_name")){
            	devicename = rowMap.get("device_name").toString();
            }
        }
        return devicename;
	}

	@Override
	public boolean WriteVehicleInfoWithPhotoTemp(String SBBH, String FXBH,
			String HPHM, String HPZL, String JGSJ, String CLSD, String HPYS,
			String CSYS, String CLLX, String TZTPFtpPath, String QMTPFtpPath,
			String HPTPFtpPath, String CDH, String XS) {
		log.debug("VehPassDaoImpl.WriteVehicleInfoWithPhotoTemp() Start......");
		
		String sql = "insert into kk_tgs_pass_vehicle_temp(fxbh,hphm,hpzl,jgsj,clsd," +
				"hpys,csys,cllx,tztp,qmtp,hptp,jgdd,cdh,xs) " +
				"values('"+FXBH+"','"+HPHM+
				"','"+HPZL+"',to_date('"+JGSJ+"','yyyy-mm-dd hh24:mi:ss'),"+CLSD+",'"+HPYS+
				"','"+CSYS+"','"+CLLX+"','"+TZTPFtpPath+"','"+QMTPFtpPath+
				"','"+HPTPFtpPath+"','"+SBBH+"','"+CDH+"',"+XS+")";
		log.debug("WriteVehicleInfoWithPhotoTemp.sql:" + sql);
		
		int updateRow = this.update(sql);
		
		log.debug("VehPassDaoImpl.WriteVehicleInfoWithPhotoTemp() End......");
		return (updateRow == 1);
	}

	@Override
	public boolean WriteSurveilInfoTemp(String SBBH, String FXBH, String HPHM,
			String HPZL, String JGSJ, String CLSD, String HPYS, String CSYS,
			String CLLX, String CDH, String XS, String Cltp1Path, String Cltp2Path,
			String Cltp3Path, String CltpType, String VideoFilePath, String VideoType,
			String CJFS) {
		log.debug("VehPassDaoImpl.WriteSurveilInfoTemp() Start......");
		
		int insertRow = 0;
		String insertSql = "";
		insertSql = "insert into WFSJB_YSH_TEMP(SBBH,FXBH,HPHM,HPZL,WFSJ,SCZ,BZZ,HPYS,CSYS,CLLX,CDH,ZPSTR1,ZPSTR2,ZPSTR3,SPDZ,CJFS) " +
		" values('"+SBBH+"','"+FXBH+"','"+HPHM+"','"+HPZL+"',to_date('"+JGSJ+"','yyyy-mm-dd hh24:mi:ss'),'"+CLSD+"','"+XS+"','"+HPYS+"','"+CSYS+"','"+CLLX+"','"+CDH+"','"+Cltp1Path+"','"+Cltp2Path+"','"+Cltp3Path+"','"+VideoFilePath+"','"+CJFS+"')";
		
		log.debug("WriteSurveilInfoTemp.insertSql= :" + insertSql);
		insertRow = this.update(insertSql);
		
		log.debug("VehPassDaoImpl.WriteSurveilInfoTemp() End......");
		return (insertRow == 1);
	}
	
	public boolean WriteSurveilInfoExtTemp(String SBBH,String FXBH,String HPHM,String HPZL,String JGSJ,String CLSD,String HPYS,
			String CSYS,String CLLX,String CDH,String XS,String Cltp1Path,String Cltp2Path,String Cltp3Path,String CltpType,String VideoFilePath,String VideoType,String CJFS,
			String RedLightTime,String RedLightDuration,String ViolationType,
			String wfdd,String wfdz,String IsCheck,String jyr,String jysj,String cjr,String cjjg){
		log.debug("VehPassDaoImpl.WriteSurveilInfoExtTemp() Start......");
		
		int insertRow = 0;
		String insertSql = "";
		insertSql = "insert into WFSJB_YSH_TEMP(SBBH,FXBH,HPHM,HPZL,WFSJ,SCZ,BZZ,HPYS,CSYS,CLLX,CDH,ZPSTR1,ZPSTR2,ZPSTR3,SPDZ,CJFS,WFXW,HDLQSJ,HDCXSJ,WFDD,WFDZ,IS_CHECK,JYR,JYSJ,CJR,CJJG) " +
		" values('"+SBBH+"','"+FXBH+"','"+HPHM+"','"+HPZL+"',to_date('"+JGSJ+"','yyyy-mm-dd hh24:mi:ss'),'"+CLSD+"','"+XS+"','"+HPYS+"','"+CSYS+"','"+CLLX+"','"+CDH+"','"+Cltp1Path+"','"+Cltp2Path+"','"+Cltp3Path+"','"+VideoFilePath+"','"+CJFS+"','"+ViolationType+"',to_date('"+RedLightTime+"','yyyy-mm-dd hh24:mi:ss'),'"+RedLightDuration+"','"+wfdd+"','"+wfdz+"',"
				+ "'"+IsCheck+"','"+jyr+"',to_date('"+jysj+"','yyyy-mm-dd hh24:mi:ss'),'"+cjr+"','"+cjjg+"')";
		
		log.debug("WriteSurveilInfo.insertSql= :" + insertSql);
		insertRow = this.update(insertSql);
		
		log.debug("VehPassDaoImpl.WriteSurveilInfoExtTemp() End......");
		return (insertRow == 1);
	}

	@Override
	public boolean WriteBMDVehicleInfoWithPhoto(String SBBH, String FXBH,
			String HPHM, String HPZL, String JGSJ, String CLSD, String HPYS,
			String CSYS, String CLLX, String TZTPFtpPath, String QMTPFtpPath,
			String HPTPFtpPath, String CDH, String XS) {
		log.debug("VehPassDaoImpl.WriteVehicleInfoWithPhoto() Start......");
		
		String sql = "insert into kk_tgs_pass_vehicle_bmd(fxbh,hphm,hpzl,jgsj,clsd," +
				"hpys,csys,cllx,tztp,qmtp,hptp,jgdd,cdh,xs) " +
				"values('"+FXBH+"','"+HPHM+
				"','"+HPZL+"',to_date('"+JGSJ+"','yyyy-mm-dd hh24:mi:ss'),"+CLSD+",'"+HPYS+
				"','"+CSYS+"','"+CLLX+"','"+TZTPFtpPath+"','"+QMTPFtpPath+
				"','"+HPTPFtpPath+"','"+SBBH+"','"+CDH+"',"+XS+")";
		log.debug("WriteVehicleInfo.sql:" + sql);
		
		int updateRow = this.update(sql);
		
		log.debug("VehPassDaoImpl.WriteVehicleInfoWithPhoto() End......");
		return (updateRow == 1);
		
	}
	

}
