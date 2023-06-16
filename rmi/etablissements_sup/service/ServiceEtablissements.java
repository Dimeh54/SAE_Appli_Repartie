package service;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.net.ProxySelector;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public class ServiceEtablissements implements InterfaceEtablissements, Serializable {

    public Object[] recupererEtablissements() throws RemoteException, ServerNotActiveException, FileNotFoundException {
        Object[] resultat = {new Object(), new Object(), new Object()};


        //new ServiceEtablissements().recupererEtablissements();
        // URL fournie sur arche non fonctionnelle
        //String url = "https://www.data.gouv.fr/fr/datasets/r/5fb6d2e3-609c-481d-9104-350e9ca134fa";
        // URL qui donne toutes les données json
        //String url = "https://data.enseignementsup-recherche.gouv.fr//explore/dataset/fr-esr-principaux-etablissements-enseignement-superieur/download?format=json&amp;timezone=Europe/Berlin&amp;use_labels_for_header=false";
        // URL qui donne les établissements d'enseignement supérieur de l'aire urbaine de Nancy
        String url = "https://data.enseignementsup-recherche.gouv.fr/api/records/1.0/search/?dataset=fr-esr-implantations_etablissements_d_enseignement_superieur_publics&q=&refine.uucr_nom=Nancy";
        String urlProxy = "www-cache";
        int port = 3128;
        //HttpClient httpClient = HttpClient.newHttpClient();
        HttpClient httpClient = HttpClient.newBuilder().proxy(ProxySelector.of(new InetSocketAddress(urlProxy, port))).build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            Integer statusCode = response.statusCode();
            resultat[0] = statusCode;
            System.out.println("Status Code: " + statusCode);

            String contentType = response.headers().firstValue("Content-Type").orElse("");
            resultat[1] = contentType;
            System.out.println("Content-Type: " + contentType);

            String responseBody = response.body();
            resultat[2] = responseBody;
            System.out.println("Response Body:");
            System.out.println(responseBody);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return resultat;
    }
}
