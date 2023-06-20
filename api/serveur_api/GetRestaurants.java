import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.ClientRMI;

public class GetRestaurants implements HttpHandler{
    private String ip;
    private int port;

    public GetRestaurants(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }
    @Override
    public void handle(HttpExchange t) {
        try {
            ClientRMI clientRMI = new ClientRMI(ip, port);
            String response = (String) clientRMI.appelRMI("recupererRestaurants", null);
            t.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}