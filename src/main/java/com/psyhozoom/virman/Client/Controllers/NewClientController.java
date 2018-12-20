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

public class NewClientController implements Initializable {

  public JFXTextField tNaziv;
  public JFXTextField tImeVlasnika;
  public JFXTextField tMesto;
  public JFXTextField tTel1;
  public JFXTextField tTel2;
  public JFXTextArea taKomentar;
  public JFXButton bSnimi;
  private URL location;
  private ResourceBundle resources;


  private Client client;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public void saveClient(ActionEvent actionEvent) {
    JSONObject object = new JSONObject();
    object.put("action", "saveNewClient");
    object.put("naziv", tNaziv.getText().trim());
    object.put("imeVlasnika", tImeVlasnika.getText().trim());
    object.put("mesto", tMesto.getText().trim());
    object.put("tel1", tTel1.getText().trim());
    object.put("tel2", tTel2.getText().trim());
    object.put("komentar", taKomentar.getText().trim());

    object = client.send(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
    }

    Stage stage = (Stage) bSnimi.getScene().getWindow();
    stage.close();
  }

}
