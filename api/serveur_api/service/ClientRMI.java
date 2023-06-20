package service;

import java.io.FileNotFoundException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ServerNotActiveException;

public class ClientRMI {
    public Object appelRMI(String methode, String[] params) {
        Object response = "{\n\tsuccess: false,\n\tmessage: \"Une erreur est survenue\"\n}";

        try {

            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 1099);
            InterfaceRestaurant ir = null;
            InterfaceEtablissements ie = null;
            try {
                ir = (InterfaceRestaurant) reg.lookup("serviceRestaurant");
            } catch (NotBoundException e) {
                System.out.println("Service restaurant introuvable");
            }

            try {
                ie = (InterfaceEtablissements) reg.lookup("etablissements");
            } catch (NotBoundException e) {
                System.out.println("Service etablissements introuvable");
            }
            // On récupère le service distant
            switch (methode) {
                case "recupererRestaurants":
                    response = ir.recupererRestaurants();
                    break;
                case "recupererRestaurant":
                    response = ir.recupererRestaurant(String.valueOf(params[0]));
                    break;
                case "enregistrerReservation":
                    response = ir.enregistrerReservation(params[0], params[1], Integer.parseInt(params[2]), params[3], params[4], Integer.parseInt(params[5]));
                    break;
                case "recupererEtablissements":
                    response = ie.recupererEtablissements();
                    break;
            }


            // On gère les exceptions
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Une IP ou un hôte doit être spécifié en argument");
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("Le service distant appelé est introuvable");
        } catch (UnknownHostException e) {
            System.out.println("Serveur inexistant ou introuvable");
        } catch (ConnectException e) {
            System.out.println("Impossible de se connecter à l’annuaire rmiregistry distant");
        } catch (RemoteException e) {
            System.out.println("Impossible de se connecter au serveur distant");
        } catch (ServerNotActiveException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return response;
    }
}
