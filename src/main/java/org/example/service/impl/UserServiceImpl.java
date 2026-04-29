package org.example.service.impl;

import org.example.model.UserModel;

import org.example.service.UserService;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.parameters.InvalidJobParametersException;
import org.springframework.batch.core.job.parameters.JobParameter;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.launch.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job userJob;

    @Override
    public String getUsers() {
        try {
            JobParameters params = new JobParametersBuilder().addLocalDate("startAt", LocalDate.now()).toJobParameters();
            JobExecution jobExecution = jobLauncher.run(userJob, params);
            System.out.println("JOB status - " + jobExecution.getStatus());

        } catch (JobInstanceAlreadyCompleteException e) {
            throw new RuntimeException(e);
        } catch (InvalidJobParametersException e) {
            throw new RuntimeException(e);
        } catch (JobExecutionAlreadyRunningException e) {
            throw new RuntimeException(e);
        } catch (JobRestartException e) {
            throw new RuntimeException(e);
        }
        return "Batch started";
    }
}
