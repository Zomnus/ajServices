/**
 * Copyright(c) SUPCON 2008-2011. �㽭����п���Ϣ�������޹�˾
 */

package its.webservice.service;


/**
 * ϵͳ���ƣ����ܽ�ͨWebService����(ITSWebService)
 * ����ģ�飺��������д��
 * ������������������д��ӿ�
 * �ļ�����com.supconit.its.service.WriteVehInfoService.java
 * �汾��Ϣ��1.00
 * 
 * �������ţ��з�����
 * �����ߣ� lzk
 * ����ʱ�䣺Jan 25, 2011 4:05:06 PM
 * �޸��ߣ� lzk
 * �޸�ʱ�䣺Jan 25, 2011 4:05:06 PM
 */

public interface VehInfoService {
	
	/**
	 * ��ʼ����Ϣд��
	 * @param deviceCode
	 * @param deviceKey
	 * @return
	 */
	public String InitWriteVehInfo(String deviceCode,String deviceKey);
	
	/**
	 * ��������д��ӿڷ���
	 * @param 
	 * @return String
	 */
	public String WriteVehicleInfo(String SBBH,String FXBH,String HPHM,String HPZL,String JGSJ,String CLSD,String HPYS,String CSYS,String CLLX,String TZTP,String QMTP,String HPTP,String CDH,String XS);
	
	/**
	 * ��������(��ͼƬ)д��ӿڷ���
	 * 
	 * @return String
	 */
	public String WriteVehicleInfoWithPhoto(String SBBH,String FXBH,String HPHM,String HPZL,String JGSJ,String CLSD,String HPYS,String CSYS,String CLLX,String TZTP,String QMTP,String HPTP,String TPTYPE,String CDH,String XS);
	
	/**
	 * ���ر�������д��ӿڷ���
	 * @param vehInfo
	 * @return String
	 */
	//public String WriteAlarmVehInfo(VehicleInfo vehInfo);
	
	/**
	 * ����������д��ӿڷ���
	 * @param blackNameInfo
	 * @return
	 */
	//public String WriteBlackName(BlackNameInfo blackNameInfo);

	/**
	 * ��ѯԶ�̷�����ʱ��
	 * @return
	 */
	public String QueryServerTime();
	
	/**
	 * �豸״̬д��ӿڷ���
	 * @return
	 */
	//public String WriteDeviceStat(DeviceStatInfo deviceStatInfo);
	
	/**
	 * test for �ļ��ϴ�
	 */
	public String WriteFileTest(String fileName,String strFileBase64);
	
	/**
	 * Υ������(�������ڡ��羯)д��ӿ�
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
	public String WriteSurveilInfo(String SBBH,String FXBH,String HPHM,String HPZL,String JGSJ,String CLSD,String HPYS,String CSYS,String CLLX,String CDH,String XS,String Cltp1,String Cltp2,String Cltp3,String CltpType,String VideoFile,String VideoType,String CJFS);
	
	
	/**
	 * ��Υ������(�������ڡ��羯)д��ӿڣ�����RedLightTime��RedLightDuration��ViolationType
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
	 * @return
	 */
	public String WriteSurveilInfoExt(String SBBH,String FXBH,String HPHM,String HPZL,String JGSJ,String CLSD,String HPYS,String CSYS,String CLLX,String CDH,String XS,String Cltp1,String Cltp2,String Cltp3,String CltpType,String VideoFile,String VideoType,String CJFS,String RedLightTime,String RedLightDuration,String ViolationType);
	
	public String writeWbRecord(String wbjc_code, String cdh, int speed_aver, int speed_lon_veh,int speed_mid_veh,int speed_sht_veh,int volume_veh,int volume_lon_veh,int volume_mid_veh,int volume_sht_veh,int occupy_veh,int occupy_lon_veh,int occupy_mid_veh,int occupy_sht_veh,int headway_time,String gather_time);
}
