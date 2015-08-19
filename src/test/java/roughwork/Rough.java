package roughwork;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Rough {


	public static void main(String[] args) {
		
		/*String text="Tata Motors Ltd.  (-77.07%)";
		//split will always return you an array
		String temp[] = text.split("\\(");
		System.out.println(temp[0].trim()); //remove blank space after .
		System.out.println(temp[1].split("\\)")[0].split("%")[0]);*/
		
		Date currentDate = new Date();
		
		String date="8/4/2013";
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date dateToBeSelected =null;
		try {
			 dateToBeSelected = formatter.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(currentDate);
		System.out.println(dateToBeSelected);//8/4/2013
		
		String month=new SimpleDateFormat("MMMM").format(dateToBeSelected);		
		Calendar cal = Calendar.getInstance();
	    cal.setTime(dateToBeSelected);
	    int year = cal.get(Calendar.YEAR);
	    
	    System.out.println(month +" "+year);
		
		//compare currentDate with dateToBeSelected - true
		System.out.println(currentDate.after(dateToBeSelected));

	}

}
