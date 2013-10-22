package org.iq80.leveldb.ui.commands;

import java.io.ByteArrayOutputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.iq80.leveldb.ui.dbinfo.Message;
import org.iq80.leveldb.ui.http.HttpInputStream;
import org.iq80.leveldb.ui.http.LevelDbUIMain;
import org.iq80.leveldn.ui.util.JAXBUtil;
import org.w3c.dom.Document;



public class QueryCommandProcessor implements CommandProcessor {

	@SuppressWarnings("unchecked")
	public Document executeRequest(HttpInputStream in) throws Exception {
		// TODO Auto-generated method 
		Object key = in.getVariables().get("queryKey");
		Message message = new Message();
		if(key!=null){
			String key_str = key.toString();
			String value = LevelDbUIMain.getValue(key_str);
			message.setType("query");
			message.setValue(key_str+"---------->"+value);
		}else{
			message.setType("query");
			message.setValue("the operation failed,because the key is null..");
		}
		in.getVariables().put("template", "message");
		Document doc = JAXBUtil.javaToXml(message);
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
