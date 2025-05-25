package com.mysaramin.api;

import com.mysaramin.model.JobDto;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SaraminAPIClient {

    private final String accessKey = "발급 받은키 ";

    public List<JobDto> fetchJobList() {
        List<JobDto> jobList = new ArrayList<>();

        try {
            String apiUrl = "https://oapi.saramin.co.kr/job-search?access-key=" + accessKey + "&keywords=java&count=10";

            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (con.getResponseCode() == 200) ? con.getInputStream() : con.getErrorStream()
            ));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) sb.append(line);
            br.close();

            // JSON 파싱
            JSONObject root = new JSONObject(sb.toString());
            JSONArray jobs = root.getJSONObject("jobs").getJSONArray("job");

            for (int i = 0; i < jobs.length(); i++) {
                JSONObject job = jobs.getJSONObject(i);

                String title = job.getJSONObject("position").getString("title");
                String company = job.getJSONObject("company").getString("name");
                String location = job.getJSONObject("position").getString("location");
                String date = job.getString("expiration-date");
                String urlStr = job.getString("url");

                jobList.add(new JobDto(title, company, location, date, urlStr));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jobList;
    }
}
