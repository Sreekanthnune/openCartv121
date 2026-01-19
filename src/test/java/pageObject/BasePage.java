package pageObject;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.support.PageFactory;

public class BasePage {

	// Reference to WebDriver shared by all pages
	WebDriver driver;

	// Constructor gets WebDriver instance from test or other page object
	public BasePage(WebDriver driver) {

		// Assigning the incoming driver so this class can use it
		this.driver = driver;

		/*
		 * PageFactory initializes all WebElements declared in the child page classes
		 * using @FindBy annotations, so we don't need to manually call
		 * driver.findElement()
		 */
		PageFactory.initElements(driver, this);
	}
}
