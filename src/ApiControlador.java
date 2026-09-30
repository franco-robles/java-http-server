

public class ApiControlador {

    static public void registrarRutas(){
            Enrutador.get("/api/user", (body, out) -> {
                out.println("HTTP/1.1 200 OK\n\n{ \"nombre\": \"Franco\" }");
            });
            // Registrar las Rutas
            // Registramos la ruta para servir el HTML
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

            
            // Registramos la ruta POST que probamos con CURL
            Enrutador.post("/api/info", (body, out) -> {
                out.println("HTTP/1.1 201 Created");
                out.println("Content-Type: application/json; charset=UTF-8\n");
                out.println("{ \"mensaje\": \"Datos recibidos con éxito\", \"tuBody\": " + body + " }");
            });

    }

}