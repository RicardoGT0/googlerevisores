/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package google_search;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.*;
import jxl.read.biff.BiffException;

public class Excel_RW {

   
    Workbook workbook;
    Sheet sheet;
    public Excel_RW() throws IOException {
        try {
            
            this.workbook = Workbook.getWorkbook(new File("datosrevisores.xls"));

            this.sheet = this.workbook.getSheet(0);
        
        } catch (BiffException ex) {
            Logger.getLogger(Excel_RW.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String[][] leer_xls() {
        String[][] lista = new String[3][280];

        for (int col = 0; col < 3; col++) {
            for (int fila = 1; fila < 279; fila++) {
                Cell celda = this.sheet.getCell(col, fila);
                lista[col][fila] = celda.getContents();

            }
        }
        workbook.close();
        return lista;
    }

    public void escribir(String[] pages) {
        int c = 0;
        for (String page : pages) {
            if (page != "") {
                c++;
            }
        }
        System.out.println(c);
    }

    private String codificar(String contents) {

        contents = contents.replace('\u00e1', 'á');
        contents = contents.replace('\u00e9', 'é');
        contents = contents.replace('\u00ed', 'í');
        contents = contents.replace('\u00f3', 'ó');
        contents = contents.replace('\u00fa', 'ú');
        contents = contents.replace('\u00c1', 'Á');
        contents = contents.replace('\u00c9', 'É');
        contents = contents.replace('\u00cd', 'Í');
        contents = contents.replace('\u00d3', 'Ó');
        contents = contents.replace('\u00da', 'Ú');
        contents = contents.replace('\u00f1', 'ñ');
        contents = contents.replace('\u00d1', 'Ñ');

        return contents;
    }

}
