package com.psyhozoom.virman.Client.Controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.psyhozoom.virman.Client.Classes.AlertUser;
import com.psyhozoom.virman.Client.Classes.Client;
import com.psyhozoom.virman.Client.Classes.Clients;
import com.psyhozoom.virman.Client.Classes.Izvestaji;
import com.psyhozoom.virman.Client.Classes.Racuni;
import com.psyhozoom.virman.Client.Classes.SifraPlacanja;
import com.psyhozoom.virman.Client.Classes.Stampa;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.json.JSONObject;

public class UplataController implements Initializable {

  public JFXTextArea tSvrhaPlacanja;
  public JFXComboBox<SifraPlacanja> cmbSifra;
  public JFXTextField tModel;
  public JFXTextField tPozivNaBroj;
  public JFXListView<Racuni> lsNaRacun;
  public Button bStampa;
  public JFXTextField tIznos;

  private Client client;
  private Clients clients;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    cmbSifra.setConverter(new StringConverter<SifraPlacanja>() {
      @Override
      public String toString(SifraPlacanja object) {
        if (object == null) {
          return "";
        }
        return object.getBroj();
      }

      @Override
      public SifraPlacanja fromString(String string) {
        SifraPlacanja sifraPlacanja = new SifraPlacanja();
        sifraPlacanja.setBroj(string);
        sifraPlacanja.setOpis("");
        sifraPlacanja.setDuziOpis("");
        sifraPlacanja.setId(0);
        return sifraPlacanja;
      }
    });

    cmbSifra.setCellFactory(new Callback<ListView<SifraPlacanja>, ListCell<SifraPlacanja>>() {
      @Override
      public ListCell<SifraPlacanja> call(ListView<SifraPlacanja> param) {
        return new ListCell<SifraPlacanja>() {
          @Override
          protected void updateItem(SifraPlacanja item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
              setText("");
            } else {
              setText(item.getBroj());
            }
          }
        };
      }
    });

    lsNaRacun.setCellFactory(new Callback<ListView<Racuni>, ListCell<Racuni>>() {
      @Override
      public ListCell<Racuni> call(ListView<Racuni> param) {
        return new ListCell<Racuni>() {
          @Override
          protected void updateItem(Racuni item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
              setText("");
            } else {
              setText(item.getBrojRacuna());
            }
          }
        };
      }
    });
  }

  public void initData() {
    setKlijentRacuni();
    setSifre();

  }

  private void setSifre() {
    SifraPlacanja sifraPlacanja = new SifraPlacanja();
    ArrayList<SifraPlacanja> allSifre = sifraPlacanja.getAllSifre(this.client);
    cmbSifra.setItems(FXCollections.observableList(allSifre));
  }

  private void setKlijentRacuni() {
    Racuni racuni = new Racuni();
    racuni.setClient(this.client);
    racuni.setData(clients.getId());

    lsNaRacun.setItems(FXCollections.observableList(racuni.getRacuniArrayList()));
    lsNaRacun.getSelectionModel().select(0);


  }


  public void stampaj(ActionEvent actionEvent) {
    JSONObject object = new JSONObject();
    object.put("action", "uplatiUplatu");
    object.put("racunPrimaoca", lsNaRacun.getSelectionModel().getSelectedItem().getBrojRacuna());
    object.put("Platioc", clients.getNaziv());
    object.put("mesto", clients.getMesto());
    object.put("svrhaUplate", tSvrhaPlacanja.getText().trim().toUpperCase());
    object.put("sifraPlacanja", cmbSifra.getValue().getBroj());
    object.put("iznos", Double.valueOf(tIznos.getText().trim().replaceAll(",", "")));
    object.put("modelOdobrenje", tModel.getText().trim());
    object.put("pozivNaBrojOdobrenje", tPozivNaBroj.getText().trim());
    Izvestaji izvestaji = new Izvestaji();
    izvestaji.setRacunPrimaoca(object.getString("racunPrimaoca"));
    izvestaji.setPlatioc(object.getString("Platioc"));
    izvestaji.setPrimaoc(object.getString("Platioc"));
    izvestaji.setMestoPlatioca(object.getString("mesto"));
    izvestaji.setMestoPrimaoca(object.getString("mesto"));
    izvestaji.setSvrhaPlacanja(object.getString("svrhaUplate"));
    izvestaji.setSifraPlacanja(object.getString("sifraPlacanja"));
    izvestaji.setIznos(object.getDouble("iznos"));
    izvestaji.setModelOdobrenje(object.getString("modelOdobrenje"));
    izvestaji.setPozivNaBrojOdobrenje(object.getString("pozivNaBrojOdobrenje"));

    object = client.send(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      return;
    }

    boolean stampanje = AlertUser.yesNo("ŠTAMPANJE", "DA LI ŽELITE ŠTAMPANJE NALOGA ZA UPLATU?");
    if (!stampanje) {
      return;
    }

    Stampa stampa = new Stampa();
    stampa.stampaUplate(izvestaji, bStampa.getScene().getWindow());


  }

  public void setClient(Client client) {
    this.client = client;
  }

  public void setClientID(Clients clients) {
    this.clients = clients;
  }
}
