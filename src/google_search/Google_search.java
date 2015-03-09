/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package google_search;

import com.google.gson.Gson;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class Google_search {

    public static void main(String[] args) throws Exception {

        String[][] datos;
        Excel_RW excel = new Excel_RW();
        datos = excel.leer_xls();
        
        String[] hp=find(datos);
        
        excel.escribir(hp);
        

    }
    
    private static String[] find(String[][] datos) throws Exception{
        
        String[] hp = new String[280];//HomePages

        for (int fila = 1; fila < 279; fila++) {
            String email = datos[1][fila];
            
            //byte[] temp = datos[0][fila].getBytes();
            //String name=new String(temp, "UTF-8");
            String name=datos[0][fila];
            
            if (email == "") {
                email = datos[0][fila];
            }

            String url = "";
            Google results = GetGoogleResults("\"" + email + "\"");
            if (results.getResponseData() != null) {
                url = check(results, name);
            }

            email = datos[2][fila];
            if (url == "" && email != "") {
                results = GetGoogleResults("\"" + email + "\"");
                if (results.getResponseData() != null) {
                    url = check(results, name);
                }
            }
            
            if (url == "") {
                results = GetGoogleResults("\"" + name + "\"" + " -facebook -linkedin -google+ -twitter -youtube -instagram -pinterest -misprofesores");
                if (results.getResponseData() != null) {
                    url = check(results, name);
                }
            }
            
            System.out.println("***************************************************************");
            System.out.print(name);
            System.out.println(url);
            hp[fila]=url;
        }
        return hp;
    }

    private static String check(Google results, String name) throws UnsupportedEncodingException {

        for (int i = 0; i < results.getResponseData().getResults().size(); i++) {

            String title = results.getResponseData().getResults().get(i).getTitle();
            String url = URLDecoder.decode(results.getResponseData().getResults().get(i).getUrl(), "UTF-8");
            String Content = results.getResponseData().getResults().get(i).getContent();

            Boolean verificacion1 = title.toLowerCase().contains("page");

            Boolean verificacion2 = false;

            for (String retval : name.split(" ")) {
                verificacion2 = title.toLowerCase().contains(retval.toLowerCase());
                if (verificacion2) {
                    break;
                }
            }

            Boolean verificacion3 = title.toLowerCase().contains("Citas de Google Académico");

            if (verificacion1 || verificacion2 || verificacion3) {
                return url;
            }

        }
        return "";
    }

    private static Google GetGoogleResults(String term) throws Exception {
        String google;
        String charset = "UTF-8";
        URL url;
        Reader reader;
        Google results;

        google = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&rsz=8&q=";

        url = new URL(google + URLEncoder.encode(term, charset));
        reader = new InputStreamReader(url.openStream(), charset);
        results = new Gson().fromJson(reader, Google.class);

        return results;
    }
}
