import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * @author newda
 *
 */
public class BotStarter
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// createConnectionToDB();
		ApiContextInitializer.init();
		TelegramBotsApi botsApi = new TelegramBotsApi();
		try
		{
			botsApi.registerBot(new BotManager());
		}
		catch (TelegramApiException e)
		{
			e.printStackTrace();
		}

		System.out.println("Bot successfully started!");
	}

	private static void createConnectionToDB()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
			Statement stm = cn.createStatement();
		}
		catch (ClassNotFoundException ex)
		{
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		catch (SQLException ex)
		{
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}

}
