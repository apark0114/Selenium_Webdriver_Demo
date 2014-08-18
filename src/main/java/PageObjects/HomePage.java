package PageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
/**
 * Main HomePage class that has access to elements inside comment frame after user logs into the page.
 * Most elements and method to navigate in users' page can be found here.
 * @author Albert Park
 *
 */
public class HomePage extends BasePage{
	public HomePage(WebDriver driver){
		super(driver);
	}
	
	//Home page title element.
	@FindBy(css="head title")
	WebElement title;
	
	//Comment frame element.
	@FindBy(name="topiccomments-html5")
	WebElement commentFrame;
	
	/**
	 * To switch to comment frame to access to comment related elements.
	 * @return HomePage object
	 */
	public HomePage switchToCommentFrame(){
		//switch to topic frame before accessing the comment frame.
		switchToTopic();
		//wait in case Javascript is still loading the comment frame.
		customWait(10);
		//switch to comment frame before accessing commments.
		driver.switchTo().frame(commentFrame);
		return this;
	}
	
	/**
	 * @return String value of the title in page.
	 */
	public String getTitle(){
		//Switch to topic frame before accessing the title.
		switchToTopic();
		return driver.getTitle();
	}
	
	/**
	 * Comment text box element to input comments by users.
	 */
	@FindBy(css="div#status-text")
	WebElement commentBox;
	
	/**
	 * Submit button element that shares comments.
	 */
	@FindBy(css="a[onclick ^= 'onShareClick']")
	WebElement submit;
	
	/**
	 * To leave a comment use this method with comment String passed as a parameter.
	 * @param String comment text
	 * @return HomePage object.
	 */
	public HomePage leaveAComment(String text){
		//switch to comment frame before accessing comment text box.
		switchToCommentFrame();		
		//wait if comment box is still loading.
		customWait(5);
		
		//Type comments in comment box.
		commentBox.sendKeys(text);
		//Wait if the text is being sent.
		customWait(5);
		
		//wait until share button is visible after the comment has been sent to comment box.
		waitUntil(By.cssSelector("a[onclick ^= 'onShareClick']"));
		//click the submit button to share the comment.
		submit.click();
		return this;
	}
	
	/**
	 * @return first comment text in the web page in String.
	 */
	@FindBy(css="li.activity.first-child div.other div.generic div.activity-content p")
	WebElement firstComment;
	public String getFirstComment(){
		//switch to comment frame to access before accessing comments.
		switchToCommentFrame();
		//wait for the comment texts if its loading.
		customWait(5);
		//refresh the first comment element, if there is a new comment after the Homepage object was created.
		return staleElementWrapper(firstComment).getText();	
	}
	
	/**
	 * @return first comment id value in the web page in string
	 */
	@FindBy(css ="li.activity.first-child div.other div.generic div.activity-content span")
	WebElement firstCommentSearchId;
	public String getFirstCommentId(){
		//switch to comment frame.
		switchToCommentFrame();
		//wait while first comment is loading.
		customWait(5);
		//refresh the first comment element, if there is a new comment after the Homepage object was created and return the comment id.
		return staleElementWrapper(firstCommentSearchId).getAttribute("id");	
	}
	
	/**
	 * Delete button that belongs to the first comment in the page.
	 */
	@FindBy(css="li.activity.first-child div.other div.generic a.delete")
	WebElement deleteFirstCommentButton;
	
	/**
	 * Delete confirmation button element after the delete comment button has been clicked.
	 */
	@FindBy(css="div.lightbox div.dialog ul.form li.dialog-actions span a")
	WebElement deleteButton;
	
	/**
	 * Delete the first comment in page.
	 * @return HomePage object.
	 */
	public HomePage deleteFirstComment(){
		//switch to comment frame before accessing the comment element.
		switchToCommentFrame();
		//wait if the first comment is loading.
		customWait(5);
		
		//create an action object to use the cursor.
		Actions action = new Actions(driver);
		//use the action object to hover over the first comment.
		action.moveToElement(firstComment).build().perform();
		
		//refresh first comment if it's loading, then click the delete button.
		staleElementWrapper(deleteFirstCommentButton).click();
		
		//wait while the delete confirmation prompt is loading.
		customWait(5);
		
		//click the delete button is confirmation dialog.
		deleteButton.click();
		
		//wait until the delete button disappears while the comment is being removed.
		//Time exception by WaitUntilNot may occur, catch the exception.
		try{
			waitUntilNot(By.cssSelector("li.activity.first-child div.other div.generic a.delete"));
		}
		catch(TimeoutException e){
		}
		
		return this;
	}
	
	/**
	 * List of all comments web elements in the page.
	 */
	@FindAll(value = {@FindBy(css="div#activity-set ul.activities-list li.activity")})
	List<WebElement> comments;
	
	/**
	 * Retrieve all comments in the page.
	 * @return list of all comments in WebElement object.
	 */
	public List<WebElement> getTotalComments(){
		//switch to comment frame before accessing the comments.
		switchToCommentFrame();
		//wait until all comments load in the page.  If it times out, before all comments loaded, catch TimeoutException.
		try{
			waitUntil(By.cssSelector("div#activity-set ul.activities-list li.activity"));
		}catch(TimeoutException ex){
			
		}
		//List<WebElement> totalList = driver.findElements(By.cssSelector("div#activity-set ul.activities-list li.activity" ));
		//Store a list of all comments in the page.
		List<WebElement> totalList = driver.findElements(By.cssSelector("div.activity-content p" ));

		return totalList;
	}
	
	/**
	 * Logout link web element.
	 */
	@FindBy(css="span.logout a[href*='logoff']")
	WebElement logoutLink;
	
	/**
	 * Log out of user's account and back to LoginPage.
	 * @return LoginPage object.
	 */
	public LoginPage logout(){
		//switch to comment frame before accessing the log out link.
		switchToCommentFrame();
		//click the log out link element to log out.
		logoutLink.click();
		return PageFactory.initElements(driver, LoginPage.class);
	}
	
	/**
	 * Login link web element.
	 */
	@FindBy(css="span.login a.loginPopupLink")
	public WebElement loginLink;
	
	/**
	 * Register link for a new visitor.
	 */
	@FindBy(css="span.register a[href*='register']")
	public WebElement registerLink;
	
	/**
	 * Refresh the browser in HomePage.
	 */
	public void refresh(){
		driver.navigate().refresh();
	}

}
