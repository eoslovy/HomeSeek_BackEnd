package com.homeseek.map.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Dongcode {
    @Id
    private String dongCode;

    private String sidoName;
    private String gugunName;
    private String dongName;
}
