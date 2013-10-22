package org.iq80.leveldb.ui.jaxb;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.iq80.leveldb.ui.dbinfo.FileMeta;


public  class MapAdapter extends XmlAdapter<MapElements[], Map<Integer,List<FileMeta>>> {

	@Override
	public Map<Integer, List<FileMeta>> unmarshal(MapElements[] arg0) throws Exception {
		Map<Integer, List<FileMeta>> r = new TreeMap<Integer, List<FileMeta>>();
        for (MapElements mapelement : arg0)
            r.put(mapelement.key, mapelement.value);
        return r;
	}

	@Override
	public MapElements[] marshal(Map<Integer, List<FileMeta>> arg0) throws Exception {
		MapElements[] mapElements = new MapElements[arg0.size()];
        int i = 0;
        for (Entry<Integer, List<FileMeta>> entry : arg0.entrySet())
            mapElements[i++] = new MapElements(entry.getKey(), entry.getValue());

        return mapElements;

	}


}
