package com.devesh.restaurant_service.service;

import com.devesh.restaurant_service.dto.RestaurantDto;

import java.util.List;

public interface RestaurantService {
    RestaurantDto create(RestaurantDto restaurantDto);

    RestaurantDto update(RestaurantDto restaurantDto, Long id);

    List<RestaurantDto> getAllRestaurant();

    RestaurantDto getRestaurant(Long id);

    String deleteRestaurant(Long id);
}
