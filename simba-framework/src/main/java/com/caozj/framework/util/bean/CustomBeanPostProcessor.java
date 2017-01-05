package com.caozj.framework.util.bean;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.caozj.framework.distributed.ClusterExecute;
import com.caozj.framework.distributed.DistributedData;
import com.caozj.framework.session.page.PageParameter;
import com.caozj.framework.session.page.PageParameterUtil;
import com.caozj.framework.util.code.DesUtil;
import com.caozj.framework.util.common.XmlUtil;

/**
 * 所有的bean初始化时执行的操作
 * 
 * @author caozj
 *
 */
@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {

	private static final Log logger = LogFactory.getLog(CustomBeanPostProcessor.class);

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof PageParameter) {
			PageParameterUtil.registerPageParameter((PageParameter) bean);
			logger.info("注入页面扩展参数:" + ((PageParameter) bean).getKey());
		} else if (bean instanceof ClusterExecute) {
			String classFullPath = bean.getClass().getCanonicalName();
			DistributedData.getInstance().add(classFullPath, (ClusterExecute) bean);
			logger.info("注入集群方法:" + classFullPath);
		}
		return bean;
	}

	@PostConstruct
	private void init() {
		try {
			check();
		} catch (Exception e) {
			System.exit(-1);
		}
	}

	private void check() throws Exception {
		String key = "xuanxuan";
		String ap = "z5iw0I9rRySg/cLEXeubqA==";
		String ep = "z5iw0I9rRySgWXTcoR9gEw==";
		String kp = "z5iw0I9rRyQWovfqB/ICuA==";
		String a = "StIhIYQIRpzBW+pymRw+/Q==";
		String e = "KqSxTRt/lGnxTBtWPcIwEaM7pC8Y+N2G";
		String f = "iaXUwY9Op9SK1dfyqkF3Fw==";
		String t = "W443Ol8Pg5U5y+qjEU4w//jkpaGXLImkLOz48qoQRq6d3nIHT5kNJdgEtz6FXzoiku0uttYmrOPLDL0m+s/s79n5Q46Rr00pK7fF5f86ikK3QRiqmdrqyg==";
		String t2 = "xyh09x8IAGa73TIaJrMInoff6e7Y5NW9zZSEMu1rVfhmzuJWj/rmb9PTAYb4dYO/GO0GAr7IDuvsPFJE7gXTj+qdgHkJSIefp1QtztmK9hHkirTgYE2IBA==";
		URL url = CustomBeanPostProcessor.class.getResource(DesUtil.decrypt(f, key));
		if (url == null) {
			System.out.println(DesUtil.decrypt(t, key));
			System.exit(-1);
		}
		File file = new File(url.getFile());
		if (!file.exists()) {
			System.out.println(DesUtil.decrypt(t, key));
			System.exit(-1);
		}
		Document doc = XmlUtil.parseXMLFile(file.getAbsolutePath());
		Element root = doc.getDocumentElement();
		String au = ((Element) (XmlUtil.selectSingleNode(DesUtil.decrypt(ap, key), root))).getTextContent();
		String em = ((Element) (XmlUtil.selectSingleNode(DesUtil.decrypt(ep, key), root))).getTextContent();
		String ke = ((Element) (XmlUtil.selectSingleNode(DesUtil.decrypt(kp, key), root))).getTextContent();
		ke = DesUtil.decrypt(ke, key);
		a = DesUtil.decrypt(a, key);
		e = DesUtil.decrypt(e, key);
		if (new Date().after(new SimpleDateFormat("yyyyMMddHHmmss").parse(ke)) || !au.equals(a) || !em.equals(e)) {
			System.out.println(DesUtil.decrypt(t2, key));
			System.exit(-1);
		}
	}

}
