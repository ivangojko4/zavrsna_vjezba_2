package hr.algebra;

import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class ProgramObrazovanjaDAO {

    public static int insertProgramObrazovanja(ProgramObrazovanja program) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(program);
            tx.commit();
            return program.getProgramObrazovanjaID();
        }
    }

    public static ProgramObrazovanja getProgramById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(ProgramObrazovanja.class, id);
        }
    }

    public static List<ProgramObrazovanja> getAllProgrami() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM ProgramObrazovanja", ProgramObrazovanja.class).list();
        }
    }

    public static boolean updateProgram(ProgramObrazovanja program) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(program);
            tx.commit();
            return true;
        }
    }

    public static boolean deleteProgram(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            ProgramObrazovanja program = session.get(ProgramObrazovanja.class, id);
            if (program != null) {
                session.remove(program);
                tx.commit();
                return true;
            }
            tx.rollback();
            return false;
        }
    }
}