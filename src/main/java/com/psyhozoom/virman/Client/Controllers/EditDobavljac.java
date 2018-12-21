package com.psyhozoom.virman.Client.Controllers;

import com.psyhozoom.virman.Client.Classes.AlertUser;
import com.psyhozoom.virman.Client.Classes.Client;
import com.psyhozoom.virman.Client.Classes.Racuni;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import org.json.JSONObject;

public class EditDobavljac implements Initializable {

  public TextField tNaziv;
  public TextField tImeVlasnika;
  public TextField tMesto;
  public TextField tTel1;
  public TextField tTel2;
  public TextArea tKomentar;
  public Button bNov;
  public Button bSnimi;
  public ListView<Racuni> lsRacuni;
  public TextField tBrojRacuna;
  public Button bOrisi;
  private URL location;
  private ResourceBundle resources;

  private int dobavljaciD;
  private Client client;


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

    lsRacuni.setCellFactory(new Callback<ListView<Racuni>, ListCell<Racuni>>() {
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
    setDobavljacData();

  }

  private void setDobavljacData() {
    JSONObject object = new JSONObject();
    object.put("action", "getDobavljacData");
    object.put("dobavljacID", dobavljaciD);
    object = client.send(object);
    if (object.length() < 1) {
      return;
    }

    tNaziv.setText(object.getString("naziv"));
    tImeVlasnika.setText(object.getString("imeVlasnika"));
    tMesto.setText(object.getString("mesto"));
    tTel1.setText(object.getString("tel1"));
    tTel2.setText(object.getString("tel2"));
    tKomentar.setText(object.getString("komentar"));

    setRacuniOfDobavljac();


  }

  private void setRacuniOfDobavljac() {
    JSONObject object = new JSONObject();
    object.put("action", "getRacuniDobavljaca");
    object.put("dobavljacID", dobavljaciD);
    object = client.send(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      return;
    }
    ArrayList<Racuni> racuniArrayList = new ArrayList<>();
    for (int i = 0; i < object.length(); i++) {
      JSONObject rac = object.getJSONObject(String.valueOf(i));
      Racuni racun = new Racuni();
      racun.setId(rac.getInt("id"));
      racun.setBrojRacuna(rac.getString("racun"));
      racun.setMainRacun(rac.getBoolean("mainRacun"));
      racuniArrayList.add(racun);
    }

    lsRacuni.setItems(FXCollections.observableList(racuniArrayList));


  }


  public int getDobavljaciD() {
    return dobavljaciD;
  }

  public void setDobavljaciD(int dobavljaciD) {
    this.dobavljaciD = dobavljaciD;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }


  public void ObrisiRacun(ActionEvent actionEvent) {
    if (lsRacuni.getSelectionModel().getSelectedIndex() == -1) {
      return;
    }
    JSONObject object = new JSONObject();
    object.put("action", "deleteDobavljacRacun");
    object.put("racunID", lsRacuni.getSelectionModel().getSelectedItem().getId());
    object = client.send(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      return;
    }
    initData();

  }

  public void snimiIzmene(ActionEvent actionEvent) {
    JSONObject object = new JSONObject();
    object.put("action", "editDobavljac");
    object.put("dobavljacID", dobavljaciD);
    object.put("naziv", tNaziv.getText().trim());
    object.put("imeVlasnika", tImeVlasnika.getText().trim());
    object.put("mesto", tMesto.getText().trim());
    object.put("tel1", tTel1.getText().trim());
    object.put("tel2", tTel2.getText().trim());
    object.put("komentar", tKomentar.getText().trim());
    object = client.send(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
    }
  }

  public void dodajNovRacun(ActionEvent actionEvent) {
    if (tBrojRacuna.getText().trim().isEmpty()) {
      return;
    }
    JSONObject object = new JSONObject();
    object.put("action", "addNewRacunToDobavljac");
    object.put("racun", tBrojRacuna.getText().trim());
    object.put("dobavljacID", dobavljaciD);
    object = client.send(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      return;
    }
    initData();
  }

  public void setPodrazumevaniRacun(ActionEvent actionEvent) {
    if (lsRacuni.getSelectionModel().getSelectedIndex() == -1) {
      return;
    }
    JSONObject object = new JSONObject();
    object.put("action", "updateDobavljacMainRacun");
    object.put("dobavljacID", dobavljaciD);
    object.put("racunID", lsRacuni.getSelectionModel().getSelectedItem().getId());
    object = client.send(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      return;
    }
    initData();

  }
}
