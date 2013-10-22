package org.iq80.leveldn.ui.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.iq80.leveldb.ui.http.CommonConstants;
import org.iq80.leveldb.ui.log.Logger;
import org.w3c.dom.Document;

import com.google.common.base.Preconditions;

public class JAXBUtil {
	private static DocumentBuilder builder = null;
	private static Logger log = Logger.getLogger();
	static {
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public static Document javaToXml(Object obj) throws Exception {
		Preconditions.checkNotNull(obj, "the object is null..");
		Preconditions.checkNotNull(builder, "the builder is null..");
		JAXBContext context = JAXBContext.newInstance(obj.getClass());
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_ENCODING,
				CommonConstants.PAGE_ENCODE);
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);// ignore xml
		Document document = builder.newDocument();														// head
		marshaller.marshal(obj, document);
		return document;
	}

}
