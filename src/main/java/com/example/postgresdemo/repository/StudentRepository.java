package com.example.postgresdemo.repository;

import com.example.postgresdemo.model.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface StudentRepository extends CrudRepository<Student, Serializable> {

}