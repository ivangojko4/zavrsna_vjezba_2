package hr.algebra;

import jakarta.persistence.*;

@Entity
@Table(name = "upis")
public class Upis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "upisid")
    private int upisID;

    @ManyToOne
    @JoinColumn(name = "idpolaznik", nullable = false)
    private Polaznik polaznik;

    @ManyToOne
    @JoinColumn(name = "idprogramobrazovanja", nullable = false)
    private ProgramObrazovanja programObrazovanja;

    public Upis() {}

    public Upis(Polaznik polaznik, ProgramObrazovanja programObrazovanja) {
        this.polaznik = polaznik;
        this.programObrazovanja = programObrazovanja;
    }

    public int getUpisID() { return upisID; }
    public void setUpisID(int upisID) { this.upisID = upisID; }
    public Polaznik getPolaznik() { return polaznik; }
    public void setPolaznik(Polaznik polaznik) { this.polaznik = polaznik; }
    public ProgramObrazovanja getProgramObrazovanja() { return programObrazovanja; }
    public void setProgramObrazovanja(ProgramObrazovanja p) { this.programObrazovanja = p; }
}