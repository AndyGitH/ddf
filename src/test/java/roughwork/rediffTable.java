package roughwork;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class rediffTable {

	static WebDriver driver;
	public static void main(String[] args) {
		
		driver = new FirefoxDriver();
		driver.get("http://in.rediff.com/");
		driver.findElement(By.xpath("//*[@id='homewrapper']/div[5]/a[3]/div/u")).click();
		driver.findElement(By.xpath("//*[@id='wrapper']/div[2]/ul/li[2]/a")).click();
		driver.findElement(By.xpath("//*[@id='useremail']")).sendKeys("beast23@hotmail.com");
		driver.findElement(By.xpath("//*[@id='emailsubmit']")).click();
		
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id='userpass']")).sendKeys("Test123");
		
		if(driver.findElement(By.name("remember")).isSelected()){
			
			driver.findElement(By.xpath("//*[@id='rememberflag']")).click();		
		}
		
		driver.findElement(By.xpath("//*[@id='loginsubmit']")).click();
		
		/*String a = "//*[@id='equityRow14930086']/td[";
		String b = "]";*/
		
		
		/*List<WebElement> allNames = driver.findElements(By.xpath("//*[@id='equityRow14930086']/td[1]"));
		System.out.println("Total row = " + allNames.size());
		for(int i=1; i< allNames.size();i++)
			System.out.println("FOUND ------- " + allNames.get(i).getText());*/
		//html/body/div[3]/div[1]/div/div[3]/div[3]/table/tbody/tr[1]/td[1]
		int i = 0;
		while(isElementPresent("html/body/div[3]/div[1]/div/div[3]/div[3]/table/tbody/tr[1]/td["+i+"]")){
			
			String text = driver.findElement(By.xpath("html/body/div[3]/div[1]/div/div[3]/div[3]/table/tbody/tr[1]/td["+i+"]")).getText();
			System.out.println(text);
			i++;
		}
		
		System.out.println("Ends here");
		
	}
	
	public static boolean isElementPresent(String element_xpath){
		int count = driver.findElements(By.xpath(element_xpath)).size();
		if(count == 0)
			return false;
		else
			return true;
	}

}
