import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * @author newda
 *
 */
public class KioskDatabase
{
	private Connection connection;

	public KioskDatabase()
	{
		// this.connection = createConnectionToDB();
	}

	public Connection getConnection()
	{
		return connection;
	}

	/****************************************************
	 **************** Statements Section ****************
	 ****************************************************/

	public class Statements
	{
		public static final String	INSERT_NEW_USER	= "insert into nlp.user u values (?,?,?)"
			+ " on duplicate key update u.chat_id= chat_id, u.telegram_user_id = telegram_user_id;";
		public static final String	REMOVE_USER		= "";

	}

	/****************************************************
	 ****************** Query Section *******************
	 ****************************************************/

	public void insertNewUser(User newUser)
	{

	}

	public void removeUsers(List<User> users)
	{

	}

	public User userExists(User requestedUser)
	{
		User user = null;
		// TODO query database for the requestedUser
		return user;
	}

	/****************************************************
	 ****************** Private Methods *****************
	 ****************************************************/

	private static Connection createConnectionToDB()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/", "", ""); // TODO pass ro vared kon
		}
		catch (ClassNotFoundException | SQLException ex)
		{
			ex.printStackTrace();
		}

		return null;
	}

}
