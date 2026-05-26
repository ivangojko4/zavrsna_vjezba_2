package hr.algebra;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PolaznikDAO {

    public static int insertPolaznik(Polaznik polaznik) throws SQLException {
        String sql = "{ ? = call sp_insert_polaznik(?, ?) }";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setString(2, polaznik.getIme());
            stmt.setString(3, polaznik.getPrezime());
            stmt.execute();

            return stmt.getInt(1);
        }
    }


    public static Polaznik getPolaznikById(int id) throws SQLException {
        String sql = "SELECT * FROM Polaznik WHERE PolaznikID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Polaznik(
                        rs.getInt("PolaznikID"),
                        rs.getString("Ime"),
                        rs.getString("Prezime")
                    );
                }
            }
        }
        return null;
    }


    public static List<Polaznik> getAllPolaznici() throws SQLException {
        List<Polaznik> polaznici = new ArrayList<>();
        String sql = "SELECT * FROM Polaznik";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                polaznici.add(new Polaznik(
                    rs.getInt("PolaznikID"),
                    rs.getString("Ime"),
                    rs.getString("Prezime")
                ));
            }
        }
        return polaznici;
    }


    public static boolean updatePolaznik(Polaznik polaznik) throws SQLException {
        String sql = "UPDATE Polaznik SET Ime = ?, Prezime = ?, Naziv = ? WHERE PolaznikID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, polaznik.getIme());
            stmt.setString(2, polaznik.getPrezime());
            stmt.setInt(4, polaznik.getPolaznikID());
            
            return stmt.executeUpdate() > 0;
        }
    }


    public static boolean deletePolaznik(int id) throws SQLException {
        String sql = "DELETE FROM Polaznik WHERE PolaznikID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}
