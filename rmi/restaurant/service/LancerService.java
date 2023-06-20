package service;

import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.AccessException;
import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class LancerService {
    private static final boolean DEBUG = true;
    public static void main(String[] args) {
        try {
            // On récupère le port spécifié en argument ou 1099 par défaut
            int port = 1099;
            if (args.length > 0) {
                port = Integer.parseInt(args[0]);
            }
            // On crée une instance du service
            ServiceRestaurant serv = new ServiceRestaurant();
            if (DEBUG){
                System.out.println("service lancé");
            }
            // On exporte l'objet
            InterfaceRestaurant rd = (InterfaceRestaurant) UnicastRemoteObject.exportObject(serv, 0);
            if (DEBUG){
                System.out.println("objet exporté");
            }
            // On récupère l'annuaire local rmiregistry
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", port);
            if (DEBUG){
                System.out.println("annuaire local récupéré");
            }
            // On enregistre le service dans l'annuaire
            reg.rebind("serviceRestaurant", rd);
            if (DEBUG){
                System.out.println("service enregistré");
            }

        // On gère les exceptions
        } catch (NumberFormatException e) {
            System.out.println("Le port spécifié n'est pas un entier");
        } catch (ExportException e){
            System.out.println("Le port pour l’export de l’objet est déjà utilisé");
        } catch (ConnectException e) {
            System.out.println("L’annuaire rmiregistry est introuvable");
            //e.printStackTrace();
        } catch (AccessException e) {
            System.out.println("erreur : accès interdit");
            System.exit(1);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }    
}