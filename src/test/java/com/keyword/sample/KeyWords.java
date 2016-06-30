package com.keyword.sample;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.util.concurrent.TimeUnit;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;

/**
 *  Class Name is KeyWords. This class includes all keywords(method
 *         names). On the basis of these keywords,their corresponding locator
 *         type,locator value and test data will be fetched.
 */

public class KeyWords {

	static Logger log = Logger.getLogger(KeyWords.class.getName());
	static WebDriver driver;
	static WebDriverWait wait;
	static String separator = ",";
	static String result = "";
	static String error_message;
	static SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyyMMdd hh mm ss a");
	static String current_DateTime = new SimpleDateFormat("MM-dd-yyyy_HH-mm-ss")
			.format(new Date().getTime());
	static String userHome = System.getProperty("user.home");
	static String fileInput = "../Demo_Test/config.properties";
	static String autoITScriptfile = "../Demo_Test/basicauth.exe";
	private static final long ASYNC_SCRIPT_TIMEOUT = 6;

	/**
	 * This method is used to open browser. Keyword is open_Browser. Parameters
	 * passed is browserName.
	 * 
	 * @throws InterruptedException
	 */

	public String open_Browser(String browserName) throws InterruptedException {
		try {
			if (browserName.equalsIgnoreCase("Firefox")) {
				driver = new FirefoxDriver();
				waitForAsync();
				KeyWordExecutionTest.bResult = true;

				error_message = "Firefox Browser is successfully initiated ";
				result = KeyWordExecutionTest.bResult + separator + error_message + separator ;
			} else if (browserName.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver",
						"/usr/local/bin/chromedriver");
				driver = new ChromeDriver();
				waitForAsync();
				KeyWordExecutionTest.bResult = true;

				error_message = "Chrome Browser is successfully initiated ";
				result = KeyWordExecutionTest.bResult + separator + error_message + separator ;
			} /**else if (browserName.equalsIgnoreCase("IE")) {
				System.setProperty("webdriver.ie.driver", "IEDriverServer");
				driver = new InternetExplorerDriver();
				waitForPageToLoad();
				KeyWordExecutionTest.bResult = true;

				error_message = "IE Browser is successfully initiated ";
				result = KeyWordExecutionTest.bResult + separator + error_message + separator ;
			}**/
			
		} catch (WebDriverException e) {
			KeyWordExecutionTest.bResult = false;

			log.info("e.getMessage()");
			System.out.println(e.getMessage());
			error_message = "Browser cant be initiated = " + e;
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;
		}
		// return KeyWordExecutionTest.bResult;
		return (result);
	}

	

	/**
	 * This method is used to check whether an expected page is displayed. Keyword
	 * is isExpectedPageDisplayed. Parameters passed are
	 * locatorType,locatorValue,locatorTestData.
	 */
	public static String verifyPageTitle(String locatorTestData) throws InterruptedException {
		
		try {
			
			String expectedTitle = locatorTestData;					
		    String actualTitle = driver.getTitle();
			if(actualTitle.contains("'")){
				actualTitle=actualTitle.replaceAll("'","\\'");
			}
			System.out.println("The actual page displayed is : " + actualTitle);			
			
			if (actualTitle.contains(expectedTitle)) {
				
				System.out.println(expectedTitle
								+ " The Expected page is displayed");
				log.info(locatorTestData + " page is present ");
				KeyWordExecutionTest.bResult = true;

				error_message = locatorTestData	+ " page is present";
				result = KeyWordExecutionTest.bResult + separator
						+ error_message + separator;
			} else {
				System.out.println(locatorTestData + " The expected page is not displayed");
				log.info("Expected Page not Found..");
				KeyWordExecutionTest.bResult = false;

				error_message = locatorTestData	+ " page is not displayed";
				result = KeyWordExecutionTest.bResult + separator
						+ error_message + separator;
			}

		} catch (Exception e) {
			KeyWordExecutionTest.bResult = false;

			log.info("e.getMessage()");
			System.out
					.println(locatorTestData + " The expected page is not displayed");
			log.info(locatorTestData + " page not found");
			error_message = locatorTestData
					+ "Expected Page Title could not be verified"
					+ "\n";
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;
		}
		return (result);
		
	}
	

	
	/**
	 * This method is used to take screenshot of full page Keyword is enter_URL.
	 * Parameters passed screenshot name.
	 * 
	 * @return
	 * @throws AWTException
	 * @throws InterruptedException
	 */

	public String takeScreenshot(String ss_name) {
		try {
			// Take Screenshot of first URL
			File scrFile1 = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);

			// Store in staging area
			String first = "../Demo_Test/Reports/Screenshot/" + ss_name
					+ "App " + current_DateTime + ".png";
			FileUtils.copyFile(scrFile1, new File(first));

			KeyWordExecutionTest.bResult = true;
			error_message = "Screenshot taken successfully ";
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error in taking screenshot");
			e.printStackTrace();

			KeyWordExecutionTest.bResult = false;
			log.info("e.getMessage()");
			System.out.println(e.getMessage());
			error_message = "Error in taking ss = " + e;
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;

		} finally {
			// Close the driver
			// driver.close();
			System.out.println("Screenshot taken successfully!");

		}
		return (result);
	}

	
	/**
	 * This method is used to create new folder. Keyword is enter_URL.
	 * Parameters passed is URL.
	 */

	public void createFolderAtRunTime() {
		File file = new File("C:\\" + current_DateTime);
		if (!file.exists()) {
			if (file.mkdir()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}
	}

	/**
	 * This method is used to enter URL browser. Keyword is enter_URL.
	 * Parameters passed is URL.
	 * 
	 * @throws IOException
	 */

	public String enter_URL(String URL) throws InterruptedException,
			IOException {
		try {
			Properties prop = new Properties();
			FileInputStream fis = new FileInputStream(fileInput);
			prop.load(fis);
			String baseURL = prop.getProperty("baseURL");
			String finalURL = baseURL + URL;
			System.out.println("finalURL" + finalURL);
			
			driver.get(finalURL);
			waitForPageToLoad();
			//driver.get(finalURL);
			//waitForPageToLoad();
			//KeyWordExecutionTest.bResult = true;

			//error_message = "Url is launched successfully ";
			//result = KeyWordExecutionTest.bResult + separator + error_message
			//		+ separator;
		} catch (WebDriverException e) {
			KeyWordExecutionTest.bResult = false;

			log.info("e.getMessage()");
			System.out.println(e.getMessage());
			error_message = "Url is not launched successfully = " + e;
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;
		}

		return (result);
	}

	/** This method is used to enter preview URL. */
	public String enter_URL_Preview(String URL) throws InterruptedException,
			IOException {
		try {
			Properties prop = new Properties();
			FileInputStream fis = new FileInputStream(fileInput);
			prop.load(fis);
			//String baseURL_preview = prop.getProperty("baseURL_preview");
			driver.get(URL);			
			waitForPageToLoad();
			
		} catch (WebDriverException e) {
			KeyWordExecutionTest.bResult = false;

			log.info("e.getMessage()");
			System.out.println(e.getMessage());
			error_message = "Url is not launched successfully = " + e;
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;
		}

		return (result);
	}

	/** This method is used to maximize window. Keyword is window_maximize. */
	public String window_maximize() throws InterruptedException {
		try {
			driver.manage().window().maximize();
			KeyWordExecutionTest.bResult = true;

			error_message = "Window is  maximized successfully";
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;
		} catch (WebDriverException e) {
			KeyWordExecutionTest.bResult = false;

			log.info("e.getMessage()");
			System.out.println(e.getMessage());
			error_message = "Window is not maximized = " + e;
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;
		}

		return (result);
	}

	/**
	 * This method is used to wait for a page to load for some time. Keyword is
	 * waitForPageToLoad.
	 */

	public static void waitForPageToLoad() throws InterruptedException {
		try {
			Thread.sleep(10000);

		} catch (WebDriverException e) {
			e.getMessage();
		}

	}
	
	
	/**
     * Wait for Page to load and wait. 
     * @param driver
     *            the driver 
     */  
    public static void waitForAsync()
    {
        try
        {
            driver.manage().timeouts().setScriptTimeout(ASYNC_SCRIPT_TIMEOUT, TimeUnit.SECONDS);
            waitForAjaxCalls(driver, 10);
            
        } catch (TimeoutException ex)
        {
            ex.getMessage();
        }
    }
    
    
    /**
     * Wait for Ajax calls to complete. 
     * @param driver
     *            the driver
     * @param timeOutInSeconds
     *            the time out in seconds
     * @return true, if successful
     */
    private static boolean waitForAjaxCalls(WebDriver driver, long timeOutInSeconds)
    {
        boolean ajaxCall = false;
        try
        {
            new WebDriverWait(driver, timeOutInSeconds) {
            }.until(new ExpectedCondition<Boolean>() {

                public Boolean apply(WebDriver driverObject)
                {
                    return (Boolean) ((JavascriptExecutor) driverObject).executeScript("return jQuery.active == 0");
                }
            });
            ajaxCall = (Boolean) ((JavascriptExecutor) driver).executeScript("return window.jQuery != undefined && jQuery.active == 0");
            return ajaxCall;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return ajaxCall;
    }
	

	/**
	 * This method is used to find element on the basis of locatortype and
	 * locatorvalue. Method Name is locatorValue. Parameters passed are
	 * locatorType,locatorValue.
	 */
	public static By locatorValue(String locatorType, String locatorValue) {
		By by = null;
		/*if(locatorValue.contains("'")){
			locatorValue = locatorValue.replaceAll("'", "\\'");
		}*/
		try {

			switch (locatorType) {
			case "id":
				by = By.id(locatorValue);
				break;
			case "name":
				by = By.name(locatorValue);
				break;
			case "xpath":
				by = By.xpath(locatorValue);
				break;
			case "css":
				by = By.cssSelector(locatorValue);
				break;
			case "linkText":
				by = By.linkText(locatorValue);
				break;
			case "partialLinkText":
				by = By.partialLinkText(locatorValue);
				break;
			default:
				by = null;
				break;
			}

		} catch (WebDriverException e) {

			log.info("e.getMessage()");
			System.out.println(e.getMessage());
		}
		return by;
	}

	/**
	 * This method is used to enter text. Keyword is enter_Text. Parameters
	 * passed are locatorType,locatorValue,locatorTestData.
	 * 
	 * @throws InterruptedException
	 */

	public String enter_Text(String locatorType, String locatorValue,
			String locatorTestData) throws InterruptedException {
		try {
			By locator;
			locator = locatorValue(locatorType, locatorValue);
			new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(locator));
			WebElement element = driver.findElement(locator);
			element.clear();
			element.sendKeys(locatorTestData);
			//waitForAsync();
			KeyWordExecutionTest.bResult = true;

			error_message = "Element Found to enter text";
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;
		} catch (NoSuchElementException e) {
			KeyWordExecutionTest.bResult = false;

			error_message = "No Element Found to enter text = " + e;
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;
			log.info("e.getMessage()");
			System.err.format("No Element Found to enter text" + e);
		}

		return (result);
	}

	/**
	 * This method is used to enter random email. Keyword is enter_RandomText.
	 * Parameters passed are locatorType,locatorValue,locatorTestData.
	 * 
	 * @throws InterruptedException
	 */

	public String enter_RandomEmail(String locatorType, String locatorValue)
			throws InterruptedException {
		try {
			GenerateData genData = new GenerateData();
			By locator;
			locator = locatorValue(locatorType, locatorValue);
			new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(locator));
			WebElement element = driver.findElement(locator);
			element.clear();
			element.sendKeys(genData.generateEmail(30));
			//waitForAsync();
			KeyWordExecutionTest.bResult = true;

			error_message = "Element Found to enter text";
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;
		} catch (NoSuchElementException e) {
			KeyWordExecutionTest.bResult = false;

			error_message = "No Element Found to enter text = " + e;
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;
			log.info("e.getMessage()");
			System.err.format("No Element Found to enter text" + e);
		}

		return (result);
	}

	/**
	 * This method is used to select a value from the drop down list. Keyword is
	 * selectDropDownValue. Parameters passed are
	 * locatorType,locatorValue,locatorTestData.
	 */
	public String selectDropDownValue(String locatorType, String locatorValue,
			String locatorTestData) throws Exception {
		try {
			By locator;
			locator = locatorValue(locatorType, locatorValue);
			new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(locator));
			WebElement element = driver.findElement(locator);
			new Select(element).selectByVisibleText(locatorTestData);
			KeyWordExecutionTest.bResult = true;

			error_message = "Element Found to perform drag and drop = ";
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;
		} catch (NoSuchElementException e) {
			KeyWordExecutionTest.bResult = false;

			log.info("e.getMessage()");
			System.err.format("No Element Found to perform drag and drop" + e);
			error_message = "No Element Found to perform drag and drop = " + e;
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;
		}

		return (result);
	}

	/**
	 * This method is used to click on an element. Keyword is clickOn_TheElement.
	 * Parameters passed are locatorType,locatorValue.
	 * 
	 * @throws InterruptedException
	 */

	public String clickOn_TheElement(String locatorType, String locatorValue)
			throws InterruptedException {
		try {
			By locator;
			locator = locatorValue(locatorType, locatorValue);			
			try {
			
			WebElement element = driver.findElement(locator);			
			element.click();
			Thread.sleep(3000);
			waitForAsync();			
			KeyWordExecutionTest.bResult = true; 

			error_message = "Element Found to perform click = ";
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator; } catch(Exception e) {
						System.out.println(e);
					}
		} catch (NoSuchElementException e) {
			KeyWordExecutionTest.bResult = false;

			error_message = "No Element Found to perform click = " + e;
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;
			log.info("e.getMessage()");
			System.err.format("No Element Found to perform click" + e);
		}
		return (result);
	}
	


	/**
	 * This method is used to click link. Keyword is click_On_Link. Parameters
	 * passed are locatorType,locatorValue.
	 */
	public String click_On_Link(String locatorType, String locatorValue)
			throws InterruptedException {
		try {
			By locator;
			locator = locatorValue(locatorType, locatorValue);			
			try {
			
				new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(locator));
				WebElement element = driver.findElement(locator);
				element.click();
				waitForAsync();
				KeyWordExecutionTest.bResult = true; 

				error_message = "Element Found to perform click = ";
				result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator; } catch(Exception e) {
						System.out.println(e);
				}
		} catch (NoSuchElementException e) {
			KeyWordExecutionTest.bResult = false;

			error_message = "No Element Found to perform click = " + e;
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;
			log.info("e.getMessage()");
			System.err.format("No Element Found to perform click" + e);
		}
		return (result);
	}

	/**
	 * This method is used to click on button. Keyword is click_On_Button.
	 * Parameters passed are locatorType,locatorValue.
	 * 
	 * @throws InterruptedException
	 */

	public String click_On_Button(String locatorType, String locatorValue)
			throws InterruptedException {
		try {
			By locator;
			locator = locatorValue(locatorType, locatorValue);
			//new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(locator));
			try {
			
			WebElement element = driver.findElement(locator);
			element.click();
			waitForPageToLoad();
			//waitForAsync();
			KeyWordExecutionTest.bResult = true; 

			error_message = "Element Found to perform click = ";
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator; } catch(Exception e) {
						System.out.println(e);
					}
		} catch (NoSuchElementException e) {
			KeyWordExecutionTest.bResult = false;

			error_message = "No Element Found to perform click = " + e;
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;
			log.info("e.getMessage()");
			System.err.format("No Element Found to perform click" + e);
		}
		return (result);
	}

	/**
	 * This method is used to check whether an expected link is present in current page. Keyword
	 * is isLinkPresent. Parameters passed are
	 * locatorType,locatorValue,locatorTestData.
	 */
	public static String isLinkPresent(String locatorType, String locatorValue) throws InterruptedException {
		
		try {
			By locator;
			locator = locatorValue(locatorType, locatorValue);			
			
			
				new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(locator));
				WebElement element = driver.findElement(locator);
				element.isDisplayed();
				element.isEnabled();
				waitForAsync();
				KeyWordExecutionTest.bResult = true; 

				error_message = "Expected Link found to perform click = ";
				result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator; 
				} 	
		    catch (NoSuchElementException e) {
				KeyWordExecutionTest.bResult = false;

				error_message = "Expected Link not present on current page = " + e;
				result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;
				log.info("e.getMessage()");
				System.err.format("Expected Link not present on current page" + e);
			}
		return (result);
		
	}

	/**
	 * This method is used to drag and drop slider from one location to another
	 * location. Keyword is dragAndDropSlider. Parameters passed are
	 * locatorType,locatorValue.
	 */
	public String dragAndDropSlider(String locatorType, String locatorValue)
			throws Exception {
		try {
			Actions move = new Actions(driver);
			By locator;
			locator = locatorValue(locatorType, locatorValue);
			new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(locator));
			WebElement element = driver.findElement(locator);
			move.dragAndDropBy(element, 200, 0).build().perform();
			waitForAsync();
			KeyWordExecutionTest.bResult = true;

			error_message = "Element Found to perform drag and drop = ";
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;
		} catch (NoSuchElementException e) {
			KeyWordExecutionTest.bResult = false;

			log.info("e.getMessage()");
			System.err.format("No Element Found to perform drag and drop" + e);
			error_message = "No Element Found to perform drag and drop = " + e;
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;
		}
		return (result);
	}

	/**
	 * This method is used to drag and drop slider from one location to another
	 * location. Keyword is dragAndDropSlider. Parameters passed are
	 * locatorType,locatorValue.
	 */

	public String dragAndDropSliderMore(String locatorType, String locatorValue)
			throws Exception {
		try {
			Actions move = new Actions(driver);
			By locator;
			locator = locatorValue(locatorType, locatorValue);
			new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(locator));
			WebElement element = driver.findElement(locator);
			move.dragAndDropBy(element, 500, 0).build().perform();
			waitForAsync();
			KeyWordExecutionTest.bResult = true;

			error_message = "Element Found to perform drag and drop = ";
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;
		} catch (NoSuchElementException e) {
			KeyWordExecutionTest.bResult = false;

			log.info("e.getMessage()");
			System.err.format("No Element Found to perform drag and drop" + e);
			error_message = "No Element Found to perform drag and drop = " + e;
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;
		}

		return (result);
	}

	public void dragdrop() {
		WebElement LocatorFrom = driver.findElement(By
				.xpath("(//div[@id='ctlId-0-CheckBoxes-ranked']/div)[1]"));
		WebElement LocatorTo = driver.findElement(By
				.xpath("(//div[@id='ctlId-0-CheckBoxes-ranked']/div)[4]"));
		String xto = Integer.toString(LocatorTo.getLocation().x);
		String yto = Integer.toString(LocatorTo.getLocation().y);
		((JavascriptExecutor) driver)
				.executeScript(
						"function simulate(f,c,d,e){var b,a=null;for(b in eventMatchers)if(eventMatchers[b].test(c)){a=b;break}if(!a)return!1;document.createEvent?(b=document.createEvent(a),a==\"HTMLEvents\"?b.initEvent(c,!0,!0):b.initMouseEvent(c,!0,!0,document.defaultView,0,d,e,d,e,!1,!1,!1,!1,0,null),f.dispatchEvent(b)):(a=document.createEventObject(),a.detail=0,a.screenX=d,a.screenY=e,a.clientX=d,a.clientY=e,a.ctrlKey=!1,a.altKey=!1,a.shiftKey=!1,a.metaKey=!1,a.button=1,f.fireEvent(\"on\"+c,a));return!0} var eventMatchers={HTMLEvents:/^(?:load|unload|abort|error|select|change|submit|reset|focus|blur|resize|scroll)$/,MouseEvents:/^(?:click|dblclick|mouse(?:down|up|over|move|out))$/}; "
								+ "simulate(arguments[0],\"mousedown\",0,0); simulate(arguments[0],\"mousemove\",arguments[1],arguments[2]); simulate(arguments[0],\"mouseup\",arguments[1],arguments[2]); ",
						LocatorFrom, xto, yto);
	}

	public String dragAndDropSliderVertically() throws Exception {
		try {
			WebElement source = driver.findElement(By
					.xpath("(//div[@id='ctlId-0-CheckBoxes-ranked']/div)[1]"));

			driver.switchTo().frame(0);
			Actions dragAndDrop = new Actions(driver);

			// drag downwards
			int numberOfPixelsToDragTheScrollbarDown = 10;
			for (int i = 10; i < 150; i = i
					+ numberOfPixelsToDragTheScrollbarDown) {
				// this causes a gradual drag of the scroll bar, 10 units at a
				// time
				dragAndDrop.moveToElement(source).clickAndHold()
						.moveByOffset(0, numberOfPixelsToDragTheScrollbarDown)
						.release().perform();
			}

			// Thread.sleep(5000);
			waitForAsync();
			KeyWordExecutionTest.bResult = true;

			error_message = "Element Found to perform drag and drop = ";
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;
		} catch (NoSuchElementException e) {
			KeyWordExecutionTest.bResult = false;

			log.info("e.getMessage()");
			System.err.format("No Element Found to perform drag and drop" + e);
			error_message = "No Element Found to perform drag and drop = " + e;
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;
		}

		return (result);
	}

	public String dragAndDropVerticalshbjafly(String locatorType,
			String locatorValue) throws Exception {
		try {

			By locator;
			locator = locatorValue(locatorType, locatorValue);
			new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(locator));
			WebElement element = driver.findElement(locator);
			driver.switchTo().frame(0);
			Actions dragAndDrop = new Actions(driver);

			// drag downwards
			int numberOfPixelsToDragTheScrollbarDown = 10;
			for (int i = 10; i < 150; i = i
					+ numberOfPixelsToDragTheScrollbarDown) {
				// this causes a gradual drag of the scroll bar, 10 units at a
				// time
				dragAndDrop.moveToElement(element).clickAndHold()
						.moveByOffset(0, numberOfPixelsToDragTheScrollbarDown)
						.release().perform();
			}
			// Thread.sleep(5000);
			waitForAsync();
			KeyWordExecutionTest.bResult = true;

			error_message = "Element Found to perform drag and drop = ";
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;
		} catch (NoSuchElementException e) {
			KeyWordExecutionTest.bResult = false;

			log.info("e.getMessage()");
			System.err.format("No Element Found to perform drag and drop" + e);
			error_message = "No Element Found to perform drag and drop = " + e;
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;
		}

		return (result);
	}

	/**
	 * This method is used to check that expected text is coming or not. Keyword
	 * is isTextPresent. Parameters passed are
	 * locatorType,locatorValue,locatorTestData.
	 */

	public static String isTextPresent(String locatorType, String locatorValue,
			String locatorTestData) throws InterruptedException {
		try {
			By locator;
			locator = locatorValue(locatorType, locatorValue);
			new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(locator));
			WebElement element = driver.findElement(locator);

			String actualText = element.getText().toString().trim();
			System.out.println("The actual text present is : " + actualText);				
			
			if (actualText.contains(locatorTestData)) {
				
				System.out.println(locatorTestData
								+ " Expected text/value is present on the current page");
				log.info(locatorTestData + " is present on the current page");
				KeyWordExecutionTest.bResult = true;

				error_message = locatorTestData	+ " is present on the current page";
				result = KeyWordExecutionTest.bResult + separator
						+ error_message + separator;
			} else {
				System.out.println(locatorTestData + " Expected text/value is not present on the current page");
				log.info("Element not Found..");
				KeyWordExecutionTest.bResult = false;

				error_message = locatorTestData	+ " is not present on the current page";
				result = KeyWordExecutionTest.bResult + separator
						+ error_message + separator;
			}

		} catch (Exception e) {
			KeyWordExecutionTest.bResult = false;

			log.info("e.getMessage()");
			System.out
					.println(locatorTestData + " Expected text/value is not present on the current page");
			log.info(locatorTestData + " Text is not present on the current page");
			error_message = locatorTestData
					+ "is not present on the current page"
					+ "Text is checked by passing three parameters " + "\n";
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;
		}

		return (result);
	}


	/**
	 * This method is used to check that expected text is coming or not. Keyword
	 * is fetchTextValue. Parameters passed are locatorType,locatorValue.
	 */

	public String fetchTextValue(String locatorType, String locatorValue)
			throws Exception {
		String fetchedText = null;
		try {
			By locator;
			locator = locatorValue(locatorType, locatorValue);
			new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(locator));
			WebElement element = driver.findElement(locator);
			fetchedText = element.getText();
			// Thread.sleep(5000);
			KeyWordExecutionTest.bResult = true;

			System.out.println("Element Found.."
					+ " Text Value Fetched is-------- " + fetchedText);
			log.info("Element Found.." + " Text Value Fetched is-------- ");

		} catch (NoSuchElementException e) {
			KeyWordExecutionTest.bResult = false;

			log.info("e.getMessage()");
			System.err.format("No Element Found to fetch text.." + e);
		}
		return fetchedText;
	}

	/**
	 * This method is used to fetch the first integer value from the given text.
	 * Method name is gettingFirstIntegerValueFromtext. Parameters passed are
	 * locatorTestData.
	 * 
	 * @throws InterruptedException
	 */
	public int gettingFirstIntegerValueFromtext(String locatorTestData)
			throws InterruptedException {
		int percentagevalue = 0;
		try {

			String tempString = cleanuptags(locatorTestData);
			waitForAsync();
			if (tempString.matches(".*\\d.*")) {
				percentagevalue = Integer.parseInt((tempString));
				System.out.println("'" + locatorTestData + "' contains digit");
				System.out.println("Percentagevalue fetched ="
						+ percentagevalue + "\n");

			} else {
				System.out.println("'" + tempString
						+ "' does not contain a digit");
				System.out.println("Percentagevalue fetched is zero ="
						+ percentagevalue + "\n");
			}
		} catch (NoSuchElementException e) {
			KeyWordExecutionTest.bResult = false;
			log.info("e.getMessage()");
			System.err.format("No Element Found to fetch text.." + e);
		}
		return percentagevalue;
	}

	/**
	 * This method is used to find the total sum of all integers value fetched
	 * from all the locatorTestData. Method name is sum. Parameters passed are
	 * List<Number> numbers.
	 */

	public double sum(List<Number> numbers) {
		double retSum = 0;
		for (Number i : numbers) {
			retSum += i.doubleValue();
		}
		return retSum;
	}

	/**
	 * This method is used to fetch the first integer value from the given text.
	 * Method name is cleanuptags. Parameters passed are locatorTestData.
	 */

	public String cleanuptags(String locatorTestData) {
		return locatorTestData.replace("\n", "").replaceFirst(".*?(\\d+).*",
				"$1");
	}

	/**
	 * This method is used to fetch the texts of all contents. This method is
	 * used to fetch the integers value of all contents. This method is used to
	 * compare the text of all the contents with the locatorTestData passed from
	 * excel. This method is used to get the sum of all integers value fetched
	 * form the text content. This method is checking :Sum should be greater
	 * than 90. Keyword is fetchTextContentAndSum. Parameters passed are
	 * locatorType,locatorValues,locatorTestData.
	 */
	public String fetchTextContentAndSum(String locatorType,
			String locatorValues, String locatorTestData) throws Exception {
		try {

			StringTokenizer st = new StringTokenizer(locatorValues, ",");
			int countTokens = st.countTokens();
			StringTokenizer st2 = new StringTokenizer(locatorTestData, ",");
			int countTokens2 = st2.countTokens();

			final List<String> stringElements4 = new ArrayList<>(countTokens);
			final List<Number> numbers = new ArrayList<Number>(countTokens);
			List<String> StringElementsAlreadyPresent = new ArrayList<>(
					countTokens2);

			for (int i = 0; i < countTokens; i++) {
				String fetchedText = fetchTextValue(locatorType, st.nextToken());
				int percentagevalue = gettingFirstIntegerValueFromtext(fetchedText);
				stringElements4.add(fetchedText);
				numbers.add(percentagevalue);

			}

			System.out.println("Total count of Tokens== " + countTokens);
			System.out.println("StringElements fetched during runtime=="
					+ stringElements4);
			System.out.println("Numbers Fetched from the Elements==" + numbers);

			StringElementsAlreadyPresent.add(locatorTestData);
			System.out.println("StringElementsAlreadyPresent"
					+ StringElementsAlreadyPresent + "\n");

			contentMatchCheck(stringElements4, StringElementsAlreadyPresent);
			double sum = sum(numbers);
			System.out.println("Total sum value ==" + sum + "\n");
			if (sum >= 90) {
				System.out.println("Result = sum is greater than 90");
				KeyWordExecutionTest.bResult = true;

				error_message = locatorTestData
						+ " is not present on the current page"
						+ "Text is checked by passing three parameters " + "\n";
				result = KeyWordExecutionTest.bResult + separator
						+ error_message + separator;
			} else {
				System.out.println("Result = sum is not greater than 90");
				KeyWordExecutionTest.bResult = false;

				error_message = locatorTestData
						+ " is not present on the current page"
						+ "Text is checked by passing three parameters " + "\n";
				result = KeyWordExecutionTest.bResult + separator
						+ error_message + separator;
			}
			System.out
					.println("*****************TextContent And Sum is fetched for a curent page***********"
							+ "\n");
		} catch (NoSuchElementException e) {
			KeyWordExecutionTest.bResult = false;

			log.info("e.getMessage()");
			System.err.format("No Element Found to fetch text.." + e);
			error_message = locatorTestData
					+ " is not present on the current page"
					+ "Text is checked by passing three parameters " + "\n";
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;
		}
		return (result);

	}

	/**
	 * This method is used to check the text of all contents with the
	 * locatorTestData passed from excel. Method name is contentMatchCheck.
	 * Parameters passed are locatorValue,locatorTestData.
	 */

	public void contentMatchCheck(List<String> locatorValue,
			List<String> locatorTestData) throws InterruptedException {
		try {

			boolean value = locatorValue.retainAll(locatorTestData);
			waitForAsync();
			if (value = true)
				System.out.println("Content is matched  "
						+ "Value to be returned is=" + value + "\n");
			else
				System.out.println("Content is not matched  "
						+ "Value to be returned is=" + value + "\n");

		} catch (NoSuchElementException e) {
			KeyWordExecutionTest.bResult = false;
			log.info("e.getMessage()");
			System.err.format("No Element Found to fetch text.." + e);
		}
	}

	/**
	 * This method is used to close browser. KeyWord is close_Browser.
	 */

	public String close_Browser() {
		try {
			waitForAsync();
			driver.quit();

			KeyWordExecutionTest.bResult = true;

			error_message = "Browser is closed successfully";
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;
		} catch (NoSuchElementException e) {
			KeyWordExecutionTest.bResult = false;

			log.info("e.getMessage()");
			System.err.format("Browser is not closed successfully" + e);
			error_message = "Browser is not closed successfully" + e;
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;
		} catch (Exception e) {
			System.out.println("generic exception handler");
		}
		return (result);
	}

	public void message_builder(boolean flag) {
		System.out
				.println("------------------ Called from method Starts ------------------");
		System.out.println(Thread.currentThread().getStackTrace()[0]
				.getMethodName());
		System.out
				.println("------------------ Called from method Ends ------------------");
	}

	public String window_scroll() throws InterruptedException {
		try {
			System.out.println("inside window_scroll 1");
			waitForAsync();
			System.out.println("inside window_scroll 2");
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,500)", "");
			jse.executeScript("window.scrollBy(0,500)", "");
			waitForAsync();
			System.out.println("inside window_scroll 3");
			KeyWordExecutionTest.bResult = true;

			error_message = "Page is scrolled successfully";
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;
		} catch (WebDriverException e) {
			KeyWordExecutionTest.bResult = false;
			System.out.println("inside window_scroll 4");
			log.info("e.getMessage()");
			System.out.println(e.getMessage());
			error_message = "Page is not scrolled successfully = " + e;
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;
		}
		return (result);
	}

	public String window_scroll_up() throws InterruptedException {
		try {
			waitForAsync();
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,-500)", "");
			jse.executeScript("window.scrollBy(0,-500)", "");
			KeyWordExecutionTest.bResult = true;

			error_message = "Page is scrolled up successfully";
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;
		} catch (WebDriverException e) {
			KeyWordExecutionTest.bResult = false;

			log.info("e.getMessage()");
			System.out.println(e.getMessage());
			error_message = "Page is not scrolled  up successfully = " + e;
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;
		}
		return (result);
	}

	public static void robo(String locatorTestData) throws Exception {
		Calendar now = Calendar.getInstance();
		Robot robot = new Robot();
		BufferedImage screenShot = robot.createScreenCapture(new Rectangle(
				Toolkit.getDefaultToolkit().getScreenSize()));
		ImageIO.write(screenShot, "JPG", new File(
				"../Demo_Test/screenshots/" + "locatorTestData"
						+ formatter.format(now.getTime()) + ".jpg"));
		System.out.println(formatter.format(now.getTime()));

	}

	
	
	/** Method to handle switching between windows. */
	public void switchWindow() throws AWTException, InterruptedException {
		String winHandleBefore = driver.getWindowHandle();
		Set<String> handles = driver.getWindowHandles();
		String[] individualHandle = new String[handles.size()];
		Iterator<String> it = handles.iterator();
		int i = 0;
		while (it.hasNext()) {
			individualHandle[i] = (String) it.next();
			System.out.println("individualHandle[i]");
			i++;
		}
		System.out.println("individualHandle[1]" + individualHandle[1]);
		driver.switchTo().window(individualHandle[1]);
		
		waitForPageToLoad();
		Robot robot = new Robot();

		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_S);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_S);		
		waitForAsync();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);		
		waitForAsync();
		driver.close();
		waitForPageToLoad();
		driver.switchTo().window(winHandleBefore);

	}

	/** Method to get the last modified file from a folder */
	public static File lastFileModified(String dir) {
	    File fl = new File(dir);
	    File[] files = fl.listFiles(new FileFilter() {          
	        public boolean accept(File file) {
	            return file.isFile();
	        }
	    });
	    long lastMod = Long.MIN_VALUE;
	    File expectedFile = null;
	    for (File file : files) {
	        if (file.lastModified() > lastMod) {
	            expectedFile = file;
	            lastMod = file.lastModified();
	        }
	    }
	    return expectedFile;
	}


	public String addingShorcutKeysForFullPageScreenCaptureExtension()
			throws InterruptedException, AWTException {
		try {
			
			driver.get("chrome://extensions");
			Robot robot = new Robot();			
			waitForAsync();
			driver.switchTo().frame("extensions");
			driver.findElement(By.xpath("//*[@id='footer-section']/a[2]")).click();			
			waitForAsync();
			driver.findElement(By.xpath("//*[@class='command-shortcut-text']"))
					.sendKeys("Ctrl");

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_R);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_SHIFT);
			robot.keyRelease(KeyEvent.VK_R);

			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);			
			waitForAsync();

			driver.findElement(By.id("extension-commands-dismiss")).click();
			waitForAsync();
			// driver.close();
			driver.switchTo().defaultContent();
			KeyWordExecutionTest.bResult = true;

			error_message = "ShorcutKeysForFullPageScreenCaptureExtensionis added successfully";
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;
		} catch (WebDriverException e) {
			KeyWordExecutionTest.bResult = false;

			log.info("e.getMessage()");
			System.out.println(e.getMessage());
			error_message = "ShorcutKeysForFullPageScreenCaptureExtension is not added successfully= "
					+ e;
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;
		}
		return (result);
	}
	


	public String captureFullPageScreenshotUsingFullPageScreenCaptureExtension()
			throws AWTException, InterruptedException {
		try {

			waitForAsync();
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_R);

			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_SHIFT);
			robot.keyRelease(KeyEvent.VK_R);			
			waitForAsync();
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);			
			waitForPageToLoad();
			switchWindow();
			error_message = "FullPageScreenshot is captured successfully UsingFullPageScreenCaptureExtension";
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;

		} catch (WebDriverException e) {
			KeyWordExecutionTest.bResult = false;

			log.info("e.getMessage()");
			System.out.println(e.getMessage());
			error_message = "FullPageScreenshot is not captured successfully UsingFullPageScreenCaptureExtension = "
					+ e;
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;
		}
		return (result);
	}


	/**
	 * This method is used to open browser with Full Page Screen Capture.
	 * Keyword is open_Browser. Parameters passed is browserName.
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */

	public String open_ChromeBrowser_WithFullPageScreenCaptureExtension(
			String browserName) throws InterruptedException, IOException {
		try {
			if (browserName.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver",
						"../Demo_Test/chromedriver.exe");

				// Setting Desired capability in Chrome browser
				ChromeOptions chromeOptions = new ChromeOptions();
				chromeOptions
						.addExtensions(new File(
								userHome
										+ "\\"
										+ "AppData\\Local\\Google\\Chrome\\User Data\\Default\\Extensions\\fdpohaocaechififmbbbbbknoalclacl\\0.0.15_0.crx"));

				// System.out.println("2nd check");
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				capabilities.setCapability(ChromeOptions.CAPABILITY,
						chromeOptions);
				driver = new ChromeDriver(capabilities);

				// System.out.println("3rd check");
				waitForAsync();
				driver.manage().window().maximize();
				KeyWordExecutionTest.bResult = true;

				error_message = "ChromeBrowser_WithFullPageScreenCaptureExtension is successfully initiated ";
				result = KeyWordExecutionTest.bResult + separator
						+ error_message + separator;

			}
		} catch (WebDriverException e) {
			KeyWordExecutionTest.bResult = false;

			log.info("e.getMessage()");
			System.out.println(e.getMessage());
			error_message = "ChromeBrowser_WithFullPageScreenCaptureExtension cant be initiated = "
					+ e;
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;
		}
		// return KeyWordExecutionTest.bResult;
		return (result);
	}

    /**
	 * This method is used to set Chrome Download path browser. Keyword is
	 * enter_URL. Parameters passed is URL.
	 * 
	 * @return
	 * @throws AWTException
	 * @throws InterruptedException
	 */

	public String open_ChromeBrowser_WithFullPageScreenCaptureExtensionAndSetDownloadFilePath(
			String browserName) throws AWTException, InterruptedException {
		try {
			createFolderAtRunTime();
			String downloadFilepath = "C:\\" + current_DateTime;
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("download.default_directory", downloadFilepath);
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", chromePrefs);
			options.addExtensions(new File(
					userHome
							+ "\\"
							+ "AppData\\Local\\Google\\Chrome\\User Data\\Default\\Extensions\\fdpohaocaechififmbbbbbknoalclacl\\0.0.15_0.crx"));
			
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			cap.setCapability(ChromeOptions.CAPABILITY, options);
			WebDriver driver = new ChromeDriver(cap);
			waitForAsync();
			driver.get("https://www.google.com");
			driver.manage().window().maximize();

			Robot robot = new Robot();

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_S);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_S);			
			waitForPageToLoad();
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);			
			waitForPageToLoad();
			KeyWordExecutionTest.bResult = true;
			error_message = "ChromeBrowser_WithFullPageScreenCaptureExtensionAndSetDownloadFilePath is successfully initiated ";
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;
		} catch (WebDriverException e) {
			KeyWordExecutionTest.bResult = false;

			log.info("e.getMessage()");
			System.out.println(e.getMessage());
			error_message = "ChromeBrowser_WithFullPageScreenCaptureExtensionAndSetDownloadFilePathcant be initiated = "
					+ e;
			result = KeyWordExecutionTest.bResult + separator + error_message
					+ separator;
		}

		return (result);
	}



}
