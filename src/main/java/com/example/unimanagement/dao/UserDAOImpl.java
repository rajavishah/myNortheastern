package com.example.unimanagement.dao;

import com.example.unimanagement.model.*;
import com.example.unimanagement.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDAOImpl implements UserDAO{
    private SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();

    @Override
    public void save(User user) {
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.persist(user);
            transaction.commit();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public User getByEmail(String email) {
        try(Session session = sessionFactory.openSession()) {
            String queryString = "FROM User u where u.email=\""+email+"\"";
            Query query = session.createQuery(queryString, User.class);
            List<User> users = query.list();
            return users.size() == 1 ? users.get(0) : null;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public User getById(int id) {
        try(Session session = sessionFactory.openSession()) {
            String queryString = "FROM User where id="+id;
            Query query = session.createQuery(queryString, User.class);
            List<User> users = query.list();
            return users.size() == 1 ? users.get(0) : null;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteById(int id) {
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.getTransaction();
            transaction.begin();
            String queryString = "delete from User where id= :id";
            Query query = session.createQuery(queryString);
            query.setParameter("id",id);
            query.executeUpdate();
            transaction.commit();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

//    Delete faculty by id
    @Override
    public void deleteFacultyById(int id) {
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.getTransaction();
            transaction.begin();

            String prequeryString = "update Course set faculty.userId = null where faculty.userId= :id";
            Query prequery = session.createQuery(prequeryString);
            prequery.setParameter("id",id);
            prequery.executeUpdate();

            String queryString = "delete from User where role='FACULTY' and userId= :id";
            Query query = session.createQuery(queryString);
            query.setParameter("id",id);
            query.executeUpdate();
            transaction.commit();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.update(user);
            transaction.commit();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

//    Student Specific Operations
    @Override
    public List<User> getAllStudents() {
        try(Session session = sessionFactory.openSession()) {
            String queryString = "FROM User WHERE role = 'STUDENT'";
            Query query = session.createQuery(queryString, User.class);
            List<User> students = query.list();
            return students;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //    Faculty Specific Operations
@Override
public List<User> getAllFaculties() {
    try(Session session = sessionFactory.openSession()) {
        String queryString = "FROM User WHERE role = 'FACULTY'";
        Query query = session.createQuery(queryString, User.class);
        List<User> faculties = query.list();
        return faculties;
    }
    catch (Exception e){
        e.printStackTrace();
    }
    return null;
}

    @Override
    public Faculty getFacultyById(int id) {
        try(Session session = sessionFactory.openSession()) {
            String queryString = "FROM Faculty WHERE id="+id;
//            Query q = getSession().createQuery("from House where residenceId= :id");
//            q.setParameter("id", id);
            Query query = session.createQuery(queryString, User.class);
            List<Faculty> faculties = query.list();
            return faculties.size() == 1 ? faculties.get(0) : null;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
