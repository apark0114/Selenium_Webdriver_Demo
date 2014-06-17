package Login;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.PageFactory;

import PageObjects.HomePage;
import PageObjects.LoginPage;
import Resources.BaseTest;
import User.Users;

public class loginToFail extends BaseTest {
	//private LoginPage loginPage;
	public loginToFail(Users user){
		super(user);
	}
	
	/**
	 * This test covers the login with a wrong user name.
	 * This test fails if the correct error message is not displayed.
	 */
	
	@Test
	public void invalidUserName(){
		LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
		HomePage homepage = null;
		
		try{
			loginPage.loginAs(user.getUserName()+ "invalid", user.getPassword());
		}catch(TimeoutException e){
			System.out.println("login failed moving on...");
		}
		
		
		assertTrue(loginPage.getLoginError().isDisplayed());
	}
	
	/**
	 * This test covers the login with a wrong password.
	 * This test fails if the correct error message is not displayed.
	 */
	@Test
	public void invalidPassword(){
		LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
		HomePage homepage = null;
		
		try{
			loginPage.loginAs(user.getUserName(), user.getPassword()+"invalid");
		}catch(TimeoutException e){
			System.out.println("login failed moving on...");
		}
		
		//System.out.println(loginPage.getLoginError().getText());
		assertTrue(loginPage.getLoginError().isDisplayed());
	}
}
