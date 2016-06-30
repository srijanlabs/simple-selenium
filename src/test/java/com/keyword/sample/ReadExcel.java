package com.keyword.sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

import org.apache.log4j.Logger;

import java.io.File;


import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.Pattern;
import jxl.format.UnderlineStyle;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import jxl.Sheet;

import jxl.read.biff.BiffException;


/**
 * @author Meenakshi Rathore
 * Class Name is KeyWords.
 * This class includes all keywords(method names).
 * On the basis of these keywords,their corresponding locator type,locator value and test data will be fetched.
 */

public class ReadExcel {


	static Logger log = Logger.getLogger(ReadExcel.class.getName());

	Workbook wbWorkbook;
	Sheet shSheet;
	private  WritableCellFormat timesBoldUnderline;
	private  WritableCellFormat times;	
	private static String outputFile;	
	public static String SheetNumber =null;
	public static String currentSheetName =null;

	public void opennSheet(String filePath) {
		FileInputStream fs;
		try {
			fs = new FileInputStream(filePath);
			wbWorkbook = Workbook.getWorkbook(fs);			
			shSheet = wbWorkbook.getSheet(0);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public  void openSheet(String filePath,int SheetNumber) {

		FileInputStream fs;
		try {
			fs = new FileInputStream(filePath);
			wbWorkbook = Workbook.getWorkbook(fs);			
			String [] sheetNames = wbWorkbook.getSheetNames();	
			log.info("Starting execution of Sheet Name[" + SheetNumber + "]");
			//System.out.println("Starting execution of Sheet Name[" + SheetNumber + "]");
			int sheetNumber = wbWorkbook.getNumberOfSheets();
			System.out.println("Number of sheets in this workbook = "+sheetNumber);		
			shSheet = wbWorkbook.getSheet(SheetNumber);	


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
	public  int numberOfSheets(String filePath){

		int sheetNumber = 0;		
		FileInputStream fs;
		try {
			fs = new FileInputStream(filePath);
			try {
				wbWorkbook = Workbook.getWorkbook(fs);
				String [] sheetNames = wbWorkbook.getSheetNames();
				sheetNumber = wbWorkbook.getNumberOfSheets();
				log.info("Number of sheets in this workbook = "+sheetNumber);
				//	System.out.println("Number of sheets in this workbook = "+sheetNumber);								
			} catch (BiffException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sheetNumber;

	}

	public  String currentSheetName(String filePath,int SheetNumber){			
		FileInputStream fs;
		try {
			fs = new FileInputStream(filePath);
			wbWorkbook = Workbook.getWorkbook(fs);			
			String [] sheetNames = wbWorkbook.getSheetNames();
			currentSheetName =sheetNames[SheetNumber];
			log.info("Starting execution of Sheet Name[" + SheetNumber + "] = " + sheetNames[SheetNumber]);
			//System.out.println("Starting execution of Sheet Name[" + SheetNumber + "] = " + sheetNames[SheetNumber]);
			//	int sheetNumber = wbWorkbook.getNumberOfSheets();
			//System.out.println("Number of sheets in this workbook = "+sheetNumber);		
			//shSheet = wbWorkbook.getSheet(SheetNumber);	


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return currentSheetName;


	}



	public  String getValueFromCell(int iColNumber, int iRowNumber) {
		return shSheet.getCell(iColNumber, iRowNumber).getContents().trim();
	}

	public   int getRowCount() {
		return shSheet.getRows();
	}

	public  int getColumnCount() {
		return shSheet.getColumns();
	}

	void createLabel(WritableSheet sheet)
			throws WriteException {
		// Lets create a times font
		WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
		// Define the cell format
		times = new WritableCellFormat(times10pt);
		// Lets automatically wrap the cells
		times.setWrap(true);

		// create create a bold font with unterlines
		WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false,
				UnderlineStyle.SINGLE);
		timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
		// Lets automatically wrap the cells
		timesBoldUnderline.setWrap(true);

		CellView cv = new CellView();
		cv.setFormat(times);
		cv.setFormat(timesBoldUnderline);
		cv.setSize(20);


	}

	void createContent_bak(WritableSheet sheet,Boolean bResult,String currentSheetName,int currentRow,String methodName,String error_message) throws WriteException,RowsExceededException {	   

		System.out.println("Adding <<---Method Name and Pass /Fail Label---->> for "+currentSheetName+"\t"+"Sheet");
		if(bResult ==  true){
			addLabel(sheet, 1, currentRow, " Pass ",getCellFormat(Colour.GREEN, Pattern.PATTERN14));
			addLabel(sheet, 0, currentRow, methodName,getCellFormat(Colour.BLUE, Pattern.PATTERN14));
			//addLabel(sheet, 2, currentRow, error_message,getCellFormat(Colour.GREEN, Pattern.PATTERN14));

		}
		else{
			addLabel(sheet, 1, currentRow, " Fail",getCellFormat(Colour.RED, Pattern.PATTERN14));
			addLabel(sheet, 0, currentRow, methodName,getCellFormat(Colour.BLUE, Pattern.PATTERN14));
			addLabel(sheet, 2, currentRow, error_message,getCellFormat(Colour.RED, Pattern.PATTERN14));
		}

	}
	void createContent(WritableSheet sheet,String cellValue,int currentRow, int currentCol) throws WriteException,RowsExceededException {	   
		System.out.println("Adding Label "+cellValue+"\t"+" to Sheet");
		switch (cellValue) {
			case "Pass" :
				addLabel(sheet, currentCol, currentRow, cellValue,getCellFormat(Colour.GREEN, Pattern.PATTERN14));
				break;
			case "Fail" :
				addLabel(sheet, currentCol, currentRow, cellValue,getCellFormat(Colour.RED, Pattern.PATTERN14));
				break;
			default:
				addLabel(sheet, currentCol, currentRow, cellValue, getCellFormat(Colour.BLUE, Pattern.PATTERN14));
				break;
		}
		
	}
	
	
	
	void addSummaryContent(WritableSheet sheet,String currentSheetName,int row,String passCount,String failCount) throws WriteException,RowsExceededException {	   

		System.out.println("Adding <<---Number of pass and fail count---->> for "+currentSheetName+"\t"+"Sheet");		
			
			addSummaryLabel(sheet, 2, row,failCount,getCellFormat(Colour.RED, Pattern.PATTERN14));
			addSummaryLabel(sheet, 1, row,passCount,getCellFormat(Colour.GREEN, Pattern.PATTERN14));
			addSummaryLabel(sheet, 0, row,currentSheetName,getCellFormat(Colour.BLUE, Pattern.PATTERN14));	

	}

	private  void addSummaryLabel(WritableSheet sheet, int column,int row,String s,WritableCellFormat cellFormat)
			throws WriteException, RowsExceededException {
		Label label;
		label = new Label(column,row,s, cellFormat);
		sheet.addCell(label);
	}



	private  void addLabel(WritableSheet sheet, int column, int row, String s,WritableCellFormat cellFormat)
			throws WriteException, RowsExceededException {
		Label label;
		label = new Label(column, row, s, cellFormat);
		sheet.addCell(label);
	}


	WritableCellFormat getCellFormat(Colour colour, Pattern pattern) throws WriteException
	{
		WritableFont cellFont = new WritableFont(WritableFont.TIMES, 10);
		WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
		cellFont.setColour(colour);			
		cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		cellFormat.setWrap(true);
		return cellFormat;
	}

	public int noOfFilesInDirectory(File directory) {
	    int noOfFiles = 0;
	    try{
	    for (File file : directory.listFiles()) {
	        if (file.isFile()) {
	            noOfFiles++;
	        }
	        if (file.isDirectory()) {
	            noOfFiles += noOfFilesInDirectory(file);
	        }
	    }
	 
	}
	    catch(Exception e){
	    	e.printStackTrace();
	    }
	    return noOfFiles;
	}
	
	
	
	
	public static File[] listf(String directoryName) {

	    // .............list file
	    File directory = new File(directoryName);

	    // get all the files from a directory
	    File[] fList = directory.listFiles();

//	    for (File file : fList) {
//	        if (file.isFile()) {
//	            System.out.println(file.getAbsolutePath());
//	        } else if (file.isDirectory()) {
//	            listf(file.getAbsolutePath());
//	        }
//	    }
	    //System.out.println(fList);
	    return fList;
	}
}

