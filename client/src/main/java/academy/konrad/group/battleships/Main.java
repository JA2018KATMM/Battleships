package academy.konrad.group.battleships;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));
    primaryStage.setTitle("Statki");
    primaryStage.setScene(new Scene(root, 1000, 1000));
    primaryStage.show();
    Listener.getListener().connect();
  }


  public static void main(String[] args) {
    launch(args);
  }
}