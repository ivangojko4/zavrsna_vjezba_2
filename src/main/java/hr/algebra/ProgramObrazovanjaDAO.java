import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProgramObrazovanjaDAO {

    // Unesi novi program obrazovanja
    public static int insertProgramObrazovanja(ProgramObrazovanja program) throws SQLException {
        String sql = "INSERT INTO ProgramObrazovanja (Naziv, CSVET) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, program.getNaziv());
            stmt.setInt(2, program.getCsvet());
            
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

    // Dohvati program po ID
    public static ProgramObrazovanja getProgramById(int id) throws SQLException {
        String sql = "SELECT * FROM ProgramObrazovanja WHERE ProgramObrazovanjaID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new ProgramObrazovanja(
                        rs.getInt("ProgramObrazovanjaID"),
                        rs.getString("Naziv"),
                        rs.getInt("CSVET")
                    );
                }
            }
        }
        return null;
    }

    // Dohvati sve programe
    public static List<ProgramObrazovanja> getAllProgrami() throws SQLException {
        List<ProgramObrazovanja> programi = new ArrayList<>();
        String sql = "SELECT * FROM ProgramObrazovanja";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                programi.add(new ProgramObrazovanja(
                    rs.getInt("ProgramObrazovanjaID"),
                    rs.getString("Naziv"),
                    rs.getInt("CSVET")
                ));
            }
        }
        return programi;
    }

    // Ažuriraj program
    public static boolean updateProgram(ProgramObrazovanja program) throws SQLException {
        String sql = "UPDATE ProgramObrazovanja SET Naziv = ?, CSVET = ? WHERE ProgramObrazovanjaID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, program.getNaziv());
            stmt.setInt(2, program.getCsvet());
            stmt.setInt(3, program.getProgramObrazovanjaID());
            
            return stmt.executeUpdate() > 0;
        }
    }

    // Obriši program
    public static boolean deleteProgram(int id) throws SQLException {
        String sql = "DELETE FROM ProgramObrazovanja WHERE ProgramObrazovanjaID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}
