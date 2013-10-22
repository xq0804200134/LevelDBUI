package org.iq80.leveldb.ui.dbinfo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Message {
	private String type;
	private String value;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
