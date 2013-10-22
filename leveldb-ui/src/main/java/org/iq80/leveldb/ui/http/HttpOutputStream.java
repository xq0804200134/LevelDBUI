package org.iq80.leveldb.ui.http;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.iq80.leveldn.ui.util.HttpUtil;

public class HttpOutputStream extends BufferedOutputStream {
	/**
	 * Answer code
	 */

	protected int code;

	/**
	 * whether to send the headers
	 */

	protected boolean sendHeaders;

	/**
	 * Headers to be sent
	 */

	@SuppressWarnings("rawtypes")
	protected Map headers = new HashMap(7);

	/**
	 * Creates a new HttpOutputStream with a given OutputStream and an
	 * InputStream
	 * 
	 * @param out
	 *            The OutputStream normally associated with the output socket
	 *            <p/>
	 *            stream of the incoming connection
	 * @param in
	 *            HttpInputStream containing the incoming request
	 */
	public HttpOutputStream(OutputStream out, HttpInputStream in){
		super(out);
		code = HttpConstants.STATUS_OKAY;
		setHeader("Server", HttpConstants.SERVER_INFO);
		sendHeaders = (in.getVersion() >= 1.0);
	}

	/**
	 * Sets the answer code
	 * 
	 * @param code
	 *            The new code value
	 */
	public void setCode(int code){
		this.code = code;
	}
	/**
	 * Sets a given header code
	 * 
	 * @param attr
	 *            The new header name
	 * @param value
	 *            The new header value
	 */
	@SuppressWarnings("unchecked")
	public void setHeader(String attr, String value){
		headers.put(attr, value);
	}

	/**
	 * Sends the headers
	 * 
	 * @return Description of the Returned Value
	 * @throws IOException
	 *             Description of Exception
	 */
	@SuppressWarnings("rawtypes")
	public boolean sendHeaders() throws IOException{
		if (sendHeaders){
			StringBuffer buffer = new StringBuffer(512);
			buffer.append(HttpConstants.HTTP_VERSION);
			buffer.append(code);
			buffer.append(" ");
			buffer.append(HttpUtil.getCodeMessage(code));
			buffer.append("\r\n");
			Iterator attrs = headers.keySet().iterator();
			int size = headers.size();
			for (int i = 0; i < size; i++){
				String attr = (String) attrs.next();
				buffer.append(attr);
				buffer.append(": ");
				buffer.append(headers.get(attr));
				buffer.append("\r\n");
			}
			buffer.append("\r\n");
			write(buffer.toString());
		}
		return sendHeaders;
	}

	/**
	 * Writes a given message line
	 * 
	 * @param msg
	 *            The message to be written
	 * @throws IOException
	 */
	public void write(String msg) throws IOException{
		write(msg.getBytes("latin1"));
	}
}
