package com.spring_boot_rest_api_base_app.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.spring_boot_rest_api_base_app.dto.StudentDto;
import com.spring_boot_rest_api_base_app.mapper.StudentMapper;
import com.spring_boot_rest_api_base_app.model.Student;
import com.spring_boot_rest_api_base_app.repository.StudentRepository;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class StudentServiceTest {
  @Mock
  private StudentRepository studentRepository;
  @InjectMocks
  private StudentServiceImpl studentService;

  Student student;
  Long studentID;

  Student getStudent(Long id) {
    student = new Student();
    student.setId(id);
    student.setFirstName("Mark");
    student.setLastName("White");
    student.setEmail("markwhite@gmail.com");
    return student;
  }

  @Test
  void studentService_addStudent_returnStudentDtoObject() {
    //given
    Long studentId = 100L;
    Student student = getStudent(studentId);

    when(studentRepository.save(any(Student.class))).thenReturn(student);
    //when
    StudentDto savedStudentDto = studentService.create(
        StudentMapper.mapToStudentDto(student));
    //then
    Assertions.assertThat(savedStudentDto).isNotNull();
  }

  @Test
  void studentService_getStudentById_returnStudentDtoObject() {
    //given
    Long studentId = 100L;
    Student student = getStudent(studentId);

    when(studentRepository.findById(studentID)).
        thenReturn(Optional.of(student));

    //when
    StudentDto studentDto = studentService.getById(studentId);
    //then
    Assertions.assertThat(studentDto).isNotNull();
    verify(studentRepository).findById(studentId);
  }

  @Test
  void studentService_getAllStudent_returnStudentDtoObjectList() {
    //when
    studentService.getAll();
    //then
    verify(studentRepository).findAll();
  }

  @Test
  void updateStudentInfo() {
    //given
    Long studentId = 101L;
    Student existingStudent = getStudent(studentId);
    StudentDto newInfo = new StudentDto(
        studentId, "Sunil", "Narin", "sunil@gmail.com");

    when(studentRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));
    when(studentRepository.save(existingStudent)).thenReturn(existingStudent);

    //when
    StudentDto updatedStudent = studentService.updateInfo(studentId, newInfo);

    //then
    Assertions.assertThat(updatedStudent.getFirstName()).isEqualTo(newInfo.getFirstName());
  }

  @Test
  void studentService_deleteStudent_returnNothing() {
    //given
    Long studentId = 100L;
    Student student = getStudent(studentId);

    when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
    doNothing().when(studentRepository).deleteById(studentId);
    //when
    studentService.delete(studentId);

    //then
    verify(studentRepository).deleteById(studentId);
  }
}
