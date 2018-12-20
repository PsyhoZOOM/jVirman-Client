package com.psyhozoom.virman.Client.Controllers;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.json.JSONObject;

public class IzvestajiController implements Initializable {

  public JFXDatePicker dtpDatum;
  public Button bRefresh;
  public MenuItem cmIzbrisi;
  public JFXTreeTableView<Izvestaji> tblIzvestaj;
  private JFXTreeTableColumn<Izvestaji, String> cDatum;
  private JFXTreeTableColumn<Izvestaji, String> cVirman;
  private JFXTreeTableColumn<Izvestaji, String> cKlijent;
  private JFXTreeTableColumn<Izvestaji, String> cDobavljac;
  private JFXTreeTableColumn<Izvestaji, String> cRacunPlatioca;
  private JFXTreeTableColumn<Izvestaji, String> cRacunPrimaoca;
  private JFXTreeTableColumn<Izvestaji, String> cSifraPlacanja;
  private JFXTreeTableColumn<Izvestaji, String> cModelZaduzenje;
  private JFXTreeTableColumn<Izvestaji, String> cModelOdobrenje;
  private JFXTreeTableColumn<Izvestaji, String> cPozivNaBrojZaduzenje;
  private JFXTreeTableColumn<Izvestaji, String> cPozivNaBrojOdobrenje;
  private JFXTreeTableColumn<Izvestaji, String> cSvrhaPlacanja;
  private JFXTreeTableColumn<Izvestaji, Double> cIznos;


  private DecimalFormat df = new DecimalFormat("###,###,###.00");
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

    cDatum = new JFXTreeTableColumn<>("DATUM");
    cVirman = new JFXTreeTableColumn<>("NALOG");
    cKlijent = new JFXTreeTableColumn<>("PLATIOC");
    cDobavljac = new JFXTreeTableColumn<>("PRIMAOC");
    cRacunPlatioca = new JFXTreeTableColumn<>("RAČUN PLATIOCA");
    cRacunPrimaoca = new JFXTreeTableColumn<>("RAČUN PRIMAOCA");
    cSifraPlacanja = new JFXTreeTableColumn<>("ŠIFRA PLAĆANJA");
    cModelZaduzenje = new JFXTreeTableColumn<>("MODEL ZADUŽENJA");
    cPozivNaBrojZaduzenje = new JFXTreeTableColumn<>("POZIV NA BROJ ZADUŽENJE");
    cModelOdobrenje = new JFXTreeTableColumn<>("MODEL ODOBRENJA");
    cPozivNaBrojOdobrenje = new JFXTreeTableColumn<>("POZIV NA BROJ ODOBRENJE");
    cSvrhaPlacanja = new JFXTreeTableColumn<>("SVRHA PLAĆANJA");
    cIznos = new JFXTreeTableColumn<>("IZNOS");

    cDatum.setCellValueFactory(new TreeItemPropertyValueFactory<>("date"));
    cVirman.setCellValueFactory(new TreeItemPropertyValueFactory<>("virman"));
    cKlijent.setCellValueFactory(new TreeItemPropertyValueFactory<>("platioc"));
    cDobavljac.setCellValueFactory(new TreeItemPropertyValueFactory<>("primaoc"));
    cRacunPlatioca.setCellValueFactory(new TreeItemPropertyValueFactory<>("racunPlatioca"));
    cRacunPrimaoca.setCellValueFactory(new TreeItemPropertyValueFactory<>("racunPrimaoca"));
    cSifraPlacanja.setCellValueFactory(new TreeItemPropertyValueFactory<>("sifraPlacanja"));
    cModelZaduzenje.setCellValueFactory(new TreeItemPropertyValueFactory<>("modelZaduzenje"));
    cPozivNaBrojZaduzenje
        .setCellValueFactory(new TreeItemPropertyValueFactory<>("pozivNaBrojZaduzenje"));
    cModelOdobrenje.setCellValueFactory(new TreeItemPropertyValueFactory<>("modelOdobrenje"));
    cPozivNaBrojOdobrenje
        .setCellValueFactory(new TreeItemPropertyValueFactory<>("pozivNaBrojOdobrenje"));
    cSvrhaPlacanja.setCellValueFactory(new TreeItemPropertyValueFactory<>("svrhaPlacanja"));
    cIznos.setCellValueFactory(new TreeItemPropertyValueFactory<>("iznos"));

    tblIzvestaj.getColumns()
        .addAll(cDatum, cVirman, cKlijent, cDobavljac, cRacunPlatioca, cRacunPrimaoca,
            cSifraPlacanja,
            cModelOdobrenje, cPozivNaBrojOdobrenje, cModelZaduzenje, cPozivNaBrojZaduzenje,
            cSvrhaPlacanja, cIznos);

    cIznos.setCellFactory(
        new Callback<TreeTableColumn<Izvestaji, Double>, TreeTableCell<Izvestaji, Double>>() {
          @Override
          public TreeTableCell<Izvestaji, Double> call(TreeTableColumn<Izvestaji, Double> param) {
            return new TreeTableCell<Izvestaji, Double>() {
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

    tblIzvestaj.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);


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

    ObservableList list = FXCollections.observableList(izvestajiArrayList);
    TreeItem<Izvestaji> root = new RecursiveTreeItem<Izvestaji>(list,
        RecursiveTreeObject::getChildren);
    tblIzvestaj.setRoot(root);
    tblIzvestaj.setShowRoot(false);


  }

  public void izbrisiUplatu(ActionEvent actionEvent) {
  }

  public void setClient(Client client) {
    this.client = client;
  }
}
