import com.sun.net.httpserver.*;
import org.json.JSONObject;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

public class serveur {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyStoreException, CertificateException, UnrecoverableKeyException, KeyManagementException {
        System.out.println("Lancement du serveur Https en cours...");
        String ip = "127.0.0.1";
        int port = 1099;
        try {
            if (args.length == 2) {
                System.out.println("utilisation : java Appel <adresse annuaire> <port annuaire>");
                System.out.println("utilisation des valeurs par défaut : ");
                System.out.println("\t- adresse : 127.0.0.1");
                System.out.println("\t- port : 1099");
                System.out.println();
                ip = args[0];
                port = Integer.parseInt(args[1]);
            }
        } catch (NumberFormatException e) {
            System.out.println("le port doit être un entier");
            System.exit(1);
        }

        HttpsServer server = HttpsServer.create(new InetSocketAddress(8000), 0);
        SSLContext sslContext = SSLContext.getInstance("TLS");

        // initialise the keystore
        char[] password = "password".toCharArray();
        KeyStore ks = KeyStore.getInstance("JKS");
        FileInputStream fis = new FileInputStream("key.jks");
        ks.load(fis, password);

        // setup the key manager factory
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, password);

        // setup the trust manager factory
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(ks);

        // setup the HTTPS context and parameters
        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        server.setHttpsConfigurator(new HttpsConfigurator(sslContext) {
            public void configure(HttpsParameters params) {
                try {
                    // initialise the SSL context
                    SSLContext context = getSSLContext();
                    SSLEngine engine = context.createSSLEngine();
                    params.setNeedClientAuth(false);
                    params.setCipherSuites(engine.getEnabledCipherSuites());
                    params.setProtocols(engine.getEnabledProtocols());

                    // Set the SSL parameters
                    SSLParameters sslParameters = context.getSupportedSSLParameters();
                    params.setSSLParameters(sslParameters);

                } catch (Exception ex) {
                    System.out.println("Failed to create HTTPS port");
                }
            }
        });
        server.createContext("/api/restaurants", new GetRestaurants(ip, port));
        String finalIp = ip;
        int finalPort = port;
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
            HttpHandler handler = new GetRestaurant(restaurantId, finalIp, finalPort);

            // Exécuter la logique de traitement de la requête dans getRestaurant
            handler.handle(exchange);
        });
        String finalIp1 = ip;
        int finalPort1 = port;
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

                HttpHandler handler = new PostReservation(parameters, finalIp1, finalPort1);

                handler.handle(exchange);
            }
        });

        server.createContext("/api/etablissements", new GetEtablissements(ip, port));
        server.setExecutor(null); // creates a default executor
        server.start();
        // on affiche un message de lancement du serveur
        System.out.println("Serveur Http démarré sur le port 8000");
    }
}