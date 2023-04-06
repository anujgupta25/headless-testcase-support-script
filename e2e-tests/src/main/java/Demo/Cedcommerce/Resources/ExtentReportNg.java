package Demo.Cedcommerce.Resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportNg {
	static ExtentReports extent;

	public static ExtentReports getReportObject() {
//			Create directory where we want to generate reports.
		String path = System.getProperty("user.dir") + "/reports/index.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);

//			Set Report Name
		reporter.config().setReportName("Automation");

//			Set Document title
		reporter.config().setDocumentTitle("Cedcommerce (Jenkins Demo)");

//			Creating class that drives all the reporting execution.
		extent = new ExtentReports();
		extent.attachReporter(reporter);

//			Adding tester name
		extent.setSystemInfo("Tested by", "Abhay Hayaran");
		return extent;
	}
}