package org.iq80.leveldb.ui.mx4j.test;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

import mx4j.MX4JSystemKeys;
import mx4j.tools.adaptor.http.HttpAdaptor;
import mx4j.tools.adaptor.http.XSLTProcessor;

public class Mx4jTest {
	public static void main(String []args) throws Exception{
		System.setProperty(MX4JSystemKeys.MX4J_LOG_PRIORITY,"debug");
		MBeanServer server = MBeanServerFactory.createMBeanServer();
		HttpAdaptor adapter = new HttpAdaptor();
		ObjectName name = new ObjectName("Server:name=HttpAdaptor");
		server.registerMBean(adapter, name);
		adapter.setPort(8080);
		adapter.setProcessor(new XSLTProcessor());
		adapter.setHost("localhost");
		adapter.start();
	}
}
