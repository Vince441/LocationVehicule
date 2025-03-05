package com.accenture.service.mapper;


import com.accenture.repository.entity.location.Location;
import com.accenture.service.dto.locations.LocationRequestDto;
import com.accenture.service.dto.locations.LocationResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    Location toLocation(LocationRequestDto locationRequestDto);

    LocationResponseDto toLocationResponseDto(Location location);


}
