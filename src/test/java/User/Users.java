package User;

public class Users {
	protected String username;
	protected String userPassword;
	protected String firstName ;
	protected String lastName;
	protected String emailpw;
	protected String level;
	
	/**
	 * This is an Users Object that contains each properties that child classes share.
	 * Admin, customer and employee are Users child objects.
	 */
	public Users(){
		
	}
	
	public String getUserName(){
		return this.username;
	}
	
	public String getPassword(){
		return this.userPassword;
	}
	
	public String getFirstName(){
		return this.firstName;
	}
	
	public String getLastNAme(){
		return this.lastName;
	}
	
	public String getLevel(){
		return this.level;
	}
	
	public String getEmailPassword(){
		return this.emailpw;
	}
}
