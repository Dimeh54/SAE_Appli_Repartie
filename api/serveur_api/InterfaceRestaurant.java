import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

// Définition de l'interface du service distant
public interface InterfaceRestaurant extends Remote{
   // Définition de la méthode distante qui throw RemoteException et ServerNotActiveException
   public String recupererRestaurants() throws RemoteException, ServerNotActiveException;
   public String recupererRestaurant(String nom) throws RemoteException, ServerNotActiveException;

   public String enregistrerReservation(String nom, String prenom, int nbpers, String numtel, String date, int id_restaurant) throws RemoteException, ServerNotActiveException;
}