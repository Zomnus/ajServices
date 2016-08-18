package its.webservice.entity;


/**
 * 系统名称：集成平台(JCPT)
 * 所属模块：非现场违法数据处理
 * 功能描述：CCTV设备bean
 * 文件名：com.supcon.supconit.its.business.jcj.entity.CctvInfoBean.java
 * 版本信息：1.00
 * 
 * 开发部门：研发中心
 * 创建者： 张晓宇
 * 创建时间：2011-08-15
 */
public class EpsInfoBean{
	
	//设备ID
	private String device_id;
	//设备Code
	private String device_code;
	//设备名称
	private String device_name;
	//违法道路编号
	private String road_name;
	//违法路段编号
	private String cross_id;
	
	

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String deviceId) {
		device_id = deviceId;
	}

	public String getDevice_code() {
		return device_code;
	}

	public void setDevice_code(String deviceCode) {
		device_code = deviceCode;
	}

	public String getDevice_name() {
		return device_name;
	}

	public void setDevice_name(String deviceName) {
		device_name = deviceName;
	}


	public String getRoad_name() {
		return road_name;
	}

	public void setRoad_name(String roadName) {
		road_name = roadName;
	}

	public String getCross_id() {
		return cross_id;
	}

	public void setCross_id(String crossId) {
		cross_id = crossId;
	}


}
