
/**
 * @author newda
 *
 */
public class Kiosk
{
	private int		id;
	private User	user;
	private int		textType;
	private String	simpleText;
	private String	complexText;

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

	public int getTextType()
	{
		return textType;
	}

	public void setTextType(int textType)
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
}
