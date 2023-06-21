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

    @Override
    public String recupererRestaurants() throws RemoteException, ServerNotActiveException {
        StringBuilder res;
        try {
            Statement stmt = connect.createStatement();
            stmt.executeQuery("SELECT * FROM S402_restaurants");
            ResultSet rs = stmt.getResultSet();

            res = new StringBuilder("{\n");
            res.append("\t\"restaurants\": [\n");
            while (rs.next()) {
                res.append("\t\t{\n");
                res.append("\t\t\t\"id\": "+rs.getInt("id_restaurant")+",\n");
                res.append("\t\t\t\"nom\": \""+rs.getString("nom")+"\",\n");
                res.append("\t\t\t\"adresse\": \""+rs.getString("adresse")+"\",\n");
                res.append("\t\t\t\"latitude\": \""+rs.getString("latitude")+"\",\n");
                res.append("\t\t\t\"longitude\": \""+rs.getString("longitude")+"\"\n");
                res.append("\t\t},\n");
            }
            res.deleteCharAt(res.length()-2);
            res.append("\t]\n");
            res.append("}");
        } catch (SQLException e) {
            e.printStackTrace();
            res = new StringBuilder("{");
            res.append("\t\"success\": \"false\"\n");
            res.append("\t\"error\": \""+e.getMessage()+"\"");
            res.append("}");
        }
        return res.toString();
    }

    @Override
    public String recupererRestaurant(String nom) throws RemoteException, ServerNotActiveException {
        StringBuilder res;
        try {
            String SQLPrep = "SELECT * FROM S402_restaurants WHERE LOWER(nom) like LOWER(?);";
            PreparedStatement prep = connect.prepareStatement(SQLPrep);
            prep.setString(1, '%'+nom+'%');
            prep.execute();
            ResultSet rs = prep.getResultSet();

            res = new StringBuilder("{\n");
            if (rs.next()) {
                res.append("\t\"success\": \"true\",\n");
                res.append("\t\"restaurant\": {\n");
                res.append("\t\t\"id\": "+rs.getInt("id_restaurant")+",\n");
                res.append("\t\t\"nom\": \""+rs.getString("nom")+"\",\n");
                res.append("\t\t\"adresse\": \""+rs.getString("adresse")+"\",\n");
                res.append("\t\t\"latitude\": "+rs.getString("latitude")+",\n");
                res.append("\t\t\"longitude\": "+rs.getString("longitude")+"\n");
                res.append("\t}\n");
            } else {
                res.append("\t\"success\": \"false\",\n");
                res.append("\t\"error\": \"Aucun restaurant trouvé avec le nom : \'"+nom+"\'\"\n");
            }
            res.append("}");
        } catch (SQLException e) {
            e.printStackTrace();
            res = new StringBuilder("{");
            res.append("\t\"success\": \"false\"\n");
            res.append("\t\"error\": \""+e.getMessage()+"\"");
            res.append("}");
        }
        return res.toString();
    }

    @Override
    public String enregistrerReservation(String nom, String prenom, int nbpers, String numtel, String date, int id_restaurant) throws RemoteException {
        StringBuilder res;
        try {
            String SQLPrep = "INSERT INTO S402_reservations (nom, prenom, nb_pers, num_tel, date, id_restaurant) VALUES (?, ?, ?, ?, STR_TO_DATE(?, '%Y-%m-%d %H:%i'), ?);";
            PreparedStatement prep = connect.prepareStatement(SQLPrep);
            prep.setString(1, nom);
            prep.setString(2, prenom);
            prep.setInt(3, nbpers);
            prep.setString(4, numtel);
            prep.setString(5, date);
            prep.setInt(6, id_restaurant);
            prep.execute();
            res = new StringBuilder("{\n");
            res.append("\t\"success\": \"true\"\n");
            res.append("}");
        } catch (SQLException e) {
            e.printStackTrace();
            res = new StringBuilder("{\n");
            res.append("\t\"success\": \"false\"\n");
            res.append("\t\"error\": \""+e.getMessage()+"\"\n");
            res.append("}");
        }
        return res.toString();
    }
}
