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
import java.util.logging.Level;
import java.util.logging.Logger;

public class Google_search {

    public static void main(String[] args) throws Exception {

        String[][] datos;
        Files_rw excel = new Files_rw();
        datos = excel.leer();

        String[] hp = find(datos);

        excel.escribir(hp);

    }

    private static String finder_email(String name, String email) {
        String url = "";
        try {
            Google results = GetGoogleResults("\"" + email + "\"");
            if (results.getResponseData() != null) {
                url = check(results, name);
            }
        } catch (Exception ex) {
            Logger.getLogger(Google_search.class.getName()).log(Level.SEVERE, null, ex);
        }
        return url;
    }

    private static String finder_name(String name) {
        String url = "";
        try {
            Google results = GetGoogleResults("\"" + name + "\"" + " -facebook -linkedin -google+ -twitter -youtube -instagram -pinterest -misprofesores");
            if (results.getResponseData() != null) {
                url = check(results, name);
            }
        } catch (Exception ex) {
            Logger.getLogger(Google_search.class.getName()).log(Level.SEVERE, null, ex);
        }

        return url;
    }

    private static String[] find(String[][] datos) throws Exception {

        String[] hp = new String[280];//HomePages

        for (int fila = 0; fila < 279; fila++) {
            String email = datos[2][fila];
            String name = datos[0][fila];
            String url = "";

            if (email.isEmpty()) {
                email = datos[1][fila];
                if (email.isEmpty() == false) {
                    url = finder_email(name, email);
                }
            } else {
                url = finder_email(name, email);
            }

            if (url.isEmpty()) {
                url = finder_name(name);
            }

            System.out.println("***************************************************************");
            System.out.println(name);
            System.out.println(url);
            hp[fila] = url;
        }
        return hp;
    }

    private static Boolean verificacion(String name, String title, String content) {
        
        title=title.toLowerCase();
        content=content.toLowerCase();
        
        Boolean verificacion0 = title.contains("home") || title.contains("page");
        Boolean verificacion1 = title.contains("Citas de Google Académico");

        Boolean verificacion2 = false;

        for (String val : name.split(" ")) {
            verificacion2 = title.contains(val.toLowerCase());
            if (verificacion2) {
                break;
            }
        }
        
        Boolean verificacion3 = false;
        for (String val : name.split(" ")) {
            verificacion3 = content.contains(val.toLowerCase());
            if (verificacion3) {
                break;
            }
        }
        
        Boolean verif = verificacion0 || verificacion1 || verificacion2 || verificacion3;
        return verif;
    }

    private static String check(Google results, String name) throws UnsupportedEncodingException {

        for (int i = 0; i < results.getResponseData().getResults().size(); i++) {

            String title = results.getResponseData().getResults().get(i).getTitle();
            String url = URLDecoder.decode(results.getResponseData().getResults().get(i).getUrl(), "UTF-8");
            String content = results.getResponseData().getResults().get(i).getContent();

            if (verificacion(name, title, content)) {
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
