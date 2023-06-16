package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.sql.ResultSet;
import java.sql.Statement;

public class ServiceRestaurant implements InterfaceRestaurant {
    // Définition de la méthode distante qui throw RemoteException et ServerNotActiveException
    public String recupererRestaurants() throws RemoteException, ServerNotActiveException {
        StringBuilder res = new StringBuilder("[");
        Statement st = createStatement("SELECT * FROM RESTAURANT");
        ResultSet rs = st.executeStatement();
        while (rs.next()) {
            res.append("{\"id\":"+rs.getInt("id_restaurant")+",");
            res.append("\"nom\":\""+rs.getString("nom")+"\",");
            res.append("\"adresse\":\""+rs.getString("adresse")+"\",");
            res.append("\"latitude\":"+rs.getDouble("latitude")+",");
            res.append("\"longitude\":"+rs.getDouble("longitude")+"},");
        }
        res.append("]");
        return res.toString();
    }

    public void enregistrerReservation() throws RemoteException, ServerNotActiveException {
        // TODO
    }
}
