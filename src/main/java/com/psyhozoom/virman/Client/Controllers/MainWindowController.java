package com.psyhozoom.virman.Client.Controllers;

import com.psyhozoom.virman.Client.Classes.AlertUser;
import com.psyhozoom.virman.Client.Classes.Client;
import com.psyhozoom.virman.Client.Classes.Clients;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.json.JSONObject;

public class MainWindowController implements Initializable {

  public TextField tSearch;
  public TableView<Clients> tblClients;
  public MenuBar menuBar;
  public Menu mClientOptions;
  public ContextMenu contextMeny;
  public MenuItem mClientNov;
  public MenuItem mClientIzmeni;
  public MenuItem mClientIzbrisi;
  public MenuItem mExit;
  public MenuItem mSifrePlacanja;
  public Button bUplata;
  public Button bISPLATA;
  public Button bPRENOS;
  //table columns
  TableColumn<Clients, String> cNazivFirme;
  TableColumn<Clients, String> cImeVlasnika;
  TableColumn<Clients, String> cMesto;
  TableColumn<Clients, String> cTel1;
  TableColumn<Clients, String> cTel2;
  private URL location;
  private ResourceBundle resources;
  private Client client;
  private ArrayList<Clients> clientsArrayList;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

    cNazivFirme = new TableColumn("NAZIV");
    cImeVlasnika = new TableColumn("IME VLASNIKA");
    cMesto = new TableColumn<>("MESTO");
    cTel1 = new TableColumn<>("TEL1");
    cTel2 = new TableColumn<>("TEL2");

    cNazivFirme.setCellValueFactory(new PropertyValueFactory<Clients, String>("naziv"));
    cImeVlasnika
        .setCellValueFactory(new PropertyValueFactory<Clients, String>("imeVlasnika"));
    cMesto.setCellValueFactory(new PropertyValueFactory<Clients, String>("mesto"));
    cTel1.setCellValueFactory(new PropertyValueFactory<Clients, String>("tel1"));
    cTel2.setCellValueFactory(new PropertyValueFactory<Clients, String>("tel2"));

    cNazivFirme.setCellFactory(
        new Callback<TableColumn<Clients, String>, TableCell<Clients, String>>() {
          @Override
          public TableCell<Clients, String> call(TableColumn<Clients, String> param) {
            return new TableCell<Clients, String>() {
              @Override
              protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                  setText(null);
                } else {
                  setText(item);
                }
              }
            };
          }
        });


    tSearch.setOnKeyPressed((event -> {
      if (event.getCode() == KeyCode.ENTER) {
        refreshTableSearch(tSearch.getText().trim().toLowerCase());
      }
    }));

    bUplata.setOnKeyPressed((event -> {
      if (event.getCode() == KeyCode.ENTER) {
        showUplata(null);
      }
    }));

    bISPLATA.setOnKeyPressed((event -> {
      if (event.getCode() == KeyCode.ENTER) {
        showIsplata(null);
      }
    }));

    bPRENOS.setOnKeyPressed((event -> {
      if (event.getCode() == KeyCode.ENTER) {
        showPrenos(null);
      }
    }));

    tblClients.getColumns().addAll(cNazivFirme, cImeVlasnika, cMesto, cTel1, cTel2);
    tblClients.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    tblClients.setOnKeyReleased(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
          editClient(tblClients.getSelectionModel().getSelectedItem());
        }
      }
    });

    mClientNov.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        newClient();
      }
    });

    mClientIzbrisi.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        deleteClient();
      }
    });
    mSifrePlacanja.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        showSifrePlacanja();
      }
    });

    tblClients.getSelectionModel().selectedItemProperty().addListener(
        new ChangeListener<Clients>() {
          @Override
          public void changed(ObservableValue<? extends Clients> observable, Clients oldValue,
              Clients newValue) {
            if (newValue != null) {
              bISPLATA.setDisable(false);
              bUplata.setDisable(false);
              bPRENOS.setDisable(false);
            } else {
              bISPLATA.setDisable(true);
              bUplata.setDisable(true);
              bPRENOS.setDisable(true);
            }
          }
        });


  }

  private void refreshTableSearch(String s) {
    ArrayList<Clients> clients = new ArrayList<>();
    for (Clients clsS : clientsArrayList) {
      if (
          clsS.getNaziv().toLowerCase().trim().contains(s.toLowerCase().trim()) ||
              clsS.getMesto().toLowerCase().trim().contains(s.toLowerCase().trim()) ||
              clsS.getImeVlasnika().toLowerCase().trim().contains(s.toLowerCase().trim())
          ) {
        clients.add(clsS);
      }
    }

    tblClients.setItems(FXCollections.observableArrayList(clients));

  }

  private void deleteClient() {

    if (tblClients.getSelectionModel().getSelectedIndex() == -1) {
      return;
    }
    boolean brisanje_klijenta = AlertUser
        .yesNo("BRISANJE KLIJENTA", String.format("DA LI STE SIGURNI DA ŽELITE DA IZBRIŠITE "
                + "KLIJENTA \"%s\"?",
            tblClients.getSelectionModel().getSelectedItem().getNaziv()));
    if (!brisanje_klijenta) {
      return;
    }

    int clientID = tblClients.getSelectionModel().getSelectedItem().getId();
    JSONObject object = new JSONObject();
    object.put("action", "deleteClient");
    object.put("clientID", clientID);
    object = client.send(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      return;
    }
    refreshTable(null);
  }

  private void showSifrePlacanja() {
    FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource("SifrePlacanja.fxml"));
    Parent root = null;

    try {
      root = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (root == null) {
      return;
    }

    SifrePlacanjaController sifrePlacanjaController = loader.getController();
    sifrePlacanjaController.setClient(this.client);
    sifrePlacanjaController.initData();
    Stage stage = new Stage();
    stage.setTitle("ŠIFRE PLAĆANJA");
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setScene(new Scene(root));
    stage.showAndWait();

  }

  private void editClient(Clients newValue) {
    FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource("EditClient.fxml"));
    Parent root = null;
    try {
      root = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    EditClientController editClientController = loader.getController();
    editClientController.setClient(this.client);
    editClientController.setEditClient(newValue);
    editClientController.initData();
    Stage stage = new Stage();
    stage.setTitle("IZMENA KLIJENTA");
    if (root == null) {
      return;
    }

    stage.setScene(new Scene(root));
    stage.initModality(Modality.APPLICATION_MODAL);

    stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
      if (event.getCode() == KeyCode.ESCAPE) {
        stage.close();
      }
    });
    stage.showAndWait();

  }


  private void newClient() {
    FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemResource("NewClient.fxml"));
    Parent root = null;
    try {
      root = fxmlLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    NewClientController newClientController = fxmlLoader.getController();
    newClientController.setClient(this.client);
    Stage stage = new Stage();
    stage.setTitle("NOVI KLIJENT");
    if (root == null) {
      return;
    }

    stage.setScene(new Scene(root));
    stage.initModality(Modality.APPLICATION_MODAL);

    stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
      if (event.getCode() == KeyCode.ESCAPE) {
        stage.close();
      }
    });

    stage.showAndWait();
    refreshTable(null);

  }


  public void refreshTable(ActionEvent actionEvent) {
    JSONObject object = new JSONObject();
    object.put("action", "getAllClients");
    object = client.send(object);
    ArrayList<Clients> cliensArrayList = new ArrayList<>();

    for (int i = 0; i < object.length(); i++) {
      JSONObject cliO = object.getJSONObject(String.valueOf(i));
      Clients clients = new Clients();
      clients.setId(cliO.getInt("id"));
      clients.setNaziv(cliO.getString("naziv"));
      clients.setImeVlasnika(cliO.getString("imeVlasnika"));
      clients.setTel1(cliO.getString("tel1"));
      clients.setTel2(cliO.getString("tel2"));
      clients.setMesto(cliO.getString("mesto"));
      cliensArrayList.add(clients);
    }

    if (cliensArrayList.size() > 0) {
      setTableData(cliensArrayList);
    }
    this.clientsArrayList = cliensArrayList;

  }

  private void setTableData(ArrayList<Clients> cliensArrayList) {
    ObservableList list = FXCollections.observableArrayList(cliensArrayList);
    tblClients.setItems(list);

  }

  public void setClient(Client client) {
    this.client = client;
  }

  public void exitApp(ActionEvent actionEvent) {
    Platform.exit();
    System.exit(0);
  }

  public void showUplata(ActionEvent actionEvent) {
    Parent root = null;
    FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource("Uplata.fxml"));
    try {
      root = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (root == null) {
      return;
    }
    UplataController uplataController = loader.getController();
    uplataController.setClient(this.client);
    uplataController.setClientID(tblClients.getSelectionModel().getSelectedItem());
    uplataController.initData();
    Stage stage = new Stage();
    stage.setTitle("UPLATA");
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setScene(new Scene(root));
    stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
      if (event.getCode() == KeyCode.ESCAPE) {
        stage.close();
      }
    });
    stage.showAndWait();
  }

  public void showIsplata(ActionEvent actionEvent) {
    Parent root = null;
    FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource("Isplata.fxml"));
    try {
      root = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (root == null) {
      return;
    }

    IsplataController isplataController = loader.getController();
    isplataController.setClient(this.client);
    isplataController.setClientID(tblClients.getSelectionModel().getSelectedItem());
    isplataController.initData();

    Stage stage = new Stage();
    stage.setTitle("ISPLATA");
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setScene(new Scene(root));
    stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
      if (event.getCode() == KeyCode.ESCAPE) {
        stage.close();
      }
    });
    stage.showAndWait();

  }

  public void showPrenos(ActionEvent actionEvent) {
    Parent root = null;
    FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource("Prenos.fxml"));
    try {
      root = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (root == null) {
      return;
    }

    PrenosController prenosController = loader.getController();
    prenosController.setClientID(tblClients.getSelectionModel().getSelectedItem());
    prenosController.setClient(this.client);
    prenosController.initData();

    Stage stage = new Stage();
    stage.setTitle("PRENOS");
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setScene(new Scene(root));
    stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
      if (event.getCode() == KeyCode.ESCAPE) {
        stage.close();
      }
    });
    stage.showAndWait();

  }

  public void prikaziIzvestaj(ActionEvent actionEvent) {
    Parent root = null;
    FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource("Izvestaji.fxml"));
    try {
      root = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (root == null) {
      return;
    }

    IzvestajiController izvestajiController = loader.getController();
    izvestajiController.setClient(this.client);

    Stage stage = new Stage();
    stage.setScene(new Scene(root));
    stage.setTitle("IZVEŠTAJI");
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.show();
  }
}
