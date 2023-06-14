   import java.rmi.Remote;
   import java.rmi.RemoteException;
   import java.rmi.server.ServerNotActiveException;

   // Définition de l'interface du service distant
   public interface ServiceDistant extends Remote{
      // Définition de la méthode distante qui throw RemoteException et ServerNotActiveException
      public void methodeDistante() throws RemoteException, ServerNotActiveException;
   }