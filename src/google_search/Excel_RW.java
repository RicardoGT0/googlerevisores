/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package google_search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

    private String[] getnombres(){
        String[] nombres = new String[280];
        try {
            File file = new File("nombres.txt");
            BufferedReader entrada;
            entrada = new BufferedReader(new FileReader(file));
           
            
            for (int i = 0; i < 278; i++) {//while(entrada.ready())
                nombres[i] = entrada.readLine();
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Excel_RW.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Excel_RW.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nombres;
    }

    public String[][] leer_xls() {
        String[][] lista = new String[3][280];
        String[] nombres=getnombres();
        
        for (int col = 1; col < 3; col++) {
            for (int fila = 1; fila < 279; fila++) {
                Cell celda = this.sheet.getCell(col, fila);
                lista[col][fila] = celda.getContents();
                lista[0][fila]=nombres[fila];

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
