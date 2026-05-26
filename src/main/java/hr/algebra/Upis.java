public class Upis {
    private int upisID;
    private int idPolaznik;
    private int idProgramObrazovanja;

    public Upis() {
    }

    public Upis(int idPolaznik, int idProgramObrazovanja) {
        this.idPolaznik = idPolaznik;
        this.idProgramObrazovanja = idProgramObrazovanja;
    }

    public Upis(int upisID, int idPolaznik, int idProgramObrazovanja) {
        this.upisID = upisID;
        this.idPolaznik = idPolaznik;
        this.idProgramObrazovanja = idProgramObrazovanja;
    }

    public int getUpisID() {
        return upisID;
    }

    public void setUpisID(int upisID) {
        this.upisID = upisID;
    }

    public int getIdPolaznik() {
        return idPolaznik;
    }

    public void setIdPolaznik(int idPolaznik) {
        this.idPolaznik = idPolaznik;
    }

    public int getIdProgramObrazovanja() {
        return idProgramObrazovanja;
    }

    public void setIdProgramObrazovanja(int idProgramObrazovanja) {
        this.idProgramObrazovanja = idProgramObrazovanja;
    }

    @Override
    public String toString() {
        return "Upis{" +
                "upisID=" + upisID +
                ", idPolaznik=" + idPolaznik +
                ", idProgramObrazovanja=" + idProgramObrazovanja +
                '}';
    }
}
