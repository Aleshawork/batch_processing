package org.example.controller;

import org.example.jobservices.JobManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class JobsController {

    @Autowired
    private JobManagerService jobManagerService;

    @GetMapping("/start/{jobName}/{userId}")
    public Long startJob(
            @PathVariable String jobName,
            @PathVariable String userId) {
        return jobManagerService.startJob(
                jobName,
                Map.of(
                "launchTime", String.valueOf(System.currentTimeMillis()),
                "userId", userId,
                "jobName", jobName
                ));
    }

}
