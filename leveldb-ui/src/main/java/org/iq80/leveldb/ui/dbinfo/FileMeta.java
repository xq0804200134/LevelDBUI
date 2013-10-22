package org.iq80.leveldb.ui.dbinfo;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class FileMeta {
	private long number;
	/**
	 * File size in bytes
	 */
	private long fileSize;
	/**
	 * Smallest internal key served by table
	 */
	private String smallest;
	/**
	 * Largest internal key served by table
	 */
	private String largest;

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getSmallest() {
		return smallest;
	}

	public void setSmallest(String smallest) {
		this.smallest = smallest;
	}

	public String getLargest() {
		return largest;
	}

	public void setLargest(String largest) {
		this.largest = largest;
	}

}
