package com.yawyaw.societeBatchService.config;

import com.yawyaw.societeBatchService.entities.Employee;
import com.yawyaw.societeBatchService.enums.EmployeeConstant;
import com.yawyaw.societeBatchService.reader.EmployeeItemReader;
import com.yawyaw.societeBatchService.writer.EmployeeItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SocieteServiceConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;


    @Bean
    public Job job() throws Exception {
        return this.jobBuilderFactory.get(EmployeeConstant.JOB_NAME.getDescription())
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    public Step step1() throws Exception {
        return this.stepBuilderFactory.get(EmployeeConstant.STEP_NAME.getDescription())
                .<Employee, Employee>chunk(1)
                .reader(itemReader())
                //.processor()
                .writer(itemWriter())
                .build();
    }

    @Bean
    public ItemReader<Employee> itemReader() {
        return new EmployeeItemReader().readEmployees(dataSource);
    }

    @Bean
    public ItemWriter<Employee> itemWriter() throws Exception {
        return new EmployeeItemWriter().writeEmployees();
    }

}
