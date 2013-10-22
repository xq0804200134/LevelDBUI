package org.iq80.leveldb.ui.dbinfo;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class MemTableInfo {
	private List<KeyValue> memTableList;
	private long size;
	private String startKey;
	private String endKey;
	public List<KeyValue> getMemTableList() {
		return memTableList;
	}
	public void setMemTableList(List<KeyValue> memTableList) {
		this.memTableList = memTableList;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getStartKey() {
		return startKey;
	}
	public void setStartKey(String startKey) {
		this.startKey = startKey;
	}
	public String getEndKey() {
		return endKey;
	}
	public void setEndKey(String endKey) {
		this.endKey = endKey;
	}
	
}
