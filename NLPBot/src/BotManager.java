import static java.lang.Math.toIntExact;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class BotManager extends TelegramLongPollingBot
{

	@Override
	public void onUpdateReceived(Update update)
	{
		if (update.hasMessage())
		{
			String message_text = update.getMessage().getText();
			long chat_id = update.getMessage().getChatId();
			SendMessage message = new SendMessage()
				.setChatId(chat_id);

			if (message_text.equals("/start"))
			{
				message.setText(BotConstants.Messages.WELCOME);
			}
			else if (message_text.equals("/keyboard"))
			{
				InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
				List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
				List<InlineKeyboardButton> rowInline = new ArrayList<>();

				rowInline.add(new InlineKeyboardButton().setText(BotConstants.Choices.ONE)
					.setCallbackData("First"));
				rowInline.add(new InlineKeyboardButton().setText(BotConstants.Choices.SECOND)
					.setCallbackData("Second"));
				rowsInline.add(rowInline);
				keyboardMarkup.setKeyboard(rowsInline);
				message.setReplyMarkup(keyboardMarkup)
					.setText("Here is your keyboard");
			}
			else if (message_text.equals("/hide"))
			{
				ReplyKeyboardRemove keyboardMarkup = new ReplyKeyboardRemove();
				message.setReplyMarkup(keyboardMarkup)
					.setText("Keyboard is hidden");
			}
			// else if (message_text.equals(BotConstants.OptionKeys.ONE))
			// {
			//
			// }
			else
			{
				message.setText("Unknown command");
			}

			// Sending message
			try
			{
				execute(message);
			}
			catch (TelegramApiException e)
			{
				e.printStackTrace();
			}
		}
		else if (update.hasCallbackQuery())
		{
			String call_data = update.getCallbackQuery().getData();
			long message_id = update.getCallbackQuery().getMessage().getMessageId();
			long chat_id = update.getCallbackQuery().getMessage().getChatId();
			String answer = "";

			if (call_data.equals("First"))
				answer = "Here is your NEO test";
			else if (call_data.equals("Second"))
				answer = "Here is your MBTI test";

			EditMessageText new_message = new EditMessageText()
				.setChatId(chat_id)
				.setMessageId(toIntExact(message_id))
				.setText(answer);
			try
			{
				execute(new_message);
			}
			catch (TelegramApiException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public String getBotUsername()
	{
		return null;
	}

	@Override
	public String getBotToken()
	{
		return null;
	}

}
