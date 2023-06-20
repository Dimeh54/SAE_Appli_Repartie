import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


public class serveur {
    public static void main(String[] args) throws IOException {
        System.out.println("Lancement du serveur Http en cours...");
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

                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);
                String temp = br.readLine();
                StringBuilder sb = new StringBuilder();
                while (temp != null) {
                    sb.append(temp);
                    temp = br.readLine();
                }

                String jsonString = sb.toString();
                JSONObject obj = new JSONObject(jsonString);
                // add the values to a map
                Map<String, String> parameters = new HashMap<>();
                parameters.put("nom", obj.getString("nom"));
                parameters.put("prenom", obj.getString("prenom"));
                parameters.put("nbpers", obj.getString("nbpers"));
                parameters.put("numtel", obj.getString("numtel"));
                parameters.put("date", obj.getString("date"));
                parameters.put("id_restaurant", obj.getString("id_restaurant"));

                HttpHandler handler = new PostReservation(parameters);

                handler.handle(exchange);
            }
        });

        server.createContext("/api/etablissements", new GetEtablissements());
        server.setExecutor(null); // creates a default executor
        server.start();
        // on affiche un message de lancement du serveur
        System.out.println("Serveur Http démarré sur le port 8000");
    }
}