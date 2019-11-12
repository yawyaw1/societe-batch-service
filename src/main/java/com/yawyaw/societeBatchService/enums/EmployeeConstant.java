package com.yawyaw.societeBatchService.enums;

public enum EmployeeConstant {

    //Job information
    JOB_NAME("EmployeeJob"),
    STEP_NAME("EmployeeStep"),

    //Reader information
    SELECT_BLOC_QUERY("select id, firstname, lastname, adress");

    String description;

    EmployeeConstant(String desc) {
        description = desc;
    }

    public String getDescription() {
        return description;
    }
}
