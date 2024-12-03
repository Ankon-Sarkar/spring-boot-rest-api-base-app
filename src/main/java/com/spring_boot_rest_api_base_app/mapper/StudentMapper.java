package com.spring_boot_rest_api_base_app.mapper;

import com.spring_boot_rest_api_base_app.dto.StudentDto;
import com.spring_boot_rest_api_base_app.model.Student;

public class StudentMapper {
  public static StudentDto mapToStudentDto(Student student) {
    return new StudentDto(student.getId(), student.getFirstName(),
        student.getLastName(), student.getEmail());
  }

  public static Student mapToStudent(StudentDto studentDto) {
    return new Student(studentDto.getId(), studentDto.getFirstName(),
        studentDto.getLastName(), studentDto.getEmail());
  }
}
