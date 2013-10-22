package org.iq80.leveldb.ui.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.iq80.leveldb.ui.http.HttpConstants;
import org.iq80.leveldb.ui.http.HttpInputStream;
import org.iq80.leveldb.ui.http.HttpOutputStream;
import org.iq80.leveldb.ui.log.Logger;
import org.w3c.dom.Document;

import com.google.common.base.Preconditions;

public class XSLTProcessor implements PageProcessor, URIResolver {
	private static final String defaultPage = "index";
	private String path = "org/iq80/leveldb/ui/http/xls";
	private static Logger log = Logger.getLogger();
	private static ClassLoader classLoader = XSLTProcessor.class
			.getClassLoader();
	private TransformerFactory factory;
	private boolean useCache;
	@SuppressWarnings("rawtypes")
	private Map templatesCache = null;
	private Map<String,String> mimeTypes = new HashMap<String,String>();

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings({ "rawtypes"})
	public XSLTProcessor() {
		factory = TransformerFactory.newInstance();
		factory.setURIResolver(this);
		useCache = false;
		if (useCache)
			templatesCache = new HashMap();
		mimeTypes.put(".gif", "image/gif");
		mimeTypes.put(".jpg", "image/jpg");
		mimeTypes.put(".png", "image/png");
		mimeTypes.put(".tif", "image/tiff");
		mimeTypes.put(".tiff", "image/tiff");
		mimeTypes.put(".ico", "image/ico");
		mimeTypes.put(".html", "text/html");
		mimeTypes.put(".htm", "text/html");
		mimeTypes.put(".txt", "text/plain");
		mimeTypes.put(".xml", "text/xml");
		mimeTypes.put(".xsl", "text/xsl");
		mimeTypes.put(".css", "text/css");
		mimeTypes.put(".js", "text/x-javascript");
		mimeTypes.put(".jar", "application/java-archive");
	}

	@SuppressWarnings("rawtypes")
	public void writeResponse(HttpOutputStream out, HttpInputStream in,
			Document document) throws Exception {
		// TODO Auto-generated method stub
		out.setCode(HttpConstants.STATUS_OKAY);
		out.setHeader("Content-Type", "text/html");
		// added some caching attributes to force not to cache
		out.setHeader("Cache-Control", "no-cache");
		out.setHeader("expires", "now");
		out.setHeader("pragma", "no-cache");
		out.sendHeaders();
		Transformer transformer = null;
		String path = preProcess(in.getPath());

		if (in.getVariables().containsKey("template")) {
			transformer = createTransformer(in.getVariables().get("template")
					+ ".xsl");
		} else {
			transformer = createTransformer(path + ".xsl");
		}
		if (transformer != null) {
			// added so that the document() function works
			transformer.setURIResolver(this);
			// The variables are passed to the XSLT as (param.name, value)
			Map variables = in.getVariables();
			Iterator j = variables.keySet().iterator();
			while (j.hasNext()) {
				String key = (String) j.next();
				Object value = variables.get(key);
				if (value instanceof String) {
					transformer.setParameter("request." + key, value);
				}
				if (value instanceof String[]) {
					String[] allvalues = (String[]) value;
					// not a good solution, only the first one is presented
					transformer.setParameter("request." + key, allvalues[0]);
				}
			}
			try {
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				log.debug("transforming " + path);
				transformer.transform(new DOMSource(document),
						new StreamResult(output));
				output.writeTo(out);
			} catch (TransformerException e) {
				log.error("Transformation exception ", e);
			}
		}

	}

	public void writeError(HttpOutputStream out, HttpInputStream in, Exception e)
			throws IOException {

	}

	public String preProcess(String path) {
		if (path == null || path.equals("/")) {
			return defaultPage;
		}
		if(path.startsWith("/"))
			path = path.substring(1,path.length());
		return path;
	}

	public String notFoundElement(String path, HttpOutputStream out,
			HttpInputStream in) throws Exception {
		File file = new File(getMergerPath(path));
		log.debug("Processing file request " + file);
		String name = file.getName();
		int extensionIndex = name.lastIndexOf('.');
		String mime = null;
		if (extensionIndex < 0) {
			log.warn("Filename has no extensions " + file.toString());
			mime = "text/plain";
		} else {
			String extension = name.substring(extensionIndex, name.length());
			if (mimeTypes.containsKey(extension)) {
				mime = (String) mimeTypes.get(extension);
			} else {
				log.warn("MIME type not found " + extension);
				mime = "text/plain";
			}
		}
		try {
			log.debug("Trying to read file " + file);
			BufferedInputStream fileIn = new BufferedInputStream(
					getInputStream(path));
			ByteArrayOutputStream outArray = new ByteArrayOutputStream();
			BufferedOutputStream outBuffer = new BufferedOutputStream(outArray);
			int piece = 0;
			while ((piece = fileIn.read()) >= 0) {
				outBuffer.write(piece);
			}
			outBuffer.flush();
			out.setCode(HttpConstants.STATUS_OKAY);
			out.setHeader("Content-type", mime);
			out.sendHeaders();
			if (log.isEnabledFor(Logger.DEBUG))
				log.debug("File output " + mime);
			outArray.writeTo(out);
			fileIn.close();
		} catch (Exception e) {
			log.warn("Exception loading file " + file, e);
			throw e;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	protected Transformer createTransformer(String path) throws Exception {
		try {
			synchronized (this) {
				InputStream stream = getInputStream(path);
				Preconditions.checkNotNull(stream, "the xsl doc is empty...");
				Templates template = factory.newTemplates(new StreamSource(
						stream));
				if (useCache)
					templatesCache.put(path, template);
				return template.newTransformer();
			}
		} catch (Exception e) {
			throw e;
		}
	}

	protected InputStream getInputStream(String path) {
		path = getMergerPath(path);
		log.debug("the xsl path:" + path);
		return classLoader.getResourceAsStream(path);
	}

	private String getMergerPath(String path) {
		if (path.startsWith("/"))
			path = this.path + path;
		else
			path = this.path + "/" + path;
		return path;
	}

	public Source resolve(String href, String base) throws TransformerException {
		StreamSource source = new StreamSource(getInputStream(href));
		// this works with saxon7/saxon6.5.2/xalan
		source.setSystemId(href);
		return source;
	}

}
