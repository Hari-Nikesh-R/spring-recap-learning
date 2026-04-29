package org.example.configuration;

import org.example.model.UserModel;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.database.JdbcPagingItemReader;
import org.springframework.batch.infrastructure.item.database.Order;
import org.springframework.batch.infrastructure.item.database.support.PostgresPagingQueryProvider;
import org.springframework.batch.infrastructure.item.file.FlatFileItemWriter;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class UserConfiguration {
    // Create bean for Reader
    @Bean
    public JdbcPagingItemReader<UserModel> reader(DataSource dataSource) {

        PostgresPagingQueryProvider postgresPagingQueryProvider = new PostgresPagingQueryProvider();
        postgresPagingQueryProvider.setSelectClause("id, name, active, salary");
        postgresPagingQueryProvider.setFromClause("from users");

        JdbcPagingItemReader<UserModel> jdbcPagingItemReader = new JdbcPagingItemReader<>(dataSource, postgresPagingQueryProvider);
        jdbcPagingItemReader.setDataSource(dataSource);
        jdbcPagingItemReader.setPageSize(100);
        Map<String, Order> sortKeys = new HashMap<>();
        sortKeys.put("id", Order.ASCENDING);
        postgresPagingQueryProvider.setSortKeys(sortKeys);

        jdbcPagingItemReader.setQueryProvider(postgresPagingQueryProvider);

        jdbcPagingItemReader.setRowMapper((rs, rowNumber) -> {
            UserModel user = new UserModel();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setActive(rs.getBoolean("active"));
            user.setSalary(rs.getDouble("salary"));
            return user;
        });
        return jdbcPagingItemReader;
    }

    // Processor
    @Bean
    public ItemProcessor<UserModel, UserModel> processors() {
        return user -> {
            if (user.getId() % 100 == 0) {
                long mem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                System.out.println("Proccessed: " + user.getId() + " | Memory: " + mem / (1024 * 1024) + " MB");
            }
            if (user.getId() == 1500) {
                throw new RuntimeException("Simulating Crash");
            }

            if (user.isActive() && user.getSalary() > 50000) {
                return user;
            }
            return null;
        };
    }

    // Going to write a CSV file
    @Bean
    public FlatFileItemWriter<UserModel> writer() {
        return new FlatFileItemWriterBuilder<UserModel>().name("userWriter")
                .resource(new FileSystemResource("/Users/harinikeshr/Documents/2025/Projects/java-full-stack-training/output.csv"))
                .lineAggregator(user -> user.getId() + "," + user.getName()+","+user.getSalary())
                .build();
    }

    @Bean
    public Job job(JobRepository jobRepository, Step step) {
        return new JobBuilder("userJob", jobRepository).start(step).build();
    }

    @Bean
    public Step step(JobRepository jobRepository, ItemReader<UserModel> reader,
                     ItemProcessor<UserModel, UserModel> processors, ItemWriter<UserModel> writer) {
        return new StepBuilder("step1", jobRepository)
                .<UserModel, UserModel>chunk(100)
                .reader(reader)
                .processor(processors)
                .writer(writer).faultTolerant()
                .skip(Exception.class)
                .skipLimit(10)
                .retry(Exception.class)
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}

