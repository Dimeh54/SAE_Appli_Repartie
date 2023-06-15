import java.io.FileNotFoundException;
import java.io.IOException;
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
        String url = "https://www.data.gouv.fr/fr/datasets/r/5fb6d2e3-609c-481d-9104-350e9ca134fa";

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

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
