package com.psyhozoom.virman.Client.Controllers;

import com.psyhozoom.virman.Client.Classes.AlertUser;
import com.psyhozoom.virman.Client.Classes.Client;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;

public class NovaSifraController implements Initializable {

  public TextField tBrojSifre;
  public TextField tOpis;
  public TextArea tDuziOpis;
  public Button bSnimi;
  private URL location;
  private ResourceBundle resoures;
  private Client client;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resoures = resources;

    tBrojSifre.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        if (!newValue.matches("\\d*")) {
          tBrojSifre.setText(newValue.replaceAll("[^\\d]", ""));
        }
      }
    });
  }


  public void snimiSifru(ActionEvent actionEvent) {

    JSONObject object = new JSONObject();
    object.put("action", "addSifra");
    object.put("broj", tBrojSifre.getText().trim());
    object.put("opis", tOpis.getText().trim());
    object.put("duziOpis", tDuziOpis.getText().trim());

    object = client.send(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
    }

    Stage stage = (Stage) bSnimi.getScene().getWindow();
    stage.close();
  }

  public void setClient(Client client) {
    this.client = client;
  }
}
