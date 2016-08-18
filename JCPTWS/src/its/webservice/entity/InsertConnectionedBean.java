/* SUPCON Confidential */
package its.webservice.entity;

import java.util.List;

// *****************************************************************************
//用户名      交警
//系统名      智能交通集成平台
//子系统名    系统管理
//描述       表B_CODE_TYPE
/**
* 
* 
* @author Xiangliang Kong Created on 2011-09-23
* 
*/


// *****************************************************************************

public class InsertConnectionedBean {

	    private String device_code;        //主机名
		private String connected;   //连接状态  0-正常 1-断开
	    private  String  deviceNameType;  //设备名称类型
	    private  String  cj_time;            //采集时间  lvhua2012-0-05
	    private  String  check_source;  // 检测来源
	    private  String  device_name;  // 设备名称 
	    public String getDevice_name() {
			return device_name;
		}

		public void setDevice_name(String device_name) {
			this.device_name = device_name;
		}

		public String getCheck_source() {
			return check_source;
		}

		public void setCheck_source(String check_source) {
			this.check_source = check_source;
		}

		public String getDevice_code() {
			return device_code;
		}

		public void setDevice_code(String device_code) {
			this.device_code = device_code;
		}

		public String getCj_time() {
			return cj_time;
		}

		public void setCj_time(String cj_time) {
			this.cj_time = cj_time;
		}

		public String getDeviceNameType() {
			return deviceNameType;
		}

		public void setDeviceNameType(String deviceNameType) {
			this.deviceNameType = deviceNameType;
		}


	   public String getConnected() {
				return connected;
			}

	  public void setConnected(String connected) {
				this.connected = connected;
	  }

	 
    
}