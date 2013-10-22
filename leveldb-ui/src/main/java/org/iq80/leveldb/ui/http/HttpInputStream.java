package org.iq80.leveldb.ui.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import mx4j.tools.adaptor.http.HttpConstants;

import org.iq80.leveldb.ui.log.Logger;
import org.iq80.leveldn.ui.util.FormatUtil;

import com.google.common.base.Preconditions;

public class HttpInputStream extends BufferedReader {
	/**
	 * Http method. only GET, POST implemented
	 */

	private String method;

	/**
	 * Path of the request
	 */

	private String path;

	/**
	 * Query string
	 */

	private String queryString;

	/**
	 * Request version
	 */

	private float version;

	/**
	 * Current headers
	 */

	@SuppressWarnings("rawtypes")
	private Map headers = new HashMap();

	@SuppressWarnings("rawtypes")
	private Map variables = new HashMap();
	
	private Logger log = Logger.getLogger();

	public HttpInputStream(InputStream in) {
		super(new InputStreamReader(in));
	}

	public void parse() throws Exception {
		try {
			String request = readLine();
			Preconditions.checkNotNull(request,
					"the request string is null,can't get info from request..");
			StringTokenizer parts = new StringTokenizer(request);
			parseMethod(parts.nextToken());
			String path = parts.nextToken();
			Preconditions.checkNotNull(path, "path is null..");
			parsePath(path);
			if (parts.hasMoreTokens()) {
				parseVersion(parts.nextToken());
			} else {
				version = 0.9f;
			}
			if (version >= 1.0f) {
				parseHeaders();
				parseVariables();
			}

		} catch (Exception e) {
			throw e;
		}

	}

	@SuppressWarnings("unchecked")
	private void parseHeaders() throws IOException {
		String header;
		while (((header = readLine()) != null) && !header.equals("")) {
			int colonIdx = header.indexOf(':');
			if (colonIdx != -1) {
				String name = header.substring(0, colonIdx);
				String value = header.substring(colonIdx + 1);
				headers.put(name.toLowerCase(), value.trim());
			}
		}
	}

	private void parseMethod(String method) {
		if (method.equals(HttpConstants.METHOD_GET)) {
			this.method = HttpConstants.METHOD_GET;
		} else if (method.equals(HttpConstants.METHOD_POST)) {
			this.method = HttpConstants.METHOD_POST;
		} else {
			Preconditions.checkArgument(false,
					FormatUtil.format("%s can not be support..", method));
		}
		log.debug("method:"+this.method);
	}

	private void parsePath(String path) {
		Preconditions.checkArgument(path.startsWith("/"),
				"request path is error...");
		int index = path.indexOf("?");
		if (index > 0) {
			this.path = path.substring(0, index);
			this.queryString = path.substring(index + 1, path.length());
		} else {
			this.path = path;
			this.queryString = "";
		}
		log.debug("path:"+path+"  queryString:"+queryString);
	}

	private void parseVersion(String verStr) {
		Preconditions.checkArgument(verStr.startsWith("HTTP/"),
				"request version is error...");
		this.version = Float.valueOf(verStr.substring(5)).floatValue();
		log.debug("version:"+version);
	}

	@SuppressWarnings("unchecked")
	private void parseVariables() throws IOException {
		String variableHolder = "";
		if (method.equals(HttpConstants.METHOD_POST)
				&& "application/x-www-form-urlencoded".equals(headers
						.get("content-type"))
				&& (headers.get("content-length") != null)) {
			StringBuffer buffer = new StringBuffer();

			int size = Integer.parseInt((String) headers.get("content-length"));
			for (int i = 0; i < size; i++) {
				int j = read();
				Preconditions.checkArgument(j >= 0,
						"get variables from request error...");
				buffer.append((char) j);
			}
			variableHolder = buffer.toString();
		} else if (method.equals(HttpConstants.METHOD_GET)) {
			variableHolder = getQueryString();
		} else {
			Preconditions.checkArgument(false, "can't parse the method  ...");
		}
		StringTokenizer parser = new StringTokenizer(variableHolder, "&");
		while (parser.hasMoreTokens()) {
			String command = parser.nextToken();
			int equalIndex = command.indexOf('=');
			if (equalIndex > 0) {
				String variableName = URLDecoder.decode(command.substring(0,
						equalIndex),"UTF-8");
				String variableValue = URLDecoder.decode(command.substring(
						equalIndex + 1, command.length()),"UTF-8");
				variableValue = new String(variableValue.getBytes(), "UTF-8");
				if (variables.get(variableName) != null) {
					Object value = variables.get(variableName);
					String[] newValue = null;
					if (value instanceof String) {
						newValue = new String[2];
						newValue[0] = variableValue;
						newValue[1] = (String) value;
					} else {
						String[] oldValue = (String[]) value;
						newValue = new String[oldValue.length + 1];
						System.arraycopy(oldValue, 0, newValue, 1,
								oldValue.length);
						newValue[0] = variableValue;
					}
					variables.put(variableName, newValue);
				} else {
					variables.put(variableName, variableValue);
				}
			}
		}
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public float getVersion() {
		return version;
	}

	public void setVersion(float version) {
		this.version = version;
	}

	@SuppressWarnings("rawtypes")
	public Map getHeaders() {
		return headers;
	}
	@SuppressWarnings("rawtypes")
	public void setHeaders(Map headers) {
		this.headers = headers;
	}
	@SuppressWarnings("rawtypes")
	public Map getVariables() {
		return variables;
	}
	@SuppressWarnings("rawtypes")
	public void setVariables(Map variables) {
		this.variables = variables;
	}

}
