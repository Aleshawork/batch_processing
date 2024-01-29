package org.example.listener;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;



@Component
public class JobExecutionStatusListener extends AbstractJobExecutionListener implements JobExecutionListener {

    Logger logger = LoggerFactory.getLogger(JobExecutionStatusListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        this.before(jobExecution);
        logger.info("Starting jobs :{}", jobExecution.getJobParameters().getString("jobName"));
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        this.after();
    }
}
