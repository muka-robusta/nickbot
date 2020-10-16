import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot
{
    public static void main(String[] args) {
        ApiContextInitializer.init();
        final TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());

        }catch (TelegramApiException ex) {
            ex.printStackTrace();
        }
    }

    public void sendMsg(Message message, String text) {
        final SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);

        try {
            setButtons(sendMessage);
            execute(sendMessage);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public void onUpdateReceived(Update update) {
        // for catching messages
        final Model model = new Model();
        final Message message = update.getMessage();
        if(message != null && message.hasText()) {
            switch (message.getText()) {
                case "/help":
                    sendMsg(message, "How can i help you?");
                    break;
                case "/settings":
                    sendMsg(message, "What we gonna set?");
                    break;
                default:
                    try {
                        final String weather = Weather.getWeather(message.getText(), model);
                        System.out.println(weather);
                        sendMsg(message, weather);
                    } catch (IOException e) {
                        sendMsg(message, "Місто не знайдено");
                        e.printStackTrace();
                    }
                    break;
            }
        }


    }

    public void setButtons(SendMessage sendMessage) {
        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        final KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("Київ"));
        keyboardFirstRow.add(new KeyboardButton("Чернігів"));
        keyboardFirstRow.add(new KeyboardButton("Львів"));

        keyboardRowList.add(keyboardFirstRow);

        replyKeyboardMarkup.setKeyboard(keyboardRowList);


    }

    public String getBotUsername() {
        return "one2storybot";
    }

    public String getBotToken() {
        return "1146025587:AAHGpem_QQBAllgGK_VV8uvyFx_gnhaeI_4";
    }
}
