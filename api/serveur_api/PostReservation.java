import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.ClientRMI;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class PostReservation implements HttpHandler {
    private Map<String, String> parameters;
    public PostReservation(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    @Override
    public void handle(HttpExchange t) throws IOException {
        ClientRMI clientRMI = new ClientRMI();
        String response = clientRMI.appelRMI("enregistrerReservation", new String[] {parameters.get("nom"), parameters.get("prenom"), parameters.get("nbpers"), parameters.get("numtel"), parameters.get("date"), parameters.get("id_restaurant")});

        // Envoyer la réponse
        t.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
