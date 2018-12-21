package com.psyhozoom.virman.Client.Controllers;

import com.psyhozoom.virman.Client.Classes.AlertUser;
import com.psyhozoom.virman.Client.Classes.Client;
import com.psyhozoom.virman.Client.Classes.Izvestaji;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.json.JSONObject;

public class IzvestajiController implements Initializable {

  public DatePicker dtpDatum;
  public Button bRefresh;
  public MenuItem cmIzbrisi;
  public TableView<Izvestaji> tblIzvestaj;
  private TableColumn<Izvestaji, String> cDatum;
  private TableColumn<Izvestaji, String> cVirman;
  private TableColumn<Izvestaji, String> cKlijent;
  private TableColumn<Izvestaji, String> cDobavljac;
  private TableColumn<Izvestaji, String> cRacunPlatioca;
  private TableColumn<Izvestaji, String> cRacunPrimaoca;
  private TableColumn<Izvestaji, String> cSifraPlacanja;
  private TableColumn<Izvestaji, String> cModelZaduzenje;
  private TableColumn<Izvestaji, String> cModelOdobrenje;
  private TableColumn<Izvestaji, String> cPozivNaBrojZaduzenje;
  private TableColumn<Izvestaji, String> cPozivNaBrojOdobrenje;
  private TableColumn<Izvestaji, String> cSvrhaPlacanja;
  private TableColumn<Izvestaji, Double> cIznos;


  private DecimalFormat df = new DecimalFormat("###,###,##0.00");
  private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  private Client client;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    dtpDatum.setConverter(new StringConverter<LocalDate>() {
      @Override
      public String toString(LocalDate object) {
        if (object == null) {
          return LocalDate.now().format(dtf);
        } else {
          return object.format(dtf);
        }
      }

      @Override
      public LocalDate fromString(String string) {
        if (string.trim().isEmpty() || string == null) {
          return LocalDate.now();
        } else {
          LocalDate date = LocalDate.parse(string, dtf);
          return date;
        }
      }
    });

    cDatum = new TableColumn<>("DATUM");
    cVirman = new TableColumn<>("NALOG");
    cKlijent = new TableColumn<>("PLATIOC");
    cDobavljac = new TableColumn<>("PRIMAOC");
    cRacunPlatioca = new TableColumn<>("RAČUN PLATIOCA");
    cRacunPrimaoca = new TableColumn<>("RAČUN PRIMAOCA");
    cSifraPlacanja = new TableColumn<>("ŠIFRA PLAĆANJA");
    cModelZaduzenje = new TableColumn<>("MODEL ZADUŽENJA");
    cPozivNaBrojZaduzenje = new TableColumn<>("POZIV NA BROJ ZADUŽENJE");
    cModelOdobrenje = new TableColumn<>("MODEL ODOBRENJA");
    cPozivNaBrojOdobrenje = new TableColumn<>("POZIV NA BROJ ODOBRENJE");
    cSvrhaPlacanja = new TableColumn<>("SVRHA PLAĆANJA");
    cIznos = new TableColumn<>("IZNOS");

    cDatum.setCellValueFactory(new PropertyValueFactory("date"));
    cVirman.setCellValueFactory(new PropertyValueFactory("virman"));
    cKlijent.setCellValueFactory(new PropertyValueFactory("platioc"));
    cDobavljac.setCellValueFactory(new PropertyValueFactory("primaoc"));
    cRacunPlatioca.setCellValueFactory(new PropertyValueFactory("racunPlatioca"));
    cRacunPrimaoca.setCellValueFactory(new PropertyValueFactory("racunPrimaoca"));
    cSifraPlacanja.setCellValueFactory(new PropertyValueFactory("sifraPlacanja"));
    cModelZaduzenje.setCellValueFactory(new PropertyValueFactory("modelZaduzenje"));
    cPozivNaBrojZaduzenje
        .setCellValueFactory(new PropertyValueFactory("pozivNaBrojZaduzenje"));
    cModelOdobrenje.setCellValueFactory(new PropertyValueFactory("modelOdobrenje"));
    cPozivNaBrojOdobrenje
        .setCellValueFactory(new PropertyValueFactory("pozivNaBrojOdobrenje"));
    cSvrhaPlacanja.setCellValueFactory(new PropertyValueFactory("svrhaPlacanja"));
    cIznos.setCellValueFactory(new PropertyValueFactory("iznos"));

    tblIzvestaj.getColumns()
        .addAll(cDatum, cVirman, cKlijent, cDobavljac, cRacunPlatioca, cRacunPrimaoca,
            cSifraPlacanja,
            cModelOdobrenje, cPozivNaBrojOdobrenje, cModelZaduzenje, cPozivNaBrojZaduzenje,
            cSvrhaPlacanja, cIznos);

    cIznos.setCellFactory(
        new Callback<TableColumn<Izvestaji, Double>, TableCell<Izvestaji, Double>>() {
          @Override
          public TableCell<Izvestaji, Double> call(TableColumn<Izvestaji, Double> param) {
            return new TableCell<Izvestaji, Double>() {
              @Override
              protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null | empty) {
                  setText(null);
                } else {
                  setText(df.format(item));
                }
              }
            };
          }
        });

    tblIzvestaj.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    dtpDatum.setValue(LocalDate.now());


  }


  public void osveziTablu(ActionEvent actionEvent) {
    ArrayList<Izvestaji> izvestajiArrayList = new ArrayList<>();
    if (dtpDatum.getValue() == null) {
      return;
    }

    JSONObject object = new JSONObject();
    object.put("action", "getIzvestaje");
    object.put("datum", dtpDatum.getValue().toString());

    object = client.send(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      return;
    }

    for (int i = 0; i < object.length(); i++) {
      JSONObject zad = object.getJSONObject(String.valueOf(i));
      Izvestaji izvestaji = new Izvestaji();
      izvestaji.setId(zad.getInt("id"));
      izvestaji.setPlatioc(zad.getString("Platioc"));
      izvestaji.setPrimaoc(zad.getString("Primaoc"));
      izvestaji.setRacunPlatioca(zad.getString("racunPlatioca"));
      izvestaji.setRacunPrimaoca(zad.getString("racunPrimaoca"));
      izvestaji.setDate(zad.getString("date"));
      izvestaji.setIznos(zad.getDouble("iznos"));
      izvestaji.setVirman(zad.getString("virman"));
      izvestaji.setSifraPlacanja(zad.getString("sifraPlacanja"));
      izvestaji.setModelZaduzenje(zad.getString("modelZaduzenje"));
      izvestaji.setModelOdobrenje(zad.getString("modelOdobrenje"));
      izvestaji.setPozivNaBrojOdobrenje(zad.getString("pozivNaBrojOdobrenje"));
      izvestaji.setPozivNaBrojZaduzenje(zad.getString("pozivNaBrojZaduzenje"));
      izvestaji.setSvrhaPlacanja(zad.getString("svrhaPlacanja"));
      izvestajiArrayList.add(izvestaji);
    }

    ObservableList list = FXCollections.observableArrayList(izvestajiArrayList);

    tblIzvestaj.setItems(list);

  }

  public void izbrisiUplatu(ActionEvent actionEvent) {
  }

  public void setClient(Client client) {
    this.client = client;
  }
}
