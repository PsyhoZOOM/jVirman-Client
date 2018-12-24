package com.psyhozoom.virman.Client.Controllers;

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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.json.JSONObject;

public class IsplataController implements Initializable {

  public TextArea tSvrhaPlacanja;
  public ComboBox<SifraPlacanja> cmbSifra;
  public TextField tModel;
  public TextField tPozivNaBroj;
  public ListView<Racuni> lsNaRacun;
  public Button bStampa;
  public TextField tIznos;
  public TextArea tPlatilac;
  public TextArea tPrimalac;
  public TextField tValuta;
  public TextField tRacunPrimaoca;
  private Client client;
  private Clients clients;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    lsNaRacun.setCellFactory(new Callback<ListView<Racuni>, ListCell<Racuni>>() {
      @Override
      public ListCell<Racuni> call(ListView<Racuni> param) {
        return new ListCell<Racuni>() {
          @Override
          protected void updateItem(Racuni item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
              setText("");
            } else {
              setText(item.getBrojRacuna());
            }
          }
        };
      }
    });

    cmbSifra.setConverter(new StringConverter<SifraPlacanja>() {
      @Override
      public String toString(SifraPlacanja object) {
        if (object != null) {
          return object.getBroj();
        } else {
          return "";
        }
      }

      @Override
      public SifraPlacanja fromString(String string) {
        if (string != null) {
          SifraPlacanja sifraPlacanja = new SifraPlacanja();
          sifraPlacanja.setBroj(string);
          return sifraPlacanja;
        }
        return new SifraPlacanja();
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
              setText(String.format(item.getBroj()));
            }
          }
        };
      }
    });

    lsNaRacun.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Racuni>() {
      @Override
      public void changed(ObservableValue<? extends Racuni> observable, Racuni oldValue,
          Racuni newValue) {
        tRacunPrimaoca.setText(newValue.getBrojRacuna().trim());
      }
    });

  }

  public void stampaj(ActionEvent actionEvent) {
    JSONObject object = new JSONObject();
    object.put("action", "uplatiIsplatu");
    object.put("racunPlatioca", lsNaRacun.getSelectionModel().getSelectedItem().getBrojRacuna());
    object.put("Platioc", clients.getNaziv());
    object.put("mesto", clients.getMesto());
    object.put("svrhaUplate", tSvrhaPlacanja.getText().trim().toUpperCase());
    object.put("sifraPlacanja", cmbSifra.getValue().getBroj());
    object.put("iznos", Double.valueOf(tIznos.getText().trim().replaceAll(",", "")));
    object.put("modelZaduzenje", tModel.getText().trim());
    object.put("pozivNaBrojZaduzenje", tPozivNaBroj.getText().trim());

    Izvestaji izvestaji = new Izvestaji();
    izvestaji.setRacunPlatioca(object.getString("racunPlatioca"));
    izvestaji.setPlatioc(object.getString("Platioc"));
    izvestaji.setPrimaoc(object.getString("Platioc"));
    izvestaji.setMestoPrimaoca(object.getString("mesto"));
    izvestaji.setMestoPlatioca(object.getString("mesto"));
    izvestaji.setSvrhaPlacanja(object.getString("svrhaUplate"));
    izvestaji.setSifraPlacanja(object.getString("sifraPlacanja"));
    izvestaji.setIznos(Double.valueOf(tIznos.getText().trim().replace(",", "")));
    izvestaji.setModelZaduzenje(object.getString("modelZaduzenje"));
    izvestaji.setPozivNaBrojZaduzenje(object.getString("pozivNaBrojZaduzenje"));


    object = client.send(object);

    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      return;
    }

    boolean stampanje = AlertUser.yesNo("ŠTAMPANJE", "DA LI ŽELITE ŠTAMAPANJE NALOGA ZA ISPLATU?");
    if (!stampanje) {
      return;
    }

    Stampa stampa = new Stampa();
    stampa.stampaIsplate(izvestaji, bStampa.getScene().getWindow());
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public void initData() {
    setKlijentRacuni();
    setSifre();
    tPlatilac.setText(String.format("%s\n%s", clients.getNaziv(), clients.getMesto()));
    tPrimalac.setText(String.format("%s\n%s", clients.getNaziv(), clients.getMesto()));
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

  public void setClientID(Clients clients) {
    this.clients = clients;
  }
}
