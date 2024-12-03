package com.spring_boot_rest_api_base_app.service;

import com.spring_boot_rest_api_base_app.dto.StudentDto;
import com.spring_boot_rest_api_base_app.exception.ResourceNotFoundException;
import com.spring_boot_rest_api_base_app.mapper.StudentMapper;
import com.spring_boot_rest_api_base_app.model.Student;
import com.spring_boot_rest_api_base_app.repository.StudentRepository;
import java.text.MessageFormat;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

  private final StudentRepository studentRepository;


  public StudentServiceImpl(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  @Override
  public StudentDto create(StudentDto studentDto) {
    Student student = StudentMapper.mapToStudent(studentDto);
    Student savedStudent = studentRepository.save(student);
    return StudentMapper.mapToStudentDto(savedStudent);
  }

  @Override
  public StudentDto getById(Long id) {
    Student student = studentRepository.findById(id).
        orElseThrow(() -> new ResourceNotFoundException(
            MessageFormat.format("Student not found by id {0}", id)));
    return StudentMapper.mapToStudentDto(student);
  }

  @Override
  public List<StudentDto> getAll() {
    List<Student> students = studentRepository.findAll();

    return students.stream()
        .map(StudentMapper::mapToStudentDto).toList();
  }

  @Override
  public StudentDto updateInfo(Long id, StudentDto updateStudentDto) {
    Student existingStudent = studentRepository.findById(id).
        orElseThrow(() -> new ResourceNotFoundException("Unavailable"));

    updateExistingStudent(updateStudentDto, existingStudent);
    Student updatedStudent = studentRepository.save(existingStudent);
    return StudentMapper.mapToStudentDto(updatedStudent);
  }

  private void updateExistingStudent(StudentDto updateStudentDto, Student exixtingStudent) {
    exixtingStudent.setFirstName(updateStudentDto.getFirstName());
    exixtingStudent.setLastName(updateStudentDto.getLastName());
    exixtingStudent.setEmail(updateStudentDto.getEmail());
  }

  @Override
  public void delete(Long id) {
    studentRepository.findById(id).orElseThrow(
        () -> new ResourceNotFoundException("Unavailable"));
    studentRepository.deleteById(id);
  }

}
