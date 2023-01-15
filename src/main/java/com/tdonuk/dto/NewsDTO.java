package com.tdonuk.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class NewsDTO {
    private String title;
    private String link;
    private List<String> keywords;
    private List<String> creator;
    private String description;
    private String pubDate;
    private String source_id;
    private List<String> country;
    private List<String> category;
    private String language;
}
