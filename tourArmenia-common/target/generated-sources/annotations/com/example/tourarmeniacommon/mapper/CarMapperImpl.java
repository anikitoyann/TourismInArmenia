package com.example.tourarmeniacommon.mapper;

import com.example.tourarmeniacommon.dto.CarDto;
import com.example.tourarmeniacommon.dto.CreateCarRequestDto;
import com.example.tourarmeniacommon.entity.Car;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-28T17:00:50+0400",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19.0.2 (Amazon.com Inc.)"
)
@Component
public class CarMapperImpl implements CarMapper {

    @Override
    public Car map(CreateCarRequestDto createCarRequestDto) {
        if ( createCarRequestDto == null ) {
            return null;
        }

        Car car = new Car();

        car.setName( createCarRequestDto.getName() );
        car.setSeats( createCarRequestDto.getSeats() );

        return car;
    }

    @Override
    public CarDto mapToDto(Car car) {
        if ( car == null ) {
            return null;
        }

        CarDto carDto = new CarDto();

        carDto.setId( car.getId() );
        carDto.setName( car.getName() );
        carDto.setSeats( car.getSeats() );

        return carDto;
    }

    @Override
    public List<CarDto> mapListToDtos(List<Car> cars) {
        if ( cars == null ) {
            return null;
        }

        List<CarDto> list = new ArrayList<CarDto>( cars.size() );
        for ( Car car : cars ) {
            list.add( mapToDto( car ) );
        }

        return list;
    }
}
