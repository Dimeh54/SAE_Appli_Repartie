import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.ClientRMI;
import service.ReponseEtablissement;

import java.io.IOException;
import java.io.OutputStream;

public class GetEtablissements implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException {
        try {
            ClientRMI clientRMI = new ClientRMI();
            ReponseEtablissement response = (ReponseEtablissement) clientRMI.appelRMI("recupererEtablissements", null);
            t.getResponseHeaders().set("Content-Type", response.getContentType());
            t.sendResponseHeaders(response.getStatusCode(), response.getResponseBody().getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getResponseBody().getBytes());
            os.close();
        } catch (IOException e) {
            String message = "Une erreur s'est produite lors du traitement de la requête : " + e.getMessage();
            throw new RuntimeException(message, e);
        }
    }
}
