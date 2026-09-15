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

            Enrutador.get("/api/user", (body, out) -> {
                out.println("HTTP/1.1 200 OK\n\n{ \"nombre\": \"Franco\" }");
            });
            // Registrar las Rutas
            // 1. Registramos la ruta para servir el HTML
            Enrutador.get("/", (body, out) -> {
                try {
                    String html = java.nio.file.Files.readString(java.nio.file.Path.of("public/index.html"));
                    out.println("HTTP/1.1 200 OK");
                    out.println("Content-Type: text/html; charset=UTF-8\n");
                    out.println(html);
                } catch (Exception e) {
                    out.println("HTTP/1.1 500 Internal Server Error\n\nError interno");
                }
            });

            // 2. Registramos la ruta para servir el CSS
            Enrutador.get("/style.css", (body, out) -> {
                try {
                    String css = java.nio.file.Files.readString(java.nio.file.Path.of("public/style.css"));
                    out.println("HTTP/1.1 200 OK");
                    out.println("Content-Type: text/css; charset=UTF-8\n");
                    out.println(css);
                } catch (Exception e) {
                    out.println("HTTP/1.1 500 Internal Server Error\n\nError interno");
                }
            });

            // 3. Registramos la ruta para servir el js
            Enrutador.get("/script.js", (body, out) -> {
                try {
                    String js = java.nio.file.Files.readString(java.nio.file.Path.of("public/script.js"));
                    out.println("HTTP/1.1 200 OK");
                    out.println("Content-Type: text/javascript; charset=UTF-8\n");
                    out.println(js);
                } catch (Exception e) {
                    out.println("HTTP/1.1 500 Internal Server Error\n\nError interno");
                }
            });
            // 4. Registramos la ruta POST que probamos con CURL
            Enrutador.post("/api/info", (body, out) -> {
                out.println("HTTP/1.1 201 Created");
                out.println("Content-Type: application/json; charset=UTF-8\n");
                out.println("{ \"mensaje\": \"Datos recibidos con éxito\", \"tuBody\": " + body + " }");
            });
            
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
