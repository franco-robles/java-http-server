import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ManejadorCliente extends Thread {
    Socket cliente;

    ManejadorCliente(Socket c) {
        this.cliente = c;
    }

    @Override
    public void run() {

        // Preparamos el lector para escuchar al cliente
        try (BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                PrintWriter out = new PrintWriter(cliente.getOutputStream(), true)) {
            String linea = in.readLine();

            // Si la conexión viene vacía, la cerramos
            if (linea == null || linea.isEmpty()) {
                cliente.close();
                return; // se termina la ejecucion
            }

            String[] primeraLinea = linea.split(" ");
            System.out.println("el Usuario quiere ingresar a " + primeraLinea[1]);
            
            // IMPORTANTE: Leemos el resto de las cabeceras para que el navegador no se trabe
            String cabecera;
            int lengthBody = 0;
            String ruta = primeraLinea[1];
            String metodo = primeraLinea[0];

            while ((cabecera = in.readLine()) != null && !cabecera.isEmpty()) {
                if(cabecera.startsWith("Content-Length:")){
                    lengthBody = Integer.parseInt(cabecera.substring(15).trim());
                }
            }
            
            if(metodo.equals("POST") && lengthBody>0){
                char[] cuerpoDeLaPeticion = new char[lengthBody];
                in.read(cuerpoDeLaPeticion, 0, lengthBody);
                String body = new String(cuerpoDeLaPeticion);
                System.out.println("EL metodo es: "+ metodo);
                System.out.println("el cuerpo de la peticion es: "+ body);
            }
            
            // Preparamos el canal de salida para responder
            out.print(Enrutador.generarRespuesta(ruta));
            out.flush(); // Asegura que se envíe todo

            System.out.println("--> Respuesta HTML enviada con éxito.");

        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }finally {
            try {
                cliente.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}