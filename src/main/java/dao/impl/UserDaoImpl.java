package dao.impl;

import dao.UserDao;
import entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class UserDaoImpl extends BaseDaoImpl<User, Long> implements UserDao {
    private final SessionFactory sessionFactory;

    public UserDaoImpl() {
        super(User.class);
        this.sessionFactory = HibernateUtils.getSessionFactory();
    }

    @Override
    public boolean checkLogin(String username, String password) {
        password = encryptPassword(password);
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM User WHERE username = :username AND password = :password";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("username", username);
            query.setParameter("password", password);
            User user = query.uniqueResult();
            return user != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean checkIfUserExists(String username) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT COUNT(*) FROM User WHERE username = :username";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("username", username);
            Long count = query.uniqueResult();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public void registerUser(String username, String password) {
        if (!checkIfUserExists(username)) {
            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = session.beginTransaction();
                password = encryptPassword(password);
                User user = new User(username, password);
                session.save(user);
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String encryptPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] encodedHash = digest.digest(password.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}
