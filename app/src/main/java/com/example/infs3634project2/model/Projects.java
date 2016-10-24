package com.example.infs3634project2.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by student on 22/10/2016.
 */

public class Projects {

    private String projectName;
    private String url;
    private String createdDate;
    private String updatedDate;

    public Projects(String projectName, String url, String createdDate, String updatedDate) {
        this.projectName = projectName;
        this.url = url;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getFormattedDate(String date) {
        String formatDate = date.substring(0, 10);
        return formatDate;
    }

}
