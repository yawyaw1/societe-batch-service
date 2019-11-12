package com.yawyaw.societeBatchService.writer;

import com.yawyaw.societeBatchService.entities.Employee;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.util.List;

public class EmployeeItemWriter implements ItemWriter<Employee> {


    public ItemWriter<Employee> writeEmployees() throws Exception {
        FlatFileItemWriter<Employee> employeeFlatFileItemWriter = new FlatFileItemWriter<>();

        DelimitedLineAggregator<Employee> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");
        BeanWrapperFieldExtractor<Employee> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[]{"id", "firstname", "lastname", "adress"});
        lineAggregator.setFieldExtractor(fieldExtractor);

        //In case we would like to return a json file
        //employeeFlatFileItemWriter.setLineAggregator(new EmployeeCutomerLineAggregator())

        employeeFlatFileItemWriter.setLineAggregator(lineAggregator);
        String employeeOutPutPath = File.createTempFile("employeeOutPut", ".csv").getAbsolutePath();
        employeeFlatFileItemWriter.setResource(new FileSystemResource(employeeOutPutPath));
        employeeFlatFileItemWriter.afterPropertiesSet();

        return employeeFlatFileItemWriter;
    }

    @Override
    public void write(List<? extends Employee> list) throws Exception {

    }
}
