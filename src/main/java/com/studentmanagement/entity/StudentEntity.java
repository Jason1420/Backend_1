package com.studentmanagement.entity;

import com.studentmanagement.dto.Gender;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "students")
@Data
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code", unique = true)
    private Long code;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @Column(name = "gender", length = 6)
    private Gender gender;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number", length = 11)
    private String phoneNumber;
    @Column(name = "address")
    private String address;
    @Column(name = "department")
    private String department;

}
