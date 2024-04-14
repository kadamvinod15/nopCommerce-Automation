package com.nopCommerce.helpClasses;

import static org.testng.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ValueInput {

	public static WebDriver driver;
	public static JavascriptExecutor js = (JavascriptExecutor) driver;

	/**
	 * {@code} Because of static variable driver get call only once
	 * 
	 * @param driverName = chrome, html, chrome with headless
	 * @param webSite    = Name of the website
	 * @param driverPath = Driver location
	 * @return return type is WebDriver
	 */
	public static WebDriver initDriver(String driverName, String webSite) {
		switch (driverName.toLowerCase()) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;
		case "headless":
			WebDriverManager.chromedriver().setup();
			ChromeOptions optn = new ChromeOptions();
			optn.addArguments("--headless");
			driver = new ChromeDriver(optn);
			break;
		}
		driver.get(webSite);
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30)); // If page not open in 30 sec then will give
																			// timeout exception
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30)); // NoSuchElement Exception
		
		return driver;
	}

	void explicitWait(String by, String locValue, String action) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		switch (action.toLowerCase()) {

		case "clickable":
			wait.until(ExpectedConditions.elementToBeClickable(findElement(by, locValue)));
			break;
		case "present":
			wait.until(ExpectedConditions.presenceOfElementLocated((By) findElement(by, locValue)));
			break;
		case "selected":
			wait.until(ExpectedConditions.elementToBeSelected(findElement(by, locValue)));
			break;
		}
	}

	/**
	 * {@code} To findout web-element
	 * 
	 * @param by       = id, xpath, ....etc
	 * @param locValue = Locator Value
	 * @return is WebElement
	 */
	public WebElement findElement(String by, String locValue) {
		WebElement we = null;
		
		switch (by.toLowerCase()) {
		case "id":
			we = driver.findElement(By.id(locValue));
			break;
		case "xpath":
			we = driver.findElement(By.xpath(locValue));
			break;
		default:
			System.out.println("Only id and xpath will accept");
			break;
		}
		return we;
	}

	/**
	 * {@code} To enter value in field
	 * @author Vinod Kadam
	 * @param by        = id, xpath, ....etc
	 * @param locValue  = Locator Value
	 * @param fieldData = Field Data
	 */
	public void setTextField(String by, String locValue, String fieldData) {
		WebElement el = findElement(by, locValue);
		explicitWait(by, locValue, "clickable");
		el.clear();
		el.sendKeys(fieldData);
		sleep(2000);
	}

	/**
	 * {@code} To select dropdown
	 * @author Vinod Kadam
	 * @param condition = index, VT, Value
	 * @param by        = id, xpath, ....etc
	 * @param locValue  = Locator Value
	 * @param fieldData = Field Data
	 */
	public void setDropdown(String condition, String by, String locValue, String fieldData) {
		Select sl;

		sl = new Select(findElement(by, locValue));
		explicitWait(by, locValue, "clickable");
		switch (condition.toLowerCase()) {

		case "index":
			int numberInt = Integer.parseInt(fieldData);
			sl.selectByIndex(numberInt);
			break;
		case "vt":
			sl.selectByVisibleText(fieldData);
			break;
		case "value":
			sl.selectByValue(locValue);
			break;
		}
	}

	/**
	 * {@code} To click on field, form, button
	 * @author Vinod Kadam
	 * @param by       = id, xpath, ....etc
	 * @param locValue = Locator Value
	 */
	public void elementClick(String by, String locValue) {
		explicitWait(by, locValue, "clickable");
		try {
			findElement(by, locValue).click();
		} catch (Exception e) {
			js.executeScript("arguments[0].click();", locValue);
		}
		sleep(2000);
	}

	/**
	 * {@code} To get text
	 * @author Vinod Kadam
	 * @param by       = id, xpath, ....etc
	 * @param locValue = Locator Value
	 * @return Text Element
	 */
	public String getTextByElement(String by, String locValue) {
		String data = null;
		data = findElement(by, locValue).getText();
		return data;
	}

	/**
	 * {@code} To get the title
	 * @author Vinod Kadam
	 * @return Title name
	 */
	public String getTitle() {
		String data = driver.getTitle();
		return data;
	}

	/**
	 * {@code} To get the value of the attribute
	 * @author Vinod Kadam
	 * @param by       = id, xpath, ....etc
	 * @param locValue = Locator Value
	 * @param attName  = Name of Attribute
	 * @return value of attribute
	 */
	public String getAttributByElement(String by, String locValue, String attName) {
		String data = null;
		data = findElement(by, locValue).getAttribute(attName);
		return data;
	}
	
	/**
	 * @author Vinod Kadam
	 * @param by       = id, xpath, ....etc
	 * @param locValue = Locator Value
	 * @return         = It check whether element display or not
	 */
	public boolean isDisplay(String by, String locValue) {
		WebElement el = findElement(by, locValue);
		return el.isDisplayed();
	}
	
	/**
	 * @author Vinod Kadam
	 * @param by       = id, xpath, ....etc
	 * @param locValue = Locator Value
	 * @return         = It check whether element enabled or not
	 */
	public boolean isEnable(String by, String locValue) {
		WebElement el = findElement(by, locValue);
		return el.isEnabled();
	}
	
	/**
	 * @author Vinod Kadam
	 * @param by       = id, xpath, ....etc
	 * @param locValue = Locator Value
	 * @return         = It check whether element selected or not
	 */
	public boolean isSelect(String by, String locValue) {
		WebElement el = findElement(by, locValue);
		return el.isSelected();
	}

	/**
	 * @author Vinod Kadam
	 * @param action   = Enter what action you want to perform
	 * @param by       = id, xpath, ....etc
	 * @param locValue = Locator Value
	 */
	public void actionMethods(String action, String by, String locValue) {
		Actions act = new Actions(driver);
		if ("click".equalsIgnoreCase(action)) {
			act.click().perform();
		}
		if ("doubleclick".equalsIgnoreCase(action)) {
			act.doubleClick(findElement(by, locValue)).perform();
		}
		if ("MouseHover".equalsIgnoreCase(action)) {
			act.moveToElement(findElement(by, locValue)).perform();
		}
	}

	/**
	 * @author Vinod Kadam
	 * @param sleepTime = In milliSeconds
	 */
	public void sleep(long sleepTime) {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * {@code} To close the current wiondow
	 * @author Vinod Kadam
	 */
	public void closeDriver() {
		driver.close();
	}

	/**
	 * {@code} To close the all wiondow
	 * @author Vinod Kadam
	 */
	public void quiteDriver() {
		driver.quit();
	}

	/**
	 * @author Vinod Kadam
	 * @param by       = id, xpath, ....etc
	 * @param locValue = Locator Value {@code} To switch from one frame to another
	 *                 frame
	 */
	public void frameSwitch(String by, String locValue) {
		WebElement el = findElement(by, locValue);
		driver.switchTo().frame(el);
	}

	/**
	 * @author Vinod Kadam 
	 * {@code} To switch from nested frame to parent frame
	 */
	public void switchToParentFrame() {
		driver.switchTo().parentFrame();
	}

	/**
	 * @author Vinod Kadam 
	 * {@code} Switch to default content
	 */
	public void switchToDefaultContent() {
		driver.switchTo().defaultContent();
	}

	/**
	 * @author vinod Kadam
	 * @param alert accepted
	 */
	public void alertAccept() {
		try {
			System.out.println("Alert name is: " + driver.switchTo().alert().getText());
			driver.switchTo().alert().accept();
		} catch (Exception e) {
			System.out.println("Alert not present");
		}
	}

	/**
	 * @author vinod Kadam
	 * @param alert reject
	 */
	public void alertReject() {
		try {
			System.out.println("Alert name is: " + driver.switchTo().alert().getText());
			driver.switchTo().alert().dismiss();
		} catch (Exception e) {
			System.out.println("Alert not present");
		}
	}
	
	/**
	 * @author vinod Kadam
	 * @param actualResult   = Get from webElement
	 * @param expectedResult = Get from excel sheet
	 * @param field          = Field name
	 */
	public void hardAssert(String actualResult, String expectedResult, String field) {
		try {
			assertEquals(actualResult, expectedResult, field);
		} catch (Exception e) {
			System.out.println("Value Not Match => " + actualResult + " : " + expectedResult);
		}
	}

	/**
	 * @author vinod Kadam
	 * @param actualResult   = Get from webElement
	 * @param expectedResult = Get from excel sheet
	 * @param field          = Field name
	 */
	public void softAssert(String actualResult, String expectedResult, String field) {
		SoftAssert sa = new SoftAssert();
		sa.assertEquals(actualResult, expectedResult, field);
	}

	/**
	 * @author vinod Kadam
	 * @param propName = Key present in .properties file
	 * @return data in type of String
	 */
	public String propertiesFile(String propName) {
		FileInputStream file = null;
		Properties prop = new Properties();
		try {
			file = new FileInputStream("src/main/resources\\nopCommerce_TestData.properties");
			prop.load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String data = prop.getProperty(propName);
		return data;
	}

}
