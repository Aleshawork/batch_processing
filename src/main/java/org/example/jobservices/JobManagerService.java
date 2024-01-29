package org.example.jobservices;

import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JobManagerService {

    @Autowired
    private SimpleJobOperator simpleJobOperator;


    public Long startJob(String name, Map<String, String> jobsParams) {
        String params = jobsParams.entrySet().stream().map(pair -> pair.getKey() + "=" + pair.getValue()).collect(Collectors.joining(","));
        try {
            return simpleJobOperator.start(name, params);
        } catch (Exception ex) {
            throw new RuntimeException(String.format("Error when starting job with name:{}", name), ex);
        }
    }
}
