package qa.Assignment.util;


import java.io.FileInputStream;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

	
public class UtilityFunction {
	
     //Declare variables
	  static HSSFWorkbook excel_Workbook;
	  static HSSFSheet excel_sheet;
	  static HSSFRow row;
	  static HSSFCell cell;
	  public static WebDriver driver;
	  private int intObjectSyncTimeOut = 90;
	  private int intBrowserSyncTimeOut = 180;
	  public String moduleName;
	  private Properties properties;
	  private Select select;
	  private Actions action;
	  private String Wrapper_Path;
	  
	  
	  // to read the confiduration File using the string name as argument
	  public String readConfigurationFile(String key)
	  {
	    try
	    {
	      properties = new Properties();
	      properties.load(new FileInputStream("Test_Configuration\\Config.properties"));
	      if (key.length() > 0) {
	        return properties.getProperty(key).trim();
	      }
	    }
	    catch (Exception e) {
	      System.out.println(e);
	      
	      System.err.println("Could not read property " + key + " from Config file."); }
	    return null;
	  }
	  

	  // to read the object repository
	  public String readObjectRepositoryFile(String key)
	  {
	    try
	    {
	      properties = new Properties();
	      properties.load(new FileInputStream("Test_ObjectRepository\\OR.properties"));
	      if (key.trim().length() > 0) {
	        return properties.getProperty(key).trim();
	      }
	    }
	    catch (Exception e) {
	      System.out.println(e);
	      
	      System.err.print("Could not to read Object property " + key); }
	    return null;
	  }
	  
	  // to initialize the web driver
	  public WebDriver initilizeDriver(String BrowserName) { 
		  String URL = null;
		  URL = readConfigurationFile("URL");
		  try {
		 
			  if (URL.trim().length() > 0) {
		        if (BrowserName.trim().equalsIgnoreCase("firefox")) {
		          driver = new org.openqa.selenium.firefox.FirefoxDriver();
		          driver.manage().window().maximize();
		          driver.get(URL);
		          driver.manage().timeouts().pageLoadTimeout(intBrowserSyncTimeOut, TimeUnit.SECONDS);
		          return driver;
		        }
		        if (BrowserName.trim().equalsIgnoreCase("chrome")) {
		          System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		          driver = new org.openqa.selenium.chrome.ChromeDriver();
		          driver.manage().window().maximize();
		          driver.get(URL);
		          driver.manage().timeouts().pageLoadTimeout(intBrowserSyncTimeOut, TimeUnit.SECONDS);
		          return driver;
		        }
		        if (BrowserName.trim().equalsIgnoreCase("EO_Web")) {		              
		          ChromeOptions options = new ChromeOptions();
			      Proxy proxy = new Proxy();
			      proxy.setHttpProxy("localhost:3128");
			      DesiredCapabilities cap=new DesiredCapabilities();
		          cap.setCapability("network.proxy.type", 1);
		          cap.setCapability("network.proxy.http", "localhost");
		          cap.setCapability("network.proxy.http_port", 3128);
		          cap.setCapability("dom.max_script_run_time", 30000);
		          cap.setCapability("network.proxy.no_proxies_on", "adadvisor.net, intuit.com, doubleclick.net, doubleclick.com, google.com, webengage.com, demdex.net");
		          Wrapper_Path = readConfigurationFile("EO_Web_path");		
		          options.setBinary(Wrapper_Path);
			      System.setProperty("webdriver.chrome.driver", ".//chromedriver.exe");       
			      driver = new ChromeDriver(options);        
			      Reporter.log("EO.Web - Browser Launched Successfully");
     	          Thread.sleep(10000);
      
		        }
		      }
		  }
	        catch (Exception e) {
	        System.err.println("Failed to Launch the Application");
	        System.out.println(e);
	      }
	    return null;
	  }
	  

	  // to enter value into an edit field
	  public boolean enterValueInEditField(String objName, String value)
	  {
	    try
	    {
	      WebElement tempElement = getWebElement(objName);
	      if ((tempElement != null) && 
	        (tempElement.isEnabled())) {
	        tempElement.clear();
	        tempElement.sendKeys(new CharSequence[] { value });
	        return true;
	      }
	    }
	    catch (Exception e) {
	      System.out.println(e);
	      
	      System.err.println("Failed to Enter the text field " + objName); }
	    return false;
	  }
	  
	// to enter value into an edit field
		  public boolean pressEnter(String objName)
		  {
		    try
		    {
		      WebElement tempElement = getWebElement(objName);
		      if ((tempElement != null) && 
		        (tempElement.isEnabled())) {
		        tempElement.sendKeys(Keys.ENTER);
		        return true;
		      }
		    }
		    catch (Exception e) {
		      System.out.println(e);
		      
		      System.err.println("Failed to press Enter key " + objName); }
		    return false;
		  }

	  

	  public boolean clickElement(String objName)
	  {
	    try
	    {
	      WebElement tempElement = getWebElement(objName);
	      if ((tempElement != null) && 
	        (tempElement.isEnabled())) {
	        tempElement.click();
	        return true;
	      }
	    }
	    catch (Exception e) {
	      System.out.println(e.getMessage());
	      
	      System.err.println("Failed to Click on Element  " + objName); }
	    return false;
	  }

	  public boolean selectItemFromListBoxByValue(String objName, String value)
	  {
	    value = value.trim();
	    try {
	      WebElement tempElement = getWebElement(objName);
	      if ((tempElement != null) && 
	        (tempElement.isEnabled())) {
	        select = new Select(tempElement);
	        select.selectByValue(value);
	        System.out.println(value + " selected from the listbox " + objName);
	        return true;
	      }
	    }
	    catch (Exception e) {
	      System.out.println(e.getMessage());
	      
	      System.err.println("Failed to Select item " + value + " from the list box"); }
	    return false;
	  }
	  


	  public WebElement getWebElement(String objName)
	  {
	    objName = objName.trim();
	    int size = 0;
	    String locatorType = null;
	    String locator = null;
	    try
	    {
	      if (readObjectRepositoryFile(objName).length() > 0) {
	        String[] strpropertyname = readObjectRepositoryFile(objName).split("\\|");
	        locatorType = strpropertyname[0].toLowerCase().trim();
	        locator = strpropertyname[1].trim();
	        
	        if (locatorType.length() < 1) {
	          System.err.println("Locator type not specified in OR file.");
	        }
	        size = driver.findElements(By.xpath(locator)).size();
            //if (size <= 0) break label590;
            if (driver.findElement(By.xpath(locator)).isDisplayed()) {
              return driver.findElement(By.xpath(strpropertyname[1]));
            }
	        String str1;
	        switch ((str1 = locatorType).hashCode()) {case 3355:  if (str1.equals("id")) {} break; case 3373707:  if (str1.equals("name")) {} break; case 114256029:  if (str1.equals("xpath")) break; break; case 228335784:  if (str1.equals("partiallinktext")) {} break; case 858964706:  if (str1.equals("cssselector")) {} break; case 1195141159:  if (!str1.equals("linktext"))
	          {
	         //   break label590;
	            size = driver.findElements(By.xpath(locator)).size();
	            //if (size <= 0) break label590;
	            if (driver.findElement(By.xpath(locator)).isDisplayed()) {
	              return driver.findElement(By.xpath(strpropertyname[1]));
	            }
	            




	            size = driver.findElements(By.name(locator)).size();
	            //if (size <= 0) break label590;
	            if (driver.findElement(By.name(locator)).isDisplayed()) {
	              return driver.findElement(By.name(locator));
	            }
	            




	            size = driver.findElements(By.id(locator)).size();
	          //  if (size <= 0) break label590;
	            if (driver.findElement(By.id(locator)).isDisplayed()) {
	              return driver.findElement(By.id(locator));
	            }
	          }
	          



	          size = driver.findElements(By.linkText(locator)).size();
	          if (size > 0) {
	            if (driver.findElement(By.linkText(locator)).isDisplayed()) {
	              return driver.findElement(By.linkText(locator));
	            }
	            




	            size = driver.findElements(By.partialLinkText(locator)).size();
	            if (size > 0) {
	              if (driver.findElement(By.partialLinkText(locator)).isDisplayed()) {
	                return driver.findElement(By.partialLinkText(locator));
	              }
	              




	              size = driver.findElements(By.cssSelector(locator)).size();
	              if ((size > 0) && 
	                (driver.findElement(By.cssSelector(locator)).isDisplayed()))
	                return driver.findElement(By.cssSelector(locator));
	            }
	          }
	          break; }
	      }
	    } catch (NoSuchElementException e) {
	      System.out.println(e.getMessage()); }
	    label590:
	    System.err.println("The specified webelement " + objName + " was not found");
	    return null;
	  }
	  

	  public boolean mouseHoverMenuItem(List<WebElement> webelement)
	  {
	    try
	    {
	      action = new Actions(driver);
	      for (WebElement element : webelement) {
	        action.moveToElement(element).build().perform();
	      }
	      return true;
	    } catch (Exception e) {
	      System.out.println(e.getMessage());
	    }
	    
	    return false;
	  }
	  



	  public boolean waitForElementVisibility(String objName)
	  {
	    WebDriverWait wait = new WebDriverWait(driver, intObjectSyncTimeOut);
	    try {
	      if (readObjectRepositoryFile(objName).length() > 0) {
	        String[] strpropertyname = readObjectRepositoryFile(objName).split("\\|");
	        if (strpropertyname[0].equalsIgnoreCase("xpath"))
	        {
	          wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(strpropertyname[1])));
	          System.out.println(objName + " was synchronized successfully for Visibility");
	          return true;
	        }
	        
	        if (strpropertyname[0].equalsIgnoreCase("name")) {
	          wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(strpropertyname[1])));
	          System.out.println(objName + " was synchronized successfully for Visibility");
	          return true;
	        }
	        if (strpropertyname[0].equalsIgnoreCase("id")) {
	          wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(strpropertyname[1])));
	          System.out.println(objName + " was synchronized successfully");
	          return true;
	        }
	        if (strpropertyname[0].equalsIgnoreCase("linkText")) {
	          wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(strpropertyname[1])));
	          System.out.println(objName + " was synchronized successfully for Visibility");
	          return true;
	        }
	      }
	    }
	    catch (Exception e) {
	      System.out.println(e.getMessage());
	      
	      System.err.println("Could not synchronize " + objName + " for Visibility"); }
	    return false;
	  }

	 	  
	  
	  public void closeBrowsers()
	  {
	      driver.close();
		  driver.quit();
	    //return true;
	  }
	  

	  public String getElementText(String objName)
	  {
	    try
	    {
	      WebElement tempElement = getWebElement(objName);
	      if ((tempElement != null) && 
	        (tempElement.getText().trim().length() > 0)) {
	        return tempElement.getText().trim();
	      }
	    }
	    catch (Exception e) {
	      System.out.println(e.getMessage());
	      
	      System.err.println("No Text Found for the object " + objName); }
	    return null;
	  }
	  

	  

public boolean Validate(String objName)
{
  objName = objName.trim();
  String locatorType = null;
  String locator = null;
  boolean res = false;
  try
  {
    if (readObjectRepositoryFile(objName).length() > 0) {
      String[] strpropertyname = readObjectRepositoryFile(objName).split("\\|");
      locatorType = strpropertyname[0].toLowerCase().trim();
      locator = strpropertyname[1].trim();
      
      if (locatorType.length() < 1) {
        System.err.println("Locator type not specified in OR file.");
      }
      if (driver.findElement(By.xpath(locator)).isDisplayed()){
    	  res = true;
          return res; 
      }   
    }
  } 
  catch (NoSuchElementException e) {
    System.out.println(e.getMessage());
    System.err.println("The specified webelement " + objName + " was not found");
    }
  return res;
}

}