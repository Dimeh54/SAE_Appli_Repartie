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
        //String url = "https://data.enseignementsup-recherche.gouv.fr//explore/dataset/fr-esr-principaux-etablissements-enseignement-superieur/download?format=json&amp;timezone=Europe/Berlin&amp;use_labels_for_header=false";
        //String url = "https://data.enseignementsup-recherche.gouv.fr/api/records/1.0/search/?dataset=fr-esr-principaux-etablissements-enseignement-superieur&q=&facet=etablissement_id_paysage&facet=type_d_etablissement&facet=typologie_d_universites_et_assimiles&facet=secteur_d_etablissement&facet=vague_contractuelle&facet=localisation&facet=uai&facet=siret&facet=siren&facet=identifiant_ror&facet=identifiant_grid&facet=identifiant_pic&facet=identifiant_dataesr&facet=anciens_codes_uai&facet=com_nom&facet=dep_nom&facet=aca_nom&facet=reg_nom&facet=pays_etranger_acheminement&facet=statut_juridique_court&facet=qualification_long&facet=uai_rgp_loi_esr_2013&facet=universites_fusionnees&facet=etablissement_experimental&facet=statut_operateur_lolf&facet=identifiant_programme_lolf_chef_de_file&refine.com_nom=Nancy";
        String url = "https://data.enseignementsup-recherche.gouv.fr/api/records/1.0/search/?dataset=fr-esr-implantations_etablissements_d_enseignement_superieur_publics&q=&facet=localisation&facet=siege_lib&facet=type_d_etablissement&facet=bcnag_n_nature_uai_libelle_editi&facet=services&facet=bcnag_n_type_uai_libelle_edition&facet=uai&facet=ur&facet=etablissement_uai&facet=identifiant_eter&facet=identifiant_wikidata&facet=com_nom&facet=uucr_nom&facet=dep_nom&facet=aca_nom&facet=reg_nom&facet=type_uai&facet=nature_uai&refine.uucr_nom=Nancy";

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
