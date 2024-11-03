package com.bs.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class IssueRequest {

    private String title;
    private String description;
    private String status;
    @JsonProperty("projectId")
    private Long projectID;
    private String priority;
    private LocalDate dueDate;
}
