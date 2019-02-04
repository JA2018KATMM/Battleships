package academy.konrad.group.battleships.userinterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.writers.FileWriter;

import java.io.IOException;
import java.util.Objects;

public class MainWithGUI extends Application {

  public static void main(String[] args) {
    launch(args);
  }


  public void start(Stage primaryStage) throws IOException {
    loggerSetup();
    renderView(primaryStage);
  }


  private void loggerSetup() {
    Configurator.defaultConfig().writer(new FileWriter(System.getProperty("user.home")
        + "/logi/info_klient.txt"), Level.INFO).addWriter(new FileWriter(
            System.getProperty("user.home") + "/logi/bledy_klient.txt"), Level.ERROR).activate();
  }

  private void renderView(Stage primaryStage) throws IOException {
    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("sample.fxml")));
    primaryStage.setTitle("Statki");
    primaryStage.setScene(new Scene(root, 800, 700));
    primaryStage.show();
  }
}