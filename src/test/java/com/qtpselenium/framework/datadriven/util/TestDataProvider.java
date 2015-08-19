package com.qtpselenium.framework.datadriven.util;

import java.lang.reflect.Method;
import org.testng.annotations.DataProvider;
import com.qtpselenium.framework.datadriven.TestBase;


/********************************************* PORTFOLIO DATA PROVIDER ****************************************/


public class TestDataProvider {

	@DataProvider(name="PortfolioDataProvider")
	public static Object[][] getDataPortfolioSuite(Method m){
		TestBase.init();
		Xls_Reader xls1 = new Xls_Reader(TestBase.prop.getProperty("xlsFileLocation")+Constants.PORTFOLIO_SUITE_+".xlsx");
		
		return Utility.getData(m.getName(), xls1);

	}
	
	
/********************************************* STOCK SUITE PROVIDER ******************************************/
	
	@DataProvider(name="StocksuiteDataProvider")
	public static Object[][] getDataStockSuite(Method m){
		TestBase.init();
		Xls_Reader xls2 = new Xls_Reader(TestBase.prop.getProperty("xlsFileLocation")+Constants.SECOND_SUITE_+".xlsx");
		
		return Utility.getData(m.getName(), xls2);
	}
}
