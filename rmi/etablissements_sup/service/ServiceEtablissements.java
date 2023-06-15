import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ProxySelector;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public class ServiceEtablissements implements InterfaceEtablissements {

    public String recupererEtablissements() throws RemoteException, ServerNotActiveException, FileNotFoundException {
        return "";
    }

    public static void main(String[] args) throws RemoteException, FileNotFoundException, ServerNotActiveException {
        //new ServiceEtablissements().recupererEtablissements();
        //String url = "https://www.data.gouv.fr/fr/datasets/r/5fb6d2e3-609c-481d-9104-350e9ca134fa";
        String url = "https://data.enseignementsup-recherche.gouv.fr//explore/dataset/fr-esr-principaux-etablissements-enseignement-superieur/download?format=json&amp;timezone=Europe/Berlin&amp;use_labels_for_header=false";

        String urlProxy = "www-cache";
        int port = 3128;
        //HttpClient httpClient = HttpClient.newHttpClient();
        HttpClient httpClient = HttpClient.newBuilder().proxy(ProxySelector.of(new InetSocketAddress(urlProxy, port))).build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            System.out.println("avant");
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("apres");

            int statusCode = response.statusCode();
            System.out.println("Status Code: " + statusCode);

            String contentType = response.headers().firstValue("Content-Type").orElse("");
            System.out.println("Content-Type: " + contentType);

            String responseBody = response.body();
            System.out.println("Response Body:");
            System.out.println(responseBody);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
