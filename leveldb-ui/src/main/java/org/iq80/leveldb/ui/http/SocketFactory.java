package org.iq80.leveldb.ui.http;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class SocketFactory implements AbstractSocketFactory {
	public ServerSocket createServerSocket(int port, int backlog, String host) throws IOException
	   {
	      return new ServerSocket(port, backlog, InetAddress.getByName(host));
	   }
}
