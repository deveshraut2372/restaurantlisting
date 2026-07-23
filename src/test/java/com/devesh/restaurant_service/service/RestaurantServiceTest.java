package com.devesh.restaurant_service.service;


import com.devesh.restaurant_service.RestaurantMapper;
import com.devesh.restaurant_service.dto.RestaurantDto;
import com.devesh.restaurant_service.entity.Restaurant;
import com.devesh.restaurant_service.repository.RestaurantRepo;
import com.devesh.restaurant_service.service.impl.RestaurantServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class RestaurantServiceTest {


    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    @Mock
    private  RestaurantRepo restaurantRepo;

    @BeforeEach
    public  void setUp()
    {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllRestaurant(){

//         Arrage data
        List<Restaurant> restaurantList= List.of(
                new Restaurant(100L," restaurant1", " Add 1", "City 1", "descr 1"),
                new Restaurant(101L," restaurant2", " Add 2", "City 2", "descr 2")
        );
        Mockito.when(restaurantRepo.findAll()).thenReturn(restaurantList);
//         Act
        List<RestaurantDto> restaurantDtoList=restaurantService.getAllRestaurant();

//        Assert
        for (int i = 0; i < restaurantList.size(); i++) {
            RestaurantDto expected = RestaurantMapper.INSTANCE.mapToRestaurantDto(restaurantList.get(i));
            assertEquals(expected,restaurantDtoList.get(i));
        }

//             verify
        verify(restaurantRepo,times(1)).findAll();

    }



    @Test
    public void testGetRestaurant()
    {
        Long id=100L;
        Restaurant restaurant=new Restaurant(100L," restaurant1", " Add 1", "City 1", "descr 1");
        when(restaurantRepo.findById(100L)).thenReturn(Optional.of(restaurant));
        RestaurantDto restaurantDto=restaurantService.getRestaurant(100L);

        assertEquals(restaurantDto.getId(),restaurant.getId());
        assertEquals(id,restaurantDto.getId());
        verify(restaurantRepo,times(1)).findById(100L);
    }




    @Test
    public void testCreate()
    {

        RestaurantDto restaurantDto=new RestaurantDto(100L," restaurant1", " Add 1", "City 1", "descr 1");

        Restaurant restaurant=RestaurantMapper.INSTANCE.mapToRestaurant(restaurantDto);

        when(restaurantRepo.save(restaurant)).thenReturn(restaurant);

        RestaurantDto restaurantDto1=RestaurantMapper.INSTANCE.mapToRestaurantDto(restaurant);

        RestaurantDto saverest=restaurantService.create(restaurantDto);

        assertEquals(saverest,restaurantDto1);
        assertEquals(saverest.getId(),restaurantDto1.getId());
        verify(restaurantRepo,times(1)).save(restaurant);


    }


    @Test
    public void testCreateError()
    {

       RestaurantDto restaurantDto=new RestaurantDto(102L," restaurant1", " Add 1", "City 1", "descr 1");

       when(restaurantRepo.save(any(Restaurant.class)))
               .thenThrow(new RuntimeException("Database Error"));




       RuntimeException exception=assertThrows(RuntimeException.class,() -> restaurantService.create(restaurantDto));

       assertEquals("Database Error",exception.getMessage());

       verify(restaurantRepo,times(1)).save(any(Restaurant.class));

    }

    @Test
    public void deleteRestaurant()
    {
        Restaurant restaurant=new Restaurant(102L," restaurant1", " Add 1", "City 1", "descr 1");

        when(restaurantRepo.findById(102L)).thenReturn(Optional.of(restaurant));

        String res=restaurantService.deleteRestaurant(102L);

        assertEquals("Restaurant_Delete_Succesfully",res);

        verify(restaurantRepo,times(1)).findById(102L);
        verify(restaurantRepo,times(1)).delete(restaurant);


    }

    @Test
    public void deleteRestaurantError()
    {
        Restaurant restaurant=new Restaurant(102L," restaurant1", " Add 1", "City 1", "descr 1");

        when(restaurantRepo.findById(102L)).thenReturn(Optional.empty());

        RuntimeException exception=assertThrows(RuntimeException.class,() -> restaurantService.deleteRestaurant(102L));


        assertEquals("RESOURCE_NOT_FOUND",exception.getMessage());

        verify(restaurantRepo,times(1)).findById(102L);
        verify(restaurantRepo,never()).delete(any(Restaurant.class));
    }

    @Test
    public void testGetRestaurantNull()
    {
        Long id=100L;
//        Restaurant restaurant=new Restaurant(100L," restaurant1", " Add 1", "City 1", "descr 1");
        when(restaurantRepo.findById(id)).thenReturn(Optional.empty());
        RestaurantDto restaurantDto=restaurantService.getRestaurant(id);

        System.out.println("  rest "+restaurantDto.toString());
        assertNull(restaurantDto.getId());
        verify(restaurantRepo,times(1)).findById(100L);
    }



    @Test
    public void testUpdate()
    {
        // Arrange
        Long id = 100L;

        Restaurant restaurant = new Restaurant();
        restaurant.setId(100L);
        restaurant.setName("Restaurant 1");
        restaurant.setAddress("Address 1");
        restaurant.setCity("City 1");
        restaurant.setRestaurantDescription("Good Restaurant");


        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setId(100L);
        restaurantDto.setName("Updated Restaurant");
        restaurantDto.setAddress("Updated Address");
        restaurantDto.setCity("Updated City");
        restaurantDto.setRestaurantDescription("Updated Description");


        when(restaurantRepo.existsById(id))
                .thenReturn(true);

        when(restaurantRepo.findById(id))
                .thenReturn(Optional.of(restaurant));


        when(restaurantRepo.save(any(Restaurant.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));


        // Act
        RestaurantDto result =
                restaurantService.update(restaurantDto, id);


        // Assert
        assertNotNull(result);

        assertEquals(id, result.getId());
//        assertEquals("Updated Restaurant", result.getName());
//        assertEquals("Updated Address", result.getAddress());
//        assertEquals("Updated City", result.getCity());


        verify(restaurantRepo,times(1))
                .existsById(id);

        verify(restaurantRepo,times(1))
                .findById(id);

        verify(restaurantRepo,times(1))
                .save(any(Restaurant.class));
    }




    @Test
    public void testUpdateExceptionsecond()
    {

        // Arrange
        Long id = 200L;

        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setId(100L);
        restaurantDto.setName("Updated Restaurant");
        restaurantDto.setAddress("Updated Address");
        restaurantDto.setCity("Updated City");
        restaurantDto.setRestaurantDescription("Updated Description");


        when(restaurantRepo.existsById(id))
                .thenReturn(false);



        // Act & Assert

        RuntimeException exception =
                assertThrows(RuntimeException.class,
                        () -> restaurantService.update(restaurantDto,id));


        assertEquals(
                " User Not Found Exception ",
                exception.getMessage()
        );


        verify(restaurantRepo,times(1))
                .existsById(id);


        verify(restaurantRepo,never())
                .save(any(Restaurant.class));
    }

    @Test
    public void testUpdateException()
    {
        Long id = 100L;
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setId(100L);
        restaurantDto.setName("Updated Restaurant");
        restaurantDto.setAddress("Updated Address");
        restaurantDto.setCity("Updated City");
        restaurantDto.setRestaurantDescription("Updated Description");

        when(restaurantRepo.existsById(id))
                .thenThrow(new RuntimeException("Database Error"));



        RuntimeException exception =
                assertThrows(RuntimeException.class,
                        () -> restaurantService.update(restaurantDto,id));


        assertEquals(
                "Database Error",
                exception.getMessage()
        );


        verify(restaurantRepo,times(1))
                .existsById(id);
    }


}
