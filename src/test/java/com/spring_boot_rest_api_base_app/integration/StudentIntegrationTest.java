package com.spring_boot_rest_api_base_app.integration;

import com.spring_boot_rest_api_base_app.dto.StudentDto;
import com.spring_boot_rest_api_base_app.model.Student;
import com.spring_boot_rest_api_base_app.repository.StudentRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class StudentIntegrationTest {

  @LocalServerPort
  private int port;

  private String baseUrl = "http://localhost:";

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  StudentRepository studentRepositoryTest;

  @BeforeEach
  void setUp() {
    baseUrl = baseUrl.concat(Integer.toString(port)).concat("/api/v1/students");
  }

  @Test
  public void testAddStudent() {
    StudentDto expectedStudentDto = new StudentDto(1L, "Mark", "White",
        "markwhite@gmail.com");
    StudentDto response = restTemplate.postForObject(baseUrl, expectedStudentDto,
        StudentDto.class);

    Assertions.assertEquals(response.getEmail(), expectedStudentDto.getEmail());
    Assertions.assertEquals(response.getFirstName(), expectedStudentDto.getFirstName());
    Assertions.assertEquals(response.getLastName(), expectedStudentDto.getLastName());
  }

  @Test
  @Sql(statements =
      "INSERT INTO STUDENTS (ID, EMAIL, FIRST_NAME, LAST_NAME) VALUES (101L, 'test@gmail.com'" +
          ", 'ABC', 'XYZ')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  @Sql(statements = "DELETE FROM STUDENTS WHERE ID= 101L", executionPhase = Sql.ExecutionPhase.
      AFTER_TEST_METHOD)
  public void testGetStudentById() {
    String expectedEmail = "test@gmail.com";
    Long expectedID = 101L;
    String expectedFirstName = "ABC";
    String expectedLastName = "XYZ";

    StudentDto response = restTemplate.getForObject(baseUrl + "/{id}", StudentDto.class,
        expectedID);

    Assertions.assertEquals(expectedID, response.getId());
    Assertions.assertEquals(expectedEmail, response.getEmail());
    Assertions.assertEquals(expectedFirstName, response.getFirstName());
    Assertions.assertEquals(expectedLastName, response.getLastName());

  }

  @Test
  @Sql(statements =
      "INSERT INTO STUDENTS (ID, EMAIL, FIRST_NAME, LAST_NAME) VALUES (101L, 'test@gmail.com'" +
          ", 'ABC', 'XYZ')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  @Sql(statements = "DELETE FROM STUDENTS WHERE ID= 101L", executionPhase = Sql.ExecutionPhase.
      AFTER_TEST_METHOD)
  public void testStudentAll() {
    List response = restTemplate.getForObject(baseUrl, List.class);
    Assertions.assertEquals(1, response.size());
  }

  @Test
  @Sql(statements =
      "INSERT INTO STUDENTS (ID, EMAIL, FIRST_NAME, LAST_NAME) VALUES (101L, 'test@gmail.com'" +
          ", 'ABC', 'XYZ')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  @Sql(statements = "DELETE FROM STUDENTS WHERE ID= 101L", executionPhase = Sql.ExecutionPhase.
      AFTER_TEST_METHOD)
  public void testUpdateStudent() {
    String expectedEmail = "new@gmail.com";
    String expectedFirstName = "Updated Fist Name";
    String expectedLastName = "Updated Last Name";
    StudentDto updateStudentDtoInfo = new StudentDto(101L, expectedFirstName, expectedLastName,
        expectedEmail);

    restTemplate.put(baseUrl + "/{id}", updateStudentDtoInfo, 101L);
    Optional<Student> updatedStudentDto = studentRepositoryTest.findById(101L);

    Assertions.assertEquals(expectedEmail, updatedStudentDto.get().getEmail());
    Assertions.assertEquals(expectedFirstName, updatedStudentDto.get().getFirstName());
    Assertions.assertEquals(expectedLastName, updatedStudentDto.get().getLastName());
  }

  @Test
  @Sql(statements =
      "INSERT INTO STUDENTS (ID, EMAIL, FIRST_NAME, LAST_NAME) VALUES (101L, 'test@gmail.com'" +
          ", 'ABC', 'XYZ')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  public void deleteStudent() {
    Assertions.assertEquals(1, studentRepositoryTest.findAll().size());

    restTemplate.delete(baseUrl + "/{id}", 101L);

    Assertions.assertEquals(0, studentRepositoryTest.findAll().size());
  }
}
