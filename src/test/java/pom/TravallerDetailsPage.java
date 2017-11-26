package pom;


import qa.Assignment.util.UtilityFunction;
import org.openqa.selenium.WebDriver;
import qa.Assignment.util.Report;

public class TravallerDetailsPage {
		
		public boolean travallerDetailsEntry(WebDriver driver) throws InterruptedException{
		
			UtilityFunction utilityFunction = new UtilityFunction();
			Report report = new Report();
			final String EXTENT_REPORT_PASS= "PASS";
			final String EXTENT_REPORT_FAIL= "FAIL";
			
			String emailId = utilityFunction.readConfigurationFile("Email");
			String firstName = utilityFunction.readConfigurationFile("FirstName");
			String lastName = utilityFunction.readConfigurationFile("LastName");
			String mobNo = utilityFunction.readConfigurationFile("MobNo");
			

			if(utilityFunction.clickElement("CONDITION_CHECKBOX"))
				report.reportEvent(driver,"User information page", EXTENT_REPORT_PASS,"Details to enter for the user");
			else
				report.reportEvent(driver,"User information page not displayed", EXTENT_REPORT_FAIL,"Details to enter for the user page not displayed");
			
			utilityFunction.clickElement("CONTINUE_PAYMENT_BUTTON");
			utilityFunction.waitForElementVisibility("EMAIL_ADDRESS");
			utilityFunction.enterValueInEditField("EMAIL_ADDRESS", emailId);
			utilityFunction.clickElement("CONTINUE_BUTTON");
			utilityFunction.waitForElementVisibility("TRAVELLER_FIRSTNAME");
			utilityFunction.selectItemFromListBoxByValue("TRAVELLER_TITLE","Mr");
			utilityFunction.enterValueInEditField("TRAVELLER_FIRSTNAME", firstName);
			utilityFunction.enterValueInEditField("TRAVELLER_LASTNAME", lastName);
			utilityFunction.enterValueInEditField("TRAVELLER_MOBNO", mobNo);
			if(utilityFunction.clickElement("TRAVELLER_CONTINUE_BUTTON")){
				report.reportEvent(driver,"Travaller details entered successfully", EXTENT_REPORT_PASS,"Details entered successfully");
				return true;
			
			}else{
				report.reportEvent(driver,"Travaller details entered successfully", EXTENT_REPORT_FAIL,"Details not entered successfully");
				return false;
			}
			
		}
	}