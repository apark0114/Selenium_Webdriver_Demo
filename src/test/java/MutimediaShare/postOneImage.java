package MutimediaShare;

import static org.junit.Assert.*;

import java.net.URL;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import PageObjects.SharePage;
import Resources.BaseTest;
import User.Users;

public class postOneImage extends BaseTest{
	public postOneImage(Users user){
		super(user);
	}
	
	/**
	 * This test covered uploading an image file.
	 * Each post contains a unique id.  If the post id before uploading is same as after uploading, this test fails.
	 */
	@Test
	public void test(){
		
		simpleLogin();
		SharePage sharePage = PageFactory.initElements(driver, SharePage.class);
		
		sharePage.customWait(5);
		String valueBefore = sharePage.getFirstSharedFile().getAttribute("id");
		
		sharePage.uploadImage(getHumanFile("Bart on Skateboard.jpg"));
	
		sharePage.refresh();
		
		String valueAfter = sharePage.getFirstSharedFile().getAttribute("id");
		//System.out.println(sharePage.getFirstSharedFile().getAttribute("class")+ "  /  " + sharePage.getFirstSharedFile().getAttribute("onmousemove"));
		
		//System.out.println(valueBefore + "\n" + valueAfter);
		assertFalse("Image uploaded incorrectly on site\nvalueBefore: " + valueBefore + 
				"/ valueAfter: " +valueAfter, (valueBefore ==valueAfter) && (valueBefore != null || valueAfter != null));
	}
	

	
}
