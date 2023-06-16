package service;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.sql.*;
import java.util.Properties;

public class ServiceRestaurant implements InterfaceRestaurant {
    private Connection connect;

    public ServiceRestaurant() throws RemoteException {
        Properties connectionProps = new Properties();
        connectionProps.put("user", db_config.username);
        connectionProps.put("password", db_config.password);
        String urlDB = "jdbc:mysql://" + db_config.host + ":";
        urlDB += "3306/" + db_config.dbname;
        try {
            this.connect = DriverManager.getConnection(urlDB, connectionProps);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    // Définition de la méthode distante qui throw RemoteException et ServerNotActiveException
    public String recupererRestaurants() throws RemoteException, ServerNotActiveException {
        StringBuilder res = null;
        try {
            Statement stmt = connect.createStatement();
            stmt.executeQuery("SELECT * FROM S402_restaurants");
            ResultSet rs = stmt.getResultSet();

            res = new StringBuilder("{\n");
            res.append("\t\"restaurants\":[\n");
            while (rs.next()) {
                res.append("\t\t{\n");
                res.append("\t\t\t\"id\":"+rs.getInt("id_restaurant")+",\n");
                res.append("\t\t\t\"nom\":\""+rs.getString("nom")+"\",\n");
                res.append("\t\t\t\"adresse\":\""+rs.getString("adresse")+"\",\n");
                res.append("\t\t\t\"latitude\":"+rs.getString("latitude")+",\n");
                res.append("\t\t\t\"longitude\":"+rs.getString("longitude")+"\n");
                res.append("\t\t},\n");
            }
            res.deleteCharAt(res.length()-2);
            res.append("\t]\n");
            res.append("}\n");
        } catch (SQLException e) {
            e.printStackTrace();
            res = new StringBuilder("{");
            res.append("\"error\":\""+e.getMessage()+"\"");
            res.append("}");
        }
        return res.toString();
    }

    public void enregistrerReservation() throws RemoteException, ServerNotActiveException {
        // TODO
    }
}
