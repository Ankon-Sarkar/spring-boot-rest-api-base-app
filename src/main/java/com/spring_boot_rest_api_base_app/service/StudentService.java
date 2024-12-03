package com.spring_boot_rest_api_base_app.service;

import com.spring_boot_rest_api_base_app.dto.StudentDto;
import java.util.List;

public interface StudentService {

  public StudentDto create(StudentDto studentDto);

  public StudentDto getById(Long id);

  public List<StudentDto> getAll();

  public StudentDto updateInfo(Long id, StudentDto studentDto);

  public void delete(Long id);

}
