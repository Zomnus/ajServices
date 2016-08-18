package its.webservice.activemq;

import its.webservice.service.VehicleInfo;

/**
 * clbh$fxbh$hphm$hpzl$jgsj$clsd$hpys$csys$cllx$tztp$qmtp$hptp$jgdd$cdh$xs$csbz
 * 
 */
public class VehicleConverter {
	private static final String SPLIT = "$";

	public static VehicleInfo strToVehicleInfo(String strmsg) {
		VehicleInfo vehicleInfo = new VehicleInfo();
		String[] vehiclePassInfoArr = strmsg.split("\\" + SPLIT);
		try {
			vehicleInfo.setClbh(vehiclePassInfoArr[0]);
			vehicleInfo.setFxbh(vehiclePassInfoArr[1]);
			vehicleInfo.setHphm(vehiclePassInfoArr[2]);
			vehicleInfo.setHpzl(vehiclePassInfoArr[3]);
			vehicleInfo.setJgsj(vehiclePassInfoArr[4]);
			vehicleInfo.setClsd(Integer.valueOf(vehiclePassInfoArr[5]));
			vehicleInfo.setHpys(vehiclePassInfoArr[6]);
			vehicleInfo.setCsys(vehiclePassInfoArr[7]);
			vehicleInfo.setCllx(vehiclePassInfoArr[8]);
			vehicleInfo.setTztp(vehiclePassInfoArr[9]);
			vehicleInfo.setQmtp(vehiclePassInfoArr[10]);
			vehicleInfo.setHptp(vehiclePassInfoArr[11]);
			vehicleInfo.setJgdd(vehiclePassInfoArr[12]);
			vehicleInfo.setCdh(vehiclePassInfoArr[13]);
			vehicleInfo.setXs(Integer.valueOf(vehiclePassInfoArr[14]));
			vehicleInfo.setCsbz(vehiclePassInfoArr[15]);
		} catch (Exception e) {
			return null;
		}
		return vehicleInfo;
	}

	public static String vehicleInfoToStr(VehicleInfo vehicleInfo) {
		if (null == vehicleInfo) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append(vehicleInfo.getClbh()).append(SPLIT);
		sb.append(vehicleInfo.getFxbh()).append(SPLIT);
		sb.append(vehicleInfo.getHphm()).append(SPLIT);
		sb.append(vehicleInfo.getHpzl()).append(SPLIT);
		sb.append(vehicleInfo.getJgsj()).append(SPLIT);
		sb.append(vehicleInfo.getClsd()).append(SPLIT);
		sb.append(vehicleInfo.getHpys()).append(SPLIT);
		sb.append(vehicleInfo.getCsys()).append(SPLIT);
		sb.append(vehicleInfo.getCllx()).append(SPLIT);
		sb.append(vehicleInfo.getTztp()).append(SPLIT);
		sb.append(vehicleInfo.getQmtp()).append(SPLIT);
		sb.append(vehicleInfo.getHptp()).append(SPLIT);
		sb.append(vehicleInfo.getJgdd()).append(SPLIT);
		sb.append(vehicleInfo.getCdh()).append(SPLIT);
		sb.append(vehicleInfo.getXs()).append(SPLIT);
		return sb.append(vehicleInfo.getCsbz()).toString();
	}
}