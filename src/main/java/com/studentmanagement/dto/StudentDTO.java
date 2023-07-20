package com.studentmanagement.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StudentDTO {
    private Long id;

    private Long code;

    @NotBlank(message = "First name should not be blank")
    @Pattern(regexp = "^[a-zA-Z]{1,25}$", message = "re-enter first name consisting of 1 to 25 alphanumeric characters")
    private String firstName;

    @NotBlank(message = "Last name should not be blank")
    @Pattern(regexp = "^[a-zA-Z]{1,25}$", message = "re-enter last name consisting of 1 to 25 alphanumeric characters")
    private String lastName;

    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-\\d{2}$", message = "re-enter date of birth, format: yyyy-MM-dd")
//    private Date dateOfBirth;
    private String dateOfBirth;
    @Pattern(regexp = "^(Other|Female|Male)$", message = "Gender format: Male, Female, Other")
    private String gender;
//    private Gender gender;

    @NotBlank(message = "email should not be blank")
    @Email(message = "Invalid email")
    private String email;

    @Pattern(regexp = "^0[0-9]{9}$", message = "Invalid phone number")
    private String phoneNumber;

    private String address;

    private String department;

    private List<StudentDTO> listResult = new ArrayList<>();

}
