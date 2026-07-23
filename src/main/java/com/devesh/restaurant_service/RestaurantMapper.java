package com.devesh.restaurant_service;

import com.devesh.restaurant_service.dto.RestaurantDto;
import com.devesh.restaurant_service.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    RestaurantDto mapToRestaurantDto(Restaurant restaurant);

    Restaurant mapToRestaurant(RestaurantDto restaurantDto);
}
