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



public class SetCommandProcessor implements CommandProcessor {

	@SuppressWarnings("unchecked")
	public Document executeRequest(HttpInputStream in) throws Exception {
		// TODO Auto-generated method 
		Object key = in.getVariables().get("setKey");
		Object value = in.getVariables().get("setValue");
		Message message = new Message();
		if(key!=null&&value!=null){
			LevelDbUIMain.setKeyValue(key.toString(), value.toString());
			message.setType("set");
			message.setValue("set key["+key.toString()+"],value["+value.toString()+"]  success.....");
		}else{
			message.setType("set");
			message.setValue("set operation failed,because one of them(key or value) is null.....");
		}
		in.getVariables().put("template", "message");
		Document doc = JAXBUtil.javaToXml(message);
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
