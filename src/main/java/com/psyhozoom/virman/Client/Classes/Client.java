package com.psyhozoom.virman.Client.Classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import org.json.JSONObject;

public class Client {

  Socket socket;
  String rAddress;
  int rPort;
  boolean isConnected = false;
  InputStreamReader isr;
  OutputStreamWriter osw;
  BufferedReader bfr;
  BufferedWriter bfw;


  public Client(String rAddress, int rPort) {
    this.rAddress = rAddress;
    this.rPort = rPort;
    try {
      this.socket = new Socket(rAddress, rPort);
    } catch (IOException e) {
      AlertUser.error("GRESKA", e.getMessage());
      e.printStackTrace();

    }
  }


  public boolean isConnected() {
    if (socket == null) {
      return false;
    }
    isConnected = socket.isConnected();
    return isConnected;
  }

  public JSONObject send(JSONObject object) {
    try {
      if (osw == null || bfw == null) {
        osw = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
        bfw = new BufferedWriter(osw);
      }
      bfw.write(object.toString());
      bfw.newLine();
      bfw.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return read();
  }

  private JSONObject read() {
    JSONObject object;
    try {
      if (isr == null || bfr == null) {
        isr = new InputStreamReader(socket.getInputStream(), "UTF-8");
        bfr = new BufferedReader(isr);
      }
      object = new JSONObject(bfr.readLine());
      System.out.println(object.toString());
      return object;
    } catch (IOException e) {
      e.printStackTrace();
    }

    return new JSONObject();
  }

}
