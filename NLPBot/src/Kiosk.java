
/**
 * @author newda
 *
 */
public class Kiosk
{
	private int		id;
	private User	user;
	private String	textType;
	private String	simpleText;
	private String	complexText;
	private boolean	complete;

	public Kiosk(User user)
	{
		this.user = user;
		this.complete = false;
	}

	public Kiosk(int id, User user)
	{
		this.id = id;
		this.user = user;
		this.complete = false;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public String getTextType()
	{
		return textType;
	}

	public void setTextType(String textType)
	{
		this.textType = textType;
	}

	public String getSimpleText()
	{
		return simpleText;
	}

	public void setSimpleText(String simpleText)
	{
		this.simpleText = simpleText;
	}

	public String getComplexText()
	{
		return complexText;
	}

	public void setComplexText(String complexText)
	{
		this.complexText = complexText;
	}

	public boolean isComplete()
	{
		return complete;
	}

	public void setComplete(boolean complete)
	{
		this.complete = complete;
	}
}
