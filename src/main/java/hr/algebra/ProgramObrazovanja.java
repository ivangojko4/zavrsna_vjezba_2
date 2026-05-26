package hr.algebra;

public class ProgramObrazovanja {
    private int programObrazovanjaID;
    private String naziv;
    private int csvet;

    public ProgramObrazovanja() {
    }

    public ProgramObrazovanja(String naziv, int csvet) {
        this.naziv = naziv;
        this.csvet = csvet;
    }

    public ProgramObrazovanja(int programObrazovanjaID, String naziv, int csvet) {
        this.programObrazovanjaID = programObrazovanjaID;
        this.naziv = naziv;
        this.csvet = csvet;
    }

    public int getProgramObrazovanjaID() {
        return programObrazovanjaID;
    }

    public void setProgramObrazovanjaID(int programObrazovanjaID) {
        this.programObrazovanjaID = programObrazovanjaID;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getCsvet() {
        return csvet;
    }

    public void setCsvet(int csvet) {
        this.csvet = csvet;
    }

    @Override
    public String toString() {
        return "ProgramObrazovanja{" +
                "programObrazovanjaID=" + programObrazovanjaID +
                ", naziv='" + naziv + '\'' +
                ", csvet=" + csvet +
                '}';
    }
}
