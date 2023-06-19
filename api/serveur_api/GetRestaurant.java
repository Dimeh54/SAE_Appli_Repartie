import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.ClientRMI;

import java.io.IOException;
import java.io.OutputStream;


public class GetRestaurant implements HttpHandler {
    private String restaurantId;

    public GetRestaurant(String restaurantId) {
        this.restaurantId = restaurantId;
    }
    @Override
    public void handle(HttpExchange t) {
        try {
            ClientRMI clientRMI = new ClientRMI();
            String response = clientRMI.appelRMI("recupererRestaurant", new String[] {restaurantId});
            t.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

