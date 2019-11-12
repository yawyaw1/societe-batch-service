package com.yawyaw.societeBatchService.lineAggregator;

import com.yawyaw.societeBatchService.entities.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Autowired;

public class EmployeeCutomerLineAggregator implements LineAggregator<Employee> {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public String aggregate(Employee employee) {
        try {
            return objectMapper.writeValueAsString(employee);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to serialize employee", e);
        }
    }
}
