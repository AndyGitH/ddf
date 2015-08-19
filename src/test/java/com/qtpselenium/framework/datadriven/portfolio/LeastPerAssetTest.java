package com.qtpselenium.framework.datadriven.portfolio;

import java.net.MalformedURLException;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qtpselenium.framework.datadriven.TestBase;
import com.qtpselenium.framework.datadriven.util.Constants;
import com.qtpselenium.framework.datadriven.util.TestDataProvider;
import com.qtpselenium.framework.datadriven.util.Utility;
import com.qtpselenium.framework.datadriven.util.Xls_Reader;

public class LeastPerAssetTest extends TestBase {
	
	@Test(dataProviderClass=TestDataProvider.class, dataProvider="PortfolioDataProvider")
		public void leastPerAssetTest(Hashtable<String, String> table) throws MalformedURLException{
		APP_LOGS.debug("Executing LeastPerAssetTest");
			validateRunmodes("LeastPerAssetTest", Constants.PORTFOLIO_SUITE_, table.get(Constants.RUNMODE_COL_));
			
			//Default login from TestBase
			doDefaultLogin(table.get(Constants.BROWSER_COL_));
			//HW verify you have logged into the website here
			
			APP_LOGS.debug("Login sucessful!!!!!!!!!");
			
			
			String leasetpertext=getText("leastPerAsset_xpath");
			String temp[] = leasetpertext.split("\\(");
			String compName = temp[0].trim();
			String percentageChange = temp[1].split("\\)")[0].split("%")[0];
			
			
			APP_LOGS.debug(percentageChange);
			//Tata Motors Ltd is a link in table, need to use isElement present
			Assert.assertTrue(isElementPresent("//a[text()='"+compName+"']"), "Lease per asset company not found in table "+ compName);
			Assert.assertTrue(isElementPresent("//td/span[text()='"+percentageChange+"']"), "Least % not found");
			
			String x = driver.findElement(By.xpath("//td/span[text()='"+percentageChange+"']")).getText();
			System.out.println(x);
			
			//HW searching inside the table here
		}
	
	//for Grid, called after the Test has passed or failed
	@AfterMethod
	public void close(){
		quit();
	}
		
		
	/*	@DataProvider	
		public Object[][]getData(){
			Xls_Reader xls2 = new Xls_Reader("C:\\Users\\asagoe\\workspace\\DataDrivenFramework_TestNG\\SuiteA.xlsx");
			
			return Utility.getData("Test2", xls2);
		}*/
	
}
