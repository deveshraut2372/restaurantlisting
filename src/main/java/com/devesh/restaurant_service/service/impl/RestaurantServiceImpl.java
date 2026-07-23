package com.devesh.restaurant_service.service.impl;

import com.devesh.restaurant_service.RestaurantMapper;
import com.devesh.restaurant_service.dto.RestaurantDto;
import com.devesh.restaurant_service.entity.Restaurant;
import com.devesh.restaurant_service.repository.RestaurantRepo;
import com.devesh.restaurant_service.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {



    @Autowired
    RestaurantRepo restaurantRepo;


    @Override
    public RestaurantDto create(RestaurantDto restaurantDto)
    {
        try
        {
            Restaurant restaurant= RestaurantMapper.INSTANCE.mapToRestaurant(restaurantDto);
            Restaurant save=restaurantRepo.save(restaurant);
            return  RestaurantMapper.INSTANCE.mapToRestaurantDto(save);
        }catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public RestaurantDto update(RestaurantDto restaurantDto, Long id) {
        try
        {
            if(restaurantRepo.existsById(id)) {
                Restaurant restaurant =restaurantRepo.findById(id).orElseThrow( () -> new RuntimeException(" Resource Not Found "));
                    restaurant=RestaurantMapper.INSTANCE.mapToRestaurant(restaurantDto);
                    restaurant.setId(id);
                Restaurant save = restaurantRepo.save(restaurant);
                return RestaurantMapper.INSTANCE.mapToRestaurantDto(save);
            }else {
                throw new RuntimeException(" User Not Found Exception ");
            }
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<RestaurantDto> getAllRestaurant() {
        List<Restaurant> restaurantList=new ArrayList<>();
        restaurantList=restaurantRepo.findAll();
        return restaurantList.stream().map(RestaurantMapper.INSTANCE::mapToRestaurantDto).collect(Collectors.toList());
    }

    @Override
    public RestaurantDto getRestaurant(Long id) {
        Optional<Restaurant>restaurant=restaurantRepo.findById(id);
        if(restaurant.isPresent()) {
            RestaurantDto restaurantDto = RestaurantMapper.INSTANCE.mapToRestaurantDto(restaurant.get());
            return restaurantDto;
        }
        return new RestaurantDto();
    }

    @Override
    public String deleteRestaurant(Long id) {
        Restaurant restaurant=new Restaurant();
        restaurant=restaurantRepo.findById(id).orElseThrow(() -> new RuntimeException("RESOURCE_NOT_FOUND"));
        restaurantRepo.delete(restaurant);
        return "Restaurant_Delete_Succesfully";
    }
}
