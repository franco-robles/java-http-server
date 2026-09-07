import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        int puerto = 8080;
        Properties properties = new Properties();

        try (FileInputStream archiveConf = new FileInputStream(new File("server.properties"))) {

            properties.load(archiveConf);
            puerto = Integer.parseInt(properties.getProperty("server.port"));
            System.out.println("Configuración cargada desde server.properties");
        } catch (IOException e) {
            // 4. Si el archivo no existe, no pasa nada, avisamos y usamos el fallback
            System.out.println("No se encontró server.properties. Arrancando con puerto por defecto: " + puerto);
        } catch (NumberFormatException e) {
            // 5. Atajamos por si alguien escribe "server.port=hola" en el archivo
            System.out.println("El puerto en properties no es válido. Arrancando con puerto por defecto: " + puerto);
        }

        try (ServerSocket servidor = new ServerSocket(puerto)) {

            ExecutorService threadPool = Executors.newFixedThreadPool(10);
            System.out.println("Servidor HTTP iniciado en puerto: " + puerto);

            // El siguiente Runtime cierra los threads y no se pierde memoria al apagar el
            // servidor
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("\nApagando el servidor ...");
                threadPool.shutdown(); // el pool no acepta a nadie más
                System.out.println("Servidor cerrado.");
            }));

            while (true) {
                // El programa se pausa acá hasta que un navegador se conecta
                Socket cliente = servidor.accept();
                System.out.println("¡Alguien se conectó! IP: " + cliente.getInetAddress());

                threadPool.execute(new ManejadorCliente(cliente));

            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}
