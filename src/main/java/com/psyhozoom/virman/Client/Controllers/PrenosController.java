package com.psyhozoom.virman.Client.Controllers;

import com.psyhozoom.virman.Client.Classes.AlertUser;
import com.psyhozoom.virman.Client.Classes.Client;
import com.psyhozoom.virman.Client.Classes.Clients;
import com.psyhozoom.virman.Client.Classes.Dobavljaci;
import com.psyhozoom.virman.Client.Classes.Izvestaji;
import com.psyhozoom.virman.Client.Classes.Racuni;
import com.psyhozoom.virman.Client.Classes.SifraPlacanja;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.json.JSONObject;

public class PrenosController implements Initializable {

  public ListView<Racuni> lsSaRacuna;
  public ListView<Dobavljaci> lsDobavjac;
  public ListView<Racuni> lsNaRačun;
  public TextField tModelPlatioca;
  public TextField tPozivNaBrojPlatioca;
  public TextArea tSvrhaPlacanja;
  public TextField tIznos;
  public TextField tModelPrimaoca;
  public TextField tPozivNaBrojPrimaoca;
  public ComboBox<SifraPlacanja> cmbSifre;
  public TextField tSerachDobavljac;
  public Button bSrtampa;
  private Client client;
  private Clients clients;
  private ArrayList<Dobavljaci> dobavljaciArray;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    lsSaRacuna.setCellFactory(new Callback<ListView<Racuni>, ListCell<Racuni>>() {
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

    lsNaRačun.setCellFactory(new Callback<ListView<Racuni>, ListCell<Racuni>>() {
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

    lsDobavjac.setCellFactory(new Callback<ListView<Dobavljaci>, ListCell<Dobavljaci>>() {
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

    cmbSifre.setCellFactory(new Callback<ListView<SifraPlacanja>, ListCell<SifraPlacanja>>() {
      @Override
      public ListCell<SifraPlacanja> call(ListView<SifraPlacanja> param) {
        return new ListCell<SifraPlacanja>() {
          @Override
          protected void updateItem(SifraPlacanja item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
              setText("");
            } else {
              setText(item.getBroj());
            }
          }
        };
      }
    });

    cmbSifre.setConverter(new StringConverter<SifraPlacanja>() {
      @Override
      public String toString(SifraPlacanja object) {
        return object.getBroj();
      }

      @Override
      public SifraPlacanja fromString(String string) {
        SifraPlacanja sifraPlacanja = new SifraPlacanja();
        sifraPlacanja.setBroj(string);
        return sifraPlacanja;
      }
    });

    lsDobavjac.getSelectionModel().selectedItemProperty().addListener(
        new ChangeListener<Dobavljaci>() {
          @Override
          public void changed(ObservableValue<? extends Dobavljaci> observable, Dobavljaci oldValue,
              Dobavljaci newValue) {
            if (newValue != null) {
              setDobavljacRacune(newValue.getId());
            }
          }
        });
    tSerachDobavljac.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        filterDobavljaci(tSerachDobavljac.getText().toLowerCase().trim());
      }
    });
  }

  private void filterDobavljaci(String trim) {
    ArrayList<Dobavljaci> arrayList = new ArrayList<>();
    for (Dobavljaci item : this.dobavljaciArray) {
      if (item.getNaziv().toLowerCase().trim().contains(trim) || item.getImeVlasnika().toLowerCase()
          .trim().contains(trim)) {
        arrayList.add(item);
      }
    }

    lsDobavjac.setItems(FXCollections.observableList(arrayList));
  }

  private void setDobavljacRacune(int id) {
    JSONObject object = new JSONObject();
    object.put("action", "getRacuniDobavljaca");
    object.put("dobavljacID", id);
    object = client.send(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      return;
    }

    ArrayList<Racuni> racuniArrayList = new ArrayList<>();
    for (int i = 0; i < object.length(); i++) {
      JSONObject rac = object.getJSONObject(String.valueOf(i));
      Racuni racuni = new Racuni();
      racuni.setId(rac.getInt("id"));
      racuni.setBrojRacuna(rac.getString("racun"));
      racuniArrayList.add(racuni);
    }

    lsNaRačun.setItems(FXCollections.observableList(racuniArrayList));
    lsNaRačun.getSelectionModel().select(0);
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public void initData() {
    setClientRacuni();
    setSifre();
    setDobavljaci();
  }

  private void setDobavljaci() {
    Dobavljaci dobavljaci = new Dobavljaci();
    this.dobavljaciArray = dobavljaci.getDobavljaciArray(client, clients.getId());
    lsDobavjac.setItems(FXCollections.observableList(dobavljaciArray));


  }

  private void setSifre() {
    SifraPlacanja sifraPlacanja = new SifraPlacanja();
    ArrayList<SifraPlacanja> allSifre = sifraPlacanja.getAllSifre(this.client);
    cmbSifre.setItems(FXCollections.observableList(allSifre));
  }

  private void setClientRacuni() {
    Racuni racuni = new Racuni();
    racuni.setClient(this.client);
    racuni.setData(clients.getId());
    ArrayList<Racuni> racuniArrayList = racuni.getRacuniArrayList();
    lsSaRacuna.setItems(FXCollections.observableList(racuniArrayList));
    lsSaRacuna.getSelectionModel().select(0);
  }

  public void setClientID(Clients clients) {
    this.clients = clients;
  }

  public void stampa(ActionEvent actionEvent) {
    JSONObject object = new JSONObject();
    object.put("action", "uplatiPrenos");
    object.put("platioc", clients.getNaziv());
    object.put("mestoPlatioca", clients.getMesto());
    object.put("svrhaPlacanja", tSvrhaPlacanja.getText().trim().toUpperCase());
    object.put("racunPlatioca", lsSaRacuna.getSelectionModel().getSelectedItem().getBrojRacuna());
    object.put("primaoc", lsDobavjac.getSelectionModel().getSelectedItem().getNaziv());
    object.put("mestoPrimaoca", lsDobavjac.getSelectionModel().getSelectedItem().getMesto());
    object.put("racunPrimaoca", lsNaRačun.getSelectionModel().getSelectedItem().getBrojRacuna());
    object.put("sifraPlacanja", cmbSifre.getValue().getBroj());
    object.put("iznos", Double.valueOf(tIznos.getText().trim()));
    object.put("modelZaduzenje", tModelPlatioca.getText().trim());
    object.put("pozivNaBrojZaduzenje", tPozivNaBrojPlatioca.getText().trim());
    object.put("modelOdobrenje", tModelPrimaoca.getText().trim());
    object.put("pozivNaBrojOdobrenje", tPozivNaBrojPrimaoca.getText().trim());

    Izvestaji izvestaji = new Izvestaji();
    izvestaji.setPlatioc(object.getString("platioc"));
    izvestaji.setMestoPlatioca(object.getString("mestoPlatioca"));
    izvestaji.setSvrhaPlacanja(object.getString("svrhaPlacanja"));
    izvestaji.setRacunPlatioca(object.getString("racunPlatioca"));
    izvestaji.setPrimaoc(object.getString("primaoc"));
    izvestaji.setMestoPrimaoca(object.getString("mestoPrimaoca"));
    izvestaji.setRacunPrimaoca(object.getString("racunPrimaoca"));
    izvestaji.setSifraPlacanja(object.getString("sifraPlacanja"));
    izvestaji.setIznos(object.getDouble("iznos"));
    izvestaji.setModelZaduzenje(object.getString("modelZaduzenje"));
    izvestaji.setPozivNaBrojZaduzenje(object.getString("pozivNaBrojZaduzenje"));
    izvestaji.setModelOdobrenje(object.getString("modelOdobrenje"));
    izvestaji.setPozivNaBrojOdobrenje(object.getString("pozivNaBrojOdobrenje"));
    object = client.send(object);


    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      return;
    }

    boolean stampanje = AlertUser.yesNo("ŠTAMPA", "DA LI ŽEITE ŠTAMPANJE NALOGA ZA PRENOS?");

    if (!stampanje) {
      return;
    }

    // Stampa stampa = new Stampa(bSrtampa.getScene().getWindow());
    /// stampa.stampaPrenost(object);
  }
}
