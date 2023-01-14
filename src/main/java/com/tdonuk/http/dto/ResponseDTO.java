package com.tdonuk.http.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDTO <T> {
    private T data;
}
