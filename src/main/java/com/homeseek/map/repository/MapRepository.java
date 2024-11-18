package com.homeseek.map.repository;

import com.homeseek.map.dto.AptDto;
import com.homeseek.map.entity.HouseDeal;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapRepository extends JpaRepository<HouseDeal, Integer> {
    @Query("""
        SELECT new com.homeseek.map.dto.AptDto(
            hi.aptNm, 
            dc.sidoName, 
            dc.gugunName, 
            dc.dongName, 
            hi.roadNm, 
            hi.roadNmBonbun, 
            hi.roadNmBubun, 
            hd.floor, 
            hi.buildYear, 
            hi.latitude,
            hi.longitude
        )
        FROM HouseDeal hd
        JOIN HouseInfo hi ON hd.aptSeq = hi.aptSeq
        JOIN Dongcode dc ON hi.umdCd = dc.dongCode
        WHERE hi.aptNm LIKE %:keyword% 
        """)
    //LIMIT 20 <= Page 써야됨
    List<AptDto> findAptByName(@Param("keyword") String keyword, Sort sort);
}
