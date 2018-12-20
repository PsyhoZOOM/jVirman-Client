package com.psyhozoom.virman.Client.Classes;

public class Settings {

  String remoteHost;
  int remotePort;
  String username;
  String pass;


  public String getPass() {
    return pass;
  }

  public void setPass(String pass) {
    this.pass = pass;
  }

  public String getRemoteHost() {
    return remoteHost;
  }

  public void setRemoteHost(String remoteHost) {
    this.remoteHost = remoteHost;
  }

  public int getRemotePort() {
    return remotePort;
  }

  public void setRemotePort(int remotePort) {
    this.remotePort = remotePort;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
