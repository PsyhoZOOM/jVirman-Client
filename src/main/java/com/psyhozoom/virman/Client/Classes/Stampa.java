package com.psyhozoom.virman.Client.Classes;

import javafx.print.PageLayout;
import javafx.print.PrinterJob;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Window;
import org.json.JSONObject;

public class Stampa {

  private PrinterJob printerJob;
  private TextFlow area = new TextFlow(new Text("hi\t \t \t this tab \n new line \t tab"));


  public Stampa(Window wind) {
    printerJob = PrinterJob.createPrinterJob();
    if (printerJob != null && printerJob.showPrintDialog(wind)) {
      PageLayout pageLayout = printerJob.getJobSettings().getPageLayout();
      area.setMaxWidth(pageLayout.getPrintableWidth());
      area.setMaxHeight(pageLayout.getPrintableHeight());
    } else {
      System.out.println("CANCELED PRINTING");
    }
  }

  public void stampaUplate(JSONObject object) {
    System.out.println(String.format("STAMAPA UPLATE: %s", object.toString()));
    if (printerJob.printPage(area)) {
      printerJob.endJob();
    } else {
      System.out.println("FAILED TO PRINT");
    }

  }

  public void stampaIsplate(JSONObject object) {
    System.out.println(String.format("STAMPA ISPLATE: %s", object.toString()));
  }

  public void stampaPrenost(JSONObject object) {
    System.out.println(String.format("STAMPA PRENOSTA: %s", object.toString()));
  }

}
