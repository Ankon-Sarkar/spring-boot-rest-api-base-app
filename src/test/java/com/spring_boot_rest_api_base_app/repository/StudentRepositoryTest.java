package com.spring_boot_rest_api_base_app.repository;

import com.spring_boot_rest_api_base_app.model.Student;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
class StudentRepositoryTest {


  @Autowired
  StudentRepository studentRepositoryTest;

  Student student;

  //given
  @BeforeEach
  public void setUp(){
    student= new Student();
    student.setFirstName("Mark");
    student.setLastName("White");
    student.setEmail("markwhite@gmail.com");
  }

  @AfterEach
  void tearDown() {
    studentRepositoryTest.deleteAll();
  }

  @Test
  public void givenStudentObject_whenSave_thenReturnSavedStudent(){
    //when
    Student savedStudent = studentRepositoryTest.save(student);
    //then
    Assertions.assertThat(savedStudent).isNotNull();
    Assertions.assertThat(savedStudent.getId()).isGreaterThan(0);
  }

  @Test
  public void givenStudentList_whenFindAll_thenStudentList(){
    //when
    studentRepositoryTest.save(student);
    List<Student> studentList = studentRepositoryTest.findAll();
    //then
    Assertions.assertThat(studentList).isNotNull();
    Assertions.assertThat(studentList.size()).isEqualTo(1);
  }

  @Test
  public void givenStudentObject_whenFindById_thenReturnStudentObject(){
    //when
    studentRepositoryTest.save(student);
    Student studentDb = studentRepositoryTest.findById(student.getId()).get();
    //then
    Assertions.assertThat(studentDb.getId()).isEqualTo(student.getId());
  }

  @Test
  public void givenStudentObject_whenDelete_thenRemoveStudent(){
    // when
    studentRepositoryTest.save(student);
    studentRepositoryTest.deleteById(student.getId());
    Optional<Student> employeeOptional = studentRepositoryTest.findById(student.getId());
    //then
    Assertions.assertThat(employeeOptional).isEmpty();
  }
}
