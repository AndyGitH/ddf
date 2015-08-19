package com.qtpselenium.framework.datadriven;

import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import com.qtpselenium.framework.datadriven.util.Constants;
import com.qtpselenium.framework.datadriven.util.Utility;
import com.qtpselenium.framework.datadriven.util.Xls_Reader;

public class TestBase {
	
	public static Properties prop;
	public static Logger APP_LOGS;
	public static WebDriver driver =null;
	
	
	public void initLogs(Class<?> class1){

		FileAppender appender = new FileAppender();
		// configure the appender here, with file location, etc
		appender.setFile(System.getProperty("user.dir")+"//target//reports//"+CustomListener.resultFolderName+"//"+class1.getName()+".log");
		appender.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"));
		appender.setAppend(false);
		appender.activateOptions();

		APP_LOGS = Logger.getLogger(class1);
		APP_LOGS.setLevel(Level.DEBUG);
		APP_LOGS.addAppender(appender);
	}
	
	//initialise Properties file
	public static void init(){
		APP_LOGS = Logger.getLogger("devpinoyLogger");
		if(prop == null){
			String path = System.getProperty("user.dir")+"\\src\\test\\resources\\project.properties";

			prop = new Properties();
			try {
				FileInputStream fs = new FileInputStream(path);
				prop.load(fs);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
	}

	
	
	public static void validateRunmodes(String testName, String suiteName, String dataRunmode){
		APP_LOGS.debug("validating Runmodes in " + testName + "in suite" + suiteName);
		init(); //prop initialised
	boolean suiteRunmode =Utility.isSuiteRunnable(suiteName, new Xls_Reader(prop.getProperty("xlsFileLocation")+"Suite.xlsx"));
	boolean testRunmode=Utility.isTestCaseRunnable(testName, new Xls_Reader(prop.getProperty("xlsFileLocation")+suiteName+".xlsx"));
	boolean dataSetRunmode=false;
	
		if(dataRunmode.equals(Constants.RUNMODE_YES_))
			dataSetRunmode=true;
		//If all three fail
		if(!(suiteRunmode && testRunmode && dataSetRunmode)){
			APP_LOGS.debug("Skipping the test "+testName+" inside the suite "+ suiteName);
			throw new SkipException("Skipping the test "+testName+" inside the suite "+ suiteName);
		}
	}
	
	
	/****************************************** GENERIC FUNCTIONS *********************************************/
	
	//OPENS THE PARTICULAR BROWSER, PASSING THE BROWSER NAME
	public static void openBrowser(String browserName){
		
	 /* if(browserName.equals("Mozilla"))
			driver = new FirefoxDriver();
		else if(browserName.equals("Chrome"))
			System.setProperty("webdriver.chrome.driver", prop.getProperty("chromedriverexe"));//prop already initialised
		  else if(browserName.equals("IE"))
				System.setProperty("webdriver.ie.driver", prop.getProperty("IEdriverexe"));*/
		
		
		
		//give the URL of Hub
				/*if(browserName.equals("iexplore")){
							cap = DesiredCapabilities.internetExplorer();*/
	  try{
			DesiredCapabilities cap = new DesiredCapabilities();
			if(browserName.equals("Mozilla")){
				cap.setBrowserName("firefox");
			}else if(browserName.equals("Chrome")){
				cap.setBrowserName("chrome");
			}else if(browserName.equals("iexplore")){
				cap.setBrowserName("ie");
			}
			cap.setPlatform(Platform.ANY);
			try {
				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),cap);
			} catch (MalformedURLException e) {
				
				e.printStackTrace();
			}
			
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			}catch(Exception e){
				Assert.fail("Not able to open browser - "+e.getMessage());
			}
	  
				
		/*driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);*/
	}
	
	
	
	//PASSING URL, NAVIGATE TO THAT PARTICULAR URL
	public static void navigate(String URLKey){
		driver.get(prop.getProperty(URLKey));
	}
	
	
	
	
	
	//PASSING THE OBJECT KEY YOU WANT TO CLICK ON
	//xpath, id, name, type, classname in project.propertie file
	public static void click(String identifier){
		try{
			if(identifier.endsWith("_xpath"))
				driver.findElement(By.xpath(prop.getProperty(identifier))).click();
			else if(identifier.endsWith("_id"))
				driver.findElement(By.id(prop.getProperty(identifier))).click();
				else if(identifier.endsWith("_name"))
					driver.findElement(By.name(prop.getProperty(identifier))).click();
		}catch(NoSuchElementException e){
			
			Assert.fail("Error Element Not Found - " + identifier);
		}
		
	}
	
	
	
	
	//PASSING XPATH AND DATA FROM TABLE XLSX SHEET
	public static void input(String identifier, String data){
	try{
		if(identifier.endsWith("_xpath"))
			driver.findElement(By.xpath(prop.getProperty(identifier))).sendKeys(data);
		else if(identifier.endsWith("_id"))
			driver.findElement(By.id(prop.getProperty(identifier))).sendKeys(data);
			else if(identifier.endsWith("_name"))
				driver.findElement(By.name(prop.getProperty(identifier))).sendKeys(data);
	}catch(NoSuchElementException e){
		Assert.fail("Element not found" + identifier);
	}
		
	}
	
	
	
	
	
	public static void clear(String identifier){
		
		if(identifier.endsWith("_xpath"))
			driver.findElement(By.xpath(prop.getProperty(identifier))).clear();
	}
	
	//Validation title on page
	public static boolean verifyTitle(String expectedTitleKey){
		String expectedTitle = prop.getProperty(expectedTitleKey);
		String actualTitle=driver.getTitle();
		
			if(expectedTitle.equals(actualTitle))
				return true;
			else
				return false;
	}
	
	//check if the Element is present
	public static boolean isElementPresent(String identifier){
	
		int size=0;
		if(identifier.endsWith("_xpath"))
			size = driver.findElements(By.xpath(prop.getProperty(identifier))).size();
		else if(identifier.endsWith("_id"))
			size = driver.findElements(By.id(prop.getProperty(identifier))).size();
			else if(identifier.endsWith("_name"))
				size = driver.findElements(By.name(prop.getProperty(identifier))).size();
			else
				size = driver.findElements(By.xpath(identifier)).size();
				
		if(size>0)
			 return true;
			else
			 return false;
		
	}
	
	
	public String getText(String identifier){
		String  text="";
		if(identifier.endsWith("_xpath"))
			text = driver.findElement(By.xpath(prop.getProperty(identifier))).getText();
		else if(identifier.endsWith("_id"))
			text = driver.findElement(By.id(prop.getProperty(identifier))).getText();
		else if(identifier.endsWith("_name"))
			text = driver.findElement(By.name(prop.getProperty(identifier))).getText();
		
		return text;
		
	}
	//if the driver is not initialised, then quit the driver
	public void quit(){
		if(driver!=null){
			driver.quit();
			driver=null;
		}
	}
	
	/************************************************ APPLICATION SPECIFIC FUNCTIONS 
	 * @throws MalformedURLException ***********************************************************/
	
	public static void doLogin(String browser, String username, String password) throws MalformedURLException{
		
		openBrowser(browser);
		navigate("testSiteURL");
		//Validate money Link
		Assert.assertTrue(isElementPresent("moneyLink_xpath"), "Error, Element not found - moneyLink_xpath");
		
		//money link
		click("moneyLink_xpath");
		
		//myportfolio link
		click("myPortfolio_xpath");
		
		//Page Title Validation
		verifyTitle("PortfolioPage");
		Assert.assertTrue(verifyTitle("PortfolioPage"), "Error, Title Mismatch, actual is" + driver.getTitle());
		
		//PASSING XPATH AND DATA FROM TABLE XLSX SHEET
		clear("LoginUsername_xpath");
		input("LoginUsername_xpath", username);
		click("continueLogin_xpath");
		input("Loginname_xpath", password);
		
		
		//remember checkbox
		if(driver.findElement(By.name("remember")).isSelected()){
			
			click("checkBox_xpath");		
		}
		
		click("LoginButton_xpath");
	}
	
	
	public static void doDefaultLogin(String browser) throws MalformedURLException{
			
			doLogin(browser, prop.getProperty("doDefaultUserName"), prop.getProperty("doDefaultpassword"));
	}
}
