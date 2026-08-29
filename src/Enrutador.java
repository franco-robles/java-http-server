import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
public class Enrutador {
    
    // Método estático que recibe la ruta y devuelve la respuesta HTTP completa
    public static String generarRespuesta(String ruta) {
        StringBuilder respuesta = new StringBuilder();

        switch (ruta) {
            case "/":
                respuesta.append("HTTP/1.1 200 OK\n");
                respuesta.append("Content-Type: text/html; charset=UTF-8\n\n");
                respuesta.append("<html><body style='background: #282c34; text-align:center; color: white; padding: 50px;'>");
                respuesta.append("<h1>Página Principal</h1>");
                respuesta.append("<p>Bienvenido al inicio de mi servidor Java.</p>");
                respuesta.append("</body></html>");
                break;

            case "/contacto":
                respuesta.append("HTTP/1.1 200 OK\n");
                respuesta.append("Content-Type: text/html; charset=UTF-8\n\n");
                respuesta.append("<html><body style='background: #1e3a8a; color: white; text-align: center; padding: 50px;'>");
                respuesta.append("<h1>LISTA DE CONTACTOS</h1>");
                respuesta.append("<p>Email: Moder@UFO.com</p>");
                respuesta.append("</body></html>");
                break;

            case "/api/info":
                respuesta.append("HTTP/1.1 200 OK\n");
                respuesta.append("Content-Type: application/json; charset=UTF-8\n\n");
                respuesta.append("{ \"nombre\":\"Franco\", \"role\":\"Backend\", \"Status\":\"Online\" }");
                break;

            case "/lento":
                try { Thread.sleep(10000); } catch (InterruptedException e) { }
                respuesta.append("HTTP/1.1 200 OK\n");
                respuesta.append("Content-Type: application/json; charset=UTF-8\n\n");
                respuesta.append("{ \"mensaje\":\"Esta respuesta tardo 10 segundos\" }");
                break;

            default:
                respuesta.append("HTTP/1.1 404 NOT FOUND\n");
                respuesta.append("Content-Type: text/html; charset=UTF-8\n\n");
                respuesta.append("<html><body style='background-color: #7f1d1d; color: white; text-align: center; padding: 50px;'>");
                respuesta.append("<h1>Error 404</h1>");
                respuesta.append("<p>La pagina " + ruta + " a la que intentas acceder no existe.</p>");
                respuesta.append("</body></html>");
                break;
        }

        return respuesta.toString();
    }
}