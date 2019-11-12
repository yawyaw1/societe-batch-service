package com.example.societebatchservice.mapper;

import com.example.societebatchservice.entities.Employee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeCustomerRowMapper implements RowMapper<Employee> {

    @Override
    public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
        Employee employee=new Employee();
        employee.setId(resultSet.getLong("id"));
        employee.setFirstname(resultSet.getString("firstname"));
        employee.setLastname(resultSet.getString("lastname"));
        employee.setAdress(resultSet.getString("adress"));
        //employee.setBirthday(resultSet.getDate("birthday"));
        return employee;
    }
}
