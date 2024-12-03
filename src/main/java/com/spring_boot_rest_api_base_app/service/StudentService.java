package com.spring_boot_rest_api_base_app.service;

import com.spring_boot_rest_api_base_app.dto.StudentDto;
import java.util.List;

public interface StudentService {

  StudentDto create(StudentDto studentDto);

  StudentDto getById(Long id);

  List<StudentDto> getAll();

  StudentDto updateInfo(Long id, StudentDto studentDto);

  void delete(Long id);

}
