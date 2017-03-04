package com.cedrus.logger;

import biz.minaret.log4j.DatedFileAppender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import java.io.OutputStreamWriter;

public class ApplicationLogger {

	private static final String USER_HOME_DIR = System.getProperty("user.home") + "/Cedrus/";

	private Level logLevel = Level.ALL;
	private boolean consoleOutEnabled = false;

    private ConsoleAppender consoleAppender;
	private Logger logger;
	
	public static final String LOG_FILE_PREFIX = "mct_";
	
	
	public void setLogLevel(Level logLevel) {
		logger.setLevel(logLevel);
	}
	
	public ApplicationLogger(Class<?> logClass) {

		String conversionPattern = "[%p] %d %c %M - %m%n";

		PatternLayout layout = new PatternLayout();
		
		layout.setConversionPattern(conversionPattern);
						
		// creates daily rolling file appender	
        DatedFileAppender rollingAppender = new DatedFileAppender();
		
		rollingAppender.setPrefix(LOG_FILE_PREFIX);
		rollingAppender.setLayout(layout);
		rollingAppender.activateOptions();
		rollingAppender.setDirectory(USER_HOME_DIR + "/logs");
					
		logger = Logger.getLogger(logClass);
		logger.setAdditivity(false);
		logger.addAppender(rollingAppender);
		logger.setLevel(logLevel);
		if(consoleOutEnabled) {
			consoleAppender = new ConsoleAppender();
			consoleAppender.setWriter(new OutputStreamWriter(System.out));
			consoleAppender.setLayout(layout);
			logger.addAppender(consoleAppender);
		}
	}

	public void debug(final String message) {
		logger.debug(message);
	}

	public void info(final String message) {
		logger.info(message);
	}

	public void error(final String message) {
		logger.error(message);
	}

	public void fatal(final String message) {
		logger.fatal(message);
	}

	public void warn(String message) {
		logger.warn(message);
	}

}