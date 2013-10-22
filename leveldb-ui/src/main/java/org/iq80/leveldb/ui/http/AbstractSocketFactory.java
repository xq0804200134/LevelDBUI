package org.iq80.leveldb.ui.http;

import java.io.IOException;
import java.net.ServerSocket;

public interface AbstractSocketFactory {
	public ServerSocket createServerSocket(int port, int backlog, String host) throws IOException;
}
