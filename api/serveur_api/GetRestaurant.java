import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.ClientRMI;

import java.io.IOException;
import java.io.OutputStream;


public class GetRestaurant implements HttpHandler {
    private String restaurantId;
    private String ip;
    private int port;

    public GetRestaurant(String restaurantId, String ip, int port) {
        this.restaurantId = restaurantId;
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void handle(HttpExchange t) {
        try {
            ClientRMI clientRMI = new ClientRMI(ip, port);
            String response = (String) clientRMI.appelRMI("recupererRestaurant", new String[] {restaurantId});
            t.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

