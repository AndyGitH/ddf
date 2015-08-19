package com.qtpselenium.framework.datadriven;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.qtpselenium.framework.datadriven.util.Constants;
import com.qtpselenium.framework.datadriven.util.Xls_Reader;


public class CustomListener extends TestListenerAdapter implements IInvokedMethodListener, ISuiteListener{
	
	public static Hashtable<String,String> resultTable ;
	public static ArrayList<String> keys; //for order
	public static String resultFolderName;
	public static String resultFilePath;
	String path="C:\\Users\\asagoe\\workspace\\Core_WebDriver_DataDriven_Framework\\";

	
	public void onTestFailure(ITestResult tr){
		report(tr.getName(), tr.getThrowable().getMessage());
		//TestBase.APP_LOGS.debug("Fail - " + tr.getName());
	}
	
	public void onTestSkipped(ITestResult tr) {
		report(tr.getName(), tr.getThrowable().getMessage());
		//TestBase.APP_LOGS.debug("Skipped - " + tr.getName());

	}
	
	public void onTestSuccess(ITestResult tr){
		report(tr.getName(), "PASS");
		//TestBase.APP_LOGS.debug("Success - " + tr.getName());

	}
	
	public void afterInvocation(IInvokedMethod method, ITestResult result) {
		
		
	}
 
	public void beforeInvocation(IInvokedMethod arg0, ITestResult test) {
	}

	@Override
	public void onStart(ISuite suite) {
		System.out.println("Starting suite "+ suite.getName());
		if(resultTable==null){
		 resultTable  = new Hashtable<String,String>();
		 keys = new ArrayList<String>(); //initialise the list, when starting
		}
		
		if(resultFolderName==null){
			Date d = new Date();
			resultFolderName= d.toString().replace(":", "_");
			File f = new File(System.getProperty("user.dir")+"//target//reports//"+resultFolderName);
			
			//make the directory
			f.mkdir();
			resultFilePath=System.getProperty("user.dir")+"\\target\\reports\\"+resultFolderName+"\\Report.xlsx";
			File srcFile = new File(System.getProperty("user.dir")+"//target//reports//ReportTemplate.xlsx");
			File destFile = new File(resultFilePath);
			try {
				//copy file to the new destination
				FileUtils.copyFile(srcFile, destFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	@Override
	public void onFinish(ISuite suite) {
		if(resultTable !=null){
			System.out.println("Finishing suite "+ suite.getName());
			System.out.println(resultTable);
			
			
			//path+resultFolderName+"\\Report.xlsx"
			if(!suite.equals("My Project")){
				Xls_Reader xls = new Xls_Reader(resultFilePath);
				xls.addSheet(suite.getName());
				
				xls.setCellData(suite.getName(), 0, 1, "Test Case");
				xls.setCellData(suite.getName(), 1, 1, "Result");
				
				for(int i=0;i<keys.size();i++){
					String key = keys.get(i);
					String value = resultTable.get(key);
					xls.setCellData(suite.getName(), 0, i+2, key);
					xls.setCellData(suite.getName(), 1, i+2, value);

				}
				
			}
			keys=null;
			resultTable=null;			
		}		
	}
	
	public void report(String name,String result){
		// test Iteration 1
		// test Iteration 2
		int iteration_number=1;
		while(resultTable.containsKey(name+" Iteration "+iteration_number)){
			iteration_number++;
		}
		keys.add(name+" Iteration "+iteration_number);
		resultTable.put(name+" Iteration "+iteration_number, result);
		/*if(resultFolderName==null){
			Date d=new Date();
			resultFolderName=d.toString().replace(" ", "_").replace(":", "_");
			 File f = new File(path+resultFolderName+"\\Report.xlsx");
			 f.mkdir();
			 File src = new File(path+"ReportTemplate.xlsx");
			 File dest = new File(path+resultFolderName+"\\Report.xlsx");
			  try {
				FileUtils.copyFile(src, dest);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}*/
		
	}
			
	

}
