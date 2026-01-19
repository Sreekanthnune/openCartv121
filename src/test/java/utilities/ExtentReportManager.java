package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

/**
 * Implements TestNG ITestListener to generate Extent Reports for test
 * execution. Captures test lifecycle events, logs results, attaches
 * screenshots, and sends the final report over email.
 */
public class ExtentReportManager implements ITestListener {

	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;

	String repName;

	@Override
	public void onStart(ITestContext testContext) {

		// Generates a timestamp for report file naming
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

		// Builds the final report file name
		repName = "Test-Report-" + timeStamp + ".html";

		// Sets up HTML report path/location
		sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);

		// Basic report look/metadata configuration
		sparkReporter.config().setDocumentTitle("Opencart Automation Report");
		sparkReporter.config().setReportName("Opencart Functional Testing");
		sparkReporter.config().setTheme(Theme.DARK);

		// Initializes Extent with spark reporter
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);

		// Static environment info for report header
		extent.setSystemInfo("Application", "Opencart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");

		// Fetches dynamic values from testng.xml (OS + Browser)
		String os = testContext.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System", os);

		String browser = testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);

		// Captures groups if defined in testng.xml
		List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
		if (!includedGroups.isEmpty()) {
			extent.setSystemInfo("Groups", includedGroups.toString());
		}
	}

	@Override
	public void onTestSuccess(ITestResult result) {

		// Logs successful test execution
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.PASS, result.getName() + " executed successfully");
	}

	@Override
	public void onTestFailure(ITestResult result) {

		// Logs failure and error details
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.FAIL, result.getName() + " failed");
		test.log(Status.INFO, result.getThrowable().getMessage());

		// Captures screenshot on failure and attaches to report
		try {
			String imgPath = new BaseClass().captureScreen(result.getName());
			test.addScreenCaptureFromPath(imgPath);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {

		// Logs skipped tests
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, result.getName() + " was skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onFinish(ITestContext testContext) {

		// Writes all results to the HTML report
		extent.flush();

		// Auto-opens the report when execution finishes
		String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
		File extentReport = new File(pathOfExtentReport);

		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*
		 * // Sends report via email (optional integration) try { URL url = new
		 * URL("file:///" + System.getProperty("user.dir") + "\\reports\\" + repName);
		 * 
		 * ImageHtmlEmail email = new ImageHtmlEmail(); email.setDataSourceResolver(new
		 * DataSourceUrlResolver(url)); email.setHostName("smtp.googlemail.com");
		 * email.setSmtpPort(465); email.setAuthenticator(new
		 * DefaultAuthenticator("srikanth.nune7@gmail.com", "password"));
		 * email.setSSLOnConnect(true); email.setFrom("srikanth.nune7@gmail.com"); //
		 * Sender address email.setSubject("Test Results");
		 * email.setMsg("Please find attached test execution report.");
		 * email.addTo("heyyguru@gmail.com"); // Receiver address email.attach(url,
		 * "Extent Report", "Attached test execution report"); email.send(); // Sends
		 * email with attached report
		 * 
		 * } catch (Exception e) { e.printStackTrace(); }
		 */
	}
}
