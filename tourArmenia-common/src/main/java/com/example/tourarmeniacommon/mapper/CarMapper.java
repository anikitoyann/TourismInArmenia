package com.example.tourarmeniacommon.mapper;

import com.example.tourarmeniacommon.dto.CarDto;
import com.example.tourarmeniacommon.dto.CreateCarRequestDto;
import com.example.tourarmeniacommon.entity.Car;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarMapper {
    Car map(CreateCarRequestDto createCarRequestDto);
    CarDto mapToDto(Car car);
    List<CarDto> mapListToDtos(List<Car> cars);
}
