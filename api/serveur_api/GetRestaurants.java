import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.ClientRMI;

public class GetRestaurants implements HttpHandler{
    @Override
    public void handle(HttpExchange t) {
        try {
            ClientRMI clientRMI = new ClientRMI();
            String response = clientRMI.appelRMI("recupererRestaurants", null);
            t.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}