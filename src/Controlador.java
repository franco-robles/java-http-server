import java.io.PrintWriter;
// Esta anotación le avisa a Java que es una interfaz funcional (ideal para lambdas)
@FunctionalInterface
public interface Controlador {
    // Todo controlador recibirá el body de la petición y la herramienta para responder
    void manejar(String body, PrintWriter out);
}