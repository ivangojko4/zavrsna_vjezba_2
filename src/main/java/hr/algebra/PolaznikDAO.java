import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PolaznikDAO {

    // Unesi novog polaznika
    public static int insertPolaznik(Polaznik polaznik) throws SQLException {
        String sql = "INSERT INTO Polaznik (Ime, Prezime, Naziv) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, polaznik.getIme());
            stmt.setString(2, polaznik.getPrezime());
            stmt.setString(3, polaznik.getNaziv());
            
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

    // Dohvati polaznika po ID
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
                        rs.getString("Prezime"),
                        rs.getString("Naziv")
                    );
                }
            }
        }
        return null;
    }

    // Dohvati sve polaznika
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
                    rs.getString("Prezime"),
                    rs.getString("Naziv")
                ));
            }
        }
        return polaznici;
    }

    // Ažuriraj polaznika
    public static boolean updatePolaznik(Polaznik polaznik) throws SQLException {
        String sql = "UPDATE Polaznik SET Ime = ?, Prezime = ?, Naziv = ? WHERE PolaznikID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, polaznik.getIme());
            stmt.setString(2, polaznik.getPrezime());
            stmt.setString(3, polaznik.getNaziv());
            stmt.setInt(4, polaznik.getPolaznikID());
            
            return stmt.executeUpdate() > 0;
        }
    }

    // Obriši polaznika
    public static boolean deletePolaznik(int id) throws SQLException {
        String sql = "DELETE FROM Polaznik WHERE PolaznikID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}
