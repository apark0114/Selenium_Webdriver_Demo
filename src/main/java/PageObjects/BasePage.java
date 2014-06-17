package PageObjects;


import java.util.concurrent.TimeUnit;


import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
	protected WebDriver driver;
	public BasePage(WebDriver driver){
		this.driver = driver;
	}
	
	@FindBy(name="topic")
	WebElement topicFrame;
	
	/**
	 * Switches to iframe "topic".
	 * @return  BasePage Object.
	 */
	public BasePage switchToTopic(){
		driver.switchTo().defaultContent();
		driver.switchTo().frame(topicFrame);
		return this;
	}
	
	
	
	
	//<-------------------------------General methods----------------------------------------->
	
	//maximize the browser.
	public void maximize(){
		driver.manage().window().maximize();
	}
	
	/**
	 * Implicit wait.  Number second is passed as a parameter.
	 * @param seconds
	 */
	public void customWait(long seconds){
		driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
	}
	

	/**
	 * This is wraps the staled element and returns the refreshed element.
	 * @param e
	 * @return
	 */
	public WebElement staleElementWrapper(WebElement e){
		return e.findElement(By.xpath("."));
	}
	
	/**
	 * Implicit wait for an element by its path.  Default wait is 5 seconds.
	 * @param path
	 */
	public void waitUntil(By path){
		WebDriverWait wait = new WebDriverWait(driver, 5);
		try{
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(path));
		}catch(TimeoutException e){
			
		}
	}
	
	/**
	 * Implicit wait for an element to go invisible.  Default wait is 10 seconds.
	 * @param path
	 */
	public void waitUntilNot(By path){
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(path));
	}
}
