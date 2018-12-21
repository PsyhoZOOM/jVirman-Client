package com.psyhozoom.virman.Client.Controllers;

import com.psyhozoom.virman.Client.Classes.AlertUser;
import com.psyhozoom.virman.Client.Classes.Client;
import com.psyhozoom.virman.Client.Classes.Racuni;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import org.json.JSONObject;

public class RacuniClient implements Initializable {

  public TextField tRacun;
  public Button bAdd;
  public ListView<Racuni> listRacuni;
  private URL location;
  private ResourceBundle resources;
  private Client client;
  private int clienID;


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

    listRacuni.setCellFactory(new Callback<ListView<Racuni>, ListCell<Racuni>>() {
      @Override
      public ListCell<Racuni> call(ListView<Racuni> param) {
        return new ListCell<Racuni>() {
          @Override
          protected void updateItem(Racuni item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
              setText("");
            } else {
              if (item.isMainRacun()) {
                setStyle("-fx-background-color: green");
              } else {
                setStyle(getStyle());
              }
              setText(item.getBrojRacuna());
            }
          }
        };
      }
    });
  }

  public void initData() {
    Racuni racuni = new Racuni();
    racuni.setClient(this.client);
    racuni.setData(clienID);

    if (racuni.getRacuniArrayList() == null) {
      return;
    }

    listRacuni.setItems(FXCollections.observableList(racuni.getRacuniArrayList()));


  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public int getClienID() {
    return clienID;
  }

  public void setClienID(int clienID) {
    this.clienID = clienID;
  }

  public void addRacun(ActionEvent actionEvent) {
    JSONObject object = new JSONObject();
    object.put("action", "addRacunToClient");
    object.put("clientID", clienID);
    object.put("racun", tRacun.getText());
    object = client.send(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      return;
    }
    initData();
  }

  public void delRacun(ActionEvent actionEvent) {
    if (listRacuni.getSelectionModel().getSelectedIndex() == -1) {
      return;
    }
    Racuni selectedItem = listRacuni.getSelectionModel().getSelectedItem();
    boolean brisanje_računa = AlertUser.yesNo("BRISANJE RAČUNA", String.format(
        "Da li ste sigurni da želite da izbrišete račun %s", selectedItem.getBrojRacuna()
    ));

    if (!brisanje_računa) {
      return;
    }

    JSONObject object = new JSONObject();
    object.put("action", "deleteClientRacun");
    object.put("racunID", selectedItem.getId());
    object = client.send(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      return;
    }
    initData();

  }

  public void setPodrazumevaniRacun(ActionEvent actionEvent) {
    if (listRacuni.getSelectionModel().getSelectedIndex() == -1) {
      return;
    }

    int idRacuna = listRacuni.getSelectionModel().getSelectedItem().getId();
    JSONObject object = new JSONObject();
    object.put("action", "updateClientMainRacun");
    object.put("clientID", clienID);
    object.put("racunID", idRacuna);
    object = client.send(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      return;
    }
    initData();

  }
}
