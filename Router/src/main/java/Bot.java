import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Bot extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {

        return "@tasktrackingbot";
    }

    @Override
    public String getBotToken() {
        return "2033620622:AAG3hdT89gBiwmwte4x3P4eJsYQnHf5MSt4";
    }


    @Override
    public void onUpdateReceived(Update update) {
// We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
            message.setChatId(update.getMessage().getChatId().toString());

            String text = update.getMessage().getText();
            System.out.println(text);


            String newText =   text.toUpperCase(Locale.ROOT);
                    //Stream.of(text.split("")).collect(Collectors.joining("*")).toString();
            message.setText(newText);

            // Create ReplyKeyboardMarkup object
            ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
            // Create the keyboard (list of keyboard rows)
            List<KeyboardRow> keyboard = new ArrayList<>();
            // Create a keyboard row
            KeyboardRow row = new KeyboardRow();
            // Set each button, you can also use KeyboardButton objects if you need something else than text
            row.add("Send track");

            // Add the first row to the keyboard
            keyboard.add(row);

            keyboardMarkup.setKeyboard(keyboard);
            // Add it to the message
            message.setReplyMarkup(keyboardMarkup);




            try {
                execute(message); // Call method to send the message


            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }



}

