
/**
 * @author newda
 *
 */
public class BotConstants
{
	public class Database
	{
		public class UserColumns
		{
			public static final String	ID			= "id";
			public static final String	CHAT_ID		= "chat_id";
			public static final String	TELEGRAM_ID	= "telegram_user_id";
			public static final String	USER_NAME	= "user_name";
			public static final String	FIRST_NAME	= "first_name";
			public static final String	LAST_NAME	= "last_name";
			public static final String	STATE		= "state";
		}

		public class KioskColumns
		{
			public static final String	ID			= "id";
			public static final String	USER_ID		= "user_id";
			public static final String	TYPE		= "TYPE";
			public static final String	COMPLEX		= "complex";
			public static final String	SIMPLE		= "simple";
			public static final String	COMPLETE	= "complete";
		}

	}

	public class Type
	{
		public static final String	QUESTION	= "سوالی";
		public static final String	ORDER		= "امری";
		public static final String	CRITICISM	= "انتقادی";
		public static final String	OFFER		= "پیشنهادی";
	}

	public class CallBack
	{
		public static final String	QUESTION	= "question";
		public static final String	ORDER		= "order";
		public static final String	CRITICISM	= "criticism";
		public static final String	OFFER		= "offer";
	}

	public class State
	{
		public static final int	WELCOME	= 0;
		public static final int	BEGIN	= 1;
		public static final int	SIMPLE	= 2;
		public static final int	COMPLEX	= 3;
		public static final int	DONE	= 4;
	}

	public class Messages
	{
		public static final String	CHOOSE_TYPE		= " عزیز خوش آمدید. لطفا یکی از گزینه های زیر را انتخاب کنید";
		public static final String	SIMPLE_TEXT		= "لطفا جمله خود را وارد کنید";
		public static final String	COMPLEX_TEXT	= "لطفا جمله خود را در بیانی دیگر وارد کنید";
		public static final String	FINISHED		= "با تشکر جمله شما ذخیره شد";
		public static final String	START_NEW		= "به منظور وارد کردن جمله جدید از ابتدا شروع کنید";
	}

	public class Exceptions
	{
		public static final String	SENDING_MESSAGE	= "Something went wrong while sending new message to user";
		public static final String	CALL_BACK		= "Something went wrong while getting call back for user options";
		public static final String	EXISTANCE		= "Something went wrong while checking %s %s existence";
		public static final String	INSERT			= "Could not insert %s in database";
		public static final String	REMOVE			= "Could not remove %s from database";
	}

	public class LogStatements
	{
		public static final String	NEW_INCOME	= "new user with username %s and chatID %s started using bot";
		public static final String	CALL_BACK	= "Something went wrong while getting call back for user options";
		public static final String	EXISTANCE	= "Something went wrong while checking users existence";
		public static final String	INSERT		= "Could not insert %s in database";
		public static final String	REMOVE		= "Could not remove %s from database";
	}
}
