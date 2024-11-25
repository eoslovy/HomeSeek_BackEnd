package com.homeseek.map.service;

import com.homeseek.map.dto.*;
import com.homeseek.map.mapper.MapMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MapServiceImpl implements MapService {

    private final MapMapper mm;
    private static final double MAX_DISTANCE = 1.0; // 1km

    @Override
    public List<AptDto> getEstatesByName(String keyword) {
        return mm.findEstateByName(keyword);
    }

    @Override
    public List<DongDto> getDongNames(String si, String gu) {
        List<DongDto> list = mm.findDongNames(si, gu);
        if(si.equals("세종특별자치시")){
            list.addAll(mm.findDongNamesSe(si,gu));
        }
        return list;
    }

    @Override
    public List<GuDto> getGuNames(String si) {
        List<GuDto> list = mm.findGuNames(si);
        if(si.equals("세종특별자치시")){
            list.addAll(mm.findGuNamesSe(si));
        }
        return list;
    }

    @Override
    public List<SiDto> getSiNames() {
        List<SiDto> list = mm.findSiNames();
        list.addAll(mm.findSiNamesSe());
        return list;
    }

    @Override
    public List<ToggleEstateDto> getEstatesByToggleWithSi(String code) {
        return mm.findToggleEstateBySi(code);
    }

    @Override
    public List<ToggleEstateDto> getEstatesByToggleWithGu(String code) {
        return mm.findToggleEstateByGu(code);
    }

    @Override
    public List<ToggleEstateDto> getEstatesByToggleWithDong(String code) {
        return mm.findToggleEstateByDong(code);
    }

    @Override
    public List<HospitalDto> getHospitals() {
        return mm.getHospitals();
    }

    @Override
    public List<MarketDto> getMarkets() {
        return mm.getMarkets();
    }

    @Override
    public List<SubwayDto> getSubways() {
        return mm.getSubways();
    }

    private double calculateDistance(String lat1, String lon1, String lat2, String lon2) {
        double latitude1 = Double.parseDouble(lat1);
        double longitude1 = Double.parseDouble(lon1);
        double latitude2 = Double.parseDouble(lat2);
        double longitude2 = Double.parseDouble(lon2);

        double theta = longitude1 - longitude2;
        double dist = Math.sin(deg2rad(latitude1)) * Math.sin(deg2rad(latitude2))
                + Math.cos(deg2rad(latitude1)) * Math.cos(deg2rad(latitude2))
                * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1.609344; // 킬로미터 변환

        return dist;
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    @Override
    public Map<String, List<?>> getNearbyFacilities(String aptName) {
        List<AptDto> apts = mm.findEstateByName(aptName);
        if (apts.isEmpty()) {
            return Collections.emptyMap();
        }
        AptDto apt = apts.get(0);

        List<HospitalDto> allHospitals = mm.getHospitals();
        List<MarketDto> allMarkets = mm.getMarkets();
        List<SubwayDto> allSubways = mm.getSubways();

        Map<String, List<?>> result = new HashMap<>();
        result.put("hospitals", filterByDistance(apt, allHospitals));
        result.put("markets", filterByDistance(apt, allMarkets));
        result.put("subways", filterByDistance(apt, allSubways));

        return result;
    }

    private <T> List<FacilityDto<T>> filterByDistance(AptDto apt, List<T> facilities) {
        return facilities.stream()
                .map(facility -> {
                    String facilityLat = null;
                    String facilityLon = null;

                    if (facility instanceof HospitalDto) {
                        facilityLat = ((HospitalDto) facility).getLatitude();
                        facilityLon = ((HospitalDto) facility).getLongitude();
                    } else if (facility instanceof MarketDto) {
                        facilityLat = ((MarketDto) facility).getLatitude();
                        facilityLon = ((MarketDto) facility).getLongitude();
                    } else if (facility instanceof SubwayDto) {
                        facilityLat = ((SubwayDto) facility).getLatitude();
                        facilityLon = ((SubwayDto) facility).getLongitude();
                    }

                    double distanceKm = calculateDistance(
                            apt.getLatitude(), apt.getLongitude(),
                            facilityLat, facilityLon
                    );

                    if (distanceKm <= MAX_DISTANCE) {
                        // 킬로미터를 미터로 변환 (km * 1000)
                        double distanceMeters = Math.round(distanceKm * 1000);
                        return new FacilityDto<>(facility, distanceMeters);  // 미터 단위로 저장
                    }
                    return null;
                })
                .filter(result -> result != null)
                .collect(Collectors.toList());
    }
}
