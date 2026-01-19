package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

//HomePage is a Page Object representing the Home screen of the application
public class HomePage extends BasePage {

	// Constructor receives WebDriver from test and passes it to BasePage
	public HomePage(WebDriver driver) {

		// Calling the parent (BasePage) constructor
		// This initializes WebDriver and PageFactory elements for this page
		super(driver);

		// Any specific initialization for HomePage can be added below in future

	}

	@FindBy(xpath = "//span[normalize-space()='My Account']")
	WebElement MyAccountLink;
	@FindBy(xpath = "//a[normalize-space()='Register']")
	WebElement RegisterationLink;

	@FindBy(xpath = "(//a[normalize-space()='Login'])[1]")
	WebElement Loginlink;

	public void clickMyAccount() {
		MyAccountLink.click();
	}

	public void clickToRegister() {
		RegisterationLink.click();

	}

	public void ClickLogin() {
		Loginlink.click();

	}
}
