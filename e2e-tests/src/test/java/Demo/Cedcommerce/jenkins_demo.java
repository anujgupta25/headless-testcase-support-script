package Demo.Cedcommerce;

import Demo.Cedcommerce.Resources.BaseClass;
import Demo.Cedcommerce.PageObjects.FirstPage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class jenkins_demo extends BaseClass {
	@Test (priority = 1)
	public void testgooglesearch() {
		FirstPage fp = new FirstPage(util);
		fp.goToGoogle("chrome");
	}
}
