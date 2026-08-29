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
                
                ManejadorCliente manageClient = new ManejadorCliente(cliente);
                manageClient.start();

            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}
