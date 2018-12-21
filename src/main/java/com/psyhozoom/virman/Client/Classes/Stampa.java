package com.psyhozoom.virman.Client.Classes;

import javafx.print.PageLayout;
import javafx.print.PrinterJob;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Window;
import org.json.JSONObject;

public class Stampa {

  private PrinterJob printerJob;
  Font font = Font.loadFont(getClass().getResource("/font/saxmono.ttf").toExternalForm(), 10);


  private void stampaj(AnchorPane anchorPane, Window wind) {
    System.out.println(font.getSize());
    printerJob = PrinterJob.createPrinterJob();
    if (printerJob != null && printerJob.showPrintDialog(wind)) {
      PageLayout pageLayout = printerJob.getJobSettings().getPageLayout();
      anchorPane.setMaxWidth(pageLayout.getPrintableWidth());
      anchorPane.setMaxHeight(pageLayout.getPrintableHeight());
      if (printerJob.printPage(pageLayout, anchorPane)) {
        printerJob.endJob();
      } else {
        System.out.println("FAILED TO PRINT");
      }
    } else {
      System.out.println("CANCELED PRINTING");
    }
  }

  public void stampaUplate(Izvestaji izvestaji, Window wind) {
    AnchorPane anchorPane = new AnchorPane();
    Text platilac = new Text();
    platilac.setFont(font);
    platilac.setText(String.format("%s\n%s", izvestaji.getPlatioc(), izvestaji.getMestoPlatioca()));

    Text svrhaUplate = new Text();
    svrhaUplate.setFont(font);
    svrhaUplate.setWrappingWidth(200);
    svrhaUplate.setText(String.format("%s", izvestaji.getSvrhaPlacanja()));

    Text primalac = new Text();
    primalac.setFont(font);
    primalac.setText(String.format("%s\n%s", izvestaji.getPrimaoc(), izvestaji.getMestoPrimaoca()));

    anchorPane.getChildren().addAll(platilac, svrhaUplate, primalac);

    AnchorPane.setTopAnchor(platilac, 16.0);
    AnchorPane.setLeftAnchor(platilac, 10.0);

    AnchorPane.setTopAnchor(svrhaUplate, 50.0);
    AnchorPane.setLeftAnchor(svrhaUplate, 10.0);

    AnchorPane.setTopAnchor(primalac, 90.5);
    AnchorPane.setLeftAnchor(primalac, 10.0);
    stampaj(anchorPane, wind);

  }

  public void stampaIsplate(JSONObject object) {
  }

  public void stampaPrenost(JSONObject object) {
  }

}
