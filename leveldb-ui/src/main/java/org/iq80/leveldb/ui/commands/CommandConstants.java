package org.iq80.leveldb.ui.commands;

import java.util.HashMap;

import org.iq80.leveldb.ui.log.Logger;

public class CommandConstants {
	private static String[][] defaultCommandProcessors = {
			{ "index", "org.iq80.leveldb.ui.commands.IndexCommandProcessor" },
			{ "about", "org.iq80.leveldb.ui.commands.CommonCommandProcessor" },
			{ "operation", "org.iq80.leveldb.ui.commands.CommonCommandProcessor" },
			{ "set", "org.iq80.leveldb.ui.commands.SetCommandProcessor" },
			{ "query", "org.iq80.leveldb.ui.commands.QueryCommandProcessor" },
			{ "delete", "org.iq80.leveldb.ui.commands.DeleteCommandProcessor" }};
	
	private static HashMap<String,CommandProcessor> commands = new HashMap<String,CommandProcessor>();
	private static Logger log = Logger.getLogger();

	
	private static void buildCommands() {
		for (int i = 0; i < defaultCommandProcessors.length; i++) {
			try {
				CommandProcessor processor =(CommandProcessor)Class.forName(defaultCommandProcessors[i][1]).newInstance();
				commands.put(defaultCommandProcessors[i][0], processor);
			} catch (Exception e) {
				log.error("Exception building command procesor", e);
			}
		}
	}
	public static CommandProcessor getCommand(String name){
		return commands.get(name);
	}
	static{
		buildCommands();
	}
}
