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
import jxl.write.*;

public class Files_rw {

    private String[] leerfile(String name) throws FileNotFoundException, IOException {
        String[] contenido = new String[280];
        File file = new File(name + ".txt");
        BufferedReader entrada;
        entrada = new BufferedReader(new FileReader(file));

        for (int i = 0; i < 278; i++) {//while(entrada.ready())
            contenido[i] = entrada.readLine();
        }

        return contenido;
    }

    public String[][] leer() {
        String[][] lista = new String[3][280];

        try {

            String[] nombres = leerfile("nombres");
            String[] emails1 = leerfile("emails1");
            String[] emails2 = leerfile("emails2");
            for (int fila = 0; fila < 279; fila++) {

                lista[0][fila] = nombres[fila];
                lista[1][fila] = emails1[fila];
                lista[2][fila] = emails2[fila];

            }

        } catch (IOException ex) {
            Logger.getLogger(Files_rw.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;
    }

    public void escribir(String[] pages) {
        try {
            int c = 0;

            WritableWorkbook workbook = Workbook.createWorkbook(new File("homepages.xls"));
            WritableSheet sheet = workbook.createSheet("Resultado", 0);

            for (int fila = 1; fila < 279; fila++) {
                String page = pages[fila - 1];
                sheet.addCell(new jxl.write.Label(0, fila, page));
            }
            workbook.write();
            workbook.close();

            for (String page : pages) {
                if (page != "") {
                    c++;
                }
            }
            System.out.println(c);

        } catch (IOException ex) {
            Logger.getLogger(Files_rw.class.getName()).log(Level.SEVERE, null, ex);
        } catch (WriteException ex) {
            Logger.getLogger(Files_rw.class.getName()).log(Level.SEVERE, null, ex);
        }
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
