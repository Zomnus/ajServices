package its.webservice.entity;

public class PicInfo {
	private String picUrl;
	private String picBase64;
	
	public PicInfo(){}
	public PicInfo(String picUrl, String picBase64) {
		super();
		this.picUrl = picUrl;
		this.picBase64 = picBase64;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getPicBase64() {
		return picBase64;
	}
	public void setPicBase64(String picBase64) {
		this.picBase64 = picBase64;
	}
	
	

}
