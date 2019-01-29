package com.psyhozoom.virman.Client.Classes;

import com.sun.javafx.print.PrintHelper;
import com.sun.javafx.print.Units;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.json.JSONObject;
import java.text.DecimalFormat;

public class Stampa {

  private DecimalFormat dtf = new DecimalFormat("###,###,###,##0.00");
  private AnchorPane anchorPane = new AnchorPane();
  private Font font = Font.loadFont(ClassLoader.getSystemResourceAsStream("font/roboto/led_counter-7.ttf"), 12);
  //private  Font font = Font.font("Courier New", FontWeight.NORMAL, 12);
  private PrinterJob pj;



  private void stampaj(Window wind) {

   pj =  PrinterJob.createPrinterJob();
 //  Paper paper = PrintHelper.createPaper("VIRMAN", 8.5, 11, Units.INCH );
    Paper paper = PrintHelper.createPaper("VIRMAN", 240, 101, Units.MM);
   pj.getJobSettings().setPageLayout(pj.getPrinter().createPageLayout(Paper.A4, PageOrientation.PORTRAIT, 20, 20, 20 ,20));
   pj.showPrintDialog(null);

   double w =pj.getJobSettings().getPageLayout().getPaper().getWidth();// pj.getJobSettings().getPageLayout().getPrintableWidth();
   double h = pj.getJobSettings().getPageLayout().getPaper().getHeight(); //pj.getJobSettings().getPageLayout().getPrintableHeight();



    anchorPane.getStylesheets().removeAll();
    anchorPane.getStylesheets().add("-fx-background-color: white");

    anchorPane.setMinSize(w, h);
    anchorPane.setPrefSize(w,h);
    anchorPane.setMaxSize(w,h);

    Text texa = new Text("GORE PRVO LEVO.");
    anchorPane.getChildren().add(texa);

    AnchorPane.setTopAnchor(texa, 0.00);
    AnchorPane.setLeftAnchor(texa, 0.00);

    anchorPane.getStylesheets().removeAll();



/*
    Scene scen = new Scene(anchorPane, w, h);
    Stage sta = new Stage();
    sta.setScene(scen);
    sta.showAndWait();
*/
    System.out.println(pj.getPrinter().getName());
    if(pj!=null){
      boolean b1 = pj.printPage( anchorPane);
      System.out.println(b1);
      if(b1)
        pj.endJob();

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


    //AnchorPane.setTopAnchor(platilac, 35.0);
    AnchorPane.setTopAnchor(platilac, 30.0);
    AnchorPane.setLeftAnchor(platilac, 0.0);

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

    stampaj(wind);
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
    stampaj(window);

  }

  public void stampaPrenost(JSONObject object) {
  }

}
