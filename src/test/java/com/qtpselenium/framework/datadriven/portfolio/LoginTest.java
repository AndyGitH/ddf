package com.qtpselenium.framework.datadriven.portfolio;

import java.net.MalformedURLException;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.qtpselenium.framework.datadriven.TestBase;
import com.qtpselenium.framework.datadriven.util.Constants;
import com.qtpselenium.framework.datadriven.util.TestDataProvider;
import com.qtpselenium.framework.datadriven.util.Utility;
import com.qtpselenium.framework.datadriven.util.Xls_Reader;

public class LoginTest extends TestBase{
	
	@Test(dataProviderClass=TestDataProvider.class, dataProvider="PortfolioDataProvider")
	public static void logintest(Hashtable<String, String> table) throws MalformedURLException{
		APP_LOGS.debug("Executing Login Test");
		validateRunmodes("LoginTest", Constants.PORTFOLIO_SUITE_, table.get("Runmode"));
		
		//call login from TestBase
		doLogin(table.get(Constants.BROWSER_COL_), table.get(Constants.USERNAME_COL_), table.get(Constants.PASSWORD_COL_));
		
		
		//Expected Result column
		boolean signoutLink = isElementPresent("signout_Xpath");
		
		if(!(table.get(Constants.EXPECTEDRESULT_COL_).equals("SUCCESSFUL")) && signoutLink)
			Assert.fail("Login Unsuccessful");
		else if(table.get(Constants.EXPECTEDRESULT_COL_).equals("FAILURE")){
			
			if(signoutLink){
				Assert.fail("Error Incorrect Login details");
			}
		}
				
	}
	
	
	/*
	@DataProvider	
	public Object[][]getData(){
		Xls_Reader xls1 = new Xls_Reader(prop.getProperty("xlsFileLocation")+"SuiteA.xlsx");
		
		return Utility.getData("Test1", xls1);
	}
	*/
}
