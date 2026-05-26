package hr.algebra;

import jakarta.persistence.*;

@Entity
@Table(name = "polaznik")
public class Polaznik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "polaznikid")
    private int polaznikID;

    @Column(name = "ime", nullable = false)
    private String ime;

    @Column(name = "prezime", nullable = false)
    private String prezime;

    public Polaznik() {}

    public Polaznik(String ime, String prezime) {
        this.ime = ime;
        this.prezime = prezime;
    }

    public int getPolaznikID() { return polaznikID; }
    public void setPolaznikID(int polaznikID) { this.polaznikID = polaznikID; }
    public String getIme() { return ime; }
    public void setIme(String ime) { this.ime = ime; }
    public String getPrezime() { return prezime; }
    public void setPrezime(String prezime) { this.prezime = prezime; }

    @Override
    public String toString() {
        return "Polaznik{polaznikID=" + polaznikID +
                ", ime='" + ime + "', prezime='" + prezime + "'}";
    }
}