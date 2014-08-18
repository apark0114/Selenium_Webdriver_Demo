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
/**
 * Use this class to access media elements(image, video, files) other than text comments.
 * Since accessing these elements are more complicated than text comments, this class was created.
 * @author Albert Park
 *
 */
public class SharePage extends HomePage{
	public SharePage(WebDriver driver){
		super(driver);
		maximize();
	}
	
	/**
	 * Share video link WebElement to load video sharing dialog.
	 */
	@FindBy(id ="share-video-link")
	WebElement shareVideo;
	
	/**
	 * Share image link WebElement to load image sharing dialog.
	 */
	@FindBy(id ="share-image-link")
	WebElement shareImage;
	
	/**
	 * Share file link WebElement to load file sharing dialog.
	 */
	@FindBy(id ="share-file-link")
	WebElement shareFile;
	
	/**
	 * To upload image. Pass the file path of the image as a parameter.
	 * @param filePath String.
	 * @return
	 */
	public SharePage uploadImage(String filePath){
		//switch to comment frame before accessing sharepage elements.
		switchToCommentFrame();
		//click the share image button.
		shareImage.click();
		
		//In windows explorer, input the filepath and select an image to upload.
		selectFileInWinExplorer(filePath);
		
		//wait to find the image file.
		customWait(3);
		//click the submit to upload the image file.
		submit.click();
		return this;
	}
	
	/**
	 * To upload video, pass the file path of video as a parameter.
	 * @param filePath String.
	 * @return
	 */
	public SharePage uploadVideo(String filePath){
		//switch to comment frame before accessing sharepage elements.
		switchToCommentFrame();
		//click the share video button.
		shareVideo.click();
		
		//In windows explorer, input the filepath and select a video to upload.
		selectFileInWinExplorer(filePath);
		
		//wait to find the video file.
		customWait(3);
		//click the submit to upload the video file.
		submit.click();
		return this;
	}
	
	/**
	 * To upload a file, pass the file path as a paramter.
	 * @param filePath String.
	 * @return
	 */
	public SharePage uploadFile(String filePath){
		//switch to comment frame before accessing sharepage elements.
		switchToCommentFrame();
		//click the share file button.
		shareFile.click();
		
		//In windows explorer, input the filepath and select a file to upload.
		selectFileInWinExplorer(filePath);
		
		customWait(3);
		//click the submit to upload the file.
		submit.click();
		return this;
	}
	
	//First media post Webelement in page.
	@FindBy(css="li.activity.first-child div.other div.generic div.activity-content span.tooltip-container")
	WebElement imageOnPost;
	
	/**
	 * To get the first media post element.
	 * @return first media post Webelement in page.
	 */
	public WebElement getFirstSharedFile(){
		//switch to comment frame which contains media posts.
		switchToCommentFrame();
	
		//wait until all media element has loaded after submit.
		waitUntil(By.cssSelector("li.activity.first-child div.other div.generic div.activity-content span.tooltip-container"));
		//if the new media post are submitted after SharePage object was created, find the new first media post.
		imageOnPost= staleElementWrapper(imageOnPost);
		return imageOnPost;
		//return driver.findElement(By.cssSelector("div.container.image div.overlay")).isDisplayed();
	}
	
	//Sikuli keyboard object.
	private static Keyboard kb = new DesktopKeyboard();
	//Sikuli mouse object.
	private static Mouse mouse = new DesktopMouse();
	
	/**
	 * To interact with windows explorer to upload for media sharing(Selenium can not access this, since this is from desktop).
	 * Simply paass the media filepath in desktop to select the file you want to share.
	 * @param file path in string.
	 */
	public void selectFileInWinExplorer(String path){
		//capture desktop screenshot.
		ScreenRegion sr = new DesktopScreenRegion();
		//Create a filename text file target object.
		Target fileName = new ImageTarget(new File("ImageResources/fileName.png"));
		//Create a open button target object.
		Target Open = new ImageTarget(new File("ImageResources/Open.png"));
		//wait until file name field is found in desktop screenshot.
		sr.wait(fileName, 5);
		//click the file name text field.
		mouse.click(sr.find(fileName).getCenter());
		
		//pass the file path using keyboard.
		kb.paste(path);
		//wait until open button is visible.
		sr.wait(Open, 5);
		//click open button.
		mouse.click(sr.find(Open).getCenter());
		//Press Enter to exit out of windows explorer.
		kb.type(Key.ENTER);
		
	}
}
