package PageObjects;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.robot.Key;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;

public class SharePage extends HomePage{
	public SharePage(WebDriver driver){
		super(driver);
		maximize();
	}
	
	@FindBy(id ="share-video-link")
	WebElement shareVideo;
	
	@FindBy(id ="share-image-link")
	WebElement shareImage;
	
	@FindBy(id ="share-file-link")
	WebElement shareFile;
	
	public SharePage uploadImage(String filePath){
		switchToCommentFrame();
		shareImage.click();
		
		selectFileInWinExplorer(filePath);
		
		customWait(3);
		submit.click();
		return this;
	}
	
	public SharePage uploadVideo(String filePath){
		switchToCommentFrame();
		shareVideo.click();
		
		selectFileInWinExplorer(filePath);
		
		customWait(3);
		submit.click();
		return this;
	}
	
	public SharePage uploadFile(String filePath){
		switchToCommentFrame();
		shareFile.click();
		
		selectFileInWinExplorer(filePath);
		
		customWait(3);
		submit.click();
		return this;
	}
	
	@FindBy(css="li.activity.first-child div.other div.generic div.activity-content span.tooltip-container")
	WebElement imageOnPost;
	
	public WebElement getFirstSharedFile(){
		switchToCommentFrame();
	
		waitUntil(By.cssSelector("li.activity.first-child div.other div.generic div.activity-content span.tooltip-container"));
		imageOnPost= staleElementWrapper(imageOnPost);
		return imageOnPost;
		//return driver.findElement(By.cssSelector("div.container.image div.overlay")).isDisplayed();
	}
	
	private static Keyboard kb = new DesktopKeyboard();
	private static Mouse mouse = new DesktopMouse();
	
	public void selectFileInWinExplorer(String path){
		/**
		 * skuli to paste the file Path
		 * Then Enter
		 */
		ScreenRegion sr = new DesktopScreenRegion();
		Target fileName = new ImageTarget(new File("ImageResources/fileName.png"));
		Target Open = new ImageTarget(new File("ImageResources/Open.png"));
		sr.wait(fileName, 5);
		mouse.click(sr.find(fileName).getCenter());
		
		kb.paste(path);
		sr.wait(Open, 5);
		mouse.click(sr.find(Open).getCenter());

		kb.type(Key.ENTER);
		
	}
}
