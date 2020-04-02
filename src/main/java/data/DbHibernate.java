package data;


import models.Item;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.List;
import java.util.function.Function;

import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbHibernate implements Db {

    private static final Logger LOG = LoggerFactory.getLogger(DbHibernate.class);

    private static final DbHibernate INSTANCE = new DbHibernate();
    public static DbHibernate getInstance() {
        return INSTANCE;
    }


    private static final HibernateUtil HUTIL = HibernateUtil.getInstance();
    private SessionFactory sessionFactory = HUTIL.getSessionConf();

    public DbHibernate() {
    }


    private <T> T wrapper(final Function<Session, T> command) {
        final Session session = sessionFactory.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    /**
     * Add accounts in data bases;
     */
    @Override
    public boolean addNewItem(Item item) {
        boolean successful = false;
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
            successful = true;
        } catch (HibernateException r) {
            LOG.warn((r.toString()));
        } finally {
            session.close();
        }
        return successful;
    }


    /**
     * Get all items from data bases;
     */
    @Override
    public List<Item> getAllItems() {
       /* List<Item> result = new ArrayList<>();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("from Item");
            result = query.getResultList();
        } catch (HibernateException r) {
            LOG.warn((r.toString()));
        }
        return  result;*/
        return this.wrapper(
                session ->         session.createQuery("from Item").list()
        );
    }

    /**
     * Get done items from data bases;
     */
    @Override
    public List<Item> getDoneItems() {
        /*try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("from Item where done = :paramName");
            query.setParameter("paramName", true);
            List<Item> result = query.getResultList();
            return result;
        }*/
        return this.wrapper(
            session -> {
                Query query = session.createQuery("from Item where done = :paramName");
                query.setParameter("paramName", true);
                return query.getResultList();
            }
        );
    }

    @Override
    public Boolean changeItemDone(int itemId, boolean state) {
        boolean successful = false;
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("from Item where id = :paramName");
            query.setParameter("paramName", itemId);
            Item item = (Item) query.getResultList().get(0);
            item.setDone(state);
            session.saveOrUpdate(item);

            session.getTransaction().commit();
            successful = true;
        } catch (HibernateException r) {
            LOG.warn((r.toString()));
        } finally {
            session.close();
        }
        return successful;
    }
}
