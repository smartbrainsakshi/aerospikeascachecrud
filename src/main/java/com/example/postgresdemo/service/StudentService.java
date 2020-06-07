package com.example.postgresdemo.service;

import com.example.postgresdemo.cache.AerospikeCacheManager;
import com.example.postgresdemo.model.Student;
import com.example.postgresdemo.repository.StudentRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;



@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Cacheable(cacheManager = "aerospikeCacheManager",value="ITDEFAULT")
    public Student saveStudent(Student student) {


        Student response = studentRepository.save(student);
        return response;
    }

    @Transactional
    @Cacheable(cacheManager = "aerospikeCacheManager",value="ITDEFAULT")
    public Student getStudent(int id) {

        Session session = entityManager.unwrap(Session.class);
        Student studentResponse1 = session.get(Student.class, id);
        System.out.println(session.contains(studentResponse1));


        Session session2 = entityManager.unwrap(Session.class);
        Student studentResponse2 = session2.get(Student.class, id);
        System.out.println(session.contains(studentResponse2));

        System.out.println("First time record will come from databse");
        Optional<Student> studentResponse = studentRepository.findById(id);
        System.out.println("Second time record will come from cache");
        studentResponse = studentRepository.findById(id);
        System.out.println("Third time record will come from cache");
        studentResponse = studentRepository.findById(id);
        System.out.println("Fourth time record will come from cache");
        studentResponse = studentRepository.findById(id);
        Student student = studentResponse.get();
        return student;


    }

}
