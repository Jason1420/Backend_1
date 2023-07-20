package com.studentmanagement.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<StudentDTO> getListResult() {
        return listResult;
    }

    public void setListResult(List<StudentDTO> listResult) {
        this.listResult = listResult;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }
}
