import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

public class serveur {
    public static void main(String[] args) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/api/restaurants", new GetRestaurants());
        server.createContext("/api/restaurant", exchange -> {
            // Extraire les paramètres de la requête
            String query = exchange.getRequestURI().getQuery();
            String restaurantId = null;

            if (query != null) {
                String[] queryParams = query.split("&");

                for (String param : queryParams) {
                    String[] keyValue = param.split("=");

                    if (keyValue.length == 2 && keyValue[0].equals("nom")) {
                        restaurantId = keyValue[1];
                        break;
                    }
                }
            }

            // Créer une instance de getRestaurant avec l'ID du restaurant
            HttpHandler handler = new GetRestaurant(restaurantId);

            // Exécuter la logique de traitement de la requête dans getRestaurant
            handler.handle(exchange);
        });
        server.createContext("/api/reservation", exchange -> {
            if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {

                // Lire le corps de la requête POST

                System.out.println(exchange.getRequestBody().read());

                // Créer une instance de getRestaurant avec les paramètres de la requête POST
                Map<String, String> parameters = new HashMap<>();
                parameters.put("nom", "test");
                // ... Autres paramètres à inclure
                HttpHandler handler = new PostReservation(parameters);

                // Exécuter la logique de traitement de la requête dans getRestaurant
                handler.handle(exchange);
            } else {
                // Autre logique pour les autres méthodes HTTP (GET, PUT, etc.)
                // ...
            }
        });

        server.setExecutor(null); // creates a default executor
        server.start();
    }
}