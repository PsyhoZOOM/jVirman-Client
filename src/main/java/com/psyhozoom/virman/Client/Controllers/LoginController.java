package com.psyhozoom.virman.Client.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.psyhozoom.virman.Client.Classes.Client;
import com.psyhozoom.virman.Client.Classes.Settings;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONObject;
import org.kordamp.ikonli.javafx.FontIcon;

public class LoginController implements Initializable {

  public JFXTextField tUserName;
  public JFXPasswordField tPassWord;
  public JFXButton bLogin;
  public JFXButton bSettings;
  public FontIcon bSetup;
  private URL location;
  private ResourceBundle resources;

  private Stage stage;
  private Parent loginRoot;
  private Client client;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;
  }

  public void goLogin(ActionEvent actionEvent) {

    SettingsController settingsController = new SettingsController();
    settingsController.initData();
    this.client = new Client(settingsController.getSettings().getRemoteHost(),
        settingsController.getSettings().getRemotePort());

    if (client.isConnected()) {
      System.out.println("CONNECTED!");
      loginToServer(settingsController.getSettings());
    } else {
      System.out.println("NOT CONNECTED");
    }

  }

  private void loginToServer(Settings settings) {
    JSONObject object = new JSONObject();
    object.put("username", tUserName.getText());
    object.put("pass", tPassWord.getText());
    object = client.send(object);
    if (object.has("LOGGED_IN")) {
      showMainWindow();
    }

  }

  private void showMainWindow() {
    FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemResource("MainWindow.fxml"));
    Parent root = null;
    try {
      root = fxmlLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    MainWindowController mainWindowController = fxmlLoader.getController();
    mainWindowController.setClient(this.client);
    this.stage.setScene(new Scene(root));
    stage.setX(0);
    stage.setY(0);
  }

  public void openSettings(ActionEvent actionEvent) {
    FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemResource("settings.fxml"));
    try {
      Parent root = fxmlLoader.load();
      SettingsController settingsController = fxmlLoader.getController();
      settingsController.initData();
      Stage stage = new Stage();
      stage.setTitle("PODEŠAVANJA");
      stage.setScene(new Scene(root));
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.showAndWait();

      tUserName.setText(settingsController.getSettings().getUsername());

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public void setStage(Stage stage, Parent root) {
    this.stage = stage;
    this.loginRoot = root;
  }

  public Stage getStage() {
    return stage;
  }
}
