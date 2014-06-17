package CommentSection;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import PageObjects.HomePage;
import PageObjects.LoginPage;
import Resources.BaseTest;
import User.Users;

public class leaveOneCommentTest extends BaseTest{

	public leaveOneCommentTest(Users user){
		super(user);
	}
	@Test
	public void test(){
		LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
		HomePage homepage = loginPage.loginAs(user.getUserName(), user.getPassword());
		
		String comment = "Hello pulse world!";
		homepage.leaveAComment(comment);
		
		assertTrue("comment is not submitted correctly", homepage.getFirstComment().equals(comment));
	}
	

	
	
}
