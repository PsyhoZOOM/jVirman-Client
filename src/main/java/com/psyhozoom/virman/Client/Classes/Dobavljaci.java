package com.psyhozoom.virman.Client.Classes;

import java.util.ArrayList;
import org.json.JSONObject;

public class Dobavljaci {

  int id;
  String naziv;
  String imeVlasnika;
  String mesto;
  String tel1;
  String tel2;
  String komentar;
  ArrayList<Dobavljaci> dobavljaciArrayList;

  public ArrayList<Dobavljaci> getDobavljaciArray(Client client, int clientID) {
    dobavljaciArrayList = new ArrayList<>();
    JSONObject object = new JSONObject();
    object.put("action", "getDobavljaciOfClient");
    object.put("clientID", clientID);
    object = client.send(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      return null;
    }

    for (int i = 0; i < object.length(); i++) {
      JSONObject dob = object.getJSONObject(String.valueOf(i));
      Dobavljaci dobavljaci = new Dobavljaci();
      dobavljaci.setId(dob.getInt("id"));
      dobavljaci.setNaziv(dob.getString("naziv"));
      dobavljaci.setImeVlasnika(dob.getString("imeVlasnika"));
      dobavljaci.setMesto(dob.getString("mesto"));
      dobavljaci.setTel1(dob.getString("tel1"));
      dobavljaci.setTel2(dob.getString("tel2"));
      dobavljaci.setKomentar(dob.getString("komentar"));
      dobavljaciArrayList.add(dobavljaci);
    }
    return dobavljaciArrayList;

  }


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNaziv() {
    return naziv;
  }

  public void setNaziv(String naziv) {
    this.naziv = naziv;
  }

  public String getImeVlasnika() {
    return imeVlasnika;
  }

  public void setImeVlasnika(String imeVlasnika) {
    this.imeVlasnika = imeVlasnika;
  }

  public String getMesto() {
    return mesto;
  }

  public void setMesto(String mesto) {
    this.mesto = mesto;
  }

  public String getTel1() {
    return tel1;
  }

  public void setTel1(String tel1) {
    this.tel1 = tel1;
  }

  public String getTel2() {
    return tel2;
  }

  public void setTel2(String tel2) {
    this.tel2 = tel2;
  }

  public String getKomentar() {
    return komentar;
  }

  public void setKomentar(String komentar) {
    this.komentar = komentar;
  }

  public ArrayList<Dobavljaci> getDobavljaciArrayList() {
    return dobavljaciArrayList;
  }

  public void setDobavljaciArrayList(
      ArrayList<Dobavljaci> dobavljaciArrayList) {
    this.dobavljaciArrayList = dobavljaciArrayList;
  }
}
