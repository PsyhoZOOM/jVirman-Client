package com.psyhozoom.virman.Client.Classes;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.json.JSONObject;

import javax.print.attribute.HashPrintRequestAttributeSet;
import java.awt.*;
import java.awt.print.*;
import java.text.DecimalFormat;

public class Stampa {

  private DecimalFormat dtf = new DecimalFormat("###,###,###,##0.00");
  private AnchorPane anchorPane = new AnchorPane();
  private Font font = Font.loadFont(ClassLoader.getSystemResourceAsStream("font/roboto/led_counter-7.ttf"), 12);
  //private  Font font = Font.font("Courier New", FontWeight.NORMAL, 12);




  private void stampaj(AnchorPane anchorPane, Window wind) {


    PrinterJob pj = PrinterJob.getPrinterJob();
    if (pj != null && pj.printDialog()) {
      //Paper paper = printerJob.getPrintService().getAttributes().ge .createPaper("216x101", 216, 101, Units.MM);
        Paper paper = new Paper();
      paper.setSize(8.50*72, 2.97*72);
      HashPrintRequestAttributeSet  atributi  = new HashPrintRequestAttributeSet();
      pj.setPrintable(new Printable() {
        @Override
        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
          paper.setImageableArea(1, 1,  paper.getWidth(), paper.getHeight());
            pageFormat.setPaper(paper);
          System.out.println(String.format("paper imgwh %f %f",paper.getImageableWidth() , paper.getImageableHeight()));
          System.out.println(String.format("paper imgxy %f %f", paper.getImageableX(), paper.getImageableY()));
            int x = (int) Math.ceil(pageFormat.getImageableX());
            int y = (int) Math.ceil(pageFormat.getImageableY());
            if(pageIndex !=0 ){
              System.out.println("NO PAGE");
              return NO_SUCH_PAGE;
            }
            graphics.translate((int) Math.ceil(pageFormat.getImageableX()), (int) Math.ceil(pageFormat.getImageableY()));
            graphics.drawString("CAO CAO", x, y );
            graphics.drawRect(x,y, 140, 100);
          return PAGE_EXISTS;
        }
      });
      try {
        pj.print();
      } catch (PrinterException e) {
        e.printStackTrace();
      }

      boolean e = true;
      if (e) return;
/*
      System.out.println(String.format("paper name: %s, papir size: %fx%f, margine: %f %f %f %f, printable size %fx%f",
              pj.getJobSettings().getPageLayout().getPaper().getName(),
              pj.getJobSettings().getPageLayout().getPaper().getWidth(),
              pj.getJobSettings().getPageLayout().getPaper().getHeight(),
              pj.getJobSettings().getPageLayout().getTopMargin(),
              pj.getJobSettings().getPageLayout().getLeftMargin(),
              pj.getJobSettings().getPageLayout().getRightMargin(),
              pj.getJobSettings().getPageLayout().getBottomMargin(),
              pj.getJobSettings().getPageLayout().getPrintableWidth(),
              pj.getJobSettings().getPageLayout().getPrintableHeight()));
     //anchorPane.setPrefSize(printerJob.getJobSettings().getPageLayout().getPrintableWidth(), printerJob.getJobSettings().getPageLayout().getPrintableHeight());
*/


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

    AnchorPane.setTopAnchor(platilac, 35.0);
    AnchorPane.setLeftAnchor(platilac, 10.0);

    AnchorPane.setTopAnchor(svrhaUplate, 95.0);
    AnchorPane.setLeftAnchor(svrhaUplate, 10.0);

    AnchorPane.setTopAnchor(primalac, 158.0);
    AnchorPane.setLeftAnchor(primalac, 10.0);

    AnchorPane.setTopAnchor(sifraPlacanja, 48.0);
    AnchorPane.setLeftAnchor(sifraPlacanja, 308.0);

    AnchorPane.setTopAnchor(iznos, 48.0);
    AnchorPane.setLeftAnchor(iznos, 360.0);

    AnchorPane.setTopAnchor(racunPrimaoca, 80.0);
    AnchorPane.setLeftAnchor(racunPrimaoca, 340.0);

    AnchorPane.setTopAnchor(modelPozivNaBroj, 122.0);
    AnchorPane.setLeftAnchor(modelPozivNaBroj, 308.0);

    AnchorPane.setTopAnchor(odobobrenje, 120.0);
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
    stampaj(anchorPane, window);

  }

  public void stampaPrenost(JSONObject object) {
  }

}
