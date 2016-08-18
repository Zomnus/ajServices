package its.webservice.util;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.RandomStringUtils;

//import com.supconit.jcpt.PicSaver;
//import com.supconit.jcpt.access.util.DateUtil;
//import com.supconit.jcpt.access.util.FileUtils;
//import com.supconit.jcpt.exceptions.Exception;

public class HttpPicSaver{

	private String uploadIP;
	private String uploadPort;
	private String url ="/images";
	private String urlStr;

	public HttpPicSaver(String uploadIP, String uploadPort) {
		this.uploadIP = uploadIP;
		this.uploadPort = uploadPort;
	}
	
	public String getServerUrl() {
		return url;
	}

	private String getUploadUrlStr() {
		if (urlStr == null) {
			urlStr = "http://" + uploadIP + ":" + uploadPort + "/images/upload";
		}
		return urlStr;
	}

	private final String BOUNDARY = "----WebKitFormBoundary" + RandomStringUtils.randomAlphanumeric(16);

	public String save(InputStream in, String fileName) throws Exception {
		HttpURLConnection urlConnection = null;
		ByteArrayOutputStream bos = null;
		InputStream is = null;
		byte[] result = null;
		try {
			String upURL = this.getUploadUrlStr();
			upURL += "?datetime=" + DateUtil.format(new Date(), DateUtil.LONGDATE);
			upURL += "&suffix=" + fileName.substring(fileName.lastIndexOf(".") + 1);

			URL url = new URL(upURL);
			urlConnection = (HttpURLConnection) url.openConnection();

			urlConnection.setRequestProperty("Accept",
					"text/html, application/xhtml+xml, application/xml; q=0.9, image/webp,*/*; q=0.8");
			urlConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
			urlConnection.setRequestProperty("charset", "GBK");
			urlConnection.setDoOutput(true);
			urlConnection.setDoInput(true);

			OutputStream doOut = urlConnection.getOutputStream();

			// 1 鏂囧瓧褰㈠紡杈撳嚭娴�

			String boundary = BOUNDARY;
			StringBuffer resultSB = new StringBuffer("\r\n");
			String endBoundary = "\r\n--" + boundary + "--\r\n";
			List<String[]> strParams = new ArrayList<String[]>();
			strParams.add(new String[] { "datetime", DateUtil.format(new Date(), DateUtil.LONGDATE) });
			strParams.add(new String[] { "suffix", fileName.substring(fileName.lastIndexOf(".") + 1) });
			if (strParams != null) {
				for (String[] param : strParams) {
					String key = param[0];
					String value = param[1];
					resultSB.append(key).append(":").append(value).append("\r\n");
				}
			}
			String boundaryMessage = resultSB.toString();

			doOut.write(("--" + boundaryMessage).getBytes("GBK"));

			// 2 鏂囦欢褰㈠紡杈撳嚭娴�
			resultSB = new StringBuffer();
			resultSB.append("Content-Disposition: form-data; name=\"").append("uploadFile").append("\"; filename=\"")
					.append(fileName).append("\"\r\n").append("Content-Type :").append("image/jpeg").append("\r\n\r\n");
			doOut.write(resultSB.toString().getBytes("GBK"));

			// 鍐欐枃浠�
			DataInputStream ins = new DataInputStream(in);
			int bytes = 0;
			byte[] bufferOut = new byte[1024 * 5];
			while ((bytes = ins.read(bufferOut)) != -1) {
				doOut.write(bufferOut, 0, bytes);
			}

			doOut.write(endBoundary.getBytes("GBK"));

			ins.close();

			// 3 缁撳熬
			doOut.write(endBoundary.getBytes("GBK"));

			doOut.close();

			int chars;
			is = urlConnection.getInputStream();
			bos = new ByteArrayOutputStream();
			while ((chars = is.read()) != -1) {
				bos.write(chars);
			}
			result = bos.toByteArray();
		} catch (Exception e) {
			throw new Exception("", e);
		} finally {
			try {
				if (null != bos) {
					bos.close();
				}
				if (null != is) {
					is.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return new String(result);
	}

	public String save(byte[] bytes, String fileName) throws Exception {
		HttpURLConnection urlConnection = null;
		ByteArrayOutputStream bos = null;
		InputStream is = null;
		byte[] result = null;
		try {
			String upURL = this.getUploadUrlStr();
			upURL += "?datetime=" + DateUtil.format(new Date(), DateUtil.LONGDATE);
			upURL += "&suffix=" + fileName.substring(fileName.lastIndexOf(".") + 1);

			// 璁＄畻闀垮害
			StringBuffer resultb = new StringBuffer();
			resultb.append("\r\n--" + BOUNDARY + "\r\n");
			resultb.append("Content-Disposition: form-data; name=\"").append("img1").append("\"; filename=\"")
					.append(fileName).append("\"\r\n").append("Content-Type :").append("image/jpeg").append("\r\n\r\n");
			resultb.append("\r\n--" + BOUNDARY + "--\r\n");
			int bStr = resultb.toString().length();

			URL url = new URL(upURL);
			urlConnection = (HttpURLConnection) url.openConnection();

			urlConnection.setRequestProperty("Accept",
					"text/html, application/xhtml+xml, application/xml; q=0.9, image/webp,*/*; q=0.8");
			urlConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
			urlConnection.setRequestProperty("Content-Length", (bStr + bytes.length) + "");
			urlConnection.setRequestProperty("charset", "GBK");
			urlConnection.setDoOutput(true);
			urlConnection.setDoInput(true);

			OutputStream doOut = urlConnection.getOutputStream();

			// 1 鏂囧瓧褰㈠紡杈撳嚭娴�

			String boundary = BOUNDARY;
			StringBuffer resultSB = new StringBuffer("\r\n");
			String endBoundary = "\r\n--" + boundary + "--\r\n";
			System.out.println(BOUNDARY);
			List<String[]> strParams = new ArrayList<String[]>();
			strParams.add(new String[] { "datetime", DateUtil.format(new Date(), DateUtil.LONGDATE) });
			strParams.add(new String[] { "suffix", fileName.substring(fileName.lastIndexOf(".") + 1) });
			if (strParams != null) {
				for (String[] param : strParams) {
					String key = param[0];
					String value = param[1];
					resultSB.append(key).append(":").append(value).append("\r\n");
				}
			}
			String boundaryMessage = resultSB.toString();

			doOut.write(("--" + boundaryMessage).getBytes("GBK"));

			// 2 鏂囦欢褰㈠紡杈撳嚭娴�
			doOut.write(("\r\n--" + boundary + "\r\n").getBytes("GBK"));
			resultSB = new StringBuffer();
			resultSB.append("Content-Disposition: form-data; name=\"").append("img1").append("\"; filename=\"")
					.append(fileName).append("\"\r\n").append("Content-Type :").append("image/jpeg").append("\r\n\r\n");
			doOut.write(resultSB.toString().getBytes("GBK"));

			// 鍐欐枃浠�
			doOut.write(bytes, 0, bytes.length);

			// 3 缁撳熬
			doOut.write(endBoundary.getBytes("GBK"));

			doOut.close();

			int chars;
			is = urlConnection.getInputStream();
			bos = new ByteArrayOutputStream();
			while ((chars = is.read()) != -1) {
				bos.write(chars);
			}
			result = bos.toByteArray();
		} catch (Exception e) {
			throw new Exception("", e);
		} finally {
			try {
				if (null != bos) {
					bos.close();
				}
				if (null != is) {
					is.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return new String(result);
	}

	public String save(String filePath) throws Exception {
		try {
			File picFile = new File(filePath);
			String picName = picFile.getName();
			InputStream in = new FileInputStream(filePath);
			byte[] pixB = FileUtils.copyToByteArray(in);
			return save(pixB, picName);
		} catch (FileNotFoundException e) {
			throw new Exception("", e);
		} catch (IOException e) {
			throw new Exception("", e);
		}
	}

//	public String save(URL url) throws PICException {
//		InputStream in = null;
//		try {
//			String picName = url.getFile().substring(url.getFile().lastIndexOf("/") + 1);
//			FtpURLConnection f = new FtpURLConnection(url);
//			f.setReadTimeout(5000);
//			in = f.getInputStream();
//			// in = url.openStream();
//			return save(in, picName);
//		} catch (IOException e) {
//			throw new PICException("", e);
//		} finally {
//			if (in != null) {
//				try {
//					in.close();
//				} catch (IOException e1) {
//				}
//			}
//		}
//	}
//
//	public void setUploadIP(String uploadIP) {
//		this.uploadIP = uploadIP;
//	}
//
//	public void setUploadPort(String uploadPort) {
//		this.uploadPort = uploadPort;
//	}
}
