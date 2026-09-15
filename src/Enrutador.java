
import java.io.PrintWriter;

import java.util.HashMap;

public class Enrutador {
    public static HashMap<String, Controlador> rutas = new HashMap<>();

    public static void get(String ruta, Controlador controlador) {
        rutas.put("GET " + ruta, controlador);
    }

    public static void post(String ruta, Controlador controlador) {
        rutas.put("POST " + ruta, controlador);
    }

    // Método estático que recibe la ruta y devuelve la respuesta HTTP completa
    public static void generarRespuesta(String ruta, String metodo, String body, PrintWriter out) {
        String metodoRuta = metodo + " " + ruta;
        Controlador controlador = rutas.get(metodoRuta);

        if (controlador != null) {
            controlador.manejar(body, out);
        } else {
            out.println("HTTP/1.1 404 Not Found");
            out.println("Content-Type: text/html; charset=UTF-8\n");
            out.println("<h1>Error 404: Ruta no encontrada</h1>");
        }

    }
}