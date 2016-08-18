/**
 * Copyright(c) SUPCON 2008-2011. 浙江浙大中控信息技术有限公司
 */

package its.webservice.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.ws.WebServiceContext;

/**
 * 系统名称：智能交通集成平台(ITSWS) 所属模块：XXXXXXXXXXXXXX 功能描述：XXXXXXXXXXXXXX
 * 文件名：its.webservice.service.MTOMServer.java 版本信息：1.00
 * 
 * 开发部门：研发中心 创建者： lzk 创建时间：2011-10-14 下午1:07:39 修改者： lzk 修改时间：2011-10-14
 * 下午1:07:39
 */

@javax.xml.ws.soap.MTOM
//@WebService(name = "MTOMServerService", portName = "MTOMServer")
public class MTOMServer {
	private static final int BUFFER_SIZE = 1024 * 1024 * 20;
	@Resource
	private WebServiceContext serviceContext;

	/**
	 * 下载文件
	 * 
	 * @return 数据句柄
	 */
	@WebResult
	@XmlMimeType("*/*")
	public DataHandler download(@WebParam(name = "fileName") String fileName)
			throws FileNotFoundException {
		if (fileName == null || fileName.isEmpty())
			throw new FileNotFoundException("file name is empty");

		File dir = getFileDepository();
		File downloadFile = new File(dir.getAbsolutePath() + File.separatorChar
				+ fileName);
		if (!downloadFile.exists())
			throw new FileNotFoundException(fileName + " does not exist");

		return new DataHandler(new FileDataSource(downloadFile) {
			public String getContentType() {
				return "application/octet-stream";
			}
		});
	}

	@WebResult
	@XmlMimeType("*/*")
	public DataHandler[] downloadMulti() throws FileNotFoundException {
		final File[] files = getFileDepository().listFiles();
		DataHandler[] handlers = new DataHandler[files.length];

		for (int i = 0, j = files.length; i < j; i++) {
			final String fileName = files[i].getName();
			handlers[i] = new DataHandler(new FileDataSource(files[i])) {
				public String getName() {
					return fileName;
				}
			};
		}
		return handlers;
	}

	/**
	 * 上载
	 * 
	 * @param fileName
	 *            待上载的文件名
	 * @param dataHandler
	 *            数据句柄
	 * @throws IOException
	 *             IO异常
	 */
	public void upload(
			@WebParam(name = "fileName") String fileName,

			@XmlMimeType("*/*") @WebParam(name = "fileDataHandler") DataHandler dataHandler)
			throws IOException {

		File depository = getFileDepository();

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
	}

	/**
	 * 列表文件清单
	 * 
	 * @return 文件清单
	 */
	/*public java.util.List<FileDescription> listFiles() {
		File fileDepository = getFileDepository();
		java.util.List<FileDescription> L = new java.util.ArrayList<FileDescription>(
				0);

		for (File f : fileDepository.listFiles()) {

			FileDescription fds = new FileDescription();

			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(f.lastModified());

			fds.setModifiedDate(cal);
			fds.setFileName(f.getName());
			fds.setLength(f.length());
			L.add(fds);
		}

		return L;
	}*/

	/**
	 * 获取临时上载文件的路径
	 * 
	 * @return 临时文件路径
	 */
	private static File getFileDepository() {
		return new File(System.getProperty("java.io.tmpdir"));
	}
}
