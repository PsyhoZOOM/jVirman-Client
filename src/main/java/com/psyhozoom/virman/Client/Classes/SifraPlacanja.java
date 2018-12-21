package com.psyhozoom.virman.Client.Classes;

import java.util.ArrayList;
import org.json.JSONObject;

public class SifraPlacanja {

  private int id;
  private String broj;
  private String opis;
  private String duziOpis;

  private ArrayList<SifraPlacanja> sifraPlacanjaArrayList;

  public ArrayList<SifraPlacanja> getAllSifre(Client client) {
    JSONObject object = new JSONObject();
    object.put("action", "getAllSifre");
    object = client.send(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      return new ArrayList<SifraPlacanja>();
    }

    sifraPlacanjaArrayList = new ArrayList<>();
    for (int i = 0; i < object.length(); i++) {
      SifraPlacanja sifraPlacanja = new SifraPlacanja();
      JSONObject ob = object.getJSONObject(String.valueOf(i));
      sifraPlacanja.setId(ob.getInt("id"));
      sifraPlacanja.setOpis(ob.getString("opis"));
      sifraPlacanja.setDuziOpis(ob.getString("duziOpis"));
      sifraPlacanja.setBroj(ob.getString("broj"));
      sifraPlacanjaArrayList.add(sifraPlacanja);
    }

    return sifraPlacanjaArrayList;


  }


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getBroj() {
    return broj;
  }

  public void setBroj(String broj) {
    this.broj = broj;
  }

  public String getOpis() {
    return opis;
  }

  public void setOpis(String opis) {
    this.opis = opis;
  }

  public String getDuziOpis() {
    return duziOpis;
  }

  public void setDuziOpis(String duziOpis) {
    this.duziOpis = duziOpis;
  }

  public ArrayList<SifraPlacanja> getSifraPlacanjaArrayList() {
    return sifraPlacanjaArrayList;
  }

  public void setSifraPlacanjaArrayList(
      ArrayList<SifraPlacanja> sifraPlacanjaArrayList) {
    this.sifraPlacanjaArrayList = sifraPlacanjaArrayList;
  }
}
