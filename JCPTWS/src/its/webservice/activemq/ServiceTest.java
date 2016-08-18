package its.webservice.activemq;

import org.apache.log4j.Logger;

public class ServiceTest implements Runnable {

	protected static Logger log = Logger.getLogger(ServiceTest.class.getName());

	@Override
	public void run() {
		log.error("=================================≤‚ ‘ø™ º===========================");
		while (true) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// clbh$fxbh$hphm$hpzl$jgsj$clsd$hpys$csys$cllx$tztp$qmtp$hptp$jgdd$cdh$xs$csbz
			//String test = "002299$00820$2014-08-29 16:08:02$1212121$1$1$70$D:\\1.jpg$D:\\2.jpg$D:\\4.jpg$201$4$5$2$90";
			String mqMessage="0"+"$"+"3173231550021"+"$"+"4"+"$"+"’„AYY110"+"$"+"2"+"$"+"1"+"$"+"2015-06-21 13:13:13"+"$"+"80"+"$"+"1"+"$"+"1"+"$"+"1"+"$"+"wwwwwww"+"$"+"wwwwwwwwwwwwww"+"$"+"0"+"$"+"1"+"$"+""+"$"+"";
			log.error("=================================≤‚ ‘===========================");
			MessageSender.sendMessage(mqMessage);
		}
	}
	
	public static void main(String[] args) {
		
		/*String test = "26812$1$ÕÓ50843$02$2013-12-06 01:41:36$38$1$A$X99"
			+ "$ftp://supcon:supcon@10.125.146.35:21/340204184/20131206/192.168.1.115/340204184-201312060141362-26812-Q.jpg"
			+ "$ftp://supcon:supcon@10.125.146.35:21/340204184/20131206/192.168.1.115/340204184-201312060141362-26812-Q.jpg"
			+ "$ftp://supcon:supcon@10.125.146.35:21/340204184/20131206/192.168.1.115/340204184-201312060141362-26812-C.jpg"
			+ "$340204184$2$60$1";*/
		
			//MessageSender.sendMessage(mqMessage);
	}
}
