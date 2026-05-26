package hr.algebra;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProgramObrazovanjaDAO {

    public static int insertProgramObrazovanja(ProgramObrazovanja program) throws SQLException {
        String sql = "{ ? = call sp_insert_program(?, ?) }";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setString(2, program.getNaziv());
            stmt.setInt(3, program.getCsvet());
            stmt.execute();

            return stmt.getInt(1);
        }
    }


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


    public static boolean deleteProgram(int id) throws SQLException {
        String sql = "DELETE FROM ProgramObrazovanja WHERE ProgramObrazovanjaID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}
