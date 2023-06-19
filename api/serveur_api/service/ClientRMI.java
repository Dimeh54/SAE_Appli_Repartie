package service;

import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ServerNotActiveException;

public class ClientRMI {
    public String appelRMI(String methode, String[] params) {
        String response = "{\n\tsuccess: false,\n\tmessage: \"Une erreur est survenue\"\n}";

        try {
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 1099);

            InterfaceRestaurant ir = (InterfaceRestaurant) reg.lookup("serviceRestaurant");
            // On récupère le service distant
            switch (methode) {
                case "recupererRestaurants":
                    response = ir.recupererRestaurants();
                    break;
                case "recupererRestaurant":
                    response = ir.recupererRestaurant(String.valueOf(params[0]));
                    break;

            }


            // On gère les exceptions
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Une IP ou un hôte doit être spécifié en argument");
        } catch (NotBoundException e) {
            System.out.println("Le service distant appelé est introuvable");
        } catch (UnknownHostException e) {
            System.out.println("Serveur inexistant ou introuvable");
        } catch (ConnectException e) {
            System.out.println("Impossible de se connecter à l’annuaire rmiregistry distant");
        } catch (RemoteException e) {
            e.printStackTrace();
            System.out.println("Impossible de se connecter au serveur distant");
        } catch (ServerNotActiveException e) {
            throw new RuntimeException(e);
        }

        return response;
    }
}
