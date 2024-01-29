package org.example.listener;

import org.slf4j.MDC;
import org.springframework.batch.core.JobExecution;
import org.springframework.stereotype.Service;


public class AbstractJobExecutionListener {


    public AbstractJobExecutionListener() {
    }

    public void before(JobExecution jobExecution) {
        MDC.put("UserId", jobExecution.getJobParameters().getString("userId"));
    }

    public void after() {
        MDC.remove("UserId");
    }
}
