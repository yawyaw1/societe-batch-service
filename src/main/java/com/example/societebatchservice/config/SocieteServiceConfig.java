package com.example.societebatchservice.config;

import com.example.societebatchservice.entities.Employee;
import com.example.societebatchservice.mapper.EmployeeCustomerRowMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.File;
import java.util.HashMap;

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
        return this.jobBuilderFactory.get("societe-batch-service")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    public Step step1() throws Exception {
        return this.stepBuilderFactory.get("extract-employees")
                .<Employee, Employee>chunk(1)
                .reader(itemReader())
                //.processor()
                .writer(itemWriter())
                .build();
    }

    @Bean
    public ItemReader<Employee> itemReader() {
        JdbcPagingItemReader<Employee> jdbcPagingItemReader = new JdbcPagingItemReader<>();

        jdbcPagingItemReader.setDataSource(dataSource);
        jdbcPagingItemReader.setFetchSize(10);
        jdbcPagingItemReader.setRowMapper(new EmployeeCustomerRowMapper());

        MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
        queryProvider.setSelectClause("select id, firstname, lastname, adress");
        queryProvider.setFromClause("from employee");
        HashMap<String, Order> sortKeys = new HashMap<>();
        sortKeys.put("id", Order.ASCENDING);
        queryProvider.setSortKeys(sortKeys);

        jdbcPagingItemReader.setQueryProvider(queryProvider);

        return jdbcPagingItemReader;
    }

    @Bean
    public ItemWriter<Employee> itemWriter() throws Exception {
        FlatFileItemWriter<Employee> employeeFlatFileItemWriter = new FlatFileItemWriter<>();

        DelimitedLineAggregator<Employee> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");
        BeanWrapperFieldExtractor<Employee> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[]{"id", "firstname", "lastname", "adress"});
        lineAggregator.setFieldExtractor(fieldExtractor);

        employeeFlatFileItemWriter.setLineAggregator(lineAggregator);
        String employeeOutPutPath = File.createTempFile("employeeOutPut", ".csv").getAbsolutePath();
        employeeFlatFileItemWriter.setResource(new FileSystemResource(employeeOutPutPath));
        employeeFlatFileItemWriter.afterPropertiesSet();

        return employeeFlatFileItemWriter;
    }

}
