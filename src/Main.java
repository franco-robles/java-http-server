import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        int puerto = 8080;
        
        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Servidor HTTP iniciado en puerto: " + puerto);
            
            while (true) {
                // El programa se pausa acá hasta que un navegador se conecta
                Socket cliente = servidor.accept(); 
                System.out.println("¡Alguien se conectó! IP: " + cliente.getInetAddress());
                
                // 1. Preparamos el lector para escuchar al cliente
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


                // 2. Preparamos el canal de salida para responder
                PrintWriter out = new PrintWriter(cliente.getOutputStream(), true);
                
                // 3. Escribimos la cabecera HTTP estricta
                out.println("HTTP/1.1 200 OK"); 
                out.println("Content-Type: text/html; charset=UTF-8"); 
                out.println(); // ¡ESTA LÍNEA VACÍA ES OBLIGATORIA PARA SEPARAR EL CONTENIDO!
                
                // 4. Enviamos el cuerpo: Nuestro propio HTML
                out.println("<html>");
                out.println("<head><title>Servidor Java</title></head>");
                out.println("<body style='background-color: #282c34; color: white; text-align: center; font-family: sans-serif; padding: 50px;'>");
                out.println("<h1>¡Hola desde tu propio servidor Java puro! 🚀</h1>");
                out.println("<p>Si estás leyendo esto, tu código HTTP funciona a la perfección.</p>");
                out.println("</body>");
                out.println("</html>");
                
                System.out.println("--> Respuesta HTML enviada con éxito.");
               
                cliente.close(); 
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}