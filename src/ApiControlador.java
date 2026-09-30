import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode; // Necesario para leer el JSON del body
import java.util.HashMap;
import java.util.Map;
public class ApiControlador {
    // Instanciamos el traductor de Jackson
    private static final ObjectMapper mapper = new ObjectMapper();

    static public void registrarRutas(){
            Enrutador.get("/api/user", (body, out) -> {
                try {
                    
                    Map<String, String> usuario = new HashMap<>();
                    usuario.put("nombre", "Franco");
                    
                    // Jackson lo transforma a: {"nombre": "Franco"}
                    String json = mapper.writeValueAsString(usuario);
                    
                    out.println("HTTP/1.1 200 OK");
                    out.println("Content-Type: application/json; charset=UTF-8\n");
                    out.println(json);
                } catch (Exception e) {
                    out.println("HTTP/1.1 500 Internal Server Error\n\nError interno");
                }
            });
            
            Enrutador.post("/api/info", (body, out) -> {
            try {
                Map<String, Object> respuesta = new HashMap<>();
                respuesta.put("mensaje", "Datos recibidos con éxito");
                
                // Si el cliente nos mandó un body, le decimos a Jackson que lo interprete como JSON
                if (body != null && !body.isEmpty()) {
                    JsonNode jsonBody = mapper.readTree(body);
                    respuesta.put("tuBody", jsonBody);
                }
                
                // Convertimos TODO el mapa de respuesta a String JSON
                String jsonRespuesta = mapper.writeValueAsString(respuesta);
                
                out.println("HTTP/1.1 201 Created");
                out.println("Content-Type: application/json; charset=UTF-8\n");
                out.println(jsonRespuesta);
                
            } catch (Exception e) {
                out.println("HTTP/1.1 500 Internal Server Error\n\nError al procesar JSON");
            }
        });

    }

}