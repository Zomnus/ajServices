package its.webservice.config;

import java.util.ResourceBundle;


public class SystemConfig {
    static String configFile = "appconfig";
    static String tgs_configFile = "tgs_device";
    static String bmd_configFile = "white_name";
    public static String getConfigInfomation(String itemIndex) {
        try {
            ResourceBundle resource = ResourceBundle.getBundle(configFile);
            return resource.getString(itemIndex);
        } catch (Exception e) {
            return "";
        }
    }
    
    public static String getBMDConfigInfomation(String itemIndex) {
        try {
        	itemIndex = new String(itemIndex.getBytes("UTF-8"),"ISO-8859-1");
            ResourceBundle resource = ResourceBundle.getBundle(bmd_configFile);
            return new String(resource.getString(itemIndex).getBytes("ISO-8859-1"),"UTF-8");
        } catch (Exception e) {
            return "";
        }
    }
    public static ResourceBundle getTgsConfigInfomation() {
        try {
        	return ResourceBundle.getBundle(tgs_configFile);
        } catch (Exception e) {
            return null;
        }
    }
   /* public static void main(String[] args) {
    	tgs_resource = getTgsConfigInfomation();
    	System.out.println("点位序号:"+SystemConfig.tgs_resource.getString("33040601010"));
    	System.out.println("点位序号:"+SystemConfig.tgs_resource.getString("33040601007"));
    	System.out.println("点位序号:"+SystemConfig.tgs_resource.getString("33040601003"));
    	
	}*/
    public static void main(String[] args) throws Exception {
		String str = "浙E00003_02";
		System.out.println(str);
		System.out.println(getBMDConfigInfomation(str));
	}
}