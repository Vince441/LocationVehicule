package com.accenture.service.location;

import com.accenture.repository.LocationDao;
import com.accenture.repository.entity.location.Location;
import com.accenture.service.dto.locations.LocationRequestDto;
import com.accenture.service.dto.locations.LocationResponseDto;
import com.accenture.service.mapper.LocationMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    public static final String JE_N_AI_PAS_TROUVER_L_EMAIL = "Je n'ai pas trouvé l'email";
    private final LocationDao locationDao;
    private final LocationMapper locationMapper;

    public LocationServiceImpl(LocationDao locationDao, LocationMapper locationMapper) {
        this.locationDao = locationDao;
        this.locationMapper = locationMapper;
    }


    @Override
    public List<LocationResponseDto> trouverParDate(LocalDate startDate, LocalDate endDate) {
        List<Location> locations = locationDao.findLocationsByPeriod(startDate, endDate);
        return locations.stream()
                .map(locationMapper::toLocationResponseDto)
                .toList();
    }

    @Override
    public LocationResponseDto ajouter(LocationRequestDto locationRequestDto) {
        Location location = locationMapper.toLocation(locationRequestDto);
        Location locationEnreg = locationDao.save(location);
        return locationMapper.toLocationResponseDto(locationEnreg);
    }
}
