/**
 * Copyright(c) SUPCON 2008-2011. 浙江浙大中控信息技术有限公司
 */

package its.webservice.service;

/**
 * 系统名称：智能交通WebService服务(ITSWebService)
 * 所属模块：过车数据写入
 * 功能描述：设备状态信息bean
 * 文件名：its.webservice.service.DeviceStatInfo.java
 * 版本信息：1.00
 * 
 * 开发部门：研发中心
 * 创建者： lzk
 * 创建时间：Feb 24, 2011 2:31:25 PM
 * 修改者： lzk
 * 修改时间：Feb 24, 2011 2:31:25 PM
 */

public class DeviceStatInfo {
	
	private String change_time = "";
	
	private String cross_no = "";
	
	private String device_type = "";
	
	private String device_code = "";
	
	private String device_status = "";
	
	private String description = "";

	public String getChange_time() {
		return change_time;
	}

	public void setChange_time(String change_time) {
		this.change_time = change_time;
	}

	public String getCross_no() {
		return cross_no;
	}

	public void setCross_no(String cross_no) {
		this.cross_no = cross_no;
	}

	public String getDevice_type() {
		return device_type;
	}

	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}

	public String getDevice_code() {
		return device_code;
	}

	public void setDevice_code(String device_code) {
		this.device_code = device_code;
	}

	public String getDevice_status() {
		return device_status;
	}

	public void setDevice_status(String device_status) {
		this.device_status = device_status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
