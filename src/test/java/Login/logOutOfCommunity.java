package Login;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import PageObjects.HomePage;
import PageObjects.LoginPage;
import Resources.BaseTest;
import User.Users;

public class logOutOfCommunity extends BaseTest{
	public logOutOfCommunity(Users user){
		super(user);
	}
	
	/**
	 * This test covers logging out of the website.  
	 * If loginLink and registerLink is not displayed, then the test fails.
	 */
	@Test
	public void test(){
		LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
		
		HomePage homepage = loginPage.loginAs(user.getUserName(), user.getPassword());
		if(!"Welcome".equals(homepage.getTitle())){
			throw new IllegalStateException("failed at login");
		}
		
		homepage.logout();

		assertTrue(homepage.loginLink.isDisplayed() && homepage.registerLink.isDisplayed());
		
	}

}
