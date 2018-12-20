package com.psyhozoom.virman.Client.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.psyhozoom.virman.Client.Classes.AlertUser;
import com.psyhozoom.virman.Client.Classes.Client;
import com.psyhozoom.virman.Client.Classes.Clients;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;
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
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.json.JSONObject;

public class MainWindowController implements Initializable {

  public JFXTextField tSearch;
  public JFXButton bRefresh;
  public JFXTreeTableView<Clients> tblClients;
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
  JFXTreeTableColumn<Clients, String> cNazivFirme;
  JFXTreeTableColumn<Clients, String> cImeVlasnika;
  JFXTreeTableColumn<Clients, String> cMesto;
  JFXTreeTableColumn<Clients, String> cTel1;
  JFXTreeTableColumn<Clients, String> cTel2;
  private URL location;
  private ResourceBundle resources;
  private Client client;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

    cNazivFirme = new JFXTreeTableColumn<>("NAZIV");
    cImeVlasnika = new JFXTreeTableColumn<>("IME VLASNIKA");
    cMesto = new JFXTreeTableColumn<>("MESTO");
    cTel1 = new JFXTreeTableColumn<>("TEL1");
    cTel2 = new JFXTreeTableColumn<>("TEL2");

    cNazivFirme.setCellValueFactory(new TreeItemPropertyValueFactory<Clients, String>("naziv"));
    cImeVlasnika
        .setCellValueFactory(new TreeItemPropertyValueFactory<Clients, String>("imeVlasnika"));
    cMesto.setCellValueFactory(new TreeItemPropertyValueFactory<Clients, String>("mesto"));
    cTel1.setCellValueFactory(new TreeItemPropertyValueFactory<Clients, String>("tel1"));
    cTel2.setCellValueFactory(new TreeItemPropertyValueFactory<Clients, String>("tel2"));

    cNazivFirme.setCellFactory(
        new Callback<TreeTableColumn<Clients, String>, TreeTableCell<Clients, String>>() {
          @Override
          public TreeTableCell<Clients, String> call(TreeTableColumn<Clients, String> param) {
            TreeTableCell<Clients, String> cell = new TreeTableCell<Clients, String>() {
              @Override
              protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                  setText("");
                } else {
                  setText(item);
                }
              }
            };
            cell.setStyle("-fx-alignment: CENTER;");
            return cell;
          }
        });

    tSearch.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {

        tblClients.setPredicate(new Predicate<TreeItem<Clients>>() {
          @Override
          public boolean test(TreeItem<Clients> treeItem) {
            return treeItem.getValue().getImeVlasnika().toString().toLowerCase().contains(newValue)
                ||
                treeItem.getValue().getNaziv().toString().toLowerCase().contains(newValue) ||
                treeItem.getValue().getTel1().toString().toLowerCase().contains(newValue) ||
                treeItem.getValue().getTel2().toString().toLowerCase().contains(newValue) ||
                treeItem.getValue().getImeVlasnika().toString().toLowerCase().contains(newValue);
          }
        });
      }

    });

    tSearch.setOnKeyPressed((event -> {
      if (event.getCode() == KeyCode.ENTER) {
        refreshTable(null);
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
    tblClients.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);

    tblClients.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (event.getClickCount() == 2) {
          editClient(tblClients.getSelectionModel().getSelectedItem());
        }
      }
    });
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
        new ChangeListener<TreeItem<Clients>>() {
          @Override
          public void changed(ObservableValue<? extends TreeItem<Clients>> observable,
              TreeItem<Clients> oldValue, TreeItem<Clients> newValue) {
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

  private void deleteClient() {
    if (tblClients.getSelectionModel().getSelectedIndex() == -1) {
      return;
    }
    boolean brisanje_klijenta = AlertUser
        .yesNo("BRISANJE KLIJENTA", String.format("DA LI STE SIGURNI DA ŽELITE DA IZBRIŠITE "
                + "KLIJENTA \"%s\"?",
            tblClients.getSelectionModel().getSelectedItem().getValue().getNaziv()));
    if (!brisanje_klijenta) {
      return;
    }

    int clientID = tblClients.getSelectionModel().getSelectedItem().getValue().getId();
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

  private void editClient(TreeItem<Clients> newValue) {
    FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource("EditClient.fxml"));
    Parent root = null;
    try {
      root = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    EditClientController editClientController = loader.getController();
    editClientController.setClient(this.client);
    editClientController.setEditClient(newValue.getValue());
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

  }

  private void setTableData(ArrayList<Clients> cliensArrayList) {
    ObservableList list = FXCollections.observableList(cliensArrayList);
    TreeItem<Clients> trClients = new RecursiveTreeItem<Clients>(list,
        RecursiveTreeObject::getChildren);
    tblClients.setShowRoot(false);
    tblClients.setRoot(trClients);

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
    uplataController.setClientID(tblClients.getSelectionModel().getSelectedItem().getValue());
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
    isplataController.setClientID(tblClients.getSelectionModel().getSelectedItem().getValue());
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
    prenosController.setClientID(tblClients.getSelectionModel().getSelectedItem().getValue());
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
