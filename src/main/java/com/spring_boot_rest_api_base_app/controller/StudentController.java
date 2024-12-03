package com.spring_boot_rest_api_base_app.controller;

import com.spring_boot_rest_api_base_app.dto.StudentDto;
import com.spring_boot_rest_api_base_app.service.StudentService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class StudentController {

  private final StudentService studentService;

  public StudentController(StudentService studentService) {
    this.studentService = studentService;
  }

  @PostMapping("/students")
  public ResponseEntity<StudentDto> createStudent(@RequestBody StudentDto studentDto) {
    StudentDto savedStudent = studentService.create(studentDto);
    return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
  }

  @GetMapping("/students/{id}")
  public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id) {
    StudentDto studentDto = studentService.getById(id);
    return new ResponseEntity<>(studentDto, HttpStatus.OK);
  }

  @GetMapping("/students")
  public ResponseEntity<List<StudentDto>> getAllStudent() {
    List<StudentDto> studentDtoList = studentService.getAll();
    return new ResponseEntity<>(studentDtoList, HttpStatus.OK);
  }

  @PutMapping("/students/{id}")
  public ResponseEntity<StudentDto> updateStudent(@PathVariable Long id,
                                                  @RequestBody StudentDto updateStudentDto) {
    StudentDto updatedStudent = studentService.updateInfo(id, updateStudentDto);
    return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
  }

  @DeleteMapping("/students/{id}")
  public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
    studentService.delete(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
