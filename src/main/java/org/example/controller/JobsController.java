package org.example.controller;

import org.example.jobservices.JobManagerService;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class JobsController {

    @Autowired
    private JobManagerService jobManagerService;

    @GetMapping("/start/{jobName}")
    public Long startJob(@PathVariable String jobName) {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        Map<String,String> params = new HashMap<>();
        params.put("launchTime", String.valueOf(System.currentTimeMillis()));
        return jobManagerService.startJob(jobName, params);
    }


}
