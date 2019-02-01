package academy.konrad.group.battleships.userinterface;

import academy.konrad.group.battleships.message.Message;
import academy.konrad.group.battleships.message.MessageParser;
import org.pmw.tinylog.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * Odbiera wiadomości z serwera i wywołuje odpowiednie metody na podstawie tytyłu.
 */
class BattleshipClient {

  private final BufferedReader in;
  private boolean isRun = true;
  private final MessageHandler messageHandler;

  BattleshipClient(MessageHandler messageHandler) {
    this.messageHandler = messageHandler;
    this.in = new BufferedReader(new InputStreamReader(Connection.getInputStream(), StandardCharsets.UTF_8));

  }


  void play() {

    Thread t = new Thread(() -> {
      try {
        while (isRun) {
          String fromServer = in.readLine();
          String title = MessageParser.getMessageTitle(fromServer);
          String content = MessageParser.getMessageContent(fromServer);
          Optional<Message> option = MessageParser.findChosenOption(title);
          Message message;
          if (option.isPresent()) {
            message = option.get();
            runOption(message, content);
          }
        }
      } catch (IOException e) {
        Logger.error(e.getMessage());
        isRun = false;
      }
    });
    t.start();
  }


  private void runOption(Message option, String content) {
    switch(option) {
      case MOVE:
        this.messageHandler.doMove(content);
        break;
      case WELCOME:
        String messageStart = Connection.getMessage("welcomeMessage");
        this.messageHandler.logStart(messageStart);
        this.messageHandler.showMessageOnTextArea(messageStart);
        break;
      case CLOSE:
        this.isRun = false;
        break;
      case WAIT:
        break;
      case MESSAGE:
        String message = Connection.getMessage(content);
        this.messageHandler.showMessageOnTextArea(message);
        break;
      case STOP:
        String textStop = Connection.getMessage("finish");
        this.messageHandler.showMessageOnTextArea(textStop);
        break;
      case WIN:
        String text = Connection.getMessage("winner");
        this.messageHandler.showMessageOnTextArea(text);
        break;
      case HIT:
        this.messageHandler.doHit(content);
        break;
      case FIRST:
        this.messageHandler.manageTurn(content);
        break;
        default:
          throw new IllegalStateException();
        
    }

  }





}