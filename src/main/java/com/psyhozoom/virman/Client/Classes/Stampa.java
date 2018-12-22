package com.psyhozoom.virman.Client.Classes;

import java.text.DecimalFormat;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer.MarginType;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.json.JSONObject;

public class Stampa {

  private PrinterJob printerJob;
  private DecimalFormat dtf = new DecimalFormat("###,###,###,##0.00");
  // Font font = Font.loadFont(getClass().getResource("font/roboto/saxmono.ttf").toExternalForm(), 10);


  private void stampaj(AnchorPane anchorPane, Window wind) {
    printerJob = PrinterJob.createPrinterJob();
    if (printerJob != null && printerJob.showPrintDialog(wind)) {
      PageLayout pageLayout = printerJob.getPrinter()
          .createPageLayout(Paper.A4, PageOrientation.PORTRAIT, MarginType.HARDWARE_MINIMUM);
      anchorPane.setPrefSize(pageLayout.getPrintableWidth(), pageLayout.getPrintableHeight());
      printerJob.getJobSettings().setPageLayout(pageLayout);
      System.out.println(printerJob.getPrinter().getName());



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
    Font font = Font
        .loadFont(ClassLoader.getSystemResourceAsStream("font/roboto/OpenSans-Regular.ttf"), 12);
    AnchorPane anchorPane = new AnchorPane();
    Text platilac = new Text();
    platilac.setText(String.format("%s\n%s", izvestaji.getPlatioc(), izvestaji.getMestoPlatioca()));
    platilac.setFont(font);

    Text svrhaUplate = new Text();
    svrhaUplate.setFont(font);
    svrhaUplate.setWrappingWidth(200);
    svrhaUplate.setText(String.format("%s", izvestaji.getSvrhaPlacanja()));

    Text primalac = new Text();
    primalac.setFont(font);
    primalac.setText(String.format("%s\n%s", izvestaji.getPrimaoc(), izvestaji.getMestoPrimaoca()));

    Text sifraPlacanja = new Text();
    sifraPlacanja.setFont(font);
    sifraPlacanja.setText(izvestaji.getSifraPlacanja());

    Text iznos = new Text();
    iznos.setFont(font);
    iznos.setText(dtf.format(izvestaji.getIznos()));

    Text racunPrimaoca = new Text();
    racunPrimaoca.setFont(font);
    racunPrimaoca.setText(izvestaji.getRacunPrimaoca());

    Text modelPozivNaBroj = new Text();
    modelPozivNaBroj.setFont(font);
    modelPozivNaBroj.setText(izvestaji.getModelOdobrenje());

    Text odobobrenje = new Text();
    odobobrenje.setFont(font);
    odobobrenje.setText(izvestaji.pozivNaBrojOdobrenje);

    anchorPane.getChildren()
        .addAll(platilac, svrhaUplate, primalac, sifraPlacanja, iznos, racunPrimaoca,
            modelPozivNaBroj, odobobrenje);

    AnchorPane.setTopAnchor(platilac, 19.0);
    AnchorPane.setLeftAnchor(platilac, 17.0);

    AnchorPane.setTopAnchor(svrhaUplate, 100.0);
    AnchorPane.setLeftAnchor(svrhaUplate, 17.0);

    AnchorPane.setTopAnchor(primalac, 170.0);
    AnchorPane.setLeftAnchor(primalac, 17.0);

    AnchorPane.setTopAnchor(sifraPlacanja, 33.0);
    AnchorPane.setLeftAnchor(sifraPlacanja, 305.0);

    AnchorPane.setTopAnchor(iznos, 33.0);
    AnchorPane.setLeftAnchor(iznos, 420.0);

    AnchorPane.setTopAnchor(racunPrimaoca, 78.0);
    AnchorPane.setLeftAnchor(racunPrimaoca, 365.0);

    AnchorPane.setTopAnchor(modelPozivNaBroj, 122.0);
    AnchorPane.setLeftAnchor(modelPozivNaBroj, 305.0);

    AnchorPane.setTopAnchor(odobobrenje, 122.0);
    AnchorPane.setLeftAnchor(odobobrenje, 370.0);

    Scene scene = new Scene(anchorPane);
    Stage stage = new Stage();
    stage.setScene(scene);
    //stage.showAndWait();
    anchorPane.getStylesheets().removeAll();
    stampaj(anchorPane, wind);
  }

  public void stampaIsplate(JSONObject object) {
  }

  public void stampaPrenost(JSONObject object) {
  }

}
