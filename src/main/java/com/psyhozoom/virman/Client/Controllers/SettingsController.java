package com.psyhozoom.virman.Client.Controllers;

import com.jfoenix.controls.JFXTextField;
import com.psyhozoom.virman.Client.Classes.Database;
import com.psyhozoom.virman.Client.Classes.Settings;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public class SettingsController implements Initializable {

  public JFXTextField tPortNumber;
  public JFXTextField tHostaName;
  public JFXTextField tUserName;
  private URL location;
  private ResourceBundle resources;
  private Settings settings;
  private Database database;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;
  }


  public void initData() {
    String query = "SELECT * FROM Settings";
    ResultSet rs;
    database = new Database();
    settings = new Settings();

    database.connect();
    try {
      rs = database.getStatement().executeQuery(query);
      if (rs.isBeforeFirst()) {
        while (rs.next()) {
          settings.setRemoteHost(rs.getString("host"));
          settings.setRemotePort(rs.getInt("port"));
          settings.setUsername(rs.getString("username"));
          if (tHostaName != null) {
            tHostaName.setText(rs.getString("host"));
            tPortNumber.setText(String.valueOf(rs.getInt("port")));
            tUserName.setText(rs.getString("username"));
          }
        }
      }
      database.close();
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }


  }

  public Settings getSettings() {
    return settings;
  }

  public void saveData(ActionEvent actionEvent) {
    settings = new Settings();
    settings.setUsername(tUserName.getText());
    settings.setRemoteHost(tHostaName.getText());
    settings.setRemotePort(Integer.valueOf(tPortNumber.getText()));
    database.connect();

    String query = "DELETE  FROM Settings";

    try {
      database.getStatement().execute(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    query = "INSERT INTO Settings (host, port, username) VALUES (?,?,?)";
    PreparedStatement ps;
    try {
      ps = database.getConn().prepareStatement(query);
      ps.setString(1, settings.getRemoteHost());
      ps.setInt(2, settings.getRemotePort());
      ps.setString(3, settings.getUsername());
      ps.executeUpdate();

      ps.close();
      database.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    Stage st = (Stage) tPortNumber.getScene().getWindow();
    st.close();


  }
}
