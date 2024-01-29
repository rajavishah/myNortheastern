package com.example.unimanagement.dao;

import com.example.unimanagement.model.Student;
import com.example.unimanagement.model.Course;
import com.example.unimanagement.model.User;
import com.example.unimanagement.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class StudentDAOImpl implements StudentDAO{


    private SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();


    @Override
    public void save(Student student) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.update(student);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception based on your application's error handling strategy
        }
    }

    @Override
    public Student getStudentById(int studentId) {
        try (Session session = sessionFactory.openSession()) {
//            Transaction transaction = session.getTransaction();
//            transaction.begin();
//
//            transaction.commit();
            return session.get(Student.class, studentId);
        }
    }

    @Override
    public void dropCourse(Student student, Course course) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();

//            String prequeryString = "delete from User where role='STUDENT' and userId= :id";
//            Query prequery = session.createQuery(prequeryString);
//            prequery.setParameter("id",id);
//            prequery.executeUpdate();
//
//            String queryString = "delete from User where role='FACULTY' and userId= :id";
//            Query query = session.createQuery(queryString);
//            query.setParameter("id",id);
//            query.executeUpdate();
//            transaction.commit();



            Course course1 = session.get(Course.class, course.getCrn());
            Student student1 = session.get(Student.class, student.getUserId());
            student1.getMyCourses().remove(course1);
            course1.getRegisteredStudents().remove(student1);


//             Update the changes in the database
            System.out.println("Before updating database");
            session.update(student1);
            session.update(course1);
            System.out.println("After updating database");


            System.out.println(" ----xxxxxxxxxxxxx--------- Student's courses after dropping : " + student1.getMyCourses());
            System.out.println(" -----xxxxxxxxxxxxxxxxxxxxxxx---------- Course's registered students dropping : " + course1.getRegisteredStudents());
            transaction.commit();
            session.refresh(student1);
            session.refresh(course1);

        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception based on your application's error handling strategy
        }
    }

    @Override
    public void registerCourse(Student student, Course course) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();


            System.out.println("Student: " + student);
            System.out.println("Course: " + course);

            // Update Student's courses
//            String studentHql = "update Student s set s.myCourses = :courseId where s.userId = :studentId and s.role = 'STUDENT'";
//            Query studentQuery = session.createQuery(studentHql);
//            studentQuery.setParameter("courseId", course);
//            studentQuery.setParameter("studentId", student.getUserId());
//            studentQuery.executeUpdate();
//
//            // Update Course's registered students
//            String courseHql = "update Course c set c.registeredStudents = :studentId where c.crn = :courseId";
//            Query courseQuery = session.createQuery(courseHql);
//            courseQuery.setParameter("studentId", student);
//            courseQuery.setParameter("courseId", course.getCrn());
//            courseQuery.executeUpdate();

            transaction.commit();
    } catch (Exception e) {
        e.printStackTrace();
        // Handle the exception based on your application's error handling strategy
    }
    }
}
