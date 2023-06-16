package service;

import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.AccessException;
import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class LancerService {
    public static void main(String[] args) throws AccessException, RemoteException {
        try {
            // On récupère le port spécifié en argument ou 1099 par défaut
            int port = 1099;
            if (args.length > 0) {
                port = Integer.parseInt(args[0]);
            }
            // On crée une instance du service
            ServiceRestaurant serv = new ServiceRestaurant();
            // On exporte l'objet
            InterfaceRestaurant rd = (InterfaceRestaurant) UnicastRemoteObject.exportObject(serv, 0);
            // On récupère l'annuaire local rmiregistry 
            Registry reg = LocateRegistry.getRegistry(port);
            // On enregistre le service dans l'annuaire
            reg.rebind("serviceRestaurant", rd);

        // On gère les exceptions
        } catch (NumberFormatException e) {
            System.out.println("Le port spécifié n'est pas un entier");
        } catch (ExportException e){
            System.out.println("Le port pour l’export de l’objet est déjà utilisé");
        } catch (ConnectException e) {
            System.out.println("L’annuaire rmiregistry est introuvable");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }    
}