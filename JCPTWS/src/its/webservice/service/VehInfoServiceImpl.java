/**
 * Copyright(c) SUPCON 2008-2011. �㽭����п���Ϣ�������޹�˾
 */

package its.webservice.service;

import its.webservice.common.AppInitConstants;
import its.webservice.config.SystemConfig;
import its.webservice.dao.VehPassDao;
import its.webservice.entity.PicInfo;
import its.webservice.util.HttpPicSaver;
import its.webservice.util.ItsUtility;
import its.webservice.util.PassPostUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.xfire.util.Base64;


/**
 * ϵͳ���ƣ����ܽ�ͨWebService����(ITSWebService)
 * ����ģ�飺��������д��
 * ������������������д��ӿ�ʵ����
 * �ļ�����com.supconit.its.service.impl.WriteVehInfoServiceImpl.java
 * �汾��Ϣ��1.00
 * 
 * �������ţ��з�����
 * �����ߣ� lzk
 * ����ʱ�䣺Jan 25, 2011 4:16:13 PM
 * �޸��ߣ� lzk
 * �޸�ʱ�䣺Jan 25, 2011 4:16:13 PM
 */

public class VehInfoServiceImpl implements VehInfoService {
	
	/** ��ȡLog4jʵ�� */
    protected Logger log = Logger.getLogger(VehInfoServiceImpl.class.getName());
    
    private VehPassDao vehPassDao;
    
	public VehPassDao getVehPassDao() {
		return vehPassDao;
	}

	public void setVehPassDao(VehPassDao vehPassDao) {
		this.vehPassDao = vehPassDao;
	}
	
	/*
	 * ͼƬ�������洢ͼƬ·��ǰ׺����D:/picserver
	 * ��ֵ���������ļ�������
	 */
	private String imaPath = "";
	
	public String getImaPath() {
		return imaPath;
	}

	public void setImaPath(String imaPath) {
		this.imaPath = imaPath;
	}
	
	/**
	 * ͼƬ������FTPǰ׺������ͳһ�������ͼƬ����ftp://its:its@10.10.76.75:21
	 * ��ֵ���������ļ�������
	 */
	private String ftpPre = "";
	
	public String getFtpPre() {
		return ftpPre;
	}

	public void setFtpPre(String ftpPre) {
		this.ftpPre = ftpPre;
	}
	
	//FTP����·��
	String virtualRoute = "";
	
    //protected Socket socket;
    
	 //PrintWriter pw;
	/**
	 * ��ѯԶ�̷�����ʱ��
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
	 * ��ʼ����Ϣд��
	 * @param deviceCode
	 * @param deviceKey
	 * @return
	 */
	public String InitWriteVehInfo(String deviceCode,String deviceKey){
		log.debug("WriteVehInfoServiceImpl.InitWriteVehInfo()  Start......");
		String returnStr = "";
		
		/*if("".equals(deviceCode)){
			return "���ڱ�Ų���Ϊ�գ�����д�������";
		}
		if("".equals(deviceKey)){
			return "���ڱ�Ŷ�Ӧ��Կ����Ϊ�գ�����д�������";
		}
		//�жϿ���ƽ̨�Ƿ���Ӹÿ��ڱ��
		if(!vehPassDao.hasAddedCode(deviceCode)){
			return "���ڱ��("+deviceCode+")��δ�ڿ��ڹ���ƽ̨��������Ϣ��������ϵ����Ա��������ٽ�������д�룡";
		}
		try{
			//��֤������Կ�Ƿ�Ϸ�
			if(deviceKey.equals(ItsUtility.getMD5String(deviceCode+"supcon"))){
				//�ж��Ƿ��Ѿ�����ʼ����֤
				if(AppInitConstants.ALL_CODE_KEY_MAP.containsKey(deviceCode)){
					//return "���ڱ��("+deviceCode+")��Ӧ����Կ("+deviceKey+")�Ѿ�����ʼ��ע�ᣬ�����ٴγ�ʼ��ע�ᡣ";
					return "";
				}else{
					AppInitConstants.ALL_CODE_KEY_MAP.put(deviceCode, deviceKey);
				}
			}else{
				return "���ڱ��("+deviceCode+")��Ӧ����Կ("+deviceKey+")��������ϵ����Ա���»����Կ��";
			}
		
			boolean isInsert = vehPassDao.InitWriteVehInfo(deviceCode, deviceKey);
			if(isInsert){
				log.debug("���ڱ��:"+deviceCode+"��ʼ��ע��ɹ���");
			}else{
				returnStr = "���ڱ��"+deviceCode+"��ʼ��ע��ʧ�ܣ�����ϵ����Ա��";
			}
		}catch(Exception e){
			log.error("���ڱ��"+deviceCode+"��ʼ��ע��ʧ�ܡ�" + e.getMessage());
			returnStr = "���ڱ��"+deviceCode+"��ʼ��ע��ʧ�ܣ�����ϵ����Ա��" + e.getMessage();
		}*/
		
		log.debug("WriteVehInfoServiceImpl.InitWriteVehInfo()  End......");
		return returnStr;
	}
	
	/**
	 * ��������(��ͼƬ)д��ӿڷ���
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
		//�ж�һЩ�������Ƿ�д��
		if("".equals(HPHM)){
			return "���ƺ���(HPHM)����Ϊ�գ�����д�������";
		}
		if("".equals(JGSJ)){
			return "����ʱ��(JGSJ)����Ϊ�գ�����д�������";
		}
		Date jgsj = ItsUtility.StringToDate(JGSJ,"yyyy-MM-dd HH:mm:ss");
		if(jgsj == null){
			return "����ʱ��(JGSJ:"+JGSJ+")��ʽ��������д�������";
		}
		if("".equals(HPYS)){
			return "������ɫ(HPYS)����Ϊ�գ�����д�������";
		}
		if("".equals(SBBH)){
			return "�豸���(SBBH)����Ϊ�գ�����д�������";
		}
		if("".equals(CDH)){
			return "������(CDH)����Ϊ�գ�����д�������";
		}
		if("".equals(TPTYPE)){
			TPTYPE = "jpg";
		}
		
		if ("δʶ��".equals(HPHM)) {
			HPHM="�޷�ʶ��";
		}
		
		String tztpFtpPath = "";
		String qmtpFtpPath = "";
		String hptpFtpPath = "";
//		String fileNewPath = "";
		
		//���������ļ����ѡ��   Added by Tony Lin on 2013-06-14
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
	//	String gaTztpFtpPath = "";
	//	String gaQmtpFtpPath = "";
	//	String gaHptpFtpPath = "";
	//	String isWriteGA = SystemConfig.getConfigInfomation("isWriteGA");
		 //mowangzhong
		String tztpHttpPath="";
		String hptpHttpPath="";
		try{
			FileOutputStream fos = null;
		//	File file = null;
			byte[] bytes = null;
			String cltpName = "";
			HttpPicSaver httpPicSaver = new HttpPicSaver(AppInitConstants.HTTP_KK_PIC_IP_ZW, AppInitConstants.HTTP_PIC_PORT);
			String zwurl = "http://"+AppInitConstants.HTTP_KK_PIC_IP_ZW+":"+AppInitConstants.HTTP_PIC_PORT + "/images";
				try {
					//������ͼƬ����ڱ��ػ���FTP�ϣ���������д�����ݿⲢ��¼����ͼƬ��ַ
					//����ͼƬ����Ŀ¼·��Ϊ������(��)/�豸���/����/������/ͼƬ��
					if(null != TZTP &&  !"".equals(TZTP)){//�����һ���ļ�
			        	//**********************************************************************************************************************
						//����ͼƬ��ŵ�http ͼƬ������
						//�����һ��
						cltpName = SBBH + "_" +  ItsUtility.DateToString(jgsj, "yyyyMMddHHmmss") + "_" + FXBH + "_" + CDH + "_" + "tztp." + TPTYPE;
						//��ͼƬ������ת��
						bytes = Base64.decode(TZTP);
						//��ͼƬ���浽ͼƬ������ ������ͼƬ�����ַ
						String picKey =httpPicSaver.save(bytes, cltpName);
						tztpHttpPath = zwurl+picKey; //ר��http ͼƬ��ַ
						
					}
		        	if(null != QMTP && !"".equals(QMTP)){//����ڶ����ļ�
		        		System.out.println("hello supconit!...");
		        	}else{
		        		qmtpFtpPath = tztpFtpPath;
		        //		gaQmtpFtpPath = gaTztpFtpPath;
		        	}
		        	
		        	if(null != HPTP && !"".equals(HPTP)){//����������ļ�
			        	//����ͼƬ��ŵ�hptp ͼƬ������
						//���������
						cltpName = SBBH + "_" +  ItsUtility.DateToString(jgsj, "yyyyMMddHHmmss") + "_" + FXBH + "_" + CDH + "_" + "hptp." + TPTYPE;
						//��ͼƬ������ת��
						bytes = Base64.decode(HPTP);
						//��ͼƬ���浽ͼƬ������ ������ͼƬ�����ַ
						String picKey =httpPicSaver.save(bytes, cltpName);
						hptpHttpPath = zwurl+picKey; //ר��http ͼƬ��ַ        	
		        	}
		        } catch (Exception e) {
		        	log.error("д�����ͼƬʧ�ܣ�" + SBBH + "_" + HPHM + "_" + JGSJ + "; error:" + e.getMessage());
		        	//ȡ��ͼƬ�������ƣ��������ͼƬ������д�� Added by Tony Lin on 2012-4-28
		        	returnStr = "��������ͼƬд��ʧ�ܣ����ȷ��ͼƬ�����Ƿ���ȷ��" + e;
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
				//������
				vehPassDao.WriteBMDVehicleInfoWithPhoto(SBBH,FXBH,HPHM,HPZL,JGSJ,CLSD,HPYS,CSYS,CLLX,tztpFtpPath,qmtpFtpPath,hptpFtpPath,CDH,XS);
			}else{
				//���͸������ݣ�д��oracle start
				//д��ר�����ݿ�
				vehPassDao.WriteVehicleInfoWithPhoto(SBBH,FXBH,HPHM,HPZL,JGSJ,CLSD,HPYS,CSYS,CLLX,tztpHttpPath,hptpHttpPath,hptpHttpPath,CDH,XS);
				
				if ("�޷�ʶ��".equals(HPHM)) {
					HPHM="-";
				}		
				String result=PassPostUtil.postClientUploadTgs(AppInitConstants.uploadForBigDate.toString(), "999999", 
						SBBH, SBBH, HPHM, HPZL, HPYS,CLLX,
						JGSJ, FXBH, CDH, CLSD,"1", "1", "2","",
						CSYS, "000,000,000,000", "000,000,000,000", "000,000,000,000",
						"0",tztpHttpPath);
			    String code = result.substring(result.indexOf(":")+1,result.indexOf(","));
			    //���͵�������-end
				if("0".endsWith(code)){ 
					System.out.println("��������д������ݳɹ���+++++++++++++++++");
					if ("δʶ��".equals(HPHM)) {
						HPHM="����";
					}
					//ת��������ɫ�ṩ������
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
				    	 log.debug("+++++++++��ͼƬ�������ݷ���MQ���гɹ������г��ȣ�"+AppInitConstants.sendMQQueue.size()+"+++++++++++");
					}
				}else{
					returnStr = "��������д��ʧ�ܣ����������ֶ��Ƿ���ȷ��";
				}
				
				/*
				if("1".equals(isWriteGA)){ //д�뵽��ʱ��ͬ����������
					//ͨ��ftp ��ַ��ʽͬ��
					boolean isInsertGa = vehPassDao.WriteVehicleInfoWithPhotoTemp(SBBH,FXBH,HPHM,HPZL,JGSJ,CLSD,HPYS,CSYS,CLLX,tztpHttpPath,tztpHttpPath,hptpHttpPath,CDH,XS);
					if(isInsertGa){
						log.debug("��������д����ʱ��ɹ���");
					}else{
						returnStr = "��������д����ʱ��ʧ�ܣ����������ֶ��Ƿ���ȷ��";
					}
				}*/
			}
			
		}catch(Exception e){
			log.error("д���������ʧ�ܣ�" + SBBH + "_" + HPHM + "_" + JGSJ + "; error:" + e.getMessage());
			returnStr = "д���������ʧ�ܣ�����������������ʽ�Ƿ���ȷ��" + e.getMessage();
			return returnStr;
		}
		
		end = System.currentTimeMillis();
		total = end - start;
		long sec = total / 1000;		//����ʱ�侫ȷ����
		log.error("��������д���ܹ���ʱ�� " + sec / 60 + " �� " + sec % 60 + " ��"+total%1000+"���롣 ");
		
		log.debug("WriteVehInfoServiceImpl.WriteVehicleInfoWithPhoto()  End......");
		return returnStr;
	}
	
	
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
	 * @param check 
	 * @return
	 * String wfdd,String wfdz, String check,String jyr,String jysj,String cjr,String cjjg ���Υͣ�¼�
	 */
	public String WriteSurveilInfoExt(String SBBH,String FXBH,String HPHM,String HPZL,String JGSJ,String CLSD,String HPYS,String CSYS,
			String CLLX,String CDH,String XS,String Cltp1,String Cltp2,String Cltp3,String CltpType,String VideoFile,String VideoType,String CJFS,
			String RedLightTime,String RedLightDuration,String ViolationType,String wfdd,String wfdz, String check,String jyr,String jysj,String cjr,String cjjg){
		log.debug("WriteVehInfoServiceImpl.WriteSurveilInfoExt()  Start......");
	    long start = System.currentTimeMillis();																					
        long end;																																		
		long total = 0;	
		
		String returnStr = "";
		if("".equals(SBBH)){
			return "�豸���(SBBH)����Ϊ�գ�����д�������";
		}
		if("".equals(HPHM)){
			return "���ƺ���(HPHM)����Ϊ�գ�����д�������";
		}
		if("".equals(JGSJ)){
			return "����ʱ��(JGSJ)����Ϊ�գ�����д�������";
		}
		if("".equals(CDH)){
			return "�������(CDH)����Ϊ�գ�����д�������";
		}
		if("".equals(HPYS)){
			return "������ɫ(HPYS)����Ϊ�գ�����д�������";
		}
		if("".equals(Cltp1)){
			return "Υ��ͼƬ1(Cltp1)����Ϊ�գ�����д�������";
		}
		//����ͼƬĬ�Ϻ�׺��
		if("".equals(CltpType)){
			CltpType = "jpg";
		}
		//����¼��Ĭ�Ϻ�׺��
		if("".equals(VideoType)){
			VideoType = "mp4";
		}
		
		Date checkJgsj = ItsUtility.StringToDate(JGSJ,"yyyy-MM-dd HH:mm:ss");
		log.debug("jgsj:"+checkJgsj);
		if(checkJgsj == null){
			return "����ʱ��(JGSJ:"+JGSJ+")��ʽ��������д�������";
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
		
		if("".equals(imaPath)){
			imaPath = AppInitConstants.IMG_SERVER_ABS_FILE_PATH;
		}
		if("".equals(ftpPre)){
			ftpPre = AppInitConstants.IMG_SERVER_FTP_PRE;
		}
		//���������ļ����ѡ��   Added by Tony Lin on 2013-06-14
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
		
		try{
			try {
				//��Υ��ͼƬ����ڱ��ػ���FTP�ϣ�Υ������д�����ݿⲢ��¼Υ��ͼƬ��ַ
				//Υ��ͼƬ¼�񱣴�Ŀ¼·��Ϊ������(��)/�豸���/ͼƬ��¼����
				if(null != Cltp1  && !"".equals(Cltp1)){//�����һ���ļ�
					//cltpName = (CJFS + "_" + SBBH + "_" +  ItsUtility.DateToString(checkJgsj, "yyyyMMddHHmmss") + "_" + FXBH + "_" + CDH + "_" + "1." + CltpType);
					//fileNewPath = AppInitConstants.IMG_SERVER_ABS_FILE_PATH_WF  + "/"+  ItsUtility.DateToString(checkJgsj, "yyyyMMdd") + "/" + SBBH + "/";
					//fileNewPath = AppInitConstants.IMG_SERVER_ABS_FILE_PATH_WF + "/"+   ItsUtility.DateToString(checkJgsj, "yyyyMMdd") + "/" + SBBH + "/";
					fileNewPath = AppInitConstants.IMG_SERVER_ABS_FILE_PATH_WF  + "/"+(!AppInitConstants.DEPLOY_PLACE.equals("yuhuan")?(ItsUtility.DateToString(checkJgsj, "yyyyMMdd") + "/" + SBBH + "/"):(SBBH + "/"+ItsUtility.DateToString(checkJgsj, "yyyyMMddHH")+ "/"));
					cltpName = CJFS + "_" + SBBH + "_" +  ItsUtility.DateToString(checkJgsj, "yyyyMMddHHmmss") + "_" + FXBH + "_" + CDH + "_" + "1." + CltpType;
					try {
						file = new File(imaPath  + fileNewPath + cltpName);  
//						if(file.isFile()){
//							returnStr = "Υ������ͼƬ��"+imaPath  + fileNewPath + cltpName+"���Ѵ��ڣ��������ظ��ϴ����ݣ�";
//							return returnStr;
//						}
						//����ļ��в����ڣ����ȴ����ļ���
						if(file.isDirectory()){
							log.debug("the directory is exists!");
						}else{
							file.getParentFile().mkdirs();
							//filePath.createNewFile();
							log.debug("�½�Ŀ¼��"+file+" �ɹ�");
						}
						
						bytes = Base64.decode(Cltp1); 
						fos = new FileOutputStream(file); 
						fos.write(bytes);   
						fos.flush();   
						fos.close();
					} catch (Exception e) {
						PicInfo picInfo = new PicInfo(imaPath  + fileNewPath + cltpName,Cltp1);
						AppInitConstants.picInfoQueue.put(picInfo);
						log.error("д��Υ��ͼƬʧ�ܣ�" + e.getMessage());
					} 
		        	cltp1FtpPath = ftpPre + virtualRoute + fileNewPath +cltpName;
		        	gaCltp1FtpPath = gaFtpPre + virtualRoute + fileNewPath +cltpName;
				}
	        	if(null != Cltp2 &&   !"".equals(Cltp2)){//����ڶ����ļ�
	        		//cltpName = CJFS + "_" + SBBH + "_" +  ItsUtility.DateToString(checkJgsj, "yyyyMMddHHmmss") + "_" + FXBH + "_"  + CDH + "_" + "2." + CltpType;
	        		//fileNewPath = AppInitConstants.IMG_SERVER_ABS_FILE_PATH_WF + "/"+   ItsUtility.DateToString(checkJgsj, "yyyyMMdd") + "/" + SBBH + "/";
					fileNewPath = AppInitConstants.IMG_SERVER_ABS_FILE_PATH_WF  + "/"+(!AppInitConstants.DEPLOY_PLACE.equals("yuhuan")?(ItsUtility.DateToString(checkJgsj, "yyyyMMdd") + "/" + SBBH + "/"):(SBBH + "/"+ItsUtility.DateToString(checkJgsj, "yyyyMMddHH")+ "/"));
					cltpName = CJFS + "_" + SBBH + "_" +  ItsUtility.DateToString(checkJgsj, "yyyyMMddHHmmss") + "_" + FXBH + "_" + CDH + "_" + "2." + CltpType;
					try {
						file = new File(imaPath  + fileNewPath + cltpName);   
//						if(file.isFile()){
//							returnStr = "Υ������ͼƬ��"+imaPath  + fileNewPath + cltpName+"���Ѵ��ڣ��������ظ��ϴ����ݣ�";
//							return returnStr;
//						}
						//����ļ��в����ڣ����ȴ����ļ���
						if(file.isDirectory()){
							log.debug("the directory is exists!");
						}else{
							file.getParentFile().mkdirs();
							//filePath.createNewFile();
							log.debug("�½�Ŀ¼��"+file+" �ɹ�");
						}
						
						bytes = Base64.decode(Cltp2);   
						fos = new FileOutputStream(file);   
						fos.write(bytes);   
						fos.flush();   
						fos.close();
					} catch (Exception e) {
						PicInfo picInfo = new PicInfo(imaPath  + fileNewPath + cltpName,Cltp2);
						AppInitConstants.picInfoQueue.put(picInfo);
						log.error("д��Υ��ͼƬʧ�ܣ�" + e.getMessage());
					} 
		        	cltp2FtpPath = ftpPre + virtualRoute  + fileNewPath +cltpName;
		        	gaCltp2FtpPath = gaFtpPre + virtualRoute + fileNewPath +cltpName;
	        	}
	        	if(null != Cltp3 && !"".equals(Cltp3)){//����������ļ�
	        		//cltpName = CJFS + "_" + SBBH + "_" +  ItsUtility.DateToString(checkJgsj, "yyyyMMddHHmmss") + "_" + FXBH + "_"  + CDH + "_" + "3." + CltpType;
	        		//fileNewPath = AppInitConstants.IMG_SERVER_ABS_FILE_PATH_WF + "/"+   ItsUtility.DateToString(checkJgsj, "yyyyMMdd") + "/" + SBBH + "/";
					fileNewPath = AppInitConstants.IMG_SERVER_ABS_FILE_PATH_WF  + "/"+(!AppInitConstants.DEPLOY_PLACE.equals("yuhuan")?(ItsUtility.DateToString(checkJgsj, "yyyyMMdd") + "/" + SBBH + "/"):(SBBH + "/"+ItsUtility.DateToString(checkJgsj, "yyyyMMddHH")+ "/"));
					cltpName = CJFS + "_" + SBBH + "_" +  ItsUtility.DateToString(checkJgsj, "yyyyMMddHHmmss") + "_" + FXBH + "_" + CDH + "_" + "3." + CltpType;
					try {
						file = new File(imaPath  + fileNewPath + cltpName);   
//						if(file.isFile()){
//							returnStr = "Υ������ͼƬ��"+imaPath  + fileNewPath + cltpName+"���Ѵ��ڣ��������ظ��ϴ����ݣ�";
//							return returnStr;
//						}
						//����ļ��в����ڣ����ȴ����ļ���
						if(file.isDirectory()){
							log.debug("the directory is exists!");
						}else{
							file.getParentFile().mkdirs();
							//filePath.createNewFile();
							log.debug("�½�Ŀ¼��"+file+" �ɹ�");
						}
						
						bytes = Base64.decode(Cltp3);
						fos = new FileOutputStream(file);   
						fos.write(bytes);   
						fos.flush();   
						fos.close();
					} catch (Exception e) {
						PicInfo picInfo = new PicInfo(imaPath  + fileNewPath + cltpName,Cltp3);
						AppInitConstants.picInfoQueue.put(picInfo);
						log.error("д��Υ��ͼƬʧ�ܣ�" + e.getMessage());
					} 
		        	cltp3FtpPath = ftpPre + virtualRoute  + fileNewPath +cltpName;
		        	gaCltp3FtpPath = gaFtpPre + virtualRoute + fileNewPath +cltpName;
	        	}
	        	if(null != VideoFile &&  !"".equals(VideoFile)){//����¼���ļ�
	        		//cltpName = CJFS + "_" + SBBH + "_" +  ItsUtility.DateToString(checkJgsj, "yyyyMMddHHmmss") + "_" + FXBH + "_" + CDH + "." + VideoType;
					//fileNewPath = AppInitConstants.IMG_SERVER_ABS_FILE_PATH_WF + "/"+   ItsUtility.DateToString(checkJgsj, "yyyyMMdd") + "/" + SBBH + "/";
					 // ��������� Ŀ¼�ṻ  ����ʱ��\·��\�ļ�����  ��֮�෴ by lvhua 2013-05-20
					fileNewPath = AppInitConstants.IMG_SERVER_ABS_FILE_PATH_WF  + "/"+(!AppInitConstants.DEPLOY_PLACE.equals("yuhuan")?(ItsUtility.DateToString(checkJgsj, "yyyyMMdd") + "/" + SBBH + "/"):(SBBH + "/"+ItsUtility.DateToString(checkJgsj, "yyyyMMddHH")+ "/"));
					cltpName = CJFS + "_" + SBBH + "_" +  ItsUtility.DateToString(checkJgsj, "yyyyMMddHHmmss") + "_" + FXBH + "_" + CDH + "_" + "." + VideoType;
					try {
						file = new File(imaPath  + fileNewPath + cltpName);   
//						if(file.isFile()){
//							returnStr = "Υ������¼��"+imaPath  + fileNewPath + cltpName+"���Ѵ��ڣ��������ظ��ϴ����ݣ�";
//							return returnStr;
//						}
						//����ļ��в����ڣ����ȴ����ļ���
						if(file.isDirectory()){
							log.debug("the directory is exists!");
						}else{
							file.getParentFile().mkdirs();
							//filePath.createNewFile();
							log.debug("�½�Ŀ¼��"+file+" �ɹ�");
						}
						
						bytes = Base64.decode(VideoFile);
						fos = new FileOutputStream(file);   
						fos.write(bytes);   
						fos.flush();   
						fos.close();
						
					} catch (Exception e) {
						PicInfo picInfo = new PicInfo(imaPath  + fileNewPath + cltpName,VideoFile);
						AppInitConstants.picInfoQueue.put(picInfo);
						log.error("д��Υ��ͼƬʧ�ܣ�" + e.getMessage());
					} 
		        	videoFtpPath = ftpPre + virtualRoute  + fileNewPath +cltpName;
		        	gaVideoFtpPath = gaFtpPre + virtualRoute + fileNewPath +cltpName;
	        	}
	        	
	        	
	        } catch (Exception e) {
	        	log.error("д��Υ��ͼƬʧ�ܣ�" + e.getMessage());
	        	returnStr = "Υ������ͼƬд��ʧ�ܣ����ȷ��ͼƬ�����Ƿ���ȷ��" + e;
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
	        //������д�����ݿ�(WFSJB_YSH) 
			//2017-2-23 add wfdd mowangzhong
			//2017-5-3 add is_check mowangzhong 
			//2017-5-5 add jyr jyss��cjr,cjjg mowanghzong
			boolean isInsert = vehPassDao.WriteSurveilInfoExt(SBBH,FXBH,HPHM,HPZL,JGSJ,CLSD,HPYS,CSYS,CLLX,CDH,XS,cltp1FtpPath,cltp2FtpPath,cltp3FtpPath,CltpType,videoFtpPath,VideoType,CJFS,RedLightTime,RedLightDuration,ViolationType,wfdd,wfdz,check,jyr,jysj,cjr,cjjg);
			if(isInsert){
				log.debug("Υ������д��ɹ���");
			}else{
				returnStr = "Υ������д��ʧ�ܣ����������ֶ��Ƿ���ȷ��";
				return returnStr;
			}
			if("1".equals(isWriteGA)){
				boolean isInsertTemp = vehPassDao.WriteSurveilInfoExtTemp(SBBH,FXBH,HPHM,HPZL,JGSJ,CLSD,HPYS,CSYS,CLLX,CDH,XS,gaCltp1FtpPath,gaCltp2FtpPath,gaCltp3FtpPath,CltpType,gaVideoFtpPath,VideoType,CJFS,RedLightTime,RedLightDuration,ViolationType,wfdd,wfdz,check,jyr,jysj,cjr,cjjg);
				if(isInsertTemp){
					log.debug("Υ������д����ʱ��ɹ���");
				}else{
					returnStr = "Υ������д����ʱ��ʧ�ܣ����������ֶ��Ƿ���ȷ��";
					return returnStr;
				}
			}
			
		}catch(Exception e){
			log.error("д��Υ������ʧ�ܣ�" + e.getMessage());
			returnStr = "д��Υ������ʧ�ܣ�����������������ʽ�Ƿ���ȷ��" + e.getMessage();
			return returnStr;
		}
		
		log.debug("WriteVehInfoServiceImpl.WriteSurveilInfoExt()  End......");
		
		end = System.currentTimeMillis();
		total = end - start;
		long sec = total / 1000;		//����ʱ�侫ȷ����
		log.info("��������д���ܹ���ʱ�� " + sec / 60 + " �� " + sec % 60 + " ��"+total%1000+"���롣 ");
		return returnStr;
	}
	
}
