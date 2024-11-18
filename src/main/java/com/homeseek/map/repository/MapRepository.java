package com.homeseek.map.repository;

import com.homeseek.map.dto.AptDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MapRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<AptDto> findEstatesByName(String keyword, String sortBy) {
        // 기본 정렬 조건 설정
        String sortColumn = switch (sortBy) {
            case "price" -> // 가격
                    "hd.dealAmount"; // dealAmount는 String이라 정렬 시 CAST 필요
            case "area" -> // 면적
                    "hd.excluUseAr";
            case "year" -> // 건축 연도
                    "hi.buildYear";
            default -> throw new IllegalArgumentException("Invalid sortBy value: " + sortBy);
        };

        // JPQL 쿼리 작성
        String jpql = """
            SELECT new com.homeseek.map.dto.AptDto(
                hi.aptNm,\s
                dc.sidoName,\s
                dc.gugunName,\s
                hi.umdNm,\s
                hi.roadNm,\s
                hi.roadNmBonbun,\s
                hi.roadNmBubun,\s
                hd.floor,\s
                hi.buildYear,\s
                hi.latitude,\s
                hi.longitude
            )
            FROM HouseDeal hd
            JOIN HouseInfo hi ON hd.aptSeq = hi.aptSeq
            JOIN Dongcode dc ON hi.umdCd = dc.dongCode
            WHERE hi.aptNm LIKE :keyword
            ORDER BY %s
       \s""".formatted(sortColumn);

        // 쿼리 생성
        TypedQuery<AptDto> query = entityManager.createQuery(jpql, AptDto.class);
        query.setParameter("keyword", "%" + keyword + "%");

        // 결과 반환
        return query.getResultList();
    }
}
