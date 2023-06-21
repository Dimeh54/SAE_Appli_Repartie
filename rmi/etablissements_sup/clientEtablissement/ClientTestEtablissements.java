package clientEtablissement;

import java.io.FileNotFoundException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ServerNotActiveException;


public class ClientTestEtablissements {
    public static void main(String[] args) throws RemoteException, NotBoundException, ServerNotActiveException {
        try {
            // On récupère l'adresse et le port
            String adresse = "127.0.0.1";
            if (args.length > 0) adresse = args[0];

            int port = 1099;
            if(args.length > 1) port = Integer.parseInt(args[1]);
            // On récupère le registre distant
            Registry reg = LocateRegistry.getRegistry(adresse, port);
            // On récupère le service distant
            InterfaceEtablissements se = (InterfaceEtablissements) reg.lookup("etablissements");
            // On appelle la méthode distante
            ReponseEtablissement res = se.recupererEtablissements();
            // On affiche le résultat
            System.out.println(res);
        // On gère les exceptions
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            //System.out.println("Une IP ou un hôte doit être spécifié en argument");
        } catch (NotBoundException e) {
            System.out.println("Le service distant appelé est introuvable");
        } catch (UnknownHostException e) {
            System.out.println("Serveur inexistant ou introuvable");
        } catch (ConnectException e) {
            System.out.println("Impossible de se connecter à l’annuaire rmiregistry distant");
        } catch (RemoteException e) {
            e.printStackTrace();
            //System.out.println(e.getMessage());
            System.out.println("Impossible de se connecter au serveur distant");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}