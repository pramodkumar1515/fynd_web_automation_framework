
package web;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import commonlib.AppConstants;

public class Gofynd extends Gofynd_Sanity_Scenarios {

	
	@BeforeClass
	public void initDriver() throws Exception {
		// setup the driver
		setUp();
	}
	
	@Test(priority = 0, enabled = true)
	public void OpenFyndHomePage() {
		//step 1:- Open https://www.gofynd.com/ 
		//Step 2:- Wait for the page load
		openFyndHomePage();
	}
	
	@Test(priority = 1, enabled = true)
	public void SelectCategory() throws InterruptedException {
		//Step 3:- Click on Women ​link from menu
		System.out.println(AppConstants.SANITY_CATEGORY);
		SelectCategory(AppConstants.SANITY_CATEGORY);
	}
	
	@Test(priority = 2, enabled = true)
	public void SelectCategoryFilter() throws InterruptedException {
		//Step 4:- Click on any one Category​ filter
		SelectCategoryFilters(AppConstants.CATEGORY_FILTER_INDEX);
	}
	
	@Test(priority = 3, enabled = true)
	public void SelectOtherFiters() throws InterruptedException {
		//Step 5:- Select any of two price filter option
		SelectFilter(AppConstants.PRODUCT_FILTER, AppConstants.PRODUCT_FLTER_SELECTION_NO);
	}
	

	@Test(priority = 4, enabled = true)
	public void addTwoProductsInCartsCheckOutFlow() throws InterruptedException {
		int productcount = 1;
		//Step 6:- Add product 1 in cart from first page.
		String product_name = SelectProduct(AppConstants.PRODUCT_NAME, AppConstants.DEFAULT_PRODUCT_INDEX);
		SelectSize(AppConstants.PRODUCT_SIZE);
		AddtoCart();
		getDriver().navigate().back();
		//Step 7:-Add product 2 in cart from second page after scroll.
		Thread.sleep(2000);
		ScrolltoEndofPage();
		String product_name_lazy_load = SelectProduct(AppConstants.LAZY_LOAD_PRODUCT_NAME, AppConstants.DEFAULT_LAZY_LOAD_PRODUCT_INDEX);
		SelectSize(AppConstants.LAZY_LOAD_PRODUCT_SIZE);
		AddtoCart();
		//Step 8:- Checkout Flow with login
		List<WebElement> product;
		product = CheckoutFlowWithLogin();
		System.out.println("cart Product name: " + product.get(0).getText().toString());
		for (WebElement webElement : product) {
			if (!webElement.getText().toString().isEmpty()) {
				if (productcount == 1) {
					Assert.assertEquals(webElement.getText().toString(), product_name);
					productcount++;
					System.out.println("verified the product: " + product_name);
				} else if (productcount == 2) {
					Assert.assertEquals(webElement.getText().toString(), product_name_lazy_load);
					System.out.println("verified the product: " + product_name_lazy_load);
				}

			}

		}
		

	}
	
	@Test(priority = 5, enabled = true)
	public void SelectPaymentMethod() throws InterruptedException {
		// Step 9:- Selecting the Payments Methods
		SelectPayment(AppConstants.PAYMENT_METHOD, AppConstants.DEFAULT_PAYMENT_METHOD_INDEX);
	}

	@Test(priority = 6, enabled = true)
	public void RemoveItemFromcart() throws InterruptedException {	
		//Step 10:- Remove the Added item
		RemoveCartItem();
		RemoveCartItem();
	}
	

	@AfterClass
	public void terminateBrowser() throws InterruptedException {
		//Step 11:- Closing the Browser
		shutDown();
	}

}
