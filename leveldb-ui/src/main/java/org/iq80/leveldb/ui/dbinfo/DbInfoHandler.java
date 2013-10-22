package org.iq80.leveldb.ui.dbinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentSkipListMap;

import org.iq80.leveldb.impl.DbConstants;
import org.iq80.leveldb.impl.DbImpl;
import org.iq80.leveldb.impl.FileMetaData;
import org.iq80.leveldb.impl.InternalKey;
import org.iq80.leveldb.impl.MemTable;
import org.iq80.leveldb.impl.MemTable.MemTableIterator;
import org.iq80.leveldb.impl.Version;
import org.iq80.leveldb.impl.VersionSet;
import org.iq80.leveldb.ui.http.LevelDbUIMain;
import org.iq80.leveldb.ui.log.Logger;
import org.iq80.leveldb.util.Slice;
import org.iq80.leveldn.ui.util.ReflectUtil;

public class DbInfoHandler {
	private final static String encoding = LevelDbUIMain.getEncoding();
	private final static int keyNum = LevelDbUIMain.getKeyNum();
	private static Logger log = Logger.getLogger(); 
	public static DbInfo getDbInfo(){
		DbImpl db = LevelDbUIMain.getDbImp();
		if(db==null) return null;
		try{
			DbInfo dbInfo = new DbInfo();
			getMemTableInfo(dbInfo);
			getImmutableMemTableInfo(dbInfo);
			getLevel0FileMeta(dbInfo);
			getLevelsFileMeta(dbInfo);
			return dbInfo;
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		return null;
	}
	private static void getLevelsFileMeta(DbInfo dbInfo){
		try{
			DbImpl db = LevelDbUIMain.getDbImp();
			VersionSet versions = (VersionSet) ReflectUtil.getFieldValue(db,"versions");
			Version current = versions.getCurrent();
			Map<Integer,List<FileMeta>> map = new HashMap<Integer,List<FileMeta>>();
			for(int i=0;i<DbConstants.NUM_LEVELS;i++){
				List<FileMeta> levelFile = getLevelFileMeta(current.getFiles(i));
				map.put(i, levelFile);
			}
			dbInfo.setLevelsFileMeta(map);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	private static List<FileMeta> getLevelFileMeta(List<FileMetaData> listFile){
		List<FileMeta> list = new ArrayList<FileMeta>();
		for(int i=0;i<listFile.size();i++){
			list.add(fileMetaDataToFileMeta(listFile.get(i)));
		}
		return list;
	}
	private static void getLevel0FileMeta(DbInfo dbInfo){
		try{
			List<FileMeta> list = new ArrayList<FileMeta>();
			DbImpl db = LevelDbUIMain.getDbImp();
			VersionSet versions = (VersionSet) ReflectUtil.getFieldValue(db,"versions");
			Version current = versions.getCurrent();
			List<FileMetaData> listFile =  current.getFiles(0);
			for(int i=0;i<listFile.size();i++){
				list.add(fileMetaDataToFileMeta(listFile.get(i)));
			}
			dbInfo.setLevel0FileMeta(list);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	private static FileMeta fileMetaDataToFileMeta(FileMetaData fmd){
		FileMeta meta = new FileMeta();
		meta.setFileSize(fmd.getFileSize());
		meta.setLargest(internalKeyToString(fmd.getLargest()));
		meta.setNumber(fmd.getNumber());
		meta.setSmallest(internalKeyToString(fmd.getSmallest()));
		return meta;
	}
	@SuppressWarnings("unchecked")
	private static void getImmutableMemTableInfo(DbInfo dbInfo){
		try{
			MemTableInfo memInfo = new MemTableInfo();
			List<KeyValue> list = new ArrayList<KeyValue>();
			DbImpl db = LevelDbUIMain.getDbImp();
			MemTable memTable = (MemTable) ReflectUtil.getFieldValue(db,"immutableMemTable");
			if(memTable==null){
				memInfo.setSize(0);
				memInfo.setEndKey("");
				memInfo.setStartKey("");
				dbInfo.setImmutableMemTableInfo(memInfo);
				return;
			}
			ConcurrentSkipListMap<InternalKey, Slice> skipMap = (ConcurrentSkipListMap<InternalKey, Slice>) ReflectUtil.getFieldValue(memTable,"table");
			
			MemTableIterator it = memTable.iterator();
			int size = 1;
			while(it.hasNext()){
				Entry<InternalKey, Slice> entry = it.next();
				KeyValue one = new KeyValue();
				one.setKey(internalKeyToString(entry.getKey()));
				one.setValue(sliceToString(entry.getValue()));
				one.setType(entry.getKey().getValueType().name());
				one.setSequenceNumber(entry.getKey().getSequenceNumber());
				list.add(one);
				if(size>=keyNum) 
					break;
				else size++;
			}
			if(skipMap.firstKey()!=null){
				memInfo.setStartKey(internalKeyToString(skipMap.firstKey()));
			}
			if(skipMap.lastKey()!=null){
				memInfo.setEndKey(internalKeyToString(skipMap.lastKey()));
			}
			memInfo.setSize(skipMap.size());
			memInfo.setMemTableList(list);
			dbInfo.setImmutableMemTableInfo(memInfo);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	@SuppressWarnings("unchecked")
	private static void getMemTableInfo(DbInfo dbInfo){
		try{
			MemTableInfo memInfo = new MemTableInfo();
			List<KeyValue> list = new ArrayList<KeyValue>();
			DbImpl db = LevelDbUIMain.getDbImp();
			MemTable memTable = (MemTable) ReflectUtil.getFieldValue(db,"memTable");
			ConcurrentSkipListMap<InternalKey, Slice> skipMap = (ConcurrentSkipListMap<InternalKey, Slice>) ReflectUtil.getFieldValue(memTable,"table");
			MemTableIterator it = memTable.iterator();
			int size = 1;
			while(it.hasNext()){
				Entry<InternalKey, Slice> entry = it.next();
				KeyValue one = new KeyValue();
				one.setKey(internalKeyToString(entry.getKey()));
				one.setValue(sliceToString(entry.getValue()));
				one.setType(entry.getKey().getValueType().name());
				one.setSequenceNumber(entry.getKey().getSequenceNumber());
				list.add(one);
				if(size>=keyNum) 
					break;
				else size++;
			}
			if(skipMap.firstKey()!=null){
				memInfo.setStartKey(internalKeyToString(skipMap.firstKey()));
			}
			if(skipMap.lastKey()!=null){
				memInfo.setEndKey(internalKeyToString(skipMap.lastKey()));
			}
			memInfo.setSize(skipMap.size());
			memInfo.setMemTableList(list);
			dbInfo.setMemTableInfo(memInfo);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	private static String internalKeyToString(InternalKey key) {
		try{
			return new String(key.getUserKey().copyBytes(),encoding);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		return null;
	}
	private static String sliceToString(Slice slice){
		try{
			return new String(slice.copyBytes(),encoding);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		return null;
	}
}
