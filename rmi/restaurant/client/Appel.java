package client;

import service.InterfaceRestaurant;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.server.ServerNotActiveException;


public class Appel {
    public static void main(String[] args) throws ServerNotActiveException {
        try {
            Registry reg = null;

            try {
                if (args.length != 2) {
                    System.out.println("utilisation : java Appel <adresse annuaire> <port annuaire>");
                    System.out.println("utilisation des valeurs par défaut : ");
                    System.out.println("\t- adresse : 127.0.0.1");
                    System.out.println("\t- port : 1099");
                    System.out.println();
                    reg = LocateRegistry.getRegistry("127.0.0.1", 1099);
                } else {
                    reg = LocateRegistry.getRegistry(args[0], Integer.parseInt(args[1]));
                }
            } catch (RemoteException e) {
                System.out.println("connexion au serveur impossible");
                System.exit(1);
            } catch (NumberFormatException e) {
                System.out.println("le port doit être un entier");
                System.exit(1);
            }

            // On récupère le service distant
            InterfaceRestaurant ir = (InterfaceRestaurant) reg.lookup("serviceRestaurant");
            
            // On appelle la méthode distante
//            System.out.println(ir.recupererRestaurants());
            System.out.println(ir.enregistrerReservation("Jean", "Dupont", 2, "0666666666", "2023-02-26 19:30",3));
//            System.out.println(ir.recupererRestaurant("Les Ptits oignons"));

            
        // On gère les exceptions
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Une IP ou un hôte doit être spécifié en argument");
        } catch (NotBoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.out.println("Le service distant appelé est introuvable");
        } catch (UnknownHostException e) {
            System.out.println("Serveur inexistant ou introuvable");
        } catch (ConnectException e) {
            System.out.println("Impossible de se connecter à l’annuaire rmiregistry distant");
        } catch (RemoteException e) {
            System.out.println("Impossible de se connecter au serveur distant");
        }
    }
}