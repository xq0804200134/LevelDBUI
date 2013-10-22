package org.iq80.leveldb.ui.http;

import java.io.File;

import org.iq80.leveldb.Options;
import org.iq80.leveldb.impl.DbImpl;

import com.google.common.base.Preconditions;

public class LevelDbUIMain {
	private static DbImpl dbImp=null;
	private static HttpAdapter http = null;
	private  static String encoding = "utf-8";
	private  static int keyNum = 10;
	private static int port=8080;
	public static void start(Options options, File databaseDir) throws Exception {
		if(dbImp!=null){
			dbImp.close();
		}
		if(http!=null) http.stop();
		dbImp = new DbImpl(options,databaseDir);
		http = new HttpAdapter();
		http.setPort(port);
		http.start();
	}
	public static DbImpl getDbImp() {
		return dbImp;
	}
	public static void stopLevelDbUI(){
		if(isAlive()) http.stop();
	}
	public static boolean isAlive(){
		if(http!=null&&http.isAlive()) return true;
		return false;
	}
	public static String getEncoding(){
		return encoding;
	}
	public static int getKeyNum(){
		return keyNum;
	}
	public static String getValue(String name) throws Exception{
        Preconditions.checkNotNull(dbImp, "leveldb have not started..");
        Preconditions.checkNotNull(name, "key is null..");
        try{
            byte[] bytes = dbImp.get(name.getBytes());
            if(bytes!=null){
                return new String(bytes,encoding);
            } 
        }catch(Exception e){
            throw e;
        }
        return null;
    }
	public static void deleteKey(String name) throws Exception{
        Preconditions.checkNotNull(dbImp, "leveldb have not started..");
        Preconditions.checkNotNull(name, "key is null..");
        dbImp.delete(name.getBytes());
    }
    public static void setKeyValue(String name,String value){
        Preconditions.checkNotNull(name, "key is null..");
        Preconditions.checkNotNull(value, "value is null..");
        dbImp.put(name.getBytes(),value.getBytes());
    }
	public static int getPort() {
		return port;
	}
	public static void setPort(int port) {
		LevelDbUIMain.port = port;
	}
 
}
