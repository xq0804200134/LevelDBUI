package org.iq80.leveldb.ui.http;

import java.io.InputStream;
import java.net.Socket;

import org.iq80.leveldb.ui.commands.CommandConstants;
import org.iq80.leveldb.ui.commands.CommandProcessor;
import org.iq80.leveldb.ui.commands.PageProcessor;
import org.iq80.leveldb.ui.log.Logger;
import org.w3c.dom.Document;

import com.google.common.base.Preconditions;

public class HttpClient extends Thread {
	private static Logger log = Logger.getLogger();
	private Socket socket = null;
	private PageProcessor processor = null;

	public HttpClient(Socket socket, PageProcessor processor) {
		this.socket = socket;
		this.processor = processor;
	}

	public void process() throws Exception {
		Preconditions.checkNotNull(socket, "the client socket is null...");
		InputStream inputStream = socket.getInputStream();
		HttpInputStream in = new HttpInputStream(inputStream);
		in.parse();
		HttpOutputStream out = new HttpOutputStream(socket.getOutputStream(),
				in);
		try{
			Preconditions.checkNotNull(processor, "the processor  is null...");
			String path = processor.preProcess(in.getPath());
			CommandProcessor command = CommandConstants.getCommand(path);
			if(command==null){
				processor.notFoundElement(path, out, in);
			}else{
				Document document = command.executeRequest(in);
				processor.writeResponse(out, in, document);
			}
			
		}catch(Exception e){
			log.error(e.getMessage(), e);
			out.write(e.getMessage().getBytes());
		}
		if (out != null) {
			out.flush();
		}
	}

	public void run() {
		try {
			process();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				if (socket != null)
					socket.close();
			} catch (Exception e) {

			}
		}
	}
}
