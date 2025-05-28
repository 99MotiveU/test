package com.mysaramin.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

public class JobDao {
    private static final String DB_URL = "jdbc:mysql://localhost:33306/xe";
    private static final String DB_USER = "";
    private static final String DB_PASS = "";

    public void saveJobs(List<JobDto> jobList) {
        String sql = "INSERT INTO jobs (title, company, location, date, url) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            for (JobDto job : jobList) {
                pstmt.setString(1, job.getTitle());
                pstmt.setString(2, job.getCompany());
                pstmt.setString(3, job.getLocation());
                pstmt.setString(4, job.getDate());
                pstmt.setString(5, job.getUrl());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
