package com.devesh.restaurant_service.controller;


import com.devesh.restaurant_service.dto.RestaurantDto;
import com.devesh.restaurant_service.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RestaurantControllerTest {


    @InjectMocks
    RestaurantController restaurantController;


    @Mock
    RestaurantService restaurantService;


    @BeforeEach
    public  void setUp()
    {
            MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllRestaurant()
    {

        System.out.println("  test called get All Restaurant ");
//         Arrage data
        List<RestaurantDto> restaurantDtoList= List.of(
                new RestaurantDto(100L," restaurant1", " Add 1", "City 1", "descr 1"),
                new RestaurantDto(101L," restaurant2", " Add 2", "City 2", "descr 2")
        );

        when(restaurantService.getAllRestaurant()).thenReturn(restaurantDtoList);

//         Act
        ResponseEntity<List<RestaurantDto>> response=restaurantController.getAllRestaurant();

//        Assert
            assertEquals(HttpStatus.OK,response.getStatusCode());
            assertEquals(restaurantDtoList,response.getBody());

//             verify
            verify(restaurantService,times(1)).getAllRestaurant();

    }


    @Test
    public void testCreate()
    {
        RestaurantDto restaurantDto=new RestaurantDto(102L," Restaurant ","  Address "," City ","Restaurant Desc ");

//        restaurantService.create(restaurantDto);
        when(restaurantService.create(restaurantDto)).thenReturn(restaurantDto);

        ResponseEntity<RestaurantDto> response=restaurantController.create(restaurantDto);

        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(restaurantDto,response.getBody());

        verify(restaurantService,times(1)).create(restaurantDto);
    }


    @Test
    public void testGetRestaurant()
    {
//         create mock data
        RestaurantDto restaurantDto=new RestaurantDto(102L," Restaurant ","  Address "," City ","Restaurant Desc ");

//        Mock the service behavior
        when(restaurantService.getRestaurant(102L)).thenReturn(restaurantDto);

//          call the controller method
        ResponseEntity<RestaurantDto> response=restaurantController.getRestaurant(102L);

//      verify the response
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(102L,response.getBody().getId());

//        Verify the sevice method was called
        verify(restaurantService,times(1)).getRestaurant(102L);

    }

    @Test
    public void testGetRestaurantNull()
    {
//    mock data
        RestaurantDto restaurantDto=new RestaurantDto(null,null,null,null,null);

//        mock ther service behavior
        when(restaurantService.getRestaurant(null)).thenReturn(restaurantDto);

//        called the controller method
        ResponseEntity<RestaurantDto> response=restaurantController.getRestaurant(null);

//        verify the response
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        assertEquals(null,response.getBody().getId());

//        verify the service method was called
        verify(restaurantService,times(1)).getRestaurant(null);

    }

    @Test
    public void testDeleteRestaurant()
    {
        Long id=102L;

        String val ="Restaurant_Delete_Succesfully";

        when(restaurantService.deleteRestaurant(id)).thenReturn(val);

        ResponseEntity<String> response=restaurantController.deleteRestaurant(id);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals("Restaurant_Delete_Succesfully",response.getBody());

        verify(restaurantService,times(1)).deleteRestaurant(id);

    }


    @Test
    public void testUpdate()
    {
        Long id =102L;
        RestaurantDto restaurantDto=new RestaurantDto(102L," Restaurant ","  Address "," City ","Restaurant Desc ");

        when(restaurantService.update(restaurantDto,id)).thenReturn(restaurantDto);

        ResponseEntity<RestaurantDto> response=restaurantController.update(restaurantDto,id);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(id,response.getBody().getId());

        verify(restaurantService,times(1)).update(restaurantDto,id);

    }





}
