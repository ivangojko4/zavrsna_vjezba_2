import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UpisDAO {

    // Unesi novi upis
    public static int insertUpis(Upis upis) throws SQLException {
        String sql = "INSERT INTO Upis (IDPolaznik, IDProgramObrazovanja) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, upis.getIdPolaznik());
            stmt.setInt(2, upis.getIdProgramObrazovanja());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        }
        return -1;
    }

    // Dohvati upis po ID
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

    // Dohvati sve upise
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

    // Dohvati sve upise za određenog polaznika
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

    // Ažuriraj upis
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

    // Obriši upis
    public static boolean deleteUpis(int id) throws SQLException {
        String sql = "DELETE FROM Upis WHERE UpisID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}
