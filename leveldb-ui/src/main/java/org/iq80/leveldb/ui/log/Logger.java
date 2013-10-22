package org.iq80.leveldb.ui.log;

import javax.annotation.Nullable;

import com.google.common.annotations.VisibleForTesting;

public class Logger {
	public static final int TRACE = 0;
	public static final int DEBUG = TRACE + 10;
	public static final int INFO = DEBUG + 10;
	public static final int WARN = INFO + 10;
	public static final int ERROR = WARN + 10;
	public static final int FATAL = ERROR + 10;

	private int m_priority = DEBUG;

	private Logger() {
	}

	private static Logger logger = null;

	public static Logger getLogger() {
		if (logger == null)
			logger = new Logger();
		return logger;
	}

	public void setPriority(int priority) {
		m_priority = priority;
	}

	public int getPriority() {
		return m_priority;
	}

	public final boolean isEnabledFor(int priority) {
		return priority >= getPriority();
	}

	public final void fatal(Object message) {
		log(FATAL, message, null);
	}

	public final void fatal(Object message, Throwable t) {
		log(FATAL, message, t);
	}

	public final void error(Object message) {
		log(ERROR, message, null);
	}

	public final void error(Object message, Throwable t) {
		log(ERROR, message, t);
	}

	public final void warn(Object message) {
		log(WARN, message, null);
	}

	public final void warn(Object message, Throwable t) {
		log(WARN, message, t);
	}

	public final void info(Object message) {
		log(INFO, message, null);
	}

	public final void info(Object message, Throwable t) {
		log(INFO, message, t);
	}

	public final void debug(Object message) {
		log(DEBUG, message, null);
	}

	public final void debug(Object message, Throwable t) {
		log(DEBUG, message, t);
	}

	public final void trace(Object message) {
		log(TRACE, message, null);
	}

	public final void trace(Object message, Throwable t) {
		log(TRACE, message, t);
	}

	protected void log(int priority, Object message, Throwable t) {
		if (isEnabledFor(priority)) {
			System.out.println(message);
			if (t != null) {
				t.printStackTrace(System.out);
			}
		}
	}
}
