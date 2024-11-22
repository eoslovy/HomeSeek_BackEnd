package com.homeseek.map.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchoolDto {
    private int id;
    private String name;
    private String category;
    private String type;
    private String address;
    private String latitude;
    private String longitude;
}
