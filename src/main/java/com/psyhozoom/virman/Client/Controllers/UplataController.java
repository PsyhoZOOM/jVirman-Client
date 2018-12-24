package com.psyhozoom.virman.Client.Controllers;

import com.psyhozoom.virman.Client.Classes.AlertUser;
import com.psyhozoom.virman.Client.Classes.Client;
import com.psyhozoom.virman.Client.Classes.Clients;
import com.psyhozoom.virman.Client.Classes.Izvestaji;
import com.psyhozoom.virman.Client.Classes.Racuni;
import com.psyhozoom.virman.Client.Classes.SifraPlacanja;
import com.psyhozoom.virman.Client.Classes.Stampa;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
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

public class UplataController implements Initializable {

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
  NumberFormat formatRS = NumberFormat.getInstance(new Locale.Builder().setLanguage("RS").setRegion("rs").build());
  NumberFormat formatEN = NumberFormat.getInstance(new Locale.Builder().setLanguage("EN").setRegion("us").build());

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

    lsNaRacun.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Racuni>() {
      @Override
      public void changed(ObservableValue<? extends Racuni> observable, Racuni oldValue,
          Racuni newValue) {
        tPlatilac.setText(String.format("%s\n%s", clients.getNaziv().trim().toUpperCase(),
            clients.getMesto().trim().toUpperCase()));
        tPrimalac.setText(String.format("%s\n%s", clients.getNaziv().trim().toUpperCase(),
            clients.getMesto().trim().toUpperCase()));
        tRacunPrimaoca.setText(newValue.getBrojRacuna());
      }
    });

    tIznos.focusedProperty().addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
          Boolean newValue) {
        if(!newValue){
          //tIznos.setText(nf.format(iznos).replace("din.", ""));
        }
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
