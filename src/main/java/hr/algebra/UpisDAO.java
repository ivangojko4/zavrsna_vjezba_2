package hr.algebra;

import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class UpisDAO {

    public static int insertUpis(Upis upis) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(upis);
            tx.commit();
            return upis.getUpisID();
        }
    }

    public static Upis getUpisById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Upis.class, id);
        }
    }

    public static List<Upis> getAllUpisi() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Upis", Upis.class).list();
        }
    }

    public static List<Upis> getUpisiByPolaznik(int polaznikID) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Upis u WHERE u.polaznik.polaznikID = :pid", Upis.class)
                    .setParameter("pid", polaznikID)
                    .list();
        }
    }

    // Prebacivanje polaznika iz jednog u drugi program - sa transakcijom
    public static boolean prebaciPolaznika(int upisID, int noviProgramID) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Upis upis = session.get(Upis.class, upisID);
            ProgramObrazovanja noviProgram = session.get(ProgramObrazovanja.class, noviProgramID);

            if (upis == null || noviProgram == null) {
                tx.rollback();
                return false;
            }

            upis.setProgramObrazovanja(noviProgram);
            session.merge(upis);

            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }
}