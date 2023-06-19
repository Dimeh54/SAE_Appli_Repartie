import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class PostReservation implements HttpHandler {
    private Map<String, String> parameters;
    public PostReservation(Map<String, String> parameters) {

    }

    @Override
    public void handle(HttpExchange t) throws IOException {
        // Utiliser les paramètres de la requête POST
        String nom = parameters.get("nom");
        // ... Autres traitements

        // Réponse de la requête
        String response = "Paramètres de la requête POST : " + parameters.toString();

        // Envoyer la réponse
        t.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
