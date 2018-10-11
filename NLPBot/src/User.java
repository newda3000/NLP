import java.util.LinkedList;
import java.util.List;

/**
 * @author newda
 *
 */
public class User
{
	private int			id;
	private long		chatID;
	private long		telegramUserID;
	private String		userName;
	private String		firstName;
	private String		lastName;
	private int			userState;
	private List<Kiosk>	usersText;

	public User(long telegramUserID)
	{
		this.telegramUserID = telegramUserID;
		this.userState = BotConstants.State.WELCOME;
		this.usersText = new LinkedList<>();
	}

	public User(long chatID, long telegramUserID, String userName, String firstName, String lastName)
	{
		this.chatID = chatID;
		this.telegramUserID = telegramUserID;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userState = BotConstants.State.WELCOME;
		this.usersText = new LinkedList<>();
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public long getChatID()
	{
		return chatID;
	}

	public void setChatID(long chatID)
	{
		this.chatID = chatID;
	}

	public long getUserID()
	{
		return telegramUserID;
	}

	public void setUserID(int userID)
	{
		this.telegramUserID = userID;
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

	public int getUserState()
	{
		return userState;
	}

	public void setUserState(int userState)
	{
		this.userState = userState;
	}

	public List<Kiosk> getUsersText()
	{
		return usersText;
	}

	public void setUsersText(List<Kiosk> usersText)
	{
		this.usersText = usersText;
	}
}
