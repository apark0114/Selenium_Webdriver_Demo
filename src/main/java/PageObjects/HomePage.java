package PageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage{
	public HomePage(WebDriver driver){
		super(driver);
	}
	
	//WebElement title.
	@FindBy(css="head title")
	WebElement title;
	
	//
	@FindBy(name="topiccomments-html5")
	WebElement commentFrame;
	public BasePage switchToCommentFrame(){
		switchToTopic();
		customWait(10);
		driver.switchTo().frame(commentFrame);
		return this;
	}
	
	public String getTitle(){
		switchToTopic();
		return driver.getTitle();
	}
	
	@FindBy(css="div#status-text")
	WebElement commentBox;
	
	@FindBy(css="a[onclick ^= 'onShareClick']")
	WebElement submit;
	public HomePage leaveAComment(String text){
		switchToCommentFrame();		
		customWait(5);
		
		commentBox.sendKeys(text);
		customWait(5);
		
		waitUntil(By.cssSelector("a[onclick ^= 'onShareClick']"));
		submit.click();
		return this;
	}
	
	@FindBy(css="li.activity.first-child div.other div.generic div.activity-content p")
	WebElement firstComment;
	public String getFirstComment(){
		switchToCommentFrame();
		customWait(5);
		return staleElementWrapper(firstComment).getText();	
	}
	
	@FindBy(css ="li.activity.first-child div.other div.generic div.activity-content span")
	WebElement firstCommentSearchId;
	public String getFirstCommentId(){
		switchToCommentFrame();
		customWait(5);
		return staleElementWrapper(firstCommentSearchId).getAttribute("id");	
	}
	
	@FindBy(css="li.activity.first-child div.other div.generic a.delete")
	WebElement deleteFirstCommentButton;
	
	@FindBy(css="div.lightbox div.dialog ul.form li.dialog-actions span a")
	WebElement deleteButton;
	
	public HomePage deleteFirstComment(){
		switchToCommentFrame();
		customWait(5);
		
		Actions action = new Actions(driver);
		action.moveToElement(firstComment).build().perform();
		
		staleElementWrapper(deleteFirstCommentButton).click();
		
		customWait(5);
		
		deleteButton.click();
		
		try{
			waitUntilNot(By.cssSelector("li.activity.first-child div.other div.generic a.delete"));
		}
		catch(TimeoutException e){
		}
		
		return this;
	}
	
	@FindAll(value = {@FindBy(css="div#activity-set ul.activities-list li.activity")})
	List<WebElement> comments;
	public List<WebElement> getTotalComments(){
		switchToCommentFrame();
		try{
			waitUntil(By.cssSelector("div#activity-set ul.activities-list li.activity"));
		}catch(TimeoutException ex){
			
		}
		//List<WebElement> totalList = driver.findElements(By.cssSelector("div#activity-set ul.activities-list li.activity" ));
		List<WebElement> totalList = driver.findElements(By.cssSelector("div.activity-content p" ));

		
		return totalList;
	}
	
	@FindBy(css="span.logout a[href*='logoff']")
	WebElement logoutLink;
	public HomePage logout(){
		switchToCommentFrame();
		logoutLink.click();
		return this;
	}
	
	@FindBy(css="span.login a.loginPopupLink")
	public WebElement loginLink;
	
	@FindBy(css="span.register a[href*='register']")
	public WebElement registerLink;
	
	public void refresh(){
		driver.navigate().refresh();
	}

}
