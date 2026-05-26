package hr.algebra;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UpisDAO {

    public static int insertUpis(Upis upis) throws SQLException {
        String sql = "{ ? = call sp_insert_upis(?, ?) }";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setInt(2, upis.getIdPolaznik());
            stmt.setInt(3, upis.getIdProgramObrazovanja());
            stmt.execute();

            return stmt.getInt(1);
        }
    }


    public static Upis getUpisById(int id) throws SQLException {
        String sql = "SELECT * FROM Upis WHERE UpisID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Upis(
                        rs.getInt("UpisID"),
                        rs.getInt("IDPolaznik"),
                        rs.getInt("IDProgramObrazovanja")
                    );
                }
            }
        }
        return null;
    }


    public static List<Upis> getAllUpisi() throws SQLException {
        List<Upis> upisi = new ArrayList<>();
        String sql = "SELECT * FROM Upis";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                upisi.add(new Upis(
                    rs.getInt("UpisID"),
                    rs.getInt("IDPolaznik"),
                    rs.getInt("IDProgramObrazovanja")
                ));
            }
        }
        return upisi;
    }


    public static List<Upis> getUpisyByPolaznik(int polaznikID) throws SQLException {
        List<Upis> upisi = new ArrayList<>();
        String sql = "SELECT * FROM Upis WHERE IDPolaznik = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, polaznikID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    upisi.add(new Upis(
                        rs.getInt("UpisID"),
                        rs.getInt("IDPolaznik"),
                        rs.getInt("IDProgramObrazovanja")
                    ));
                }
            }
        }
        return upisi;
    }


    public static boolean updateUpis(Upis upis) throws SQLException {
        String sql = "UPDATE Upis SET IDPolaznik = ?, IDProgramObrazovanja = ? WHERE UpisID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, upis.getIdPolaznik());
            stmt.setInt(2, upis.getIdProgramObrazovanja());
            stmt.setInt(3, upis.getUpisID());
            
            return stmt.executeUpdate() > 0;
        }
    }


    public static boolean deleteUpis(int id) throws SQLException {
        String sql = "DELETE FROM Upis WHERE UpisID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public static boolean prebaciPolaznika(int upisID, int noviProgramID) throws SQLException {
        String sql = "{ call sp_prebaci_polaznika(?, ?) }";

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            try (CallableStatement stmt = conn.prepareCall(sql)) {
                stmt.setInt(1, upisID);
                stmt.setInt(2, noviProgramID);
                stmt.execute();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
}
