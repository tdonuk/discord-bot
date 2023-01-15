package com.tdonuk.dto;

import lombok.Data;

import java.util.List;

@Data
public class NewsResponseDTO {
    private String status;
    private Long totalResults;
    private List<NewsDTO> results;
}
