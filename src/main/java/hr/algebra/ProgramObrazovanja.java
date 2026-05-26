package hr.algebra;

import jakarta.persistence.*;

@Entity
@Table(name = "programobrazovanja")
public class ProgramObrazovanja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "programobrazovanjaid")
    private int programObrazovanjaID;

    @Column(name = "naziv", nullable = false)
    private String naziv;

    @Column(name = "csvet", nullable = false)
    private int csvet;

    public ProgramObrazovanja() {}

    public ProgramObrazovanja(String naziv, int csvet) {
        this.naziv = naziv;
        this.csvet = csvet;
    }

    public int getProgramObrazovanjaID() { return programObrazovanjaID; }
    public void setProgramObrazovanjaID(int id) { this.programObrazovanjaID = id; }
    public String getNaziv() { return naziv; }
    public void setNaziv(String naziv) { this.naziv = naziv; }
    public int getCsvet() { return csvet; }
    public void setCsvet(int csvet) { this.csvet = csvet; }

    @Override
    public String toString() {
        return "ProgramObrazovanja{programObrazovanjaID=" + programObrazovanjaID +
                ", naziv='" + naziv + "', csvet=" + csvet + "}";
    }
}