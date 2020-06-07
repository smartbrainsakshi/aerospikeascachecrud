package com.example.postgresdemo.controller;

import com.aerospike.cache.CacheAerospikeClient;
import com.aerospike.cache.transcoder.CacheTranscoder;
import com.example.postgresdemo.model.Student;
import com.example.postgresdemo.service.StudentService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(value = "/student")
public class StudentController {

    @Autowired

    private StudentService studentService;


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public void save(@RequestBody Student student) {
        Student studentResponse;
         int n=0;
         while(n<2){
         studentService.saveStudent(student);
         n++;
         }

    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Student getStudent(@PathVariable int id) {



        Student studentResponse = (Student) studentService.getStudent(id);
        return studentResponse;
    }

}
