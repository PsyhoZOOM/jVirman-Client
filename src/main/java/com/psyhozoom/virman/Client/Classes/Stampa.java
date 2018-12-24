package com.psyhozoom.virman.Client.Classes;

import java.text.DecimalFormat;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
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
  private Printer printer;
  private PageLayout pageLayout;
  private Paper paper;
  private DecimalFormat dtf = new DecimalFormat("###,###,###,##0.00");
  private AnchorPane anchorPane = new AnchorPane();
  private Font font = Font.loadFont(ClassLoader.getSystemResourceAsStream("font/roboto/saxmono.ttf"), 12);


  private void stampaj(AnchorPane anchorPane, Window wind) {

    printerJob = PrinterJob.createPrinterJob();
    if (printerJob != null && printerJob.showPrintDialog(wind)) {
      pageLayout = printerJob.getPrinter().createPageLayout(Paper.A4,  PageOrientation.PORTRAIT, 0,0,0,0);
      printerJob.getJobSettings().setPageLayout(pageLayout);

      System.out.println("papir sizce: "+ printerJob.getJobSettings().getPageLayout().getPaper().getWidth()
          + " "
          + printerJob.getJobSettings().getPageLayout().getPaper().getHeight());
      System.out.println("margine: "+pageLayout.getTopMargin() + " " + pageLayout.getLeftMargin());
      System.out.println("printable size: "+ pageLayout.getPrintableWidth() + " " + pageLayout.getPrintableHeight());

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

    AnchorPane.setTopAnchor(platilac, 25.0);
    AnchorPane.setLeftAnchor(platilac, 10.0);

    AnchorPane.setTopAnchor(svrhaUplate, 90.0);
    AnchorPane.setLeftAnchor(svrhaUplate, 10.0);

    AnchorPane.setTopAnchor(primalac, 145.0);
    AnchorPane.setLeftAnchor(primalac, 10.0);

    AnchorPane.setTopAnchor(sifraPlacanja, 38.0);
    AnchorPane.setLeftAnchor(sifraPlacanja, 312.0);

    AnchorPane.setTopAnchor(iznos, 38.0);
    AnchorPane.setLeftAnchor(iznos, 430.0);

    AnchorPane.setTopAnchor(racunPrimaoca, 75.0);
    AnchorPane.setLeftAnchor(racunPrimaoca, 340.0);

    AnchorPane.setTopAnchor(modelPozivNaBroj, 110.0);
    AnchorPane.setLeftAnchor(modelPozivNaBroj, 312.0);

    AnchorPane.setTopAnchor(odobobrenje, 110.0);
    AnchorPane.setLeftAnchor(odobobrenje, 370.0);

    Scene scene = new Scene(anchorPane);
    Stage stage = new Stage();
    stage.setScene(scene);
    //stage.showAndWait();
    anchorPane.getStylesheets().removeAll();
    stampaj(anchorPane, wind);
  }

  public void stampaIsplate(Izvestaji izvestaji, Window window) {
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

    Text racunPlatioca = new Text();
    racunPlatioca.setFont(font);
    racunPlatioca.setText(izvestaji.getRacunPlatioca());

    Text modelPozivNaBroj = new Text();
    modelPozivNaBroj.setFont(font);
    modelPozivNaBroj.setText(izvestaji.getModelZaduzenje());

    Text  zaduzenje = new Text();
    zaduzenje.setFont(font);
    zaduzenje.setText(izvestaji.getPozivNaBrojZaduzenje());

    anchorPane.getChildren()
        .addAll(platilac, svrhaUplate, primalac, sifraPlacanja, iznos, racunPlatioca,
            modelPozivNaBroj, zaduzenje);

    AnchorPane.setTopAnchor(platilac, 25.0);
    AnchorPane.setLeftAnchor(platilac, 10.0);

    AnchorPane.setTopAnchor(svrhaUplate, 90.0);
    AnchorPane.setLeftAnchor(svrhaUplate, 10.0);

    AnchorPane.setTopAnchor(primalac, 145.0);
    AnchorPane.setLeftAnchor(primalac, 10.0);

    AnchorPane.setTopAnchor(sifraPlacanja, 38.0);
    AnchorPane.setLeftAnchor(sifraPlacanja, 312.0);

    AnchorPane.setTopAnchor(iznos, 38.0);
    AnchorPane.setLeftAnchor(iznos, 430.0);

    AnchorPane.setTopAnchor(racunPlatioca, 75.0);
    AnchorPane.setLeftAnchor(racunPlatioca, 340.0);

    AnchorPane.setTopAnchor(modelPozivNaBroj, 110.0);
    AnchorPane.setLeftAnchor(modelPozivNaBroj, 312.0);

    AnchorPane.setTopAnchor(zaduzenje, 110.0);
    AnchorPane.setLeftAnchor(zaduzenje, 370.0);

    Scene scene = new Scene(anchorPane);
    Stage stage = new Stage();
    stage.setScene(scene);
    //stage.showAndWait();
    anchorPane.getStylesheets().removeAll();
    stampaj(anchorPane, window);

  }

  public void stampaPrenost(JSONObject object) {
  }

}
