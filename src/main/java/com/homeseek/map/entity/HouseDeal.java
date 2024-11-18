package com.homeseek.map.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HouseDeal {
    @Id @GeneratedValue
    private Integer no;

    private String aptSeq;
    private String aptDong;
    private String floor;
    private Integer dealYear;
    private Integer dealMonth;
    private Integer dealDay;
    private Double excluUseAr;
    private String dealAmount;
}
