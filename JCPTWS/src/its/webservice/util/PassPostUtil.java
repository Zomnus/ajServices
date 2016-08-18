package its.webservice.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.UUID;

import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class PassPostUtil {
	public static String postClientUploadTgs(String url,String jkxlh,String kkid,String sbbh,String hphm,String hpzl,String hpys,
			String cllx,String gwsj,String fxbh,String cdbh,String clsd,String jllx,String sjly,String sbcj,String clpp,
			String csys,String cpzb,String jsszb,String fjsszb,String tplx,
			String tp1)
			throws UnsupportedEncodingException {
		Charset utf8 = Charset.forName("utf-8");
		String fengefu = "-----------------------------"
				+ UUID.randomUUID().toString();
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setCharset(Charset.forName("utf-8")); // 设置请求的编码格式
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);// 设置浏览器兼容模式
		builder.setBoundary(fengefu);
		ContentType textContentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE,utf8);
		ContentType imageContentType = ContentType.create("image/x-png", utf8);
		//passInfo--Start----------------------------------------------------------
		builder.addPart("jkxlh", new StringBody(jkxlh, textContentType));
		builder.addPart("kkid", new StringBody(kkid, textContentType));
		builder.addPart("sbbh", new StringBody(sbbh,textContentType));
		builder.addPart("hphm", new StringBody(hphm, textContentType));
		builder.addPart("hpzl", new StringBody(hpzl, textContentType));
		builder.addPart("hpys", new StringBody(hpys, textContentType));
		builder.addPart("cllx", new StringBody(cllx, textContentType));
		builder.addPart("gwsj", new StringBody(gwsj,textContentType));
		builder.addPart("fxbh", new StringBody(fxbh, textContentType));
		builder.addPart("cdbh", new StringBody(cdbh, textContentType));
		builder.addPart("clsd", new StringBody(clsd, textContentType));
		builder.addPart("jllx", new StringBody(jllx, textContentType));
		builder.addPart("sjly", new StringBody(sjly, textContentType));
		builder.addPart("sbcj", new StringBody(sbcj, textContentType));
		builder.addPart("clpp", new StringBody(clpp, textContentType));
		builder.addPart("csys", new StringBody(csys, textContentType));
		builder.addPart("cpzb", new StringBody(cpzb,textContentType));
		builder.addPart("jsszb", new StringBody(jsszb,textContentType));
		builder.addPart("fjsszb", new StringBody(fjsszb,textContentType));
		builder.addPart("tplx", new StringBody(tplx, textContentType));
		HttpPost request = null;
		try {
			request = new HttpPost(url);
			request.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			request.addHeader("Content-Type","multipart/form-data;charset=utf-8; boundary=" + fengefu);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		if (1 == 1) {
			builder.addPart("tp1",new StringBody(tp1,textContentType));
		}
		/*else {
			File file = new File("D:\\car.jpg");
			builder.addBinaryBody("tztp", file, ContentType.DEFAULT_BINARY,"D:\\car.jpg");
			builder.addBinaryBody("tp1", file, ContentType.DEFAULT_BINARY,"D:\\car.jpg");
			builder.addBinaryBody("tp2", file, ContentType.DEFAULT_BINARY,"D:\\car.jpg");
			builder.addBinaryBody("tp3", file, ContentType.DEFAULT_BINARY,"D:\\car.jpg");
			builder.addBinaryBody("tp4", file, ContentType.DEFAULT_BINARY,"D:\\car.jpg");
		}
		*/
		HttpEntity entitynew = builder.build();// 生成 HTTP POST 实体
		request.setEntity(entitynew);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpResponse response = null;
		try {
			response = httpClient.execute(request);
		} catch (ClientProtocolException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		String bodyReturn = "";

		HttpEntity entity = response.getEntity();
		try {
			bodyReturn = EntityUtils.toString(entity);
		} catch (ParseException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());

				} catch (IOException e) {

					e.printStackTrace();
				} // 会自动释放连接
			}
		}
		return bodyReturn;
	}
	/*
	 public static void main(String[] args) {
		 String result = "jieni:999939,jdiengid,owji";
		 String code = result.substring(result.indexOf(":")+1,result.indexOf(","));
		 System.out.println(code);
		/*
		 try {
			
			System.out.println("开始上传过车信息");
			String result = PassPostUtil.postClientUploadTgs("http://10.10.100.201:8080/services/after-vehicle");
			System.out.println("上传结果： "+result);
		} catch (UnsupportedEncodingException e) {
	
			e.printStackTrace();
		}
		
	}*/
}
