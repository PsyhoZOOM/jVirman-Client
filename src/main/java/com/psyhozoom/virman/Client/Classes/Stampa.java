package com.psyhozoom.virman.Client.Classes;

import org.json.JSONObject;

public class Stampa {

  public void stampaUplate(JSONObject object) {
    System.out.println(String.format("STAMAPA UPLATE: %s", object.toString()));
  }

  public void stampaIsplate(JSONObject object) {
    System.out.println(String.format("STAMPA ISPLATE: %s", object.toString()));
  }

  public void stampaPrenost(JSONObject object) {
    System.out.println(String.format("STAMPA PRENOSTA: %s", object.toString()));
  }

}
