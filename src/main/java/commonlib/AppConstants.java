package commonlib;

public interface AppConstants {
	
/* ---Configurations Constants--- */
	
	public  final String SETUP_CONFIGFILE_LOCATION = "//Users//administrator//automation//ConfigFiles//common//SetUpConfig.properties";	

	public  final String CONFIG_FILES_LOCATION = "CONFIG_FILES_LOCATION";
	
	
	/* ---Website Constants--- */
	
	public  final String GOFYND_HOMEPAGE = "https://www.gofynd.com/";
	
	
	/* ---Registration Constants begins--- */
	
	public  final String GOFYND_PROD_USERNAME = "8452858570";
	public  final String GOFYND_PROD_PASSWORD = "123456";

	
	//Properties file Path Constants
	public  final String GOFYND_WEB_DATASTATUSFILE_LOCATION  = "Sanity/fynd_web_datastatus_sanity.properties";
	public  final String GOFYND_WEB_STATICDATAFILE_LOCATION =  "Sanity/fynd_web_staticdata_sanity.properties";
	
	/* ---Registration Constants End--- */
	
	/* ---Sanity Constants begins--- */
	public final String SANITY_CATEGORY="Women";
	public final int	PRODUCT_FLTER_SELECTION_NO=2;
	public final int	CATEGORY_FILTER_INDEX=4;
	public final String	PRODUCT_FILTER="PRICE";
	public final String	PRODUCT_NAME="Orange Wallet";
	public final String	LAZY_LOAD_PRODUCT_NAME="Lavender Wallet";
	public final String	PRODUCT_SIZE ="OS";
	public final String	LAZY_LOAD_PRODUCT_SIZE="OS";
	public final String	PAYMENT_METHOD="Paytm";
	public final int	DEFAULT_PAYMENT_METHOD_INDEX=5;
	public final int	DEFAULT_PRODUCT_INDEX=1;
	public final int	DEFAULT_LAZY_LOAD_PRODUCT_INDEX=32;

	/* ---Sanity Constants End--- */
	
	/* ---LocatorConstants--- */
	
	public final String	xpath_priceFilterElement ="xpath_priceFilterElement";
	public final String xpath_selectPriceFilterElement="xpath_selectPriceFilterElement";
	public final String xpath_selectProductElement="xpath_selectProductElement";
	public final String xpath_sizeFilterElement="xpath_sizeFilterElement";
	
	
	public final String xpath_loginwithpassword="xpath_loginwithpassword";
	public final String	xpath_loginusername="xpath_loginusername";
	public final String xpath_loginpassword="xpath_loginpassword";
	public final String xpath_loginsubmitbtn="xpath_loginsubmitbtn";
	public final String xpath_paymentmethod="xpath_paymentmethod";
	public final String xpath_bagitmes="xpath_bagitmes";
	public final String xpath_removeitem="xpath_removeitem";


	public final String classname_buyNowElement="classname_buyNowElement";
	public final String classname_checkoutLoginElement="classname_checkoutLoginElement";
	


	/* ---General Constants--- */

	public  final String YES =  "Y";

	/* ---Capture Screen Shot for ERROR Cases--- */
	
	public final String ERROR_SCREENSHOT_FILE_PATH = "ERROR_SCREENSHOT_FILE_PATH";
	
	public final String ERROR_SCREENSHOT_FILE_DATEFORMAT = "ERROR_SCREENSHOT_FILE_DATEFORMAT";
	
	public final String ERROR_SCREENSHOT_FILE_EXTN = "ERROR_SCREENSHOT_FILE_EXTN";
	
	public final String DOT = ".";
	
	public final String UNDER_SCORE = "_";
	
	public final String ERROR_SCREENSHOT_FILE_PREFIX = "ERROR_SCREENSHOT_FILE_PREFIX";
	
	public final String NEW_LINE = "\n";
	
	public final String COLON = " : ";
	
	public final String GREATER_SIGN = " > ";
	
	public  final String EXECUTE_TESTCASE =  "common/Execute.properties";
	public  final String EXECUTE_STATUS_TESTCASE= "common/Execute_Status.properties";
	
	public  final String COMMA =  ",";
	
	public final String DOWNLOADS_PATH ="/Users/"+System.getProperty("user.name")+"/Downloads";
	
	
}
