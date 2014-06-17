package MutimediaShare;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import PageObjects.SharePage;
import Resources.BaseTest;
import User.Users;

public class postOneVideo extends BaseTest{
	public postOneVideo(Users user){
		super(user);
	}
	
	/**
	 * This test covered uploading a video file.
	 * Each post contains a unique id.  If the post id before uploading is same as after uploading, this test fails.
	 */
	@Test
	public void test(){
		simpleLogin();
		
		SharePage sharePage = PageFactory.initElements(driver, SharePage.class);
		String beforePost = sharePage.getFirstSharedFile().getAttribute("id");
		sharePage.uploadVideo(getHumanFile("mp4.mp4"));
		
		driver.navigate().refresh();
		
		String afterPost = sharePage.getFirstSharedFile().getAttribute("id");
		System.out.println(beforePost + " \nafter: " +afterPost);
		assertTrue(beforePost != afterPost);
	}
}
