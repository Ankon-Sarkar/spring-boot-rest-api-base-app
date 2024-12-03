package com.spring_boot_rest_api_base_app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
  private long id;
  private String firstName;
  private String lastName;
  private String email;
}