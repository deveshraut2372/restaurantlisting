package com.devesh.restaurant_service.controller;

import com.devesh.restaurant_service.dto.RestaurantDto;
import com.devesh.restaurant_service.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/restaurant")
@CrossOrigin(origins = "*")
public class RestaurantController {


    @Autowired
    RestaurantService restaurantService;


    @PostMapping("/create")
    public ResponseEntity<RestaurantDto> create(@RequestBody RestaurantDto restaurantDto)
    {
        RestaurantDto save=restaurantService.create(restaurantDto);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantDto> update(@RequestBody RestaurantDto restaurantDto,@PathVariable("id") Long id)
    {
        RestaurantDto save=restaurantService.update(restaurantDto,id);
        return new ResponseEntity<>(save, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RestaurantDto>> getAllRestaurant()
    {
        List<RestaurantDto> restaurantDtoList=restaurantService.getAllRestaurant();
        return new ResponseEntity<>(restaurantDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDto> getRestaurant(@PathVariable("id") Long id)
    {
        RestaurantDto restaurantDto=restaurantService.getRestaurant(id);
        if (restaurantDto.getId()!=null)
            return new ResponseEntity<>(restaurantDto, HttpStatus.OK);
        else
        return new ResponseEntity<>(restaurantDto, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable("id") Long id)
    {
        String result=restaurantService.deleteRestaurant(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
