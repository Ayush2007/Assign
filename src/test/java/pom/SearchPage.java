package pom;

import qa.Assignment.util.UtilityFunction;
import org.openqa.selenium.WebDriver;
import qa.Assignment.util.Report;
public class SearchPage {
	
	public boolean searchFlight(WebDriver driver) throws InterruptedException{
	
		UtilityFunction utilityFunction = new UtilityFunction();
		Report report = new Report();
		final String EXTENT_REPORT_PASS= "PASS";
		final String EXTENT_REPORT_FAIL= "FAIL";
		String fromStation = utilityFunction.readConfigurationFile("FromStation");
		String toStation = utilityFunction.readConfigurationFile("ToStation");
		
		utilityFunction.clickElement("ROUND_TRIP_RADIO_BUTTON");
		utilityFunction.enterValueInEditField("FROM_AIRPORT", fromStation);
		
		Thread.sleep(2000);
		utilityFunction.pressEnter("FROM_AIRPORT");
		Thread.sleep(1000);
		utilityFunction.enterValueInEditField("TO_AIRPORT", toStation);
		Thread.sleep(2000);
		utilityFunction.pressEnter("TO_AIRPORT");
		utilityFunction.waitForElementVisibility("DEPART_DATE");
		Thread.sleep(1000);
		utilityFunction.clickElement("DEPART_DATE");
	
		Thread.sleep(1000);
		if(utilityFunction.clickElement("RETURN_DATE")){
			report.reportEvent(driver,"Round trip booking", EXTENT_REPORT_PASS,"Able to enter to and from airports");
			Thread.sleep(2000);
			utilityFunction.clickElement("SEARCH_BUTTON");
			return true;
			
		
		}else
		{
			report.reportEvent(driver,"Round trip booking", EXTENT_REPORT_FAIL,"Not Able to enter to and from airports");
			return false;
		}	
		
	}
}
