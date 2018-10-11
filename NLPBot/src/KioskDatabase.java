import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
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
		this.connection = createConnectionToDB();
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
		// user section
		public static final String	INSERT_NEW_USER			= "insert into nlp.user u values (?,?,?,?,?,?)"
			+ " on duplicate key update u.chat_id= chat_id, u.telegram_user_id = telegram_user_id;";
		public static final String	REMOVE_USER				= "delete from nlp.user u where u.telegram_user_id= ?";
		public static final String	UPDATE_USER				= "";
		public static final String	CHECK_USER_EXISTANCE	= "select * from nlp.user where telegram_user_id= ?";

		// kiosk section
		public static final String	INSERT_NEW_KIOSK				= "insert into nlp.kiosk k values (?,?,?,?,?)"
			+ " on duplicate key update k.user_id= user_id";
		public static final String	REMOVE_KIOSK					= "delete from nlp.kiosk k where k.user_id= ? and k.id=?";
		public static final String	UPDATE_KIOSK					= "";
		public static final String	FETCH_KIOSK_FOR_SELECTED_USER	= "select * from nlp.kiosk k where k.user_id=?";

	}

	/****************************************************
	 ****************** Query Section *******************
	 ****************************************************/

	public void insertNewUser(User newUser)
	{
		try (Connection cn = getConnection();
			PreparedStatement stm = cn.prepareStatement(Statements.INSERT_NEW_USER, Statement.RETURN_GENERATED_KEYS))
		{
			stm.setLong(1, newUser.getUserID());
			stm.execute();

			try (ResultSet generatedKeys = stm.getGeneratedKeys())
			{
				if (generatedKeys.next())
					newUser.setId((int) generatedKeys.getLong(1));
			}
		}
		catch (SQLException ex)
		{
			System.out.format(BotConstants.Exceptions.INSERT, "user with username:", newUser.getUserName());
			ex.printStackTrace();
		}

	}

	public void removeUsers(List<User> users)
	{
		for (User user : users)
			removeUser(user);
	}

	public void removeUser(User user)
	{
		try (Connection cn = getConnection();
			PreparedStatement stm = cn.prepareStatement(Statements.REMOVE_USER))
		{
			stm.setLong(1, user.getUserID());
			stm.execute();
		}
		catch (SQLException ex)
		{
			System.out.format(BotConstants.Exceptions.REMOVE, "user with username:", user.getUserName());
			ex.printStackTrace();
		}
	}

	public User fetchUser(int requestedUserID)
	{
		User user = null;

		try (Connection cn = getConnection();
			PreparedStatement stm = cn.prepareStatement(Statements.CHECK_USER_EXISTANCE))
		{
			stm.setLong(1, requestedUserID);

			try (ResultSet result = stm.executeQuery())
			{
				while (result.next())
				{
					user = fillUser(result);
					user.setUsersText(fetchKiosksForSelectedUser(user));
					break;
				}
			}
		}
		catch (SQLException ex)
		{
			System.out.format(BotConstants.Exceptions.EXISTANCE, "user", requestedUserID);
			ex.printStackTrace();
		}

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
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/nlp", "", ""); // TODO pass ro vared kon
		}
		catch (ClassNotFoundException | SQLException ex)
		{
			ex.printStackTrace();
		}

		return null;
	}

	private static User fillUser(ResultSet result) throws SQLException
	{
		User user = new User(result.getInt(BotConstants.Database.UserColumns.TELEGRAM_ID));
		user.setId(result.getInt(BotConstants.Database.UserColumns.ID));
		user.setChatID(result.getInt(BotConstants.Database.UserColumns.CHAT_ID));
		user.setUserName(result.getString(BotConstants.Database.UserColumns.USER_NAME));
		user.setFirstName(result.getString(BotConstants.Database.UserColumns.FIRST_NAME));
		user.setLastName(result.getString(BotConstants.Database.UserColumns.LAST_NAME));
		user.setUserState(result.getInt(BotConstants.Database.UserColumns.STATE));

		return user;
	}

	private static Kiosk fillKiosk(ResultSet result, User user) throws SQLException
	{
		Kiosk kiosk = new Kiosk(result.getInt(BotConstants.Database.KioskColumns.ID), user);
		kiosk.setTextType(result.getString(BotConstants.Database.KioskColumns.TYPE));
		kiosk.setSimpleText(result.getString(BotConstants.Database.KioskColumns.SIMPLE));
		kiosk.setComplexText(result.getString(BotConstants.Database.KioskColumns.COMPLEX));
		kiosk.setComplete(result.getBoolean(BotConstants.Database.KioskColumns.COMPLETE));

		return kiosk;
	}

	private List<Kiosk> fetchKiosksForSelectedUser(User requestedUser)
	{
		List<Kiosk> selectedKiosks = new LinkedList<>();

		try (Connection cn = getConnection();
			PreparedStatement stm = cn.prepareStatement(Statements.FETCH_KIOSK_FOR_SELECTED_USER))
		{
			stm.setLong(1, requestedUser.getId());

			try (ResultSet result = stm.executeQuery())
			{
				while (result.next())
					selectedKiosks.add(fillKiosk(result, requestedUser));
			}
		}
		catch (SQLException ex)
		{
			System.out.format(BotConstants.Exceptions.EXISTANCE, "kiosk", "");
			ex.printStackTrace();
		}

		return selectedKiosks;
	}

}
