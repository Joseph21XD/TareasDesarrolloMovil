package enigma.myapplication;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by ramir on 2/22/2018.
 */

public class HtmlRead {
    public String leerPagina(String url) {
        System.out.println("Leyendo Pagina : " + url);
        StringBuffer resultado = new StringBuffer();

        try {
            URL urlPagina = new URL(url);
            URLConnection urlConexion = urlPagina.openConnection();
            urlConexion.connect();

            DataInputStream dis = new DataInputStream(urlPagina.openStream());
            String inputLine;

            while ((inputLine = dis.readLine()) != null) {
                System.out.println(inputLine);
            }
            dis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Contenido : \n\n" + resultado.toString());
        return resultado.toString();
    }

    /**
     * Realiza la lectura de una pagina enviada como parametro y busca las
     * coincidencias de un texto especificado.
     *
     * @param url
     * @param texto
     * @return
     */
    public String buscarTextoPagina(String url, String texto, String texto2) {
        System.out.println("Leyendo Pagina : " + url);
        StringBuffer resultado = new StringBuffer();
        int veces = 0;

        try {
            URL pagina = new URL(url);
            URLConnection uc = pagina.openConnection();
            uc.connect();



            // Creamos el objeto con el que vamos a leer
            BufferedReader lector = new BufferedReader(new InputStreamReader(
                    uc.getInputStream(), "UTF-8"));
            String linea = "";
            String contenido = "";

            while ((linea = lector.readLine()) != null) {

                if (linea.contains(texto)) {
                    int idx = 0, coincidencias = 0;

                    while (linea.indexOf(texto, idx) > 0) {
                        System.out.println(""+Double.parseDouble(linea.substring(linea.indexOf(texto, idx)+4, linea.indexOf(texto2, idx))));
                        idx = linea.indexOf(texto, idx) + 1 + texto.length();
                        coincidencias++;
                    }

                    resultado.append(String.valueOf(coincidencias
                            + " coincidencia(s) =>"));
                    resultado.append(String.valueOf(linea));
                    resultado.append("\n");

                    veces += coincidencias;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Se encontro un total de : " + veces
                + " coincidencias con la expresion [" + texto + "] ");
        System.out.println(resultado.toString());
        return resultado.toString();
    }

}
