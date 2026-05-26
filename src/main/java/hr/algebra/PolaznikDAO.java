package hr.algebra;

import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class PolaznikDAO {

    public static int insertPolaznik(Polaznik polaznik) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(polaznik);
            tx.commit();
            return polaznik.getPolaznikID();
        }
    }

    public static Polaznik getPolaznikById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Polaznik.class, id);
        }
    }

    public static List<Polaznik> getAllPolaznici() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Polaznik", Polaznik.class).list();
        }
    }

    public static boolean updatePolaznik(Polaznik polaznik) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(polaznik);
            tx.commit();
            return true;
        }
    }

    public static boolean deletePolaznik(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Polaznik polaznik = session.get(Polaznik.class, id);
            if (polaznik != null) {
                session.remove(polaznik);
                tx.commit();
                return true;
            }
            tx.rollback();
            return false;
        }
    }
}