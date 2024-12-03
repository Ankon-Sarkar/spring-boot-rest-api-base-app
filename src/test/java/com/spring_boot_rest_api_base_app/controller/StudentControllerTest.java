package com.spring_boot_rest_api_base_app.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring_boot_rest_api_base_app.dto.StudentDto;
import com.spring_boot_rest_api_base_app.service.StudentService;
import java.util.Collections;
import java.util.List;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(StudentController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class StudentControllerTest {

  @MockBean
  private StudentService studentService;
  @Autowired
  MockMvc mockMvc;
  @Autowired
  ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

  StudentDto getStudentdto(Long id){
    return new StudentDto(
        id, "Mark", "White", "markwhite@gmail.com");
  }

  @Test
  void studentController_addStudent_returnCreated() throws Exception {
    //given
    StudentDto studentDtoObj = getStudentdto(100L);
    when(studentService.create(any(StudentDto.class))).thenReturn(studentDtoObj);

    // when
    ResultActions response = mockMvc.perform(post("/api/v1/students")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(studentDtoObj)));
    // then
    response.andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(jsonPath("$.firstName", CoreMatchers.is(studentDtoObj.getFirstName())))
        .andExpect(jsonPath("$.lastName", CoreMatchers.is(studentDtoObj.getLastName())))
        .andExpect(jsonPath("$.id", CoreMatchers.is((int) studentDtoObj.getId())));

  }

  @Test
  void studentController_getStudentById_returnOk() throws Exception {
    //given
    Long studentId = 100L;
    StudentDto studentDtoObj = getStudentdto(studentId);
    when(studentService.getById(studentId)).thenReturn(studentDtoObj);

    //when & then
    mockMvc.perform(get("/api/v1//students/{id}", studentId))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(jsonPath(
            "$.firstName", CoreMatchers.is(studentDtoObj.getFirstName())))
        .andExpect(jsonPath(
            "$.lastName", CoreMatchers.is(studentDtoObj.getLastName())));
  }

  @Test
  void studentController_getAllStudent_returnOk() throws Exception {
    //given
    List<StudentDto> studentDtoList = Collections.singletonList(getStudentdto(100L));
    when(studentService.getAll()).thenReturn(studentDtoList);

    //when & then
    mockMvc.perform(get("/api/v1//students"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(1)));
  }

  @Test
  public void studentController_updateStudent_returnOk() throws Exception {

    //given
    Long studentId = 101L;
    StudentDto updatedDto = getStudentdto(studentId);

    when(studentService.updateInfo(eq(studentId), any(StudentDto.class)))
        .thenReturn(updatedDto);

    //when & then
    mockMvc.perform(put("/api/v1//students/{id}", studentId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedDto)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(jsonPath("$.firstName", CoreMatchers.is(updatedDto.getFirstName())))
        .andExpect(jsonPath("$.lastName", CoreMatchers.is(updatedDto.getLastName())))
        .andExpect(jsonPath("$.email", CoreMatchers.is(updatedDto.getEmail())));
  }

  @Test
  void StudentController_deleteStudent_returnOk() throws Exception {
    //given
    Long studentId = 101L;
    doNothing().when(studentService).delete(studentId);

    //when
    ResultActions response = mockMvc.perform(delete("/api/v1/students/{id}", studentId)
        .contentType(MediaType.APPLICATION_JSON));
    //then
    response.andExpect(MockMvcResultMatchers.status().isOk());
  }
}