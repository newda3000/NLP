
/**
 * @author newda
 *
 */
public class BotConstants
{
	public class Type
	{
		public static final String	QUESTION	= "سوال";
		public static final String	ORDER		= "امری";
		public static final String	CRITICISM	= "انتقاد";
		public static final String	OFFER		= "پیشنهاد";
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
		public static final String	CHOOSE_TYPE		= "لطفا یکی از گزینه های زیر را انتخاب کنید";
		public static final String	SIMPLE_TEXT		= "لطفا جمله خود را وارد کنید";
		public static final String	COMPLEX_TEXT	= "لطفا جمله خود را در بیانی دیگر وارد کنید";
		public static final String	FINISHED		= "با تشکر جمله شما ذخیره شد";
		public static final String	START_NEW		= "به منظور وارد کردن جمله جدید از ابتدا شروع کنید";
	}
}
