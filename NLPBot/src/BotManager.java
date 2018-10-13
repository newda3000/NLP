import static java.lang.Math.toIntExact;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class BotManager extends TelegramLongPollingBot
{

	private KioskDatabase	database;
	private List<User>		usersList;
	private User			lastUser;

	public BotManager()
	{
		this.database = new KioskDatabase();
		this.usersList = new LinkedList<>();
	}

	public KioskDatabase getDatabase()
	{
		return database;
	}

	public void addNewUser(User newUser)
	{
		this.usersList.add(newUser);
	}

	public void addNewKiosk(Kiosk newKiosk)
	{
		getDatabase().insertNewKiosk(newKiosk);
	}

	public User getUser(long userID)
	{
		for (User requestedUser : this.usersList)
			if (requestedUser.getUserID() == userID)
				return requestedUser;
		return null;
	}

	/****************************************************
	 ***************** Telegram Section *****************
	 ****************************************************/

	@Override
	public void onUpdateReceived(Update update)
	{

		if (update.hasMessage())
		{
			long chat_id = update.getMessage().getChatId();
			User currentUser = checkUserExistanceInDB(update.getMessage());

			// just for caching
			// User currentUser = checkUserExistanceInCache(update.getMessage());

			System.out.format(BotConstants.LogStatements.NEW_INCOME, currentUser.getUserName(), chat_id);

			this.lastUser = currentUser;
			String message_text = update.getMessage().getText();
			SendMessage message = new SendMessage()
				.setChatId(chat_id)
				.setText("Unkown Command");

			if (message_text.equals("/start"))
			{
				currentUser.setUserState(BotConstants.State.BEGIN);
				getDatabase().updateUser(lastUser);

				InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
				List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
				List<InlineKeyboardButton> rowInline = new ArrayList<>();

				rowInline.add(new InlineKeyboardButton().setText(BotConstants.Type.QUESTION)
					.setCallbackData(BotConstants.CallBack.QUESTION));
				rowInline.add(new InlineKeyboardButton().setText(BotConstants.Type.ORDER)
					.setCallbackData(BotConstants.CallBack.ORDER));
				rowInline.add(new InlineKeyboardButton().setText(BotConstants.Type.CRITICISM)
					.setCallbackData(BotConstants.CallBack.CRITICISM));
				rowInline.add(new InlineKeyboardButton().setText(BotConstants.Type.OFFER)
					.setCallbackData(BotConstants.CallBack.OFFER));
				rowsInline.add(rowInline);
				keyboardMarkup.setKeyboard(rowsInline);
				message.setReplyMarkup(keyboardMarkup)
					.setText(currentUser.getUserName() + BotConstants.Messages.CHOOSE_TYPE);
			}
			else if (currentUser.getUserState() == BotConstants.State.SIMPLE)
			{
				currentUser.setUserState(BotConstants.State.COMPLEX);
				getDatabase().updateUser(lastUser);
				for (Kiosk item : currentUser.getUsersText())
				{
					if (!item.isComplete())
					{
						item.setSimpleText(message_text);
						getDatabase().updateKiosk(item);
						break;
					}
				}

				message.setText(BotConstants.Messages.COMPLEX_TEXT);
			}
			else if (currentUser.getUserState() == BotConstants.State.COMPLEX)
			{
				String type = "";
				currentUser.setUserState(BotConstants.State.DONE);
				getDatabase().updateUser(lastUser);
				for (Kiosk item : currentUser.getUsersText())
				{
					if (!item.isComplete())
					{
						type = item.getTextType();
						item.setComplexText(message_text);
						item.setComplete(true);
						getDatabase().updateKiosk(item);
						break;
					}
				}

				message.setText("با تشکر جمله " + type + " شما ذخیره شد");
			}
			else if (currentUser.getUserState() == BotConstants.State.DONE)
				message.setText(BotConstants.Messages.START_NEW);

			// Sending message
			try
			{
				execute(message);
			}
			catch (TelegramApiException e)
			{
				System.out.println(BotConstants.Exceptions.SENDING_MESSAGE);
				e.printStackTrace();
			}
		}
		else if (update.hasCallbackQuery())
		{
			long chat_id = update.getCallbackQuery().getMessage().getChatId();
			String call_data = update.getCallbackQuery().getData();
			long message_id = update.getCallbackQuery().getMessage().getMessageId();
			Kiosk newText = new Kiosk(this.lastUser);

			// defining text type
			if (call_data.equals(BotConstants.CallBack.QUESTION))
				newText.setTextType(BotConstants.Type.QUESTION);
			else if (call_data.equals(BotConstants.CallBack.ORDER))
				newText.setTextType(BotConstants.Type.ORDER);
			else if (call_data.equals(BotConstants.CallBack.CRITICISM))
				newText.setTextType(BotConstants.Type.CRITICISM);
			else
				newText.setTextType(BotConstants.Type.OFFER);

			// adding this new kiosk to user's kiosk list
			addNewKiosk(newText);
			this.lastUser.setUserState(BotConstants.State.SIMPLE);
			getDatabase().updateUser(lastUser);

			EditMessageText new_message = new EditMessageText()
				.setChatId(chat_id)
				.setMessageId(toIntExact(message_id))
				.setText(BotConstants.Messages.SIMPLE_TEXT);
			try
			{
				execute(new_message);
			}
			catch (TelegramApiException e)
			{
				System.out.println(BotConstants.Exceptions.CALL_BACK);
				e.printStackTrace();
			}
		}
	}

	@Override
	public String getBotUsername()
	{
		return "";
	}

	@Override
	public String getBotToken()
	{
		return "";
	}

	/****************************************************
	 ****************** Private Methods *****************
	 ****************************************************/

	/**
	 * If user is already in cache, finds it and returns a full user object,
	 * if not creates a new user and add's it to cache.
	 *
	 * @return
	 */
	private User checkUserExistanceInCache(Message newIncome)
	{
		User newUser = getUser(newIncome.getChat().getId());
		if (newUser != null)
			return newUser;

		newUser = new User(newIncome.getChatId(), newIncome.getChat().getId(),
			newIncome.getChat().getUserName(), newIncome.getChat().getFirstName(), newIncome.getChat().getLastName());

		addNewUser(newUser);
		return newUser;

	}

	/**
	 * If user is already in database, finds it and returns a full user object,
	 * if not creates a new user and add's it to database.
	 *
	 * @return
	 */
	private User checkUserExistanceInDB(Message newIncome)
	{
		User newUser = getDatabase().fetchUser(newIncome.getChat().getId());
		if (newUser != null)
			return newUser;

		newUser = new User(newIncome.getChatId(), newIncome.getChat().getId(),
			newIncome.getChat().getUserName(), newIncome.getChat().getFirstName(), newIncome.getChat().getLastName());

		getDatabase().insertNewUser(newUser);
		return newUser;

	}

}
