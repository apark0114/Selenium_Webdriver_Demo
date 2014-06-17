package CommentSection;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.WebElement;

import PageObjects.HomePage;
import Resources.BaseTest;
import User.Users;

public class leaveManyComments extends BaseTest{
	
	public leaveManyComments(Users user){
		super(user);
	}
	
	/**
	 * This test verifies that all comments submitted are live(exisits) on the website.
	 * If collectCommentNotFoundOnSite returns a value that is not empty, then this test fails.
	 */
	@Test
	public void test(){
		HomePage homepage = simpleLogin();
		
		List<String> postedCommentList = leaveFiveComments(homepage);
		
		List<WebElement> liveWebElementList = homepage.getTotalComments();
		
		StringBuilder sb = collectCommentNotFoundOnSite(postedCommentList, liveWebElementList);
		
		
		assertTrue("Comments are not submitted correctly. \n " + sb.toString() + " are not found on site", sb.length() == 0);
		
	}
	
	
	/**
	 * This method leaves 5 comments in sequence each containing a unique value.
	 * @param homepage
	 * @return comment list.
	 */
	public List<String> leaveFiveComments(HomePage homepage){
		List<String> liveCommentList = new ArrayList<String>();
		for(int i=0; i<5; i++){
			String comment = "Many comments at: " + System.currentTimeMillis();
			homepage.leaveAComment(comment);
			liveCommentList.add(comment);
		}
		return liveCommentList;
	}
	
	/**
	 * This method collects five live comments after the post, then compare with the comments that are submitted.
	 * If live comments list doesn't match with the submitted comment list, then returns the StringBuilder object of these comments.
	 * @param liveCommentList
	 * @param liveWebElementList
	 * @return commment list that are not submitted correctly.
	 */
	public StringBuilder collectCommentNotFoundOnSite(List<String> liveCommentList, List<WebElement> liveWebElementList){
		StringBuilder sb = new StringBuilder();
		for(String singleComment: liveCommentList){
			boolean commentFound = false;
			for(WebElement e: liveWebElementList){
				if(singleComment.equals(e.getText().trim())){
					commentFound = true;
				}
			}
			if(commentFound == false){
				sb.append(singleComment + ", ");
			}
		}
		
		return sb;
	}
}
