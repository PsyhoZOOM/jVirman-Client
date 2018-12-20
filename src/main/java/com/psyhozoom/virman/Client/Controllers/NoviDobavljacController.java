package com.psyhozoom.virman.Client.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.psyhozoom.virman.Client.Classes.AlertUser;
import com.psyhozoom.virman.Client.Classes.Client;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import org.json.JSONObject;

public class NoviDobavljacController implements Initializable {

  public JFXButton bSnimi;
  public JFXTextField tnaziv;
  public JFXTextField tImeVlasnika;
  public JFXTextField tMesto;
  public JFXTextField tTel1;
  public JFXTextField tTel2;
  public JFXTextArea tKomentar;
  public JFXButton bRacuni;
  private URL location;
  private ResourceBundle resources;


  private int clientID;
  private Client client;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;
  }

  public void saveNewDobavljac(ActionEvent actionEvent) {
    JSONObject object = new JSONObject();
    object.put("action", "saveNewDobavljac");
    object.put("clientID", clientID);

    object.put("naziv", tnaziv.getText().trim());
    object.put("imeVlasnika", tImeVlasnika.getText().trim());
    object.put("mesto", tMesto.getText().trim());
    object.put("tel1", tTel1.getText().trim());
    object.put("tel2", tTel2.getText().trim());
    object.put("komentar", tKomentar.getText().trim());
    object = client.send(object);

    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      return;
    }
    Stage stage = (Stage) bSnimi.getScene().getWindow();
    stage.close();

  }

  public void showRacuni(ActionEvent actionEvent) {

  }

  public void setClient(Client client) {
    this.client = client;
  }

  public void setClientID(int clientID) {
    this.clientID = clientID;
  }
}
