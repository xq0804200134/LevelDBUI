package org.iq80.leveldb.ui.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;

import org.iq80.leveldb.ui.dbinfo.FileMeta;


public class MapElements {
	@XmlAttribute
	public Integer key;
	
	public List<FileMeta> value;

    public MapElements() {
        
    }
    
    public MapElements( Integer key,List<FileMeta> value) {
        this.key = key;
        this.value = value;
    }
}
