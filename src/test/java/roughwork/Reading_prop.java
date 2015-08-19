package roughwork;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;



public class Reading_prop {

	public static void main(String[] args){
		
		String path = System.getProperty("user.dir")+"\\src\\test\\resources\\project.properties";

		Properties prop = new Properties();
		try {
			FileInputStream fs = new FileInputStream(path);
			prop.load(fs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(prop.getProperty("fileLocation"));
	}

}
