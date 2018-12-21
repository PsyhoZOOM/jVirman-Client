package com.psyhozoom.virman.Client.Controllers;

import com.psyhozoom.virman.Client.Classes.AlertUser;
import com.psyhozoom.virman.Client.Classes.Client;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;

public class NewClientController implements Initializable {

  public TextField tNaziv;
  public TextField tImeVlasnika;
  public TextField tMesto;
  public TextField tTel1;
  public TextField tTel2;
  public TextArea taKomentar;
  public Button bSnimi;
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
