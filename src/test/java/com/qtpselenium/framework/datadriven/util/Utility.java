package com.qtpselenium.framework.datadriven.util;

import java.util.Hashtable;

public class Utility {
	//check is suite runnable
	
	public static boolean isSuiteRunnable(String suiteName, Xls_Reader xls){
		
		int rows = xls.getRowCount(Constants.SUITE_SHEET_);
		
		for(int rNum=2;rNum<=rows;rNum++){
			String data = xls.getCellData(Constants.SUITE_SHEET_, Constants.SUITENAME_COL_, rNum);
			System.out.println(data);
			
			if(data.equals(suiteName)){
				String runmode = xls.getCellData(Constants.SUITE_SHEET_, Constants.RUNMODE_COL_, rNum);
				if(runmode.equals(Constants.RUNMODE_YES_))
					return true;
				else
					return false;
			}
		}
		return false;
	}
	
	
	public static boolean isTestCaseRunnable(String testCase, Xls_Reader xls){
		
		//get total number of rows in the TestCases sheet
		int rows = xls.getRowCount(Constants.TESTCASES_SHEET_);
		
		for(int rNum=2;rNum<=rows;rNum++){
			//value of the test case in the sheet 
			String testNameXls = xls.getCellData(Constants.TESTCASES_SHEET_, Constants.TESTCASES_COL_, rNum);
			
			//check if the name of the test from xls file is equal to the name of test, which
			//you are passing in to the method.
			if(testNameXls.equalsIgnoreCase(testCase)){
				String runmode = xls.getCellData(Constants.TESTCASES_SHEET_, Constants.RUNMODE_COL_, rNum);
					
				if(runmode.equalsIgnoreCase(Constants.RUNMODE_YES_)){
					return true;			
				}
				else 
					return false;
			}
		}
		return false; //default
	}
	
	
	
	
	public static Object[][] getData(String testName, Xls_Reader xls){
		
		//total number of rows
		
				int rows = xls.getRowCount(Constants.DATA_SHEET_);
				System.out.println("Total rows - " + rows);
				
				//row number for test case
				int testCaseRowNum = 1;
				
				for(testCaseRowNum=1; testCaseRowNum<rows; testCaseRowNum++){
					String testCaseXls = xls.getCellData(Constants.DATA_SHEET_, 0, testCaseRowNum);
					
					if(testCaseXls.equalsIgnoreCase(testName))
						break;
				}
				System.out.println("Test starts from row Number " + testCaseRowNum);
				int dataStartRowNum = testCaseRowNum+2;
				int colStartRowNum = testCaseRowNum+1;
				
				//rows of data
				int testRows=1;
				
				while(!xls.getCellData(Constants.DATA_SHEET_, 0, dataStartRowNum+testRows).equals("")){
					testRows++;
				}
				System.out.println("Total rows of data are - " + testRows);
				
				
				//total number of cols of data
				int testCols = 0;
				
				while(!xls.getCellData(Constants.DATA_SHEET_, testCols, colStartRowNum).equals("")){
					testCols++;
				}
				
				Object[][] data = new Object[testRows][1];
				System.out.println("Total cols of data are - " + testCols);
				
				int r =0;
				//for every row, you need a new Hashtable
				for (int rNum=dataStartRowNum; rNum<(dataStartRowNum+testRows); rNum++){
					
					Hashtable<String, String> table = new Hashtable<String, String>();
					for(int cNum=0; cNum<testCols; cNum++){
						//System.out.println(xls.getCellData(Constants.DATA_SHEET_, cNum, rNum));
						//data[r][cNum] =xls.getCellData(Constants.DATA_SHEET_, cNum, rNum);
						
						table.put(xls.getCellData(Constants.DATA_SHEET_, cNum, colStartRowNum), xls.getCellData(Constants.DATA_SHEET_, cNum, rNum));
					}
					//put the Hashtable inside the data array (every row)
					data[r][0]=table;
					r++;
				}
				//r cNum
				//0,0 0,1
				//1,0 1,1
				return data;
	}
}
