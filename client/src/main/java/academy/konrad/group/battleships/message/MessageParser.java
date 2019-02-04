package academy.konrad.group.battleships.message;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.Optional;

/**
*Tlumaczy wiadomosci z servera na enumy dzieli jest na tytuł i zawartość.
 */

public class MessageParser {

  public static Optional<Message> findChosenOption(String fromServer) {
    return Arrays.stream(Message.values()).filter(e -> e.name().equals(fromServer)).findFirst();
  }

  public static String getMessageContent(String fromServer) {
    return StringUtils.substringAfter(fromServer, ":");
  }

  public static String getMessageTitle(String fromServer) {
    return StringUtils.substringBefore(fromServer, ":");
  }
}
