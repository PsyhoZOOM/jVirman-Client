package com.psyhozoom.virman.Client.Controllers;

import com.psyhozoom.virman.Client.Classes.ESCPrinter;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.PrintServiceAttributeSet;
import java.net.URL;
import java.util.ResourceBundle;

public class TestPrinter implements Initializable {

  public TextField aa;
    public Button bADd;

    @Override
  public void initialize(URL location, ResourceBundle resources) {

    PrintService[] printServices = (PrintService[]) PrintServiceLookup
        .lookupPrintServices(null, null);
    System.out.println("Number of print services: " + printServices.length);

    for (PrintService printer : printServices) {
      System.out.println("Printer: " + printer.getName());
      PrintServiceAttributeSet att =  printer.getAttributes();
      int i =0;
      for (Object attr : att.toArray()){
        System.out.println(String.format("Attr #%d: %s",i,attr.getClass().getName()));
        i++;
      }
    }

  }

  public void init(){
    PrinterJob pj = PrinterJob.createPrinterJob();

    if(pj != null && pj.showPrintDialog(bADd.getScene().getWindow())) {

      ESCPrinter escPrinter = new ESCPrinter(pj.getPrinter().getName(), false);
      escPrinter.advanceHorizontal(15);
      escPrinter.advanceVertical(15);
      escPrinter.print("HI");
    }
  }
}