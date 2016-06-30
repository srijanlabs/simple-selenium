package com.keyword.sample;

import java.io.FileWriter;
import java.io.IOException;


public class Testing {
public static void main(String args[]) throws IOException{
	//public void callTest() throws IOException{
    String methodName = "openBrowser";
    String methodName2 = "isTextPresent";
    String currentSheetName = "54726";
	StringBuilder htmlBuilder = new StringBuilder();
	htmlBuilder.append("<html><body>");
	htmlBuilder.append("<h1><caption>Apps Automation Report</caption></h1>");
	htmlBuilder.append("<table border='1' style='width:100%'>");	
	htmlBuilder.append("<tr><td>TestSteps</td>");
	htmlBuilder.append("<td>Status</td>");
	htmlBuilder.append("<td>Reasons</td></tr>");
	//htmlBuilder.append(methodName);
//	htmlBuilder.append("methodName");
	htmlBuilder.append("<td>"+methodName+"</td>");	
	htmlBuilder.append("<td>Pass</td>");
	htmlBuilder.append("<td>Element is found</td></tr>");
	htmlBuilder.append("<td>"+methodName2+"</td>");	
	htmlBuilder.append("<td>Fail</td>");
	htmlBuilder.append("<td>Element is not found</td></tr>");
	htmlBuilder.append("</body></html>\n");
	

	FileWriter writer = new FileWriter(System.getProperty("user.home") + "/hello.html");
	writer.write(htmlBuilder.toString());
	writer.close();
	System.out.println("Done");
}
}