package com.mysaramin;

import com.mysaramin.api.SaraminAPIClient;
import com.mysaramin.model.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        SaraminAPIClient apiClient = new SaraminAPIClient();
        JobDao jobDAO = new JobDao();

        List<JobDto> jobs = apiClient.fetchJobList();
        jobDAO.saveJobs(jobs);

        System.out.println(jobs.size());
    }
}
