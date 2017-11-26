package qa.Assignment.searchFlight;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pom.SearchPage;
import pom.TravallerDetailsPage;
import qa.Assignment.util.Report;
import qa.Assignment.util.UtilityFunction;


public class NewTest {

		WebDriver driver;
		private int intBrowserSyncTimeOut = 180;
		public static final String EXTENT_REPORT_PASS= "PASS";
		public static final String EXTENT_REPORT_FAIL= "FAIL";
		
		private UtilityFunction utilityFunction = new UtilityFunction();
		private Report report = new Report();
		SearchPage search = new SearchPage();
		TravallerDetailsPage traveller = new TravallerDetailsPage();
		

		@Parameters({"BrowserName"})
		@Test
		
		public void Test(String BrowserName) throws InterruptedException, IOException {
						
			//Method call to Launch the Browser and Open Application in the specified environment
			driver = utilityFunction.initilizeDriver(BrowserName);
			
			
			try {
				
				if(utilityFunction.Validate("FROM_AIRPORT") == true){ 
					
					if(search.searchFlight(driver)==true)
						report.reportEvent(driver,"Search Flight Successfull", EXTENT_REPORT_PASS,"Search Flight Successfull");
					else
						report.reportEvent(driver,"Search Flight not Successfull", EXTENT_REPORT_FAIL,"Search Flight not Successfull");
						
					driver.manage().timeouts().pageLoadTimeout(intBrowserSyncTimeOut, TimeUnit.SECONDS);
					report.reportEvent(driver,"Result page", EXTENT_REPORT_PASS,"Dispalys all the search results");
					utilityFunction.waitForElementVisibility("BOOK_BUTTON");
					Thread.sleep(5000);
 					
					utilityFunction.clickElement("BOOK");
					
					driver.manage().timeouts().pageLoadTimeout(intBrowserSyncTimeOut, TimeUnit.SECONDS);
					
					if(traveller.travallerDetailsEntry(driver) == true)
						report.reportEvent(driver,"Travaller details entered successfully", EXTENT_REPORT_PASS,"Details entered successfully");
					else
						report.reportEvent(driver,"Travaller details entered successfully", EXTENT_REPORT_FAIL,"Details not entered successfully");
					
					utilityFunction.waitForElementVisibility("MAKE_PAYMENT_BUTTON");
					report.reportEvent(driver,"User information page", EXTENT_REPORT_PASS,"Details to enter for the user");
				}

				
			} catch (Exception e) {
				
				e.printStackTrace();
				
			} finally {
				
				// close the browser after execution
				utilityFunction.closeBrowsers();
			}
		}
	}
	
