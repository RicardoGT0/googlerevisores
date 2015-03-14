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

        String[] hp = new String[280];//HomePages

        for (int i = 0; i < 300; i++) {
            hp = find(datos, hp);
            String filename = "homepages.xls";
            excel.escribir(hp, filename);
        }

    }

    private static String finder_email(String name, String email) {
        String url = "";
        try {
            Google results = GetGoogleResults("\"" + email + "\"");
            if (results.getResponseData() != null) {
                url = check(results, name);
            } /*else {//solo usar en caso de emergencia
             results = GetGoogleResults(email);
             if (results.getResponseData() != null) {
             url = check(results, name);
             }
             }*/

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

    private static String[] find(String[][] datos, String[] hp) throws Exception {

        for (int fila = 0; fila < 278; fila++) {
            String email = datos[2][fila];
            String name = datos[0][fila];
            String url = "";
            String home = hp[fila];
            if (home == null) {
                home = "";
            }
            if (home.isEmpty()) {

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
        }
        return hp;
    }

    private static Boolean verificacion(String name, String title, String content) {

        Boolean verificacion1;
        verificacion1 = title.toLowerCase().contains("page");

        Boolean verificacion2 = false;

        for (String retval : name.split(" ")) {
            verificacion2 = title.toLowerCase().contains(retval.toLowerCase());
            if (verificacion2) {
                break;
            }
        }

        Boolean verificacion3 = title.toLowerCase().contains("Citas de Google Académico");

        Boolean verif = verificacion1 || verificacion2 || verificacion3;
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
