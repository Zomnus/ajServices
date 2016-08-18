package its.webservice.socket;

import java.io.File;
import java.io.FileOutputStream;

import its.webservice.common.AppInitConstants;
import its.webservice.entity.PicInfo;

import org.apache.log4j.Logger;
import org.codehaus.xfire.util.Base64;

public class SendReWritePicInfo implements Runnable{
	Logger log=Logger.getLogger(SendReWritePicInfo.class);
	
	@Override
	public void run() {
		while(true){
			PicInfo picInfo = null;
			FileOutputStream fos = null;
			File file = null;
			byte[] bytes = null;
			try {
				picInfo = AppInitConstants.picInfoQueue.take();
				if(picInfo!=null){
					file = new File(picInfo.getPicUrl());   
					//如果文件夹不存在，则先创建文件夹
					if(file.isDirectory()){
						log.debug("the directory is exists!");
					}else{
						file.getParentFile().mkdirs();
						log.debug("新建目录："+file+" 成功");
					}
	        		
	        		bytes = Base64.decode(picInfo.getPicBase64());
		        	fos = new FileOutputStream(file);   
		        	fos.write(bytes);   
		        	fos.flush();   
		        	fos.close();
		        	log.error("重写图片成功，物理路径："+picInfo.getPicUrl());
				}
				
			} catch (Exception e) {
				try {
					AppInitConstants.picInfoQueue.put(picInfo);
				} catch (Exception e1) {
					log.error("入队列异常",e);
				}
				log.error("重写图片失败，继续重写",e); 
			} finally {
				if (fos != null){
	                 try{
	                	 fos.flush(); 
	                     fos.close();
	                 }
	                 catch (Exception e){
	                	 log.error("",e);
	                 }
	             }
			}
		}
	}
	
}
