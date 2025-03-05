package com.accenture.service.location;

import com.accenture.service.dto.locations.LocationRequestDto;
import com.accenture.service.dto.locations.LocationResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface LocationService {


    List<LocationResponseDto> trouverParDate(LocalDate startDate, LocalDate endDate);

    LocationResponseDto ajouter(LocationRequestDto locationRequestDto);
}
