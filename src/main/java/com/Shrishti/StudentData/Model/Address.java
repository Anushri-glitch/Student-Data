package com.Shrishti.StudentData.Model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Address {
    private String landMark;
    private String zipcode;
    private String district;
    private String state;
    private String country;
}
