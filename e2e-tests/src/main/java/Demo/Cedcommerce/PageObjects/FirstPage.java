package Demo.Cedcommerce.PageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import Demo.Cedcommerce.Resources.Utilities;

public class FirstPage extends FirstPageOR {
	private static final Logger log = LogManager.getLogger(FirstPage.class);

	Utilities util;

	public FirstPage(Utilities util) {
		this.util = util;
		PageFactory.initElements(util.getDriver(), this);
	}

	public void goToGoogle(String browserName) {
		log.info("OPEN GOOGLE");
		log.info("TEST CASE goToGoogle IS RUNNING");

		// we expect the title “Google “ should be present
		String Expectedtitle = "Google";
		// it will fetch the actual title
		String Actualtitle = util.getDriver().getTitle();
		log.info("Before Assetion in " + browserName + " " + Expectedtitle + " " + Actualtitle);
		// it will compare actual title and expected title
		Assert.assertEquals(Actualtitle, Expectedtitle);
		// print out the result
		log.info("After Assertion in " + browserName + " " + Expectedtitle + " " + Actualtitle + " Title matched ");
		util.enterValue(searchBox, "hello");
		util.pressEnter();
	}
}
