package org.iq80.leveldb.ui.commands;

import org.iq80.leveldb.ui.http.HttpInputStream;
import org.w3c.dom.Document;

public interface CommandProcessor {
	public Document executeRequest(HttpInputStream in) throws Exception;
}
