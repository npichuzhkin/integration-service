package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictionaryRequestDTO {
    private Long id;
    private String name;
    private String description;
    private Map<String, String> values;
}
