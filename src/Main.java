import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        int puerto = 8081;
        
        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Servidor HTTP iniciado en puerto: " + puerto);
            
            while (true) {
                // El programa se pausa acá hasta que un navegador se conecta
                Socket cliente = servidor.accept(); 
                System.out.println("¡Alguien se conectó! IP: " + cliente.getInetAddress());
                
                //Preparamos el lector para escuchar al cliente
                BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                String linea = in.readLine();
               
                //while ((linea = in.readLine()) != null && !linea.isEmpty()) {
                //    System.out.println(linea.split); // Imprimimos la petición en consola
                //    }
                // Si la conexión viene vacía, la cerramos y volvemos a empezar
                if (linea == null || linea.isEmpty()) {
                    cliente.close();
                    continue; // Salta a la siguiente iteración del while(true)
                }

                String[] primeraLinea = linea.split(" ");
                System.out.println("la primera linea sin cortar: " + linea);

                System.out.println("imprime la Ruta get");
                System.out.println("el Usuario quiere ingresar a " + primeraLinea[1]);

                // Preparamos el canal de salida para responder
                PrintWriter out = new PrintWriter(cliente.getOutputStream(), true);

                //segun la ruta vamo a devolver la pagina que corresponda
                switch (primeraLinea[1]) {
                    case "/":
                        //devuelve el home 
                        out.println("HTTP/1.1 200 OK"); 
                        out.println("Content-Type: text/html; charset=UTF-8"); 
                        out.println(); // Línea vacía obligatoria
                        out.println("<html><body style='background: #282c34; text-align:center; color: white; padding: 50px;'>");
                        out.println("<h1>Página Principal</h1>");
                        out.println("<p>Bienvenido al inicio de mi servidor Java.</p>");
                        out.println("</body></html>");
                        break;
                    case "/contacto":
                        out.println("HTTP/1.1 200 OK"); 
                        out.println("Content-Type: text/html; charset=UTF-8"); 
                        out.println();
                        out.println("<html>");
                        out.println("<body style='background: #1e3a8a; color: white; text-align: center; font-family: sans-serif; padding: 50px;'>");
                        out.println("<h1>LISTA DE CONTACTOS</h1>");
                        out.println("<p> Email:Moder@UFO.com. </p>");
                        out.println("</body>");
                        out.println("</html>");
                        break;
                    default:
                        out.println("HTTP/1.1 404 NOT FOUND"); 
                        out.println("Content-Type: text/html; charset=UTF-8"); 
                        out.println();
                        out.println("<html>");
                        out.println("<p><h1>Error 404</h1></p>");
                        out.println("<body style='background-color: #7f1d1d; color: white; text-align: center; font-family: sans-serif; padding: 50px;'>");
                        out.println("<p>La pagina" + primeraLinea[1] + "a la que intentas acceder no existe.</p>");
                        out.println("</body>");
                        out.println("</html>");
                        break;
                }


                System.out.println("--> Respuesta HTML enviada con éxito.");
               
                cliente.close(); 
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}