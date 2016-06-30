package com.keyword.sample;

import java.io.File;
import java.io.FileWriter;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.commons.io.FileUtils;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.sun.mail.smtp.SMTPMessage;


import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * @author 
 * Class Name is KeyWordExecution.	
 * On the basis of these ClassName,their corresponding MethodName and InputArgs,testCases will execute.
 */

public  class KeyWordExecutionTest{

	static Logger log = Logger.getLogger(KeyWordExecutionTest.class.getName());	
	static String userHome = System.getProperty("user.home");
	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd hh mm ss a");
	static String log4jConfPath = "../Demo_Test/log4j.properties";
	static String current_DateTime= new SimpleDateFormat("MM-dd-yyyy_HH-mm-ss").format(new Date().getTime());
	static boolean bResult;
	static String error_message;
	int currentRow =0;
	static String res ;
	static int noOfFiles = 0;
	static String directoryName = "../Demo_Test/ExcelFilesFolder";
	String summaryReport = "";
	String testCaseReport = "";	
	static SendMail mail = new SendMail();


	/**
	 * This method is used to run test cases on the basis of these ClassName,their corresponding MethodName and InputArgs.
	 * MethodName is runReflectionMethod.
	 * Parameters passed are ClassName,MethodName,InputArgs.
	 */

	public static String runReflectionMethod(String strClassName, String strMethodName,
			Object... inputArgs) {

		Class<?> params[] = new Class[inputArgs.length];

		for (int i = 0; i < inputArgs.length; i++) {
			if (inputArgs[i] instanceof String) {
				params[i] = String.class;
			}
		}
		try {
			Class<?> cls = Class.forName(strClassName);
			Object _instance = cls.newInstance();
			Method myMethod = cls.getDeclaredMethod(strMethodName, params);
			res= (String) myMethod.invoke(_instance, inputArgs);

		} catch (ClassNotFoundException e) {
			log.info(strClassName + ":- Class not found%n");
			System.err.format(strClassName + ":- Class not found%n");
		} catch (IllegalArgumentException e) {
			log.info("Method invoked with wrong number of arguments%n");
			System.err.format("Method invoked with wrong number of arguments%n");
		} catch (NoSuchMethodException e) {
			log.info("In Class " + strClassName + "::" + strMethodName
					+ ":- method does not exists%n");
			System.err.format("In Class " + strClassName + "::" + strMethodName
					+ ":- method does not exists%n");
		} catch (InvocationTargetException e) {
			log.info("Exception thrown by an invoked method%n");
			System.err.format("Exception thrown by an invoked method%n");
		} catch (IllegalAccessException e) {
			log.info("Can not access a member of class with modifiers private%n");
			System.err.format("Can not access a member of class with modifiers private%n");
			e.printStackTrace();
		} catch (InstantiationException e) {
			log.info("Object cannot be instantiated for the specified class using the newInstance method%n");
			System.err.format("Object cannot be instantiated for the specified class using the newInstance method%n");
		}
		return res;
	}

	/**
	 * Program execution starts from this main method only.
	 * MethodName is main.
	 * This method is fetching method name and their corresponding data from the excel.
	 * Running TestCases on the basis of  ClassName, MethodName and InputArgs.
	 * @throws IOException 
	 * @throws BiffException 
	 * @throws WriteException 
	 */

	/**
	 * @param args
	 * @throws Exception 
	 */

	@Test
	public static void test() throws Exception {

		PropertyConfigurator.configure(log4jConfPath);	
		ReadExcel excelSheet = new ReadExcel();
		KeyWords key = new KeyWords();		
		File[] fList=excelSheet.listf(directoryName);	
		
		
		
		
		for (File fileLists : fList) {
			StringBuilder htmlBuilder = new StringBuilder();
			StringBuilder testCase = new StringBuilder();
			StringBuilder summaryResult = new StringBuilder();
			htmlBuilder.append("<html>");
			htmlBuilder.append("<head><!-- Latest compiled and minified CSS --><style>body{max-width: 90%;margin: 1em auto !important;}th:first-child {width: 50%;}th{width: 25%;}.table{border-top: 2px solid #ddd;border-bottom: 2px solid #ddd; margin: 0 0 1em;}</style><link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css'><!-- Optional theme --><link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css'><!-- Latest compiled and minified JavaScript --><script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js'></script></head>");
			
			htmlBuilder.append("<body><div class='panel panel-default'><div class='panel-heading'><h2> Automation Report</h2><h4><p align='right'>"+current_DateTime+"</p></h4></div><br>");
			
			String filePath = fileLists.getAbsolutePath();
			System.out.println("filePath"+filePath);
			int sheetNumber= excelSheet.numberOfSheets(filePath); 	
			String outputFile ="../Demo_Test/Reports/ExcelReport/testCases-OutPut-"+current_DateTime +"--"+ fileLists.getName();			
			String outputHTMLFile="../Demo_Test/Reports/HTML_Report/HTML-AppAutomationReport---"+ fileLists.getName()+current_DateTime+".html";

			System.out.println("<<------Number of sheets in the input excelfile----->>" + sheetNumber);

			File file = new File(outputFile);
			WorkbookSettings wbSettings = new WorkbookSettings();
			wbSettings.setLocale(new Locale("en", "EN"));
			WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
			// Creating an instance of Summary Sheet
			int j = 0;
			workbook.createSheet("Summary Sheet", j);
			WritableSheet summarySheetName = workbook.getSheet(j);
			excelSheet.createLabel(summarySheetName);
			excelSheet.createContent(summarySheetName, "Summary for the test", j, 0);
			j++;
			j++;
			excelSheet.createContent(summarySheetName, "Total Pass", j, 0);
			excelSheet.createContent(summarySheetName, "Total Fail", j, 2);
			j++;
			excelSheet.createContent(summarySheetName, "Test case Name", j, 0);
			excelSheet.createContent(summarySheetName, "PASS", j, 1);
			excelSheet.createContent(summarySheetName, "FAIL", j, 2);
			int passCount = 0;
			int failCount = 0;
			j++;
			//Initialising the Summary of tescases result
			summaryResult.append("<div class='panel-heading'><h4>Summary Report</h4></div>");
			summaryResult.append("<table class='table'><thead><th><b>TestCaseName</b></th><th>Pass Count</th><th>Fail Count</th></thead>");

			for(int i=0;i<sheetNumber;i++){
				excelSheet.openSheet(filePath, i);
				String currentSheetName = excelSheet.currentSheetName(filePath, i);
				workbook.createSheet(currentSheetName,i);			
				WritableSheet excelSheet2 = workbook.getSheet(i);
				excelSheet.createLabel(excelSheet2);
				System.out.println("**********Starting execution of Sheet Name***************\t" + currentSheetName);
				int numberOfRows =excelSheet.getRowCount();
				System.out.println("<<---------number of rows ----------->>" + numberOfRows);
				excelSheet.createContent(excelSheet2, "Pass", 0,0);
				excelSheet.createContent(excelSheet2, "Fail", 0,2);
				excelSheet.createContent(excelSheet2, "Test Steps", 1,0);
				excelSheet.createContent(excelSheet2, "Status", 1,1);
				excelSheet.createContent(excelSheet2, "Reasons", 1,2);
				int pass_count = 0;
				int fail_count = 0;				
				
				testCase.append("<table class='table'><tr><thead><th><b>"+currentSheetName+"</b></th><th></th><th></th></tr></thead><tr><thead><th><b>TestSteps</b></th><th><b>Status</b></th><th><b>Reasons</b></th></tr></thead>");
				
				for (int row = 1; row < excelSheet.getRowCount(); row++) {
					List<Object> myParamList = new ArrayList<Object>();
					String methodName = excelSheet.getValueFromCell(0, row);
					for (int col = 1; col < excelSheet.getColumnCount(); col++) {
						if (!excelSheet.getValueFromCell(col, row).isEmpty()
								& !excelSheet.getValueFromCell(col, row).equals("null")) {
							myParamList.add(excelSheet.getValueFromCell(col, row));
						}
					}

					Object[] paramListObject = new String[myParamList.size()];
					paramListObject = myParamList.toArray(paramListObject);

					System.out.println("-------------------------------------------------------------------------------");
					
					log.info("methodName=="+methodName);
					System.out.println("methodName=="+methodName);

					res = KeyWordExecutionTest.runReflectionMethod("com.keyword.sample.KeyWords",
							methodName, paramListObject);

					

					System.out.println(" <----Result --->"+" boolean flag value returned after method is invoked" + "->" + bResult);
					System.out.println(" <<------Printing number of current row-------->>\t" + row);
					Boolean bResult = Boolean.parseBoolean(res.split(",")[0]);
					String error_message = (res.split(",")[1]);					
					excelSheet.createContent(excelSheet2, methodName, row+2, 0);

					if (bResult){
						excelSheet.createContent(excelSheet2, "Pass", row+2,1);                        
						pass_count++;
						testCase.append("<tr><td>"+methodName+"</td><td>Pass</td><td></td></tr>");
					} else {
						excelSheet.createContent(excelSheet2, "Fail", row+2, 1);
						excelSheet.createContent(excelSheet2, error_message, row+2, 2);
						fail_count++;
						testCase.append("<tr><td>"+methodName+"</td><td>Fail</td><td>"+error_message+"</td></tr>");						
					}
				}
				
				testCase.append("<tr><td><b>TestCaseName</b></td><td><b>Pass Count</b></td><td><b>Fail Count<b></td></tr>");	
				testCase.append("<tr><td>"+currentSheetName+"</td><td>"+pass_count+"</td><td>"+fail_count+"</td></tr>");		
				testCase.append("</table>");
				testCase.append("<br></br>");
				excelSheet.createContent(excelSheet2, Integer.toString(pass_count), 0,1);
				excelSheet.createContent(excelSheet2, Integer.toString(fail_count), 0,3);
				excelSheet.createContent(summarySheetName, currentSheetName, j, 0);
				excelSheet.createContent(summarySheetName, Integer.toString(pass_count), j, 1);
				excelSheet.createContent(summarySheetName, Integer.toString(fail_count), j, 2);
                

			
				summaryResult.append("<tr><td>"+currentSheetName+"</td><td>"+pass_count+"</td><td>"+fail_count+"</td></tr>");		
				
				passCount += pass_count;
				failCount += fail_count;				
				j++;
			}
			summaryResult.append("</table>");
			summaryResult.append("<br></br>");
			
			
			summaryResult.append("<table class='table'><thead><th><b>Total</b></th><th>Pass</th><th>Fail</th></thead><tr><td>TotalPassFailCount</td><td>"+passCount+"</td><td>"+failCount+"</td></tr></table>");
		
			
			excelSheet.createContent(summarySheetName, Integer.toString(passCount),2, 1);
			excelSheet.createContent(summarySheetName, Integer.toString(failCount),2, 3);
			htmlBuilder.append(summaryResult);
			htmlBuilder.append("<br></br>");
			htmlBuilder.append("<div class='panel-heading'><h4>Advanced Report</h4></div><br>");
			htmlBuilder.append(testCase);
			htmlBuilder.append("</body></html>\n");
			FileWriter writer = new FileWriter(outputHTMLFile);			
			writer.write(htmlBuilder.toString());
			writer.close();
			System.out.println("HTML Reporting is Done");
			workbook.write();
			workbook.close();
			


		}

		mail.sendEmail();

	}

}