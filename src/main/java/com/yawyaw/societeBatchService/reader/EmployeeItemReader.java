package com.yawyaw.societeBatchService.reader;

import com.yawyaw.societeBatchService.entities.Employee;
import com.yawyaw.societeBatchService.mapper.EmployeeCustomerRowMapper;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import javax.sql.DataSource;
import java.util.HashMap;

public class EmployeeItemReader implements ItemReader<Employee> {

    public ItemReader<Employee> readEmployees(DataSource dataSource) {
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

    @Override
    public Employee read(){
        return null;
    }
}
