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
	 * ��������(��ͼƬ)д��ӿڷ���
	 * 
	 * @return String
	 */
	public String WriteVehicleInfoWithPhoto(String SBBH,String FXBH,String HPHM,String HPZL,String JGSJ,String CLSD,String HPYS,String CSYS,String CLLX,String TZTP,String QMTP,String HPTP,String TPTYPE,String CDH,String XS);
	

	/**
	 * ��ѯԶ�̷�����ʱ��
	 * @return
	 */
	public String QueryServerTime();
	
	
	
	/**
	 * ��Υ������(�������ڡ��羯)д��ӿڣ�����RedLightTime��RedLightDuration��ViolationType
	 */
	public String WriteSurveilInfoExt(String SBBH,String FXBH,String HPHM,String HPZL,String JGSJ,String CLSD,
			String HPYS,String CSYS,String CLLX,String CDH,String XS,String Cltp1,String Cltp2,String Cltp3,
			String CltpType,String VideoFile,String VideoType,String CJFS,String RedLightTime,
			String RedLightDuration,String ViolationType,String wfdd,String wfdz,
			String check,String jyr,String jysj,String cjr,String cjjg);
	
	}
