public class WebControlador{
     // Registramos la ruta para servir el CSS
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

            // Registramos la ruta para servir el js
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

}       
            