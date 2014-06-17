package CommentSection;

import static org.junit.Assert.*;


import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import PageObjects.HomePage;
import PageObjects.LoginPage;
import Resources.BaseTest;
import User.Users;

public class deleteOneComment extends BaseTest{
	public deleteOneComment(Users user){
		super(user);
	}
	
	/**
	 * This test posts a comment with a unique number.
	 * If the first comment before test is still the same first comment after the test, this test will fail.
	 */
	@Test
	public void createThenDeleteComment(){
		HomePage homepage = simpleLogin();
		
		String comment = "comment at : " + System.currentTimeMillis();
		
		homepage.leaveAComment(comment);
		
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		homepage.deleteFirstComment();
		
		
		assertFalse("comment was : " + homepage.getFirstComment(),homepage.getFirstComment().equals(comment));
	}
}
