package com.psyhozoom.virman.Client.Controllers;

import com.jfoenix.controls.JFXListView;
import com.psyhozoom.virman.Client.Classes.AlertUser;
import com.psyhozoom.virman.Client.Classes.Client;
import com.psyhozoom.virman.Client.Classes.SifraPlacanja;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.json.JSONObject;

public class SifrePlacanjaController implements Initializable {

  public Button bIzbris;
  public Button bNov;
  public Label lOpis;
  public Label lDuziOpis;
  public JFXListView<SifraPlacanja> lsSifre;
  Client client;
  private URL location;
  private ResourceBundle resources;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

    lsSifre.getSelectionModel().selectedItemProperty().addListener(
        new ChangeListener<SifraPlacanja>() {
          @Override
          public void changed(ObservableValue<? extends SifraPlacanja> observable,
              SifraPlacanja oldValue,
              SifraPlacanja newValue) {
            lOpis.setText(newValue.getOpis());
            lDuziOpis.setText(newValue.getDuziOpis());
          }
        });

    lsSifre.setCellFactory(new Callback<ListView<SifraPlacanja>, ListCell<SifraPlacanja>>() {
      @Override
      public ListCell<SifraPlacanja> call(ListView<SifraPlacanja> param) {
        return new ListCell<SifraPlacanja>() {
          @Override
          protected void updateItem(SifraPlacanja item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
              setText("");
            } else {
              setText(String.format("%s ", item.getBroj()));
            }
          }
        };
      }
    });
  }


  public void initData() {
    JSONObject object = new JSONObject();
    object.put("action", "getAllSifre");
    object = client.send(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      return;
    }

    ArrayList<SifraPlacanja> sifrePlacanjaArrayList = new ArrayList<>();
    for (int i = 0; i < object.length(); i++) {
      JSONObject sifra = object.getJSONObject(String.valueOf(i));
      SifraPlacanja sifraPlacanja = new SifraPlacanja();
      sifraPlacanja.setId(sifra.getInt("id"));
      sifraPlacanja.setBroj(sifra.getString("broj"));
      sifraPlacanja.setOpis(sifra.getString("opis"));
      sifraPlacanja.setDuziOpis(sifra.getString("duziOpis"));
      sifrePlacanjaArrayList.add(sifraPlacanja);
    }

    ObservableList list = FXCollections.observableList(sifrePlacanjaArrayList);
    lsSifre.setItems(list);


  }

  public void addSifru(ActionEvent actionEvent) {
    Parent root = null;
    FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource("NovaSifra.fxml"));

    try {
      root = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (root == null) {
      return;
    }

    NovaSifraController novaSifraController = loader.getController();
    novaSifraController.setClient(this.client);

    Stage stage = new Stage();
    stage.setTitle("NOVA ŠIFRA");
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setScene(new Scene(root));

    stage.showAndWait();
    initData();


  }

  public void deleteSifru(ActionEvent actionEvent) {
    if (lsSifre.getSelectionModel().getSelectedIndex() == -1) {
      return;
    }

    int idSifre = lsSifre.getSelectionModel().getSelectedItem().getId();

    JSONObject object = new JSONObject();
    object.put("action", "deleteSifra");
    object.put("idSifre", idSifre);
    object = client.send(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
    }
    initData();
  }


  public void setClient(Client client) {
    this.client = client;
  }
}
