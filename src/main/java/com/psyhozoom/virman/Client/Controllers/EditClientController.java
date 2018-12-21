package com.psyhozoom.virman.Client.Controllers;

import com.psyhozoom.virman.Client.Classes.AlertUser;
import com.psyhozoom.virman.Client.Classes.Client;
import com.psyhozoom.virman.Client.Classes.Clients;
import com.psyhozoom.virman.Client.Classes.Dobavljaci;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.json.JSONObject;

public class EditClientController implements Initializable {

  public TextField tNaziv;
  public TextField tImeVlasnika;
  public TextField tMesto;
  public TextField tTel1;
  public TextField tTel2;
  public TextArea tKomentar;
  public Button bRacuni;
  public Button bSnimi;
  public Button bNovDobavljac;
  public Button bIzmenaDobavljaca;
  public Button bBrisanje;
  public ListView<Dobavljaci> lsDobavljaci;
  private URL location;
  private ResourceBundle resource;
  private Client client;
  private Clients editClient;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resource = resources;

    lsDobavljaci.setCellFactory(new Callback<ListView<Dobavljaci>, ListCell<Dobavljaci>>() {
      @Override
      public ListCell<Dobavljaci> call(ListView<Dobavljaci> param) {
        return new ListCell<Dobavljaci>() {
          @Override
          protected void updateItem(Dobavljaci item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
              setText("");
            } else {
              setText(String.format("%s %s", item.getNaziv(), item.getImeVlasnika()));
            }
          }
        };
      }
    });
  }

  public void initData() {
    JSONObject object = new JSONObject();
    object.put("action", "getClientData");
    object.put("id", editClient.getId());
    object = client.send(object);
    if (object.has("ERROR")) {
      AlertUser.error("GREKSA", object.getString("ERROR"));
      return;
    }

    setClientData(object);

  }

  public void setClient(Client client) {
    this.client = client;
  }

  public void setEditClient(Clients editClient) {
    this.editClient = editClient;
  }

  private void setClientData(JSONObject data) {
    tNaziv.setText(data.getString("naziv"));
    tImeVlasnika.setText(data.getString("imeVlasnika"));
    tMesto.setText(data.getString("mesto"));
    tTel1.setText(data.getString("tel1"));
    tTel2.setText(data.getString("tel2"));
    tKomentar.setText(data.getString("komentar"));

    showDobavljaci();
  }

  private void showDobavljaci() {
    Dobavljaci dobavljaci = new Dobavljaci();
    ArrayList<Dobavljaci> dobavljaciArray = dobavljaci
        .getDobavljaciArray(client, editClient.getId());
    setDobavljaciData(dobavljaciArray);
  }

  private void setDobavljaciData(ArrayList<Dobavljaci> object) {
    lsDobavljaci.setItems(FXCollections.observableList(object));

  }

  public void showRacuniKlijent(ActionEvent actionEvent) {
    FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource("RacuniClient.fxml"));
    Parent root = null;
    try {
      root = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    RacuniClient racuniClient = loader.getController();
    racuniClient.setClient(this.client);
    racuniClient.setClienID(editClient.getId());
    racuniClient.initData();

    Stage stage = new Stage();
    stage.setTitle("RAČUNI KLIJENTA");
    stage.initModality(Modality.APPLICATION_MODAL);
    if (root == null) {
      return;
    }

    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.showAndWait();

  }

  public void saveData(ActionEvent actionEvent) {
    JSONObject object = new JSONObject();
    object.put("action", "saveClientData");
    object.put("naziv", tNaziv.getText().trim());
    object.put("imeVlasnika", tImeVlasnika.getText().trim());
    object.put("mesto", tMesto.getText().trim());
    object.put("tel1", tTel1.getText().trim());
    object.put("tel2", tTel2.getText().trim());
    object.put("komentar", tKomentar.getText().trim());
    object.put("clientID", editClient.getId());
    object = client.send(object);
    if (object.has("ERROR")) {
      AlertUser.error("GREKSA", object.getString("ERROR"));
      return;
    }
    initData();
  }

  public void newDobavljac(ActionEvent actionEvent) {
    FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource("NoviDobavljac.fxml"));
    Parent root = null;
    try {
      root = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    NoviDobavljacController noviDobavljacController = loader.getController();
    noviDobavljacController.setClient(this.client);
    noviDobavljacController.setClientID(editClient.getId());
    Stage stage = new Stage();
    stage.setTitle("NOVI DOBAVLJAČ");
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setScene(new Scene(root));
    stage.showAndWait();
    initData();
  }

  public void editDobavljac(ActionEvent actionEvent) {
    FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource("editDobavljac.fxml"));
    Parent root = null;
    try {
      root = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (root == null) {
      return;
    }

    if (lsDobavljaci.getSelectionModel().getSelectedIndex() == -1) {
      return;
    }

    EditDobavljac editDobavljac = loader.getController();
    editDobavljac.setClient(this.client);
    editDobavljac.setDobavljaciD(lsDobavljaci.getSelectionModel().getSelectedItem().getId());
    editDobavljac.initData();

    Stage stage = new Stage();
    stage.setTitle("IZMENA DOBAVLJAČA");
    stage.setScene(new Scene(root));
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.showAndWait();
    initData();
  }

  public void deleteDobavljac(ActionEvent actionEvent) {
    if (lsDobavljaci.getSelectionModel().getSelectedIndex() == -1) {
      return;
    }
    boolean yesNo = AlertUser
        .yesNo("BRISANJE DOBAVLJAČA", String.format("DA LI STE SIGURNI DA ŽELITE DA OBRIŠETE %s?",
            lsDobavljaci.getSelectionModel().getSelectedItem().getNaziv()));
    if (!yesNo) {
      return;
    }

    JSONObject object = new JSONObject();
    object.put("action", "deleteDobavljac");
    object.put("dobavljacID", lsDobavljaci.getSelectionModel().getSelectedItem().getId());
    object = client.send(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
    }
  }
}
