package com.highfive.highfive.model;

import java.util.Date;

/**
 * Created by heat_wave on 21.12.16.
 */

public class Job {

    private int jobId;

    public JobClass getType() {
        return type;
    }

    private JobClass type;
    private String subject;
    private String description;
    private Date date;

    public Job(JobClass type, String subject, String description) {
        this.type = type;
        this.subject = subject;
        this.description = description;
    }

    public enum JobClass {
        SCHOOL,
        STUDENT
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

}

