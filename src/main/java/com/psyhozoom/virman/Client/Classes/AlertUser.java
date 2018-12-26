package com.psyhozoom.virman.Client.Classes;


import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;


public class AlertUser {

  public static ButtonType DA = new ButtonType("DA", ButtonBar.ButtonData.YES);
  public static ButtonType NE = new ButtonType("NE", ButtonBar.ButtonData.CANCEL_CLOSE);
  private static Alert alert;

  public static void info(String title, String content) {
    alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setContentText(content);
    alert.setHeaderText("INFORMACIJA");
    alert.getDialogPane().getStylesheets()
        .add(ClassLoader.getSystemResource("main.css").toExternalForm());
    alert.showAndWait();
  }

  public static void error(String title, String content) {
    alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setContentText(content);
    alert.setHeaderText("GREŠKA");
    alert.getDialogPane().getStylesheets()
        .add(ClassLoader.getSystemResource("main.css").toExternalForm());
    alert.showAndWait();

  }

  public static void warrning(String title, String conent) {
    alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle(title);
    alert.setContentText(conent);
    alert.setHeaderText("UPOZORENJE");
    alert.getDialogPane().getStylesheets()
        .add(ClassLoader.getSystemResource("main.css").toExternalForm());
    alert.showAndWait();

  }

  public static boolean yesNo(String title, String content) {
    alert = new Alert(Alert.AlertType.WARNING, content, NE, DA);
    alert.setHeaderText(title);
    alert.setContentText(content);
    alert.setHeaderText("CHOOSE YOUR DESTINY");
    alert.getDialogPane().getStylesheets()
        .add(ClassLoader.getSystemResource("main.css").toExternalForm());

    Optional<ButtonType> result = alert.showAndWait();

    return result.isPresent() && result.get() == AlertUser.DA;

  }
}