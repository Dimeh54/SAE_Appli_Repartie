import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public class Service implements ServiceDistant {
    // Définition de la méthode distante qui throw RemoteException et ServerNotActiveException
    public void methodeDistante() throws RemoteException, ServerNotActiveException {
        // comportement de la méthode distante
        // TODO
        // Affichage de l'adresse IP du client
        System.out.println("Utilisation du service par : " + java.rmi.server.RemoteServer.getClientHost());
    }
}
