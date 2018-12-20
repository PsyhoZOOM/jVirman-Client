package com.psyhozoom.virman.Client.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class OperateriController implements Initializable {

  private URL location;
  private ResourceBundle resources;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;
  }
}
