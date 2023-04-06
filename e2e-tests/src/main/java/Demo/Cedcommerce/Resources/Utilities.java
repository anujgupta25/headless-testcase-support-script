package Demo.Cedcommerce.Resources;

import java.io.FileInputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import org.openqa.selenium.remote.Browser;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Utilities {
	private static Logger log = LogManager.getLogger(Utilities.class.getName());
	private static final ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

	private static Utilities util;
	private Actions action;
	private Workbook workbook;
	private Properties prop;
	private Sheet sheetObj;
	private String parentWindow;
	private String childWindow;

	private Utilities() {

	}

	public static Utilities getObject() {
		if (util == null) {
			util = new Utilities();
		}
		return util;
	}

	public WebDriver getDriver() {
		return driver.get();
	}

	public void LaunchBrowser(String browserName, String systemType) throws Exception {
		if (systemType != null && systemType.equalsIgnoreCase("grid")) {
			// To run within docker based selenium grid
			 DesiredCapabilities cap = new DesiredCapabilities();
			 
			 if (browserName.equalsIgnoreCase("Firefox")) {
				 cap.setBrowserName("firefox");
			 } else if (browserName.equalsIgnoreCase("Chrome")) {
				 cap.setBrowserName("chrome");
			 } else if (browserName.equalsIgnoreCase("Edge")) {
				 cap.setBrowserName("MicrosoftEdge");
			 }

			 driver.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap));
		} else {
			if (browserName.equalsIgnoreCase("Firefox")) {
				WebDriverManager.firefoxdriver().setup();
				FirefoxOptions options = new FirefoxOptions();
				options.setHeadless(true);
				driver.set(new FirefoxDriver(options));
			} else if (browserName.equalsIgnoreCase("Chrome")) {
				WebDriverManager.chromedriver().setup();
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--no-sandbox");
//				options.addArguments("--headless");
				options.addArguments("--disable-dev-shm-usage");
				options.addArguments("--remote-allow-origins=*");
				driver.set(new ChromeDriver(options));
			} else if (browserName.equalsIgnoreCase("safari")) {
				WebDriverManager.safaridriver().setup();
				driver.set(new SafariDriver());
			} else if (browserName.equalsIgnoreCase("edge")) {
				WebDriverManager.edgedriver().setup();
				driver.set(new EdgeDriver());
			}
		}

		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		getDriver().manage().window().maximize();
	}

	public void openUrl(String url) {
		try {
			getDriver().get(url);
		} catch (Exception e) {

		}
	}

	public void openFirstPage(Utilities util, String browserName, String systemType, String Url) throws Exception {
		util.LaunchBrowser(browserName, systemType);
		util.openUrl(Url);
	}
	
	public void closeBrowser() {
		getDriver().quit();
	}

	public void enterValue(WebElement element, String value) {
		element.clear();
		element.sendKeys(Keys.CONTROL + "a");
		element.sendKeys(value);
	}

	public void enterSingleValue(WebElement element, char value) {
		element.clear();
		element.sendKeys(Keys.CONTROL + "a");
		element.sendKeys(String.valueOf(value));
	}

	public void pressEnter() {
		action = new Actions(getDriver());
		action.keyUp(Keys.ENTER).build().perform();
		action.keyDown(Keys.ENTER).build().perform();
	}

	public void pressTab() {
		action = new Actions(getDriver());
		action.keyUp(Keys.TAB).build().perform();
		action.keyDown(Keys.TAB).build().perform();
	}

	public void pressEscape() {
		action = new Actions(getDriver());
		action.keyUp(Keys.ESCAPE).build().perform();
		action.keyDown(Keys.ESCAPE).build().perform();
	}

	public void click(WebElement element) {
		element.click();
	}

	public void actionClick(WebElement element) {
		action = new Actions(getDriver());
		action.click(element).build().perform();
	}

	public void scrollToBottom() {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	public void scrollToTop() {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollTo(0, -document.body.scrollHeight)");
	}

	public void jsClick(WebElement element) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeAsyncScript("arguments[0].click();", element);
	}

	public String extractValueByAttributes(WebElement element, String attributeName) {
		return element.getAttribute(attributeName);
	}

	public String getTagValue(WebElement element) {
		return element.getText();
	}

	public boolean isElementDisplyed(WebElement element) {
		return element.isDisplayed();
	}

	public boolean isElementVisible(WebElement element) {

		if ((getTagValue(element).length()) > 0) {
			return true;
		}
		return false;
	}

	public boolean isElementEnabled(WebElement element) {
		return element.isEnabled();
	}

	public boolean isElementSelected(WebElement element) {
		return element.isSelected();
	}

	public void getWindoHandleByUrl(String ExpURLvalue) {
		Set<String> AllWindowValue = getDriver().getWindowHandles();
		for (String AllValue : AllWindowValue) {
			getDriver().switchTo().window(AllValue);
			String getTitleValue = getDriver().getCurrentUrl();
			if (getTitleValue.contains(ExpURLvalue)) {
				break;
			}
		}
	}

	public void goToChildWindow() {
		Set<String> windows = getDriver().getWindowHandles();
		Iterator<String> itr = windows.iterator();
		parentWindow = itr.next();
		childWindow = itr.next();
		getDriver().switchTo().window(childWindow);
	}

	public void goToParentWindow() {
		Set<String> windows = getDriver().getWindowHandles();
		Iterator<String> itr = windows.iterator();
		parentWindow = itr.next();
		childWindow = itr.next();
		getDriver().switchTo().window(parentWindow);
	}

	public String getPageURL() {
		return getDriver().getCurrentUrl();
	}

	public String getPageTitle() {
		return getDriver().getTitle();
	}

	public boolean clickOnEachElementOfList(List<WebElement> elementList, WebElement elementToBeDisplayed) {
		for (WebElement element : elementList) {
			element.click();
			return isElementDisplyed(elementToBeDisplayed);
		}
		return false;
	}

	public void failIfTimeIsExceed(List<WebElement> allQuerySugesstion, int seconds) {
		new WebDriverWait(getDriver(), Duration.ofSeconds(seconds))
				.until(ExpectedConditions.visibilityOfAllElements(allQuerySugesstion));
	}

	public void failIfTimeIsExceed(WebElement element, int seconds) {
		new WebDriverWait(getDriver(), Duration.ofSeconds(seconds)).until(ExpectedConditions.visibilityOf(element));
	}

	public void waitUntilElementIsVisible(WebElement element) {
		new WebDriverWait(getDriver(), Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(element));
	}

	public void waitUntilElementIsInvisible(WebElement element) {
		new WebDriverWait(getDriver(), Duration.ofSeconds(30)).until(ExpectedConditions.invisibilityOf(element));
	}

	public void hold(int seconds) {
		seconds *= 1000;
		try {
			Thread.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void refreshPage() {
		getDriver().navigate().refresh();
	}

	public void copy(WebElement element) {
		element.sendKeys(Keys.CONTROL + "a");
		element.sendKeys(Keys.CONTROL + "c");
	}

	public void paste(WebElement element) {
		action = new Actions(getDriver());
		element.sendKeys(Keys.CONTROL + "a");

		element.sendKeys(Keys.CONTROL + "v");
	}

	public boolean isPasswordHave8Characters(String password) {

		if (password.length() > 7) {
			return true;
		}
		return false;
	}

	public void selectFromList(List<WebElement> listOfElements, String value) {
		for (WebElement element : listOfElements) {
			if (element.getText().equals(value)) {
				element.click();
				break;
			}
		}
	}

	public boolean selectedInterest(List<WebElement> listOfElements, String interestValue) {
		for (WebElement interest : listOfElements) {
			return interest.getText().equalsIgnoreCase(interestValue);
		}
		return false;
	}

	public void selectFromListByAttribute(List<WebElement> listOfElements, String attribute, String position) {
		for (WebElement element : listOfElements) {
			if (element.getAttribute(attribute).equalsIgnoreCase(element + position)) {
				element.click();
				break;
			}
		}
	}

	public int getSize(List<WebElement> list) {
		return list.size();
	}

	public void goBack() {
		Actions action = new Actions(getDriver());
		action.keyDown(Keys.ALT);
		action.keyDown(Keys.ARROW_LEFT);
		action.keyUp(Keys.ARROW_LEFT);
		action.keyUp(Keys.ALT);
	}

	public void moveToElement(WebElement element) {
		Actions action = new Actions(getDriver());
		action.moveToElement(element).build().perform();
	}

	public String getProperty(String key) {
		Properties prop = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(
					System.getProperty("user.dir") + "/src/main/java/Demo/Cedcommerce/Resources/config.Properties");
		} catch (Exception e) {

		}

		try {
			prop.load(fis);
		} catch (Exception e) {

		}

		if (key == "BaseUrl")
			return prop.getProperty(key);
		
		return key;
	}

	public String getToday() {
		Date currentDate = new Date();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d");

		String todaysDate = simpleDateFormat.format(currentDate);

		simpleDateFormat = new SimpleDateFormat("MMM");

		String todaysMonthName = simpleDateFormat.format(currentDate);

		simpleDateFormat = new SimpleDateFormat("MM");

		String todaysMonth = simpleDateFormat.format(currentDate);

		simpleDateFormat = new SimpleDateFormat("YYYY");

		String todaysYear = simpleDateFormat.format(currentDate);

		simpleDateFormat = new SimpleDateFormat("dd");

		String todays2DigitDate = simpleDateFormat.format(currentDate);

		String dateInFormat = todaysMonth + "/" + todays2DigitDate + "/" + todaysYear;

		return dateInFormat;

	}

	public String getTodaysDate() {

		Date currentDate = new Date();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d");

		String todaysDate = simpleDateFormat.format(currentDate);
		return todaysDate;
	}

	public String getCurrentMonth() {

		Date currentDate = new Date();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM");

		String todaysMonth = simpleDateFormat.format(currentDate);

		return todaysMonth;
	}

	public String getCurrentYear() {

		Date currentDate = new Date();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY");

		String todaysYear = simpleDateFormat.format(currentDate);

		return todaysYear;
	}

	public String extractWordAfterACharacter(String str, String character, int position) {
		String arr[] = str.split(character);
		return arr[position];

	}

	public void inpuData(WebElement webEle, String inputvalue) {

		try {
			webEle.clear();
			webEle.sendKeys(inputvalue);
		} catch (StaleElementReferenceException e) {
			webEle.clear();
			webEle.sendKeys(inputvalue);
		} catch (ElementNotInteractableException e) {

		}
	}

	public int getRowCountofTcID(Sheet sheetobj, String testcaseIdName) {
		int activateRowNum = sheetobj.getLastRowNum();
		int count = 0;
		for (int i = 1; i <= activateRowNum; i++) {
			Row rowObj = sheetobj.getRow(i);
			Cell cellObj = rowObj.getCell(1);
			String actualCellvalue = cellObj.getStringCellValue();
			if (actualCellvalue.equals(testcaseIdName)) {
				count++;
			}
		}
		return count;
	}

	public void logInfo(String msg) {
		log.info(msg);
	}

	public void logError(String msg) {
		log.error(msg);
	}
}
