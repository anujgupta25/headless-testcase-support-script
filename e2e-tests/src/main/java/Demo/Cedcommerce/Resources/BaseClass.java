package Demo.Cedcommerce.Resources;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseClass {

	protected Utilities util = Utilities.getObject();

	protected Properties prop;

	@BeforeClass(alwaysRun = true)
	public void LaunchingMethod() throws Exception {
		util.openFirstPage(util, "firefox", null, util.getProperty("BaseUrl"));
	}
	
	@AfterClass(alwaysRun = true)
	public void QuitMethod() {
		util.closeBrowser();
	}

	public String getScreenShotPath(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) util.getDriver();
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destinationFile = System.getProperty("user.dir") + "reports/" + testCaseName + ".png";
		FileUtils.copyFile(source, new File(destinationFile));
		return destinationFile;
	}
}
