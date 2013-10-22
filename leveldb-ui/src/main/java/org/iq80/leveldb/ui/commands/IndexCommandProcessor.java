package org.iq80.leveldb.ui.commands;

import java.io.ByteArrayOutputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.iq80.leveldb.ui.dbinfo.DbInfo;
import org.iq80.leveldb.ui.dbinfo.DbInfoHandler;
import org.iq80.leveldb.ui.http.HttpInputStream;
import org.iq80.leveldn.ui.util.JAXBUtil;
import org.w3c.dom.Document;


public class IndexCommandProcessor implements CommandProcessor {

	public Document executeRequest(HttpInputStream in) throws Exception {
		// TODO Auto-generated method 
		DbInfo dbInfo = DbInfoHandler.getDbInfo();
		Document doc = JAXBUtil.javaToXml(dbInfo);
		documentToString(doc);
		return doc;
	}
	
	public static void documentToString(Document doc) throws Exception{
		TransformerFactory   tf   =   TransformerFactory.newInstance();
		Transformer t = tf.newTransformer();
		t.setOutputProperty("encoding","GB23121");//解决中文问题，试过用GBK不行
		ByteArrayOutputStream   bos   =   new   ByteArrayOutputStream();
		t.transform(new DOMSource(doc), new StreamResult(bos));
		String xmlStr = bos.toString();
		System.out.println(xmlStr);
	}

}
