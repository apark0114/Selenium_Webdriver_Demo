package PageObjects;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;


/**
 * This is the log-in page, before requesting for homepage.
 * Use this class to login to the page.
 * @author Albert Park
 *
 */
public class LoginPage extends BasePage{
	
	public LoginPage(WebDriver driver){
		super(driver);
		maximize();
	}
	//login button web element.
	@FindBy(css = "button.login-button")
	private WebElement loginButton;
	
	//pop up frame that appears after the login button is click.
	@FindBy(id= "pulse-login-frame")
	private WebElement pulseFrame;
	
	//To switch to log-in frame before entering your credentials.
	public void switchToLoginFrame(){
		driver.switchTo().defaultContent();
		driver.switchTo().frame(pulseFrame);
	}
	
	/**
	 * account login button element in log-in frame.
	 */
	@FindBy(how=How.CSS, using="ul li.first-child.last-child a")
	private WebElement accountLogin;
	
	/**
	 * Username text field element in login-frame.
	 */
	@FindBy(name="txtUserName")
	private WebElement emailField;
	
	/**
	 * Password text field element in login-frame.
	 */
	@FindBy(name="txtPassword")
	private WebElement passwordField;
	
	/**
	 * submit button element in login-frame.
	 */
	@FindBy(css="input.button.login.submit")
	private WebElement submit;
	
	/**
	 * print button element outside the login-frame. Not necessary.
	 */
	@FindBy(css="button.print-button")
	private WebElement printbutton;
	
	/**
	 * Log into home page by entering username and password.
	 * @param username
	 * @param password
	 * @return HomePage object. After this method is used, use Homepage class to navigate.
	 */
	public HomePage loginAs(String username, String password){
		//Depends on the server load, login button may take some time to load.  Wati until the button is visible.
		customWait(20);
		
		//if the login button doesn't appear in given time, fail the test by showing Element NotvisibleEception.
		if(!loginButton.isDisplayed()){
			throw new ElementNotVisibleException("login button is not visible");
		}
		//if the login button showed in given time, click the login button.
		loginButton.click();
		
		//wait until the login frame appears that takes credentials.
		customWait(10);
		
		//switch to login frame.  If the login frame doesn't appear in given time fail the test by throwing IllegalStateException.
		try{
			switchToLoginFrame();
		}catch(Exception e){
			throw new IllegalStateException("This is not the login page");
		}
		
		customWait(15);
		
		//click the 2nd login button in login frame. 
		accountLogin.click();
		
		//type your username in email field.
		emailField.sendKeys(username);
		//type your password in password field.
		passwordField.sendKeys(password);
				
		//click submit button.
		submit.click();
		
		//switch to default outter frame.
		driver.switchTo().defaultContent();
		
		//Wait until log-in frame disappears, while validating user's credentials. If the frame is still visible, that means login has failed.
		try{
		waitUntilNot(By.id("pulse-login-frame"));
		}catch(TimeoutException e){
			throw new TimeoutException("Login failed.");
		}
		
		//switch to topic of the main page.
		switchToTopic();
		//wait for user accessible elements appear.
		waitUntil(By.cssSelector("iframe.pulse-frame"));
		//finally return Homepage object after login is successful.
		return PageFactory.initElements(driver, HomePage.class);
	}
		

	/**
	 * If the login fails, use this method to get the login error element.
	 * @return error message WebElement.
	 */
	public WebElement getLoginError(){
		switchToLoginFrame();
		waitUntil(By.cssSelector("ul#login_selector li div#output span.error"));
		return driver.findElement(By.cssSelector("ul#login_selector li div#output span.error"));
	}
	
	
}
