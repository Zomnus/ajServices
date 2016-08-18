/**
 * Copyright(c) SUPCON 2008-2011. 浙江浙大中控信息技术有限公司
 */

package its.webservice.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataHandler;
import javax.jws.WebParam;
import javax.xml.bind.annotation.XmlMimeType;

/**
 * 系统名称：智能交通集成平台(ITSWS)
 * 所属模块：XXXXXXXXXXXXXX
 * 功能描述：XXXXXXXXXXXXXX
 * 文件名：its.webservice.service.MTOMServiceImpl.java
 * 版本信息：1.00
 * 
 * 开发部门：研发中心
 * 创建者： lzk
 * 创建时间：2011-10-14 下午1:35:57
 * 修改者： lzk
 * 修改时间：2011-10-14 下午1:35:57
 */

@javax.xml.ws.soap.MTOM
public class MTOMServiceImpl implements MTOMService {
	
	private static final int BUFFER_SIZE = 1024 * 1024 * 20;

	/**
	 * 上载
	 * 
	 * @param fileName
	 *            待上载的文件名
	 * @param dataHandler
	 *            数据句柄
	 */
	public void upload(
			@WebParam(name = "fileName") String fileName,

			@XmlMimeType("*/*") @WebParam(name = "fileDataHandler") DataHandler dataHandler) {
		try{

		//File depository = getFileDepository();
			File depository = new File("d:\temp\new_" + fileName);

		InputStream in = dataHandler.getInputStream();
		OutputStream out = new FileOutputStream(depository.getAbsolutePath()
				+ File.separatorChar + fileName);

		byte[] buf = new byte[BUFFER_SIZE];
		int read;
		while ((read = in.read(buf)) != -1) {
			out.write(buf, 0, read);
			out.flush();
		}
		in.close();
		out.close();
		}catch(Exception e){
			
		}
	}
	
	/**
	 * 获取临时上载文件的路径
	 * 
	 * @return 临时文件路径
	 */
	private static File getFileDepository() {
		return new File(System.getProperty("java.io.tmpdir"));
	}


}
