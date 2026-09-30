# Java Custom HTTP Server & Micro-Framework

Un servidor HTTP concurrente y micro-framework web construido completamente desde cero utilizando **Java SE** (Sockets crudos), sin depender de frameworks externos como Spring Boot o Tomcat. 

Este proyecto nace con el objetivo de comprender a bajo nivel el funcionamiento del protocolo HTTP, la gestión de hilos, el enrutamiento de red y la arquitectura de aplicaciones backend.

## 🚀 Características Principales

*   **Servidor HTTP desde Cero:** Lectura y escritura directa sobre TCP Sockets (`java.net.ServerSocket`). Parseo manual de cabeceras HTTP y extracción del `Body` (Content-Length) en peticiones POST.
*   **Micro-Framework de Enrutamiento:** Sistema dinámico de rutas basado en un `HashMap` y Lambdas (`@FunctionalInterface`), inspirado en herramientas como Express.js o Sinatra.
*   **Arquitectura MVC (Controladores):** Separación de responsabilidades (SRP) utilizando Controladores dedicados (`ApiControlador`, `WebControlador`) para mantener un `Main` limpio y escalable.
*   **Concurrencia (Thread Pool):** Implementación de `ExecutorService` para manejar múltiples conexiones de clientes en paralelo sin bloquear el hilo principal.
*   **Graceful Shutdown:** Uso de `Runtime.getRuntime().addShutdownHook()` para interceptar el apagado del servidor, cerrar el Thread Pool de forma segura y liberar los puertos, evitando fugas de memoria.
*   **Configuración Externa:** Carga de variables de entorno (como el puerto) a través de un archivo `server.properties` estandarizado, con mecanismos de fallback de seguridad.
*   **Serialización JSON:** Integración de la librería **Jackson** (`ObjectMapper`) para la generación y respuesta dinámica de objetos JSON en las rutas de la API.

## 🛠️ Tecnologías Utilizadas

*   **Lenguaje:** Java SE
*   **Librerías Externas:** Jackson (Core, Annotations, Databind) para serialización JSON.
*   **Conceptos Clave:** Sockets, I/O Streams, Threading, Functional Interfaces, Colecciones (Maps).

## 📂 Estructura del Proyecto

*   `/src`: Código fuente (Main, Enrutador, ManejadorCliente, Controladores).
*   `/public`: Archivos estáticos servidos por la aplicación (index.html, style.css).
*   `/lib`: Dependencias `.jar` (Jackson).
*   `server.properties`: Archivo de configuración del puerto.

## ⚙️ Cómo Ejecutarlo

1. Clonar el repositorio:
   ```bash
   git clone [https://github.com/franco-robles/java-http-server.git](https://github.com/franco-robles/java-http-server.git)
2. Compilar el proyecto incluyendo la carpeta de dependencias:
   ```bash
   javac -cp "lib/*:src" src/*.java
3. Ejecutar el servidor:
   ```bash
   java -cp "lib/*:src" Main
4. Probar en el navegador accediendo a http://localhost:8080 o probar la API vía curl:
   ```bash
   curl -X POST http://localhost:8080/api/info -d '{"prueba":"exitosa"}'

