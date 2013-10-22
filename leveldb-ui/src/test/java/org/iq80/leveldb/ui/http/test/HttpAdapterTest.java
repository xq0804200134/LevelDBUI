package org.iq80.leveldb.ui.http.test;

import java.io.File;
import java.util.Random;

import org.iq80.leveldb.Options;
import org.iq80.leveldb.WriteOptions;
import org.iq80.leveldb.impl.DbImpl;
import org.iq80.leveldb.ui.http.LevelDbUIMain;

public class HttpAdapterTest {
	public static void main(String []args) throws Exception{
		//HttpAdapter http = new HttpAdapter();
		//http.start();
		Options options = new Options();
        options.maxOpenFiles(100);
        options.createIfMissing(true);
        File databaseDir = new File("D:/temp/levelDb");
		LevelDbUIMain.start(options, databaseDir);
		for(int i=0;i<200;i++){
			DbImpl dbImpl = LevelDbUIMain.getDbImp();
			dbImpl.put(getRandomString(27).getBytes(), getRandomString(32).getBytes(), new WriteOptions().sync(false));
		}
	}
	public static String getRandomString(int length) { //length表示生成字符串的长度
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";   
	    Random random = new Random();   
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i < length; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString();   
	 }  
}
