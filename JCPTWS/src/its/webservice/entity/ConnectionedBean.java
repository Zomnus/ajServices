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

public class ConnectionedBean {

	    private String    id;          //主键
	    private String    name;        //主机名
		private String    ip;          //ip
	    private String    connected;   //连接状态  0-正常 1-断开
	    private  String   tablename;  //表名
	    private  String   port ;       //表名
	    private  String   ids;        //设备code
	    private  String   deviceNameType;  //设备名称类型
	    private  String   cj_time;      //采集时间  lvhua2012-05-05
	    private  String   check_source;   //检测来源  lvhua2012-06-01
	    private  String   creator_id;    //记录创建者ID Xiangliang Kong 2012-06-018
	    private  String   direction;     // 方向 lvhua  2012-08-01
		public String getDirection() {
			return direction;
		}

		public void setDirection(String direction) {
			this.direction = direction;
		}

		public String getCheck_source() {
			return check_source;
		}

		public void setCheck_source(String check_source) {
			this.check_source = check_source;
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

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
		

	   public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getTablename() {
			return tablename;
		}

		public void setTablename(String tablename) {
			this.tablename = tablename;
		}

		public String getPort() {
			return port;
		}

		public void setPort(String port) {
			this.port = port;
		}

		public String getIds() {
			return ids;
		}

		public void setIds(String ids) {
			this.ids = ids;
		}

	   public String getIp() {
				return ip;
			}

	   public void setIp(String ip) {
				this.ip = ip;
			}

	   public String getConnected() {
				return connected;
			}

	  public void setConnected(String connected) {
				this.connected = connected;
	  }

	public String getCreator_id() {
		return creator_id;
	}

	public void setCreator_id(String creator_id) {
		this.creator_id = creator_id;
	}

	 
    
}