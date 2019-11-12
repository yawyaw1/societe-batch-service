package com.yawyaw.societeBatchService.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Employee {

    private Long id;

    private String firstname;

    private String lastname;

    private String adress;

    private LocalDate birthday;

}
