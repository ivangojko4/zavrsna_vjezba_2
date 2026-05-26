public class Polaznik {
    private int polaznikID;
    private String ime;
    private String prezime;
    private String naziv;

    public Polaznik() {
    }

    public Polaznik(String ime, String prezime, String naziv) {
        this.ime = ime;
        this.prezime = prezime;
        this.naziv = naziv;
    }

    public Polaznik(int polaznikID, String ime, String prezime, String naziv) {
        this.polaznikID = polaznikID;
        this.ime = ime;
        this.prezime = prezime;
        this.naziv = naziv;
    }

    public int getPolaznikID() {
        return polaznikID;
    }

    public void setPolaznikID(int polaznikID) {
        this.polaznikID = polaznikID;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @Override
    public String toString() {
        return "Polaznik{" +
                "polaznikID=" + polaznikID +
                ", ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                ", naziv='" + naziv + '\'' +
                '}';
    }
}
