import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public class ServiceRestaurant implements InterfaceRestaurant {
    // Définition de la méthode distante qui throw RemoteException et ServerNotActiveException
    public String recupererRestaurants() throws RemoteException, ServerNotActiveException {
        
        return "";
    }
}
