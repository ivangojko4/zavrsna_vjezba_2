package hr.algebra;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            System.out.println("=== Sustav za evidenciju polaznika (Hibernate) ===");

            while (true) {
                prikaziMeni();
                int izbor = unosBroja();

                switch (izbor) {
                    case 1 -> unesiNovogPolaznika();
                    case 2 -> unesiNoviProgram();
                    case 3 -> upisiPolaznika();
                    case 4 -> prikaziSvePolaznika();
                    case 5 -> prikaziSvePrograme();
                    case 6 -> prikaziSveUpise();
                    case 7 -> prebaciPolaznika();
                    case 8 -> prikaziPolaznikaIPrograme();
                    case 9 -> {
                        System.out.println("Izlaz...");
                        HibernateUtil.shutdown();
                        return;
                    }
                    default -> System.out.println("Neispravan izbor!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void prikaziMeni() {
        System.out.println("\n=== MENI ===");
        System.out.println("1. Unesi novog polaznika");
        System.out.println("2. Unesi novi program obrazovanja");
        System.out.println("3. Upisi polaznika na program");
        System.out.println("4. Prikazi sve polaznike");
        System.out.println("5. Prikazi sve programe");
        System.out.println("6. Prikazi sve upise");
        System.out.println("7. Prebaci polaznika na drugi program");
        System.out.println("8. Prikazi polaznika i njegove programe");
        System.out.println("9. Izlaz");
        System.out.print("Odaberi: ");
    }

    private static void unesiNovogPolaznika() {
        System.out.print("Ime: ");
        String ime = scanner.nextLine();
        System.out.print("Prezime: ");
        String prezime = scanner.nextLine();

        int id = PolaznikDAO.insertPolaznik(new Polaznik(ime, prezime));
        System.out.println(id > 0 ? "Unesen, ID: " + id : "Greska!");
    }

    private static void unesiNoviProgram() {
        System.out.print("Naziv programa: ");
        String naziv = scanner.nextLine();
        System.out.print("CSVET bodovi: ");
        int csvet = unosBroja();

        int id = ProgramObrazovanjaDAO.insertProgramObrazovanja(new ProgramObrazovanja(naziv, csvet));
        System.out.println(id > 0 ? "Unesen, ID: " + id : "Greska!");
    }

    private static void upisiPolaznika() {
        System.out.print("ID polaznika: ");
        int idP = unosBroja();
        System.out.print("ID programa: ");
        int idProg = unosBroja();

        Polaznik polaznik = PolaznikDAO.getPolaznikById(idP);
        ProgramObrazovanja program = ProgramObrazovanjaDAO.getProgramById(idProg);

        if (polaznik == null) { System.out.println("Polaznik ne postoji!"); return; }
        if (program == null) { System.out.println("Program ne postoji!"); return; }

        int id = UpisDAO.insertUpis(new Upis(polaznik, program));
        System.out.println(id > 0
                ? polaznik.getIme() + " " + polaznik.getPrezime() + " upisan na " + program.getNaziv()
                : "Greska!");
    }

    private static void prikaziSvePolaznika() {
        List<Polaznik> lista = PolaznikDAO.getAllPolaznici();
        if (lista.isEmpty()) { System.out.println("Nema polaznika."); return; }
        lista.forEach(System.out::println);
    }

    private static void prikaziSvePrograme() {
        List<ProgramObrazovanja> lista = ProgramObrazovanjaDAO.getAllProgrami();
        if (lista.isEmpty()) { System.out.println("Nema programa."); return; }
        lista.forEach(System.out::println);
    }

    private static void prikaziSveUpise() {
        List<Upis> lista = UpisDAO.getAllUpisi();
        if (lista.isEmpty()) { System.out.println("Nema upisa."); return; }
        for (Upis u : lista) {
            System.out.println("Upis " + u.getUpisID() + ": " +
                    u.getPolaznik().getIme() + " " + u.getPolaznik().getPrezime() +
                    " -> " + u.getProgramObrazovanja().getNaziv());
        }
    }

    private static void prebaciPolaznika() {
        System.out.print("ID upisa: ");
        int upisID = unosBroja();
        System.out.print("ID novog programa: ");
        int noviProgramID = unosBroja();

        boolean ok = UpisDAO.prebaciPolaznika(upisID, noviProgramID);
        System.out.println(ok ? "Prebacen!" : "Greska pri prebacivanju!");
    }

    private static void prikaziPolaznikaIPrograme() {
        System.out.print("ID polaznika: ");
        int id = unosBroja();

        Polaznik polaznik = PolaznikDAO.getPolaznikById(id);
        if (polaznik == null) { System.out.println("Polaznik ne postoji!"); return; }

        System.out.println(polaznik);
        List<Upis> upisi = UpisDAO.getUpisiByPolaznik(id);
        if (upisi.isEmpty()) { System.out.println("Nije upisan ni na jedan program."); return; }

        for (Upis u : upisi) {
            ProgramObrazovanja p = u.getProgramObrazovanja();
            System.out.println("  - " + p.getNaziv() + " (" + p.getCsvet() + " CSVET)");
        }
    }

    private static int unosBroja() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.print("Unesi broj: ");
            return unosBroja();
        }
    }
}