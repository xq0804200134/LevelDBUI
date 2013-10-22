package org.iq80.leveldb.ui.dbinfo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class KeyValue {
	private String key;
	private String value;
	private long sequenceNumber;
	private String type;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public long getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
