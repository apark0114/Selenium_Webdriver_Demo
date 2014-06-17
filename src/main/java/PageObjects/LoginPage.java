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



public class LoginPage extends BasePage{
	
	public LoginPage(WebDriver driver){
		super(driver);
		maximize();
	}
	//Define bunch of webelements here.
	@FindBy(css = "button.login-button")
	private WebElement loginButton;
	
	@FindBy(id= "pulse-login-frame")
	private WebElement pulseFrame;
	
	public void switchToLoginFrame(){
		driver.switchTo().defaultContent();
		driver.switchTo().frame(pulseFrame);
	}
	
	@FindBy(how=How.CSS, using="ul li.first-child.last-child a")
	private WebElement accountLogin;
	
	@FindBy(name="txtUserName")
	private WebElement emailField;
	
	@FindBy(name="txtPassword")
	private WebElement passwordField;
	
	@FindBy(css="input.button.login.submit")
	private WebElement submit;
	
	@FindBy(css="button.print-button")
	private WebElement printbutton;
	
	public HomePage loginAs(String username, String password){
		customWait(20);
		
		if(!loginButton.isDisplayed()){
			throw new ElementNotVisibleException("login button is not visible");
		}
		loginButton.click();
		
		customWait(10);
		
		try{
			switchToLoginFrame();
		}catch(Exception e){
			throw new IllegalStateException("This is not the login page");
		}
		
		customWait(15);
		
		accountLogin.click();
		
		emailField.sendKeys(username);
		passwordField.sendKeys(password);
				
		submit.click();
		
		driver.switchTo().defaultContent();
		
		try{
		waitUntilNot(By.id("pulse-login-frame"));
		}catch(TimeoutException e){
			throw new TimeoutException("Login failed.");
		}
		
		switchToTopic();
		waitUntil(By.cssSelector("iframe.pulse-frame"));
		return PageFactory.initElements(driver, HomePage.class);
	}
		
	public void waitUntil(){
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("")));
	}
	
	public void waitUntilNot(By path){
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(path));
	}
	

	public WebElement getLoginError(){
		switchToLoginFrame();
		waitUntil(By.cssSelector("ul#login_selector li div#output span.error"));
		return driver.findElement(By.cssSelector("ul#login_selector li div#output span.error"));
	}
	
	
}
