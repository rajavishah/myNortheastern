package com.example.unimanagement.dao;

import com.example.unimanagement.model.Course;
import com.example.unimanagement.model.User;
import com.example.unimanagement.util.HibernateUtil;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseDAOImpl implements CourseDAO{

    private SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();

    @Override
    public void save(Course course) {

        SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();

        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.persist(course);
            transaction.commit();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public List<Course> getAllCourses() {

        try(Session session = sessionFactory.openSession()) {
            String queryString = "FROM Course";
            Query query = session.createQuery(queryString, Course.class);
            List<Course> courses = query.list();
            return courses;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Course getCourseById(int id) {
        try (Session session = sessionFactory.openSession()) {
            String queryString = "FROM Course WHERE id = :id";
            Query<Course> query = session.createQuery(queryString, Course.class);
            query.setParameter("id", id);

            Course course = query.uniqueResult();

            if (course != null) {
                Hibernate.initialize(course.getRegisteredStudents());
            }

            return course;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteById(int crn) {
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.getTransaction();
            transaction.begin();
            String queryString = "delete from Course where id= :crn";
            Query query = session.createQuery(queryString);
            query.setParameter("crn",crn);
            query.executeUpdate();
            transaction.commit();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateCourse(Course course) {
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.update(course);
            transaction.commit();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
