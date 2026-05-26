package hr.algebra;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            System.out.println("=== Sustav za evidenciju polaznika ===\n");
            
            while (true) {
                prikaziMeni();
                int izbor = unosBroja();
                
                switch (izbor) {
                    case 1:
                        unesiNovogPolaznika();
                        break;
                    case 2:
                        unesiNoviProgramObrazovanja();
                        break;
                    case 3:
                        unesiNoviUpis();
                        break;
                    case 4:
                        prikaziSvePolaznika();
                        break;
                    case 5:
                        prikaziSveProgrameObrazovanja();
                        break;
                    case 6:
                        prikaziSveUpise();
                        break;
                    case 7:
                        prebaciPolaznikaIzJednoguUDrugiProgram();
                        break;
                    case 8:
                        prikaziPolaznikaISnjegovePrograme();
                        break;
                    case 9:
                        System.out.println("Izlaz iz programa...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Neispravan izbor!");
                }
            }
        } catch (Exception e) {
            System.err.println("Greska: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void prikaziMeni() {
        System.out.println("\n=== MENI ===");
        System.out.println("1. Unesi novog polaznika");
        System.out.println("2. Unesi novi program obrazovanja");
        System.out.println("3. Upisi polaznika na program obrazovanja");
        System.out.println("4. Prikazi sve polaznika");
        System.out.println("5. Prikazi sve programe obrazovanja");
        System.out.println("6. Prikazi sve upise");
        System.out.println("7. Prebaci polaznika iz jednog u drugi program");
        System.out.println("8. Prikazi polaznika i njegove programe");
        System.out.println("9. Izlaz");
        System.out.print("Odaberi opciju: ");
    }

    
    private static void unesiNovogPolaznika() throws SQLException {
        System.out.println("\n=== UNOS NOVOG POLAZNIKA ===");
        System.out.print("Unesi ime: ");
        String ime = scanner.nextLine();
        System.out.print("Unesi prezime: ");
        String prezime = scanner.nextLine();
        
        Polaznik polaznik = new Polaznik(ime, prezime);
        int noviID = PolaznikDAO.insertPolaznik(polaznik);
        
        if (noviID > 0) {
            System.out.println("Polaznik uspjesno unesen! ID: " + noviID);
        } else {
            System.out.println("Greska pri unosu polaznika!");
        }
    }

    
    private static void unesiNoviProgramObrazovanja() throws SQLException {
        System.out.println("\n=== UNOS NOVOG PROGRAMA OBRAZOVANJA ===");
        System.out.print("Unesi naziv programa: ");
        String naziv = scanner.nextLine();
        System.out.print("Unesi CSVET bodove: ");
        int csvet = unosBroja();
        
        ProgramObrazovanja program = new ProgramObrazovanja(naziv, csvet);
        int noviID = ProgramObrazovanjaDAO.insertProgramObrazovanja(program);
        
        if (noviID > 0) {
            System.out.println("✓ Program obrazovanja uspjesno unesen! ID: " + noviID);
        } else {
            System.out.println("✗ Greska pri unosu programa!");
        }
    }

    
    private static void unesiNoviUpis() throws SQLException {
        System.out.println("\n=== UPIS POLAZNIKA NA PROGRAM ===");
        System.out.print("Unesi ID polaznika: ");
        int idPolaznik = unosBroja();
        System.out.print("Unesi ID programa obrazovanja: ");
        int idProgram = unosBroja();
        
        Polaznik polaznik = PolaznikDAO.getPolaznikById(idPolaznik);
        ProgramObrazovanja program = ProgramObrazovanjaDAO.getProgramById(idProgram);
        
        if (polaznik == null) {
            System.out.println("✗ Polaznik sa tim ID ne postoji!");
            return;
        }
        if (program == null) {
            System.out.println("✗ Program sa tim ID ne postoji!");
            return;
        }
        
        Upis upis = new Upis(idPolaznik, idProgram);
        int noviID = UpisDAO.insertUpis(upis);
        
        if (noviID > 0) {
            System.out.println("✓ Polaznik " + polaznik.getIme() + " " + polaznik.getPrezime() + 
                             " uspjesno upisan na program: " + program.getNaziv());
        } else {
            System.out.println("✗ Greska pri upisu!");
        }
    }

    
    private static void prikaziSvePolaznika() throws SQLException {
        System.out.println("\n=== SVI POLAZNICI ===");
        List<Polaznik> polaznici = PolaznikDAO.getAllPolaznici();
        
        if (polaznici.isEmpty()) {
            System.out.println("Nema polaznika u bazi!");
            return;
        }
        
        for (Polaznik p : polaznici) {
            System.out.println(p);
        }
    }

    
    private static void prikaziSveProgrameObrazovanja() throws SQLException {
        System.out.println("\n=== SVI PROGRAMI OBRAZOVANJA ===");
        List<ProgramObrazovanja> programi = ProgramObrazovanjaDAO.getAllProgrami();
        
        if (programi.isEmpty()) {
            System.out.println("Nema programa u bazi!");
            return;
        }
        
        for (ProgramObrazovanja p : programi) {
            System.out.println(p);
        }
    }

    
    private static void prikaziSveUpise() throws SQLException {
        System.out.println("\n=== SVI UPISI ===");
        List<Upis> upisi = UpisDAO.getAllUpisi();
        
        if (upisi.isEmpty()) {
            System.out.println("Nema upisa u bazi!");
            return;
        }
        
        for (Upis u : upisi) {
            System.out.println(u);
        }
    }

    private static void prebaciPolaznikaIzJednoguUDrugiProgram() throws SQLException {
        System.out.print("Unesi ID upisa: ");
        int upisID = unosBroja();
        System.out.print("Unesi novi ID programa: ");
        int noviProgramID = unosBroja();

        if (UpisDAO.prebaciPolaznika(upisID, noviProgramID)) {
            System.out.println("Polaznik uspjesno prebacen!");
        } else {
            System.out.println("Greska pri prebacivanju!");
        }
    }
    
    private static void prikaziPolaznikaISnjegovePrograme() throws SQLException {
        System.out.println("\n=== PREGLED POLAZNIKA I PROGRAMA ===");
        System.out.print("Unesi ID polaznika: ");
        int polaznikID = unosBroja();
        
        Polaznik polaznik = PolaznikDAO.getPolaznikById(polaznikID);
        if (polaznik == null) {
            System.out.println("✗ Polaznik sa tim ID ne postoji!");
            return;
        }
        
        System.out.println("\nPolaznik: " + polaznik);
        
        List<Upis> upisi = UpisDAO.getUpisyByPolaznik(polaznikID);
        if (upisi.isEmpty()) {
            System.out.println("Ovaj polaznik nije upisan na nijedan program!");
            return;
        }
        
        System.out.println("Programi na kojima je upisan:");
        for (Upis upis : upisi) {
            ProgramObrazovanja program = ProgramObrazovanjaDAO.getProgramById(upis.getIdProgramObrazovanja());
            if (program != null) {
                System.out.println("  - " + program.getNaziv() + " (" + program.getCsvet() + " CSVET bodova)");
            }
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
