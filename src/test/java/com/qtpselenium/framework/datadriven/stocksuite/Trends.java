package com.qtpselenium.framework.datadriven.stocksuite;

import java.net.MalformedURLException;
import java.util.Hashtable;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import com.qtpselenium.framework.datadriven.TestBase;
import com.qtpselenium.framework.datadriven.util.Constants;
import com.qtpselenium.framework.datadriven.util.TestDataProvider;


public class Trends extends TestBase {
	
	
	@Test(dataProviderClass=TestDataProvider.class, dataProvider="StocksuiteDataProvider")
		public void trends(Hashtable<String, String> table) throws MalformedURLException{
			
		validateRunmodes("trends", Constants.SECOND_SUITE_, table.get("Runmode"));
			doDefaultLogin(table.get(Constants.BROWSER_COL_));
			
			//trends  html/body/div[1]/div[5]/div[2]/div[1]/form/div[3]/select/option[4]
			//table.get("Currency1")+"]")
			
			click("TrendsLink_xpath");
			click("ViewAllForexRates_xpath");
			clear("Amount_xpath");
			input("Amount_xpath", table.get("Amount"));
			driver.findElement(By.xpath("html/body/div[1]/div[5]/div[2]/div[1]/form/div[3]/select/option["+table.get("Currency1")+"]")).click();
			//driver.findElement(By.xpath("html/body/div[1]/div[5]/div[2]/div[1]/form/div[3]/select/option["+table.get("Currency2")+"]")).click();
			//click("CurrencyButton_xpath");
		}
			
	@AfterMethod
	public void close(){
		quit();
	}
	
	
}
