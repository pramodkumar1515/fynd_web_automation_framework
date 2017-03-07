package commonlib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonUtility {
	
	public static Properties prop = new Properties();
	
	private  Wait<WebDriver> wait = null;
	
	private  WebDriver driver = null;
	
	private  Logger log = Logger.getLogger(CommonUtility.class);
	
	private static File setupConfigFile = null;
	
	private static FileInputStream fileInput = null;
	
	private  String DRIVER_CLASS = null;
	
	private Properties fileData = new Properties();

	private static WebDriver ssWebSriver= null;
	
	protected  void setUp() throws InterruptedException{
		
		loadSetUpConfig();
		setUpDriver();
	}
	//Load the SetupConfig.properties File
	protected   void loadSetUpConfig(){
		
		if(setupConfigFile == null || fileInput == null){
			setupConfigFile = new File(AppConstants.SETUP_CONFIGFILE_LOCATION);
			  
			try {
				fileInput = new FileInputStream(setupConfigFile);
				log.info("Info : loading SetupConfig File ");
			} catch (FileNotFoundException e) {
				//e.printStackTrace();
				log.error("Fail: loadSetUpConfig(): Config file not found to initialize the Selenium Framework");
				System.exit(0);
			}
		}else{
			    log.info("Info : SetupConfig File is already loaded");
		}

		
		//load properties file
		try {
			
			if(prop.isEmpty()){
				prop.load(fileInput);
				log.info("SetUp Data initialized");
			}
				loadLogger();
			log.info("SetUp Data initialized: " + prop);
		} catch (IOException e) {
			log.error("Fail : Cann't Load the  SetuUp Config file...exiting the program");
			//e.printStackTrace();
			System.exit(0);
		}

	}
	//load the logger
	private  void  loadLogger(){
		Properties logProperties = new Properties();	
		try
	    {
	      // load our log4j properties / configuration file
		  String logfile = prop.getProperty("LOG_FILE");
		  log.info("Log path: "+logfile);
	      logProperties.load(new FileInputStream(logfile));
	      PropertyConfigurator.configure(logProperties);
	      log.info("Logging initialized.");
	    }
	    catch(IOException e)
	    {
	      //throw new RuntimeException("Unable to load logging File " + prop.getProperty("LOG_FILE"));
	      log.error("Unable to load logging File " + prop.getProperty("LOG_FILE"));
	    }
	}
		
	protected  void setUpDriver(){

			//load the configuration file
			loadSetUpConfig();
			
	
			String DRIVER_NAME=prop.getProperty("DRIVER");
			log.info("DRIVER NAME: "+DRIVER_NAME);			
			
			if( DRIVER_NAME.toString().equals("CHROME"))
			{
				DRIVER_CLASS = prop.getProperty("CHROME_DRIVER_CLASS");
				String DRIVER_LOC = prop.getProperty("CHROME_DRIVER_LOCATION");
				System.setProperty(DRIVER_CLASS, DRIVER_LOC);
				log.info("DRIVER_CLASS : "+DRIVER_CLASS);
				log.info("DRIVER_LOC: "+DRIVER_LOC);
				driver = new ChromeDriver();
				
			} else if(DRIVER_NAME.toString().equals("FIREFOX"))
				
			{
				DRIVER_CLASS = prop.getProperty("FIREFOX_DRIVER_CLASS");
				String DRIVER_LOC = prop.getProperty("FIREFOX_DRIVER_LOCATION");

				System.setProperty(DRIVER_CLASS, DRIVER_LOC);
				log.info("DRIVER_CLASS : "+DRIVER_CLASS);
				log.info("DRIVER_LOC: "+DRIVER_LOC);
				driver = new FirefoxDriver();
				
			}
			else if (DRIVER_NAME.toString().equals("SAFARI"))
			{
				log.info("Safari browser is Selected");
				driver = new SafariDriver();
			}
			else
			{
				DRIVER_CLASS = prop.getProperty("CHROME_DRIVER_CLASS");
				String DRIVER_LOC = prop.getProperty("CHROME_DRIVER_LOCATION");
				System.setProperty(DRIVER_CLASS, DRIVER_LOC);
				log.info("DRIVER_CLASS : "+DRIVER_CLASS);
				log.info("DRIVER_LOC: "+DRIVER_LOC);
				driver = new ChromeDriver();
				
			}
			
			
				// Put a Implicit wait, this means that any search for elements on the page
				waitForPageLoad(30);
				//maximize the browser window
				getDriver().manage().window().maximize();
				
				//set the driver for Listerner class - captures the ScreenShot
				setSsWebSriver(getDriver());
				
				log.info("Success : Driver Initialized Successfully");
			}

		
	/**
	 * Test the Page URL
	 * @param pageURL
	 * @throws Exception 
	 */
	protected  void loadPage(String pageURL) {
		try{
			getDriver().get(pageURL);
		log.info("Success : Page '"+ pageURL + "' loaded successfully");
		}
	catch(Exception e) {
			log.error("Fail : Load to Page '"+pageURL +"' Failed ....exiting the program");
			e.printStackTrace();
			System.exit(0);

		}
	
	}
	/**
	 * load the property file
	 * @param String filename
	 * @return property file 
	 * @throws Exception 
	 */
	protected Properties loadFileData(String  fileName) {
		Properties propertyFile = new Properties();
		
		   File sourceDataFile = null;
    	   FileInputStream sourceDataFileInput = null;

    	   if(sourceDataFile == null  ){
    		   sourceDataFile = new File(fileName);
   			try {
   				sourceDataFileInput = new FileInputStream(sourceDataFile);
   				propertyFile.load(sourceDataFileInput);
   				log.info("Success : Source data file  " + propertyFile + " loaded");
   				} catch (Exception e) {
   					log.error("Fail : Cann't Load the  Source Property file "+fileName +" ...exiting the program");
	    			shutDown();
   				}
   			} 

   		return propertyFile;
	}
	
	/**
	 * Close the driver
	 */
	protected  void shutDown(){
		try{
		//Thread.sleep(20);
		getDriver().quit();
		getSsWebSriver().quit(); //quit the listener driver
		System.clearProperty(DRIVER_CLASS);
		log.info("Info : Browser Driver closed successfully ");
		}catch(Exception e) {
			log.error("Fail : Browser Driver Didn't close properly ....exiting the program");
			//e.printStackTrace();
			System.exit(0);
		}
	}
	

	/**
	 * Page wait for specific time
	 * @param long wait time 
	 */
	protected  void waitForPageLoad(long waitTime){
		 try{
			getDriver().manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
		}catch(Exception e) {
			log.error("Fail : waitForPageLoad()  failed");
		}
	}
	
	
	protected  Wait<WebDriver> waitgetForPageLoad(long waitTime){
		 try{
			wait = new WebDriverWait(driver, waitTime);
		}catch(Exception e) {
			log.error("Fail : waitgetForPageLoad()  failed");
		}
		return wait;
	}
	
	protected  JavascriptExecutor jsHandle(){

		JavascriptExecutor jse = (JavascriptExecutor)getDriver();	
		return jse;
	}
	
	
	/**
	 * get the webdriver
	 * @return
	 */
	protected  WebDriver getDriver() {
		return driver;
	}
	
	
	/**
	 * Get the Property file data
	 * @return Properties
	 */
	
	protected Properties getFileData() {
		return fileData;
	}
	
	protected WebElement tryToGetElementByXPath(String xpath,int maxtry) {
	 Wait<WebDriver> waittable= new WebDriverWait(getDriver(), 30); 
	 WebElement element=null;
	 int trycount=1;
 	boolean breakit;
        	while(trycount!=maxtry){
                breakit=true;
            try{
            element=waittable.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))); 
        }
       catch (Exception e) {
              breakit=false;
              trycount++;
    	   
        } 
            if(breakit==true)
            	break;
     }
        	return element;
	}
	
	protected WebElement tryToClickElementByXPath(String xpath,int maxtry) {
		 Wait<WebDriver> waittable= new WebDriverWait(getDriver(), 30); 
		 WebElement element=null;
		 int trycount=1;
	 	boolean breakit;
	        	while(trycount!=maxtry){
	                breakit=true;
	            try{
	            element=waittable.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))); 
	        }
	       catch (Exception e) {
	              breakit=false;
	              trycount++;
	    	   
	        } 
	            if(breakit==true)
	            	break;
	     }
	        	return element;
		}
	

	
	public static WebDriver getSsWebSriver() {
		return ssWebSriver;
	}
	public static void setSsWebSriver(WebDriver ssWebSriver) {
		CommonUtility.ssWebSriver = ssWebSriver;
	}
	

	public static void main(String[] args) {}
}
