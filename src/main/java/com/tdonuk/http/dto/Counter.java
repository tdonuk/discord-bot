package com.tdonuk.http.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Counter {
    private String champ;
    private String percent;

    @Override
    public String toString() {
        return String.format("%s\t%s", champ, percent);
    }
}
