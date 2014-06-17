package Resources;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.PageFactory;

import MutimediaShare.postOneImage;
import PageObjects.HomePage;
import PageObjects.LoginPage;
import User.Admin;
import User.Customer;
import User.Employee;
import User.Users;

@RunWith(Parameterized.class)
public class BaseTest {
	protected WebDriver driver;
	
	private String className;
	private String packageName;
	protected Users user;
	
	public BaseTest(Users user){
		this.user = user;
	}
	
	/**
	 * Passing parameters which test users at different level.  In the order of "Admin", "Employee", "Customer" objects are passed as
	 * paramters to test the company website. 
	*/
	@Parameterized.Parameters
	public static Collection UserParams(){
		return Arrays.asList(new Object[][]{
				{new Admin()}, {new Employee()},{new Customer()}
		});
	}
	
	@Rule public TestName name = new TestName();
	
	//Set timeout for each test at 1 min 30 second.
	@Rule
	public Timeout globalTimeout = new Timeout(90000);
	
	
	/**
	 * Displays a simple test information.  1)User level 2)Test name
	 * The test is currently performed in Firefox browser.
	 * More browsers will be passed as parameters if needed in future.
	 */
	@Before
	public void setup(){
		System.out.println("--------------------------------------------------------------------");
		System.out.println("Running Test for user: " + user.getLevel() + " TestName: " + name.getMethodName());
		System.out.println("--------------------------------------------------------------------");

		driver= new FirefoxDriver();
		//driver.get("http://rnd.madcapsoftware.com/apark/pulse/Test1/Default.htm");

		driver.get("https://someWebsite/myCompanyTests/");
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
			
	}
	
	/**
	 * Firefox driver is cleaned up at teardown.
	 * Also a screenshot of the result is taken.
	 */
	@After
	public void teardown(){
		try {
			takeScreenshot(this.getClass().getName(), name.getMethodName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.quit();
	}
	
	/**
	 * 
	 * @param testName
	 * @param methodName
	 * @throws IOException
	 * 
	 * Images are saved in jenkins' friendly format.  Each screenshot is named after its test method.
	 */
	@Ignore
	public void takeScreenshot(String testName, String methodName) throws IOException {
       // System.out.println("Taking screenshot");


       // new File("target/surefire-reports/screenshot/").mkdirs(); 
        String filename = "target/surefire-reports/"+testName+"/" + methodName +"." + user.getLevel() + ".png";
        
        try {
        	driver = new Augmenter().augment(driver);
            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(filename), true);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error capturing screen shot of " + testName + " test failure.");
        }

        System.out.println("Saving screenshot for "  + testName + "." + user.getLevel());
        
   

    }
	//<---------------------------	 Helper methods   --------------------------------->//
	//SimpleLogin is used to log in to the website using page object pattern.
	public HomePage simpleLogin(){
		LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
		return loginPage.loginAs(user.getUserName(), user.getPassword());
	}
	
	/**
	 * 
	 * @param fileName
	 * @return absolute path of the file.
	 * 
	 * This is for Sikuli framework which loads the image file for comparison.  Files in ImageResources will be called by its filename.
	 */
	public String getHumanFile(String fileName){
		String localFileFullPath = "";
		String className = this.getClass().getSimpleName();
		//System.out.println("ClassName: " + className);
		URL imagePath =  postOneImage.class.getResource(className + ".class");
		String imageFilePath = imagePath.toString();
		String fileFound = imageFilePath.substring(0, imageFilePath.indexOf("target"));
		
		localFileFullPath = (fileFound + "ImageResources/" + fileName).replace("file:/", "").replace("/","\\");
		System.out.println(localFileFullPath);
		
		return localFileFullPath;
	
	}
	
}
