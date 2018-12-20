package com.psyhozoom.virman.Client.Classes;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class Izvestaji extends RecursiveTreeObject<Izvestaji> {

  int id;
  String platioc;
  String primaoc;
  String racunPlatioca;
  String racunPrimaoca;
  String date;
  double iznos;
  String virman;
  String sifraPlacanja;
  String modelZaduzenje;
  String modelOdobrenje;
  String pozivNaBrojZaduzenje;
  String pozivNaBrojOdobrenje;
  String svrhaPlacanja;
  String mestoPlatioca;
  String mestoPrimaoca;

  public String getMestoPlatioca() {
    return mestoPlatioca;
  }

  public void setMestoPlatioca(String mestoPlatioca) {
    this.mestoPlatioca = mestoPlatioca;
  }

  public String getMestoPrimaoca() {
    return mestoPrimaoca;
  }

  public void setMestoPrimaoca(String mestoPrimaoca) {
    this.mestoPrimaoca = mestoPrimaoca;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getPlatioc() {
    return platioc;
  }

  public void setPlatioc(String platioc) {
    this.platioc = platioc;
  }

  public String getPrimaoc() {
    return primaoc;
  }

  public void setPrimaoc(String primaoc) {
    this.primaoc = primaoc;
  }

  public String getRacunPlatioca() {
    return racunPlatioca;
  }

  public void setRacunPlatioca(String racunPlatioca) {
    this.racunPlatioca = racunPlatioca;
  }

  public String getRacunPrimaoca() {
    return racunPrimaoca;
  }

  public void setRacunPrimaoca(String racunPrimaoca) {
    this.racunPrimaoca = racunPrimaoca;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public double getIznos() {
    return iznos;
  }

  public void setIznos(double iznos) {
    this.iznos = iznos;
  }

  public String getVirman() {
    return virman;
  }

  public void setVirman(String virman) {
    this.virman = virman;
  }

  public String getSifraPlacanja() {
    return sifraPlacanja;
  }

  public void setSifraPlacanja(String sifraPlacanja) {
    this.sifraPlacanja = sifraPlacanja;
  }

  public String getModelZaduzenje() {
    return modelZaduzenje;
  }

  public void setModelZaduzenje(String modelZaduzenje) {
    this.modelZaduzenje = modelZaduzenje;
  }

  public String getModelOdobrenje() {
    return modelOdobrenje;
  }

  public void setModelOdobrenje(String modelOdobrenje) {
    this.modelOdobrenje = modelOdobrenje;
  }

  public String getPozivNaBrojZaduzenje() {
    return pozivNaBrojZaduzenje;
  }

  public void setPozivNaBrojZaduzenje(String pozivNaBrojZaduzenje) {
    this.pozivNaBrojZaduzenje = pozivNaBrojZaduzenje;
  }

  public String getPozivNaBrojOdobrenje() {
    return pozivNaBrojOdobrenje;
  }

  public void setPozivNaBrojOdobrenje(String pozivNaBrojOdobrenje) {
    this.pozivNaBrojOdobrenje = pozivNaBrojOdobrenje;
  }

  public String getSvrhaPlacanja() {
    return svrhaPlacanja;
  }

  public void setSvrhaPlacanja(String svrhaPlacanja) {
    this.svrhaPlacanja = svrhaPlacanja;
  }
}
