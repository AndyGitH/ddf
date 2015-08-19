package roughwork;

import com.qtpselenium.framework.datadriven.util.Constants;
import com.qtpselenium.framework.datadriven.util.Xls_Reader;

public class ReadingData {

	
	public static void main(String[] args) {
		
		Xls_Reader xls = new Xls_Reader("C:\\Users\\asagoe\\workspace\\DataDrivenFramework_TestNG\\SuiteA.xlsx");
		String testName = "test1";
		
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
		
		
		System.out.println("Total cols of data are - " + testCols);
		
		
		//total number of data
		for (int rNum=dataStartRowNum; rNum<(dataStartRowNum+testRows); rNum++){
			for(int cNum=0; cNum<testCols; cNum++){
				System.out.println(xls.getCellData(Constants.DATA_SHEET_, cNum, rNum));
			}
		}
		
	}

}
