///**
// * 
// */
//package its.webservice.activemq;
//
//import its.webservice.common.AppInitConstants;
//import its.webservice.entity.CrossTollgateBean;
//import its.webservice.entity.VehiclePassBean;
//import its.webservice.server.worker.WriteWhRunnableReceive;
//
//import javax.jms.JMSException;
//import javax.jms.TextMessage;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.log4j.Logger;
//import org.springframework.jms.JmsException;
//import org.springframework.jms.core.JmsTemplate;
//
///**
// * 
// * @author zx
// * 
// */
//public class VehiclePassListener implements Runnable {
//
//	protected Logger log = Logger
//			.getLogger(VehiclePassListener.class.getName());
//
//	private JmsTemplate jmsTemplate = AppInitConstants.APPLICATIONCONTEXT
//			.getBean(JmsTemplate.class);
//
//	public void run() {
//		while (true) {
//			try {
//				// 为了保证线程池不出于等待的状态造成的延迟，故当执行任务大于线程数时就不去队列中获取任务
//				if (AppInitConstants.MESSAGECOUNTER.getCount() < AppInitConstants.WEB_SERVICES_THREAD_COUNT) {
//					TextMessage txtmsg = (TextMessage) jmsTemplate.receive();
//					if (null != txtmsg) {
//						String strmsg = txtmsg.getText();
//						// String strmsg = new String(txtmsg.getText().getBytes(
//						// "ISO-8859-1"), "GBK");
//
//						if (!StringUtils.isEmpty(strmsg)) {
//							VehiclePassBean vehiclePassBean = VehicleConverter.strToVehicleInfo(strmsg);
//							if (null != vehiclePassBean) {
//								// 调用线程池去消费
//								// 查询全国卡口平台卡口编号
//								String kkbh = vehiclePassBean.getJgdd();
//								if (StringUtils.isEmpty(kkbh)) {
//									log.debug("上传过车数据，卡口编号为空，卡口编号：" + kkbh);
//								} else {
//									CrossTollgateBean bean = AppInitConstants.CROSSTOLLGATEMAP
//											.get(kkbh);
//									if (null != bean) {
//										log.debug("过车数据放入线程池中等待上传，卡口编号：" + kkbh
//												+ ";过车时间"
//												+ vehiclePassBean.getJgsj());
//										AppInitConstants.EXEC
//												.execute(new WriteWhRunnableReceive(
//														vehiclePassBean, bean)); // 启动线程消费vehPass.messages过车队列
//									} else {
//										log.debug("上传过车数据，卡口编号没有在tgs_cross_tollgate表中配置全国卡口对应关系，卡口编号为："
//												+ kkbh);
//									}
//								}
//							}
//						}
//					}
//				}
//			} catch (JmsException e) {
//				log.debug("activemq连接失败，请检查其是否启动！");
//			} catch (JMSException e) {
//				log.debug("activemq连接失败，请检查其是否启动！");
//			}
//		}
//	}
//}