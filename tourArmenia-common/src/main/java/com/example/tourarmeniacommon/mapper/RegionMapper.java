package com.example.tourarmeniacommon.mapper;

import com.example.tourarmeniacommon.dto.RegionDto;
import com.example.tourarmeniacommon.dto.RegionRequestDto;
import com.example.tourarmeniacommon.entity.Region;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RegionMapper {
    Region map(RegionRequestDto dto);

    RegionDto mapToDto(Region region);

    List<RegionDto> mapListToDtos(List<Region> all);
}
