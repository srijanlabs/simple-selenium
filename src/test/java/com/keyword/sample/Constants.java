package com.keyword.sample;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Constants {
	
	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd hh mm ss a");
	static String current_DateTime = new SimpleDateFormat("MM-dd-yyyy_HH-mm-ss").format(new Date().getTime());
	public static final int TOTAL_FILES = 0;
	public static final int CURRENT_ROW = 0;
	
	
	
	/** System variables - File paths of Tests, Objects, Logs and Property Files. */
	public static final String SUITE_DIRECTORY = "../Demo_Test/TestBase";
	public static final String HTML_REPORTS_DIR = "../Demo_Test/Reports/HTML_Report";
	public static final String SCREENSHOT_DIR = "../Demo_Test/Reports/Screenshots";
	public static final String Path_TestSuite = "../Demo_Test/ExcelFilesFolder/TestSuite.xls";
	public static final String HTML_REPORT = "../Demo_Test/Reports/HTML_Report/Demo_TestApp_AutomationReport---";
	public static final String TESTCASE_OUTPUT = "../Demo_Test/Reports/TestCaseOutPut/testCases-OutPut-";
	public static final String log4jConfPath = "../Demo_Test/log4j.properties";
	public static final String userHome = "/Users/Sweta/Documents";
	public static final String BUILD_NUMBER = "1001";	 
	public static final String CHROMEDRIVER = "/usr/local/bin/chromedriver";
	

}
