package org.iq80.leveldb.ui.dbinfo;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.iq80.leveldb.ui.jaxb.MapAdapter;
@XmlRootElement
public class DbInfo {
	private MemTableInfo memTableInfo;
	
	private MemTableInfo immutableMemTableInfo;
	//level0 file meta
	private List<FileMeta> level0FileMeta;
	//the file meta in levels
	private Map<Integer,List<FileMeta>> levelsFileMeta;
	
	
	public MemTableInfo getMemTableInfo() {
		return memTableInfo;
	}
	public void setMemTableInfo(MemTableInfo memTableInfo) {
		this.memTableInfo = memTableInfo;
	}
	public MemTableInfo getImmutableMemTableInfo() {
		return immutableMemTableInfo;
	}
	public void setImmutableMemTableInfo(MemTableInfo immutableMemTableInfo) {
		this.immutableMemTableInfo = immutableMemTableInfo;
	}
	public List<FileMeta> getLevel0FileMeta() {
		return level0FileMeta;
	}
	public void setLevel0FileMeta(List<FileMeta> level0FileMeta) {
		this.level0FileMeta = level0FileMeta;
	}
	@XmlJavaTypeAdapter(MapAdapter.class)
	public Map<Integer, List<FileMeta>> getLevelsFileMeta() {
		return levelsFileMeta;
	}
	public void setLevelsFileMeta(Map<Integer, List<FileMeta>> levelsFileMeta) {
		this.levelsFileMeta = levelsFileMeta;
	}
	
}
