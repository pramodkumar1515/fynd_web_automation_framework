
package web;

import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import commonlib.AppConstants;
import commonlib.CommonUtility;

/**
 * @author administrator
 *
 */
public class Gofynd_Sanity_Scenarios extends CommonUtility {

	protected Logger log = Logger.getLogger(Gofynd_Sanity_Scenarios.class);

	// static data
	private Properties sourceStaticDataAccounts = new Properties();	 
	protected void setUp() throws InterruptedException {
		// try{
		loadSetUpConfig();
		setUpDriver();
		readSourceDataFiles();
	}

	/**
	 * Opens the gofynd web page
	 */
	protected void openFyndHomePage() {
		// load Home Page
		loadPage(AppConstants.GOFYND_HOMEPAGE);
	}

	/**
	 * 
	 * @param category
	 *            : Name of category to be Selected e.g "women"
	 * @throws InterruptedException 
	 */
	protected void SelectCategory(String category) throws InterruptedException {
		WebElement categoryElement = getDriver().findElement(By.linkText(category));
		waitgetForPageLoad(30).until(ExpectedConditions.elementToBeClickable(categoryElement));
		Actions moveToElement = new Actions(getDriver());
		moveToElement.moveToElement(categoryElement).doubleClick().build().perform();
		Thread.sleep(2000);
		WebElement categoryElement2 = getDriver().findElement(By.linkText(category));
		moveToElement.moveToElement(categoryElement2).doubleClick().build().perform();
		log.info("Category is Selected: " + category);

	}

	/**
	 * @param categoryindex
	 *            : Index of category to be selected e.g 2
	 */
	protected void SelectCategoryFilters(int categoryindex) {
		//jsHandle().executeScript("window.scrollBy(0,10)", "");	
		WebElement categoryFilterElement = getDriver()
				.findElement(By.xpath("//*[@class='tree-level level-0 fy-tree']/li[" + categoryindex + "]"));
		Actions moveToElement = new Actions(getDriver());
		moveToElement.moveToElement(categoryFilterElement).build().perform();
		waitgetForPageLoad(30).until(ExpectedConditions.elementToBeClickable(categoryFilterElement));
		categoryFilterElement.click();
	}

	/**
	 * @param filtername : name of the filter
	 * @param noOfFilterApplied
	 */
	protected void SelectFilter(String filtername , int noOfFilterToBeApplied) throws InterruptedException {
		jsHandle().executeScript("window.scrollBy(0,800)", "");
		List<WebElement> priceFilterElements = getDriver().findElements(By.xpath(getSourceData(AppConstants.xpath_priceFilterElement)));
		waitgetForPageLoad(30).until(ExpectedConditions.visibilityOf(priceFilterElements.get(1)));
		for (WebElement webElement : priceFilterElements) {
			if (webElement.getText().toString().trim().equals(filtername.trim())) {
				webElement.click();
				SelectFilterCheckbox(filtername,noOfFilterToBeApplied);
				log.info("clicked the Filter: " + webElement.getText());
				break;
			}			
		}

	}

	/**
	 * @param filtername : Name of the filter e.g 'PRICE','COLOR','DISCOUNT'
	 * @param filter_no_applied : No of Checkbox to be selected e.g 1, 2, 4
	 * @throws InterruptedException
	 */
	protected void SelectFilterCheckbox(String filtername, int filter_no_applied) throws InterruptedException {

		int filtercountapplied = 0;		
		if( filtername=="PRICE")
		{
			List<WebElement> selectPriceFilterElements = getDriver()
			.findElements(By.xpath(getSourceData(AppConstants.xpath_selectPriceFilterElement)));
	waitgetForPageLoad(30).until(ExpectedConditions.visibilityOf(selectPriceFilterElements.get(1)));

	for (WebElement webElement : selectPriceFilterElements) {
		if (filtercountapplied < filter_no_applied) {
			webElement.click();
			waitgetForPageLoad(30).until(ExpectedConditions.elementToBeClickable(webElement));
			filtercountapplied++;
			//jsHandle().executeScript("window.scrollBy(0,800)", "");
			Thread.sleep(2000);
		} else {
			break;
		}

	}
			
		} else if(filtername=="COLOR")
			
		{
			log.info("Select the color Filter");
			//TODO
			
		}else if (filtername=="DISCOUNT")
		{
			log.info("Select the Discount Filter");
			//TODO
		}else if(filtername=="SIZE")
		{
			log.info("Select the Discount Filter");
			//TODO
		}
		
		else
		{
			log.info("No Filter Checkbox is selected");
			//TODO
		}

		
		
				
	}

	/**
	 * @param ProductName : Name of the product e.g 'Navy Socks','Brown Clutch'
	 * @param Index : Index of the product to be selected e.g 1, 5, 35
	 * @return
	 * @throws InterruptedException
	 */
	protected String SelectProduct(String ProductName, int Index) throws InterruptedException {
		Thread.sleep(2000);
		String selectedProductName;
		List<WebElement> selectProductElements = getDriver()
				.findElements(By.xpath(getSourceData(AppConstants.xpath_selectProductElement)));
		Boolean productisSelected = false;
		for (WebElement webElement : selectProductElements) {

			waitgetForPageLoad(30).until(ExpectedConditions.elementToBeClickable(webElement));
			if (webElement.getText().toString().equals(ProductName)) {

				selectedProductName=webElement.getText().toString();
				webElement.click();
				productisSelected = true;;
				return selectedProductName;
			}

		}
		if (!productisSelected) {
			Thread.sleep(2000);
			selectedProductName=selectProductElements.get(Index).getText().toString();
			Actions moveToElement = new Actions(getDriver());
			moveToElement.moveToElement(selectProductElements.get(Index)).click().build().perform();	
			return selectedProductName;
		} else {
			return "Product not Selected";
		}
	}
	
	protected void ScrolltoEndofPage()
	{
		//jsHandle().executeScript("window.scrollBy(0,"+pixel+")", "");
		jsHandle().executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	/**
	 * @param size : Select the Available size e.g 'OS','M'
	 */
	protected void SelectSize(String size){
		List<WebElement> sizeFilterElements = getDriver()
				.findElements(By.xpath(getSourceData(AppConstants.xpath_sizeFilterElement)));
		waitgetForPageLoad(30).until(ExpectedConditions.visibilityOfAllElements(sizeFilterElements));
		Boolean sizeIsSelected = false;
		for (WebElement webElement : sizeFilterElements) {
			if (webElement.getText().toString().equals(size)) {

				webElement.click();
				sizeIsSelected = true;
			}
		}
		if (!sizeIsSelected) {
			// if desired size is not found select the first available size
			sizeFilterElements.get(0).click();
		}

	}

	
	/**
	 * Add the Selected Item in the cart
	 * @throws InterruptedException
	 */
	protected void AddtoCart() throws InterruptedException {
		WebElement buyNowElement = getDriver().findElement(By.className(getSourceData(AppConstants.classname_buyNowElement)));
		waitgetForPageLoad(30).until(ExpectedConditions.elementToBeClickable(buyNowElement));
		buyNowElement.click();
		Thread.sleep(2000);
	}

	/**
	 * checkOut flow Login via username and password
	 * @return
	 * @throws InterruptedException
	 */
	protected List<WebElement> CheckoutFlowWithLogin() throws InterruptedException {
		
		WebElement checkOutElement = getDriver().findElement(By.className(getSourceData(AppConstants.classname_buyNowElement)));
		waitgetForPageLoad(30).until(ExpectedConditions.elementToBeClickable(checkOutElement));
		checkOutElement.click();
		
		//checkout flow
		Thread.sleep(2000);
		WebElement checkoutLoginElement = getDriver().findElement(By.className(getSourceData(AppConstants.classname_checkoutLoginElement)));
		waitgetForPageLoad(30).until(ExpectedConditions.elementToBeClickable(checkoutLoginElement));
		checkoutLoginElement.click();
		String handle = getDriver().getWindowHandle();
		getDriver().switchTo().window(handle);

		// Login using password
		getDriver().findElement(By.xpath(getSourceData(AppConstants.xpath_loginwithpassword))).click();
		Thread.sleep(2000);
		String loginWindowHandle = getDriver().getWindowHandle();
		getDriver().switchTo().window(loginWindowHandle);
		getDriver().findElement(By.xpath(getSourceData(AppConstants.xpath_loginusername))).sendKeys(AppConstants.GOFYND_PROD_USERNAME);
		Thread.sleep(2000);
		getDriver().findElement(By.xpath(getSourceData(AppConstants.xpath_loginpassword))).sendKeys(AppConstants.GOFYND_PROD_PASSWORD);
		getDriver().findElement(By.xpath(getSourceData(AppConstants.xpath_loginsubmitbtn))).click();
		Thread.sleep(2000);
		return getProductNameFromCart();
	}

	/**
	 * @param payment: text of the payment method to be selected e.g 'Paytm'
	 * @param index : Index to payment type to be selected e.g 'NetBanking'
	 * @throws InterruptedException
	 */
	protected void SelectPayment(String payment, int index) throws InterruptedException {

		WebElement paymentElement = getDriver().findElement(By.xpath("//*[@class='list']/div[" + index + "]"));
		waitgetForPageLoad(30).until(ExpectedConditions.elementToBeClickable(paymentElement));
		paymentElement.click();
		Thread.sleep(2000);

		List<WebElement> paymentselectionElement = getDriver().findElements(By.xpath(getSourceData(AppConstants.xpath_paymentmethod)));
		// waitgetForPageLoad(30).until(ExpectedConditions.visibilityOf(paymentFilterElement.get(3)));

		for (WebElement webElement : paymentselectionElement) {
			if (webElement.getText().toString().trim().equals(payment)) {
				// jsHandle().executeScript("window.scrollBy(0,300)", "");
				webElement.click();
				log.info("selected the payment:" + webElement.getText());
				break;
			}

		}
	}

	/**
	 * @return : Product name as text
	 * @throws InterruptedException
	 */
	protected List<WebElement> getProductNameFromCart() throws InterruptedException {
		int i=1;
		List<WebElement> productNameElement = getDriver().findElements(By.xpath(getSourceData(AppConstants.xpath_bagitmes)));
		for (WebElement webElement : productNameElement) {

			log.info("Product no"+i++ +webElement.getText());

		}

		return productNameElement;

	}

	/**
	 * Description: Clears the Cart by clicking on the Remove items
	 * @throws InterruptedException
	 */
	protected void RemoveCartItem() throws InterruptedException {
		List<WebElement> productNameElement = getDriver().findElements(By.xpath(getSourceData(AppConstants.xpath_removeitem)));
		Actions moveto = new Actions(getDriver());
		for (WebElement webElement : productNameElement) {
			try{
			moveto.moveToElement(webElement).click().build().perform();
			log.info("Removed the item from the cart");
			Thread.sleep(2000);}
			catch(Exception e){
				continue;
				
			}
		}

	}

	private void readSourceDataFiles() {
		String dataFileName = CommonUtility.prop.getProperty(AppConstants.CONFIG_FILES_LOCATION)
				.concat(AppConstants.GOFYND_WEB_STATICDATAFILE_LOCATION);
		sourceStaticDataAccounts = loadFileData(dataFileName);

	}

	/**
	 * Get the static source data
	 */

	protected String getSourceData(String inputData) {
		String returnData = null;

		if (sourceStaticDataAccounts.containsKey(inputData)) {
			returnData = findSourceData(inputData);
			log.info("Success : Static Source data '" + inputData + "' -->" + returnData);
		} else {
			returnData = "Fail :" + inputData + " Not found in Status File";
			log.error("Fail :" + inputData + " Not found in Status File");
		}

		return returnData;
	}

	/**
	 * read the static source data
	 * 
	 * @param inputData
	 * @return
	 */
	private String findSourceData(String inputData) {
		String returnData = sourceStaticDataAccounts.getProperty(inputData);
		return returnData;
	}
}
