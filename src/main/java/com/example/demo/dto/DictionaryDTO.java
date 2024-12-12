package com.example.demo.dto;

import java.util.Map;

public class DictionaryDTO {

    private Long id;
    private String name;
    private String description;
    private Map<String, String> values;

    public DictionaryDTO() {}

    public DictionaryDTO(String name, String description, Map<String, String> values) {
        this.name = name;
        this.description = description;
        this.values = values;
    }

    public DictionaryDTO(Long id, String name, String description, Map<String, String> values) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.values = values;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getValues() {
        return values;
    }

    public void setValues(Map<String, String> values) {
        this.values = values;
    }
}
