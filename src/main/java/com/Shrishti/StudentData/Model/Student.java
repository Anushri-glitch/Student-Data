package com.Shrishti.StudentData.Model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="tabl_Student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="studentId")
    private Integer StudentId;
    private String StudentName;
    private Integer age;
    private String phoneNumber;
    private String branch;
    private String department;
    @Embedded
    @Column(name="address")
    private Address address;
}
