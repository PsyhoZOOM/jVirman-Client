package com.psyhozoom.virman.Client;

import com.psyhozoom.virman.Client.Controllers.LoginController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  private static void showLogin(Stage primaryStage) {
    FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemResource("login.fxml"));
    Parent root = null;
    try {
      root = fxmlLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    LoginController loginController = fxmlLoader.getController();
    loginController.setStage(primaryStage, root);
    primaryStage.setTitle("VIRMAN");
    primaryStage.setScene(new Scene(root, 300, 500));
    primaryStage.show();
  }

  @Override
  public void start(Stage primaryStage) {
    showLogin(primaryStage);

  }
}
