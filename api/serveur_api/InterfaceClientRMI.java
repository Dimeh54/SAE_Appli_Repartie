import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceClientRMI extends Remote {
    public abstract void enregistrerService(Remote service, String nomService) throws RemoteException;
}
