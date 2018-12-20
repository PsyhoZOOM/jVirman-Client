package com.psyhozoom.virman.Client.Classes;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class Clients extends RecursiveTreeObject<Clients> {

  int id;
  String naziv;
  String imeVlasnika;
  String tel1;
  String tel2;
  String mesto;
  String[] zRacun;

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

  public String getMesto() {
    return mesto;
  }

  public void setMesto(String mesto) {
    this.mesto = mesto;
  }

  public String[] getzRacun() {
    return zRacun;
  }

  public void setzRacun(String[] zRacun) {
    this.zRacun = zRacun;
  }
}
