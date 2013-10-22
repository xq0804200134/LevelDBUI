package org.iq80.leveldb.ui.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import org.iq80.leveldb.ui.commands.PageProcessor;
import org.iq80.leveldb.ui.commands.XSLTProcessor;
import org.iq80.leveldb.ui.log.Logger;
import org.iq80.leveldn.ui.util.FormatUtil;

import com.google.common.base.Preconditions;

public class HttpAdapter {
	private static Logger log = Logger.getLogger();
	private ServerSocket server = null;
	private SocketFactory factory = null;
	private int port = 8080;
	private String host = "localhost";
	private int backLog = 50;// if the connect size > 50 ,the server will reject
	private Date startDate;
	private long requestsCount;
	/**
	 * Indicates whether the server is running
	 */
	private boolean alive = false;

	private PageProcessor processor = null;

	// start the http server
	public void start() {
		Preconditions.checkNotNull(alive, "the server is already started..");
		log.debug(FormatUtil.format(
				"the httpServer is started in the %s:%s ,backLog:%s", host,
				port, backLog));
		if (factory == null) {
			factory = new SocketFactory();
		}
		try {
			server = factory.createServerSocket(port, backLog, host);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return;
		}
		Preconditions.checkNotNull(server, "the server start failed..");
		if (processor == null)
			processor = new XSLTProcessor();
		alive = true;
		Thread serverThread = new Thread(new Runnable() {
			public void run() {
				startDate = new Date();
				requestsCount = 0;
				while (alive) {
					try {
						Socket socket = server.accept();
						log.debug(FormatUtil.format(
								"New connection accepted %s:%s",
								socket.getInetAddress(), socket.getPort()));
						new HttpClient(socket, processor).start();
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
				}
			}
		});
		serverThread.start();
	}

	public void stop() {
		try {
			if (alive) {
				alive = false;
				// force the close with a socket call
				new Socket(host, port);
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		try {
			if (server != null) {
				server.close();
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public long getRequestsCount() {
		return requestsCount;
	}

	public void setRequestsCount(long requestsCount) {
		this.requestsCount = requestsCount;
	}

}
