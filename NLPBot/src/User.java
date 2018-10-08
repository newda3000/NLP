
/**
 * @author newda
 *
 */
public class User
{
	private int		chatID;
	private int		userID;
	private String	userName;
	private String	firstName;
	private String	lastName;

	public int getChatID()
	{
		return chatID;
	}

	public void setChatID(int chatID)
	{
		this.chatID = chatID;
	}

	public int getUserID()
	{
		return userID;
	}

	public void setUserID(int userID)
	{
		this.userID = userID;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
}
