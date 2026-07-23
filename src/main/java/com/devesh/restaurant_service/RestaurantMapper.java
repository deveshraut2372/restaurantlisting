package com.devesh.restaurant_service;

import com.devesh.restaurant_service.dto.RestaurantDto;
import com.devesh.restaurant_service.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RestaurantMapper {

    RestaurantMapper INSTANCE= Mappers.getMapper(RestaurantMapper.class);

    Restaurant mapToRestaurant(RestaurantDto restaurantDto);

    RestaurantDto mapToRestaurantDto(Restaurant restaurant);


}
