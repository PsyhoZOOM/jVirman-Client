package com.psyhozoom.virman.Client.Classes;

import java.util.ArrayList;
import org.json.JSONObject;

public class Racuni {

  int id;
  int clientID;
  String brojRacuna;
  ArrayList<Racuni> racuniArrayList;
  Client client;
  boolean mainRacun = false;


  public void setData(int clientID) {
    JSONObject object = new JSONObject();
    object.put("action", "getRacuniClient");
    object.put("clientID", clientID);
    object = client.send(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      return;
    }

    racuniArrayList = new ArrayList<>();
    for (int i = 0; i < object.length(); i++) {
      JSONObject cli = object.getJSONObject(String.valueOf(i));
      Racuni racun = new Racuni();
      racun.setId(cli.getInt("id"));
      racun.setClientID(clientID);
      racun.setBrojRacuna(cli.getString("racun"));
      racun.setMainRacun(cli.getBoolean("mainRacun"));
      racuniArrayList.add(racun);
    }
  }

  public boolean isMainRacun() {
    return mainRacun;
  }

  public void setMainRacun(boolean mainRacun) {
    this.mainRacun = mainRacun;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getClientID() {
    return clientID;
  }

  public void setClientID(int clientID) {
    this.clientID = clientID;
  }

  public String getBrojRacuna() {
    return brojRacuna;
  }

  public void setBrojRacuna(String brojRacuna) {
    this.brojRacuna = brojRacuna;
  }

  public ArrayList<Racuni> getRacuniArrayList() {
    return racuniArrayList;
  }

  public void setRacuniArrayList(
      ArrayList<Racuni> racuniArrayList) {
    this.racuniArrayList = racuniArrayList;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }
}
