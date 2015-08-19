package com.qtpselenium.framework.datadriven.stocksuite;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.qtpselenium.framework.datadriven.TestBase;
import com.qtpselenium.framework.datadriven.util.Constants;
import com.qtpselenium.framework.datadriven.util.TestDataProvider;
import com.qtpselenium.framework.datadriven.util.Utility;
import com.qtpselenium.framework.datadriven.util.Xls_Reader;

public class AddStock extends TestBase{

	
	@Test(dataProviderClass=TestDataProvider.class, dataProvider="StocksuiteDataProvider")
	public void addStock(Hashtable<String, String> table) throws MalformedURLException, InterruptedException{
		
		validateRunmodes("addStock", Constants.SECOND_SUITE_, table.get("Runmode"));
		doDefaultLogin(table.get(Constants.BROWSER_COL_));
		
		//highlight the button, then fire the send keys to click
		click("addStockButton_xpath");
		driver.findElement(By.xpath(prop.getProperty("addStockButton_xpath"))).sendKeys(Keys.ENTER);
		
		
		//Calendar logic
		Thread.sleep(5000);
		click("calendar_xpath");
		String date=table.get("PurchaseDate");
		
		Date currentDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date dateToBeSelected =null;
		try {
			//covert String to a Date Object
			 dateToBeSelected = formatter.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//extract month String, int year and day in Date to be selected
		String month = new SimpleDateFormat("MMMM").format(dateToBeSelected);		
		Calendar cal = Calendar.getInstance();
	    cal.setTime(dateToBeSelected);
	    
	    int year = cal.get(Calendar.YEAR);
	    int day = cal.get(Calendar.DAY_OF_MONTH);
	    
	    String month_yearExpected = month+" "+year;
	    
	    	while(true){
			
				String month_yearDisplayed = getText("monthAndYearText_xpath");//get month and year text
				
				if(month_yearDisplayed.equals(month_yearExpected))
					break; // correct month, then clicks on the day
				
				if(currentDate.after(dateToBeSelected))
					click("calBack_xpath");
				else
					click("calFront_xpath");
		}
	    	//click on date in the calendar  
	    driver.findElement(By.xpath("//td[text()='"+day+"']")).click();
	    
	    //Fields
	    input("StockName_xpath", table.get("Stock Name"));
		input("quantity_xpath", table.get("Quantity"));
		input("price_xpath", table.get("Price"));
		click("addingStock_xpath");	
	}
	
	@AfterMethod
	public void close(){
		quit();
	}
	
}
