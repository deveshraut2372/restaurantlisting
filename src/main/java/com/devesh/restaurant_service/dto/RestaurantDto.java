package com.devesh.restaurant_service.dto;


import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class RestaurantDto {

    private Long id;

    private String name;

    private String address;

    private String city;

    private String restaurantDescription;

//    @Override
//    public boolean equals(Object obj) {
//
//        if (this == obj) return true;
//        if (!(obj instanceof RestaurantDto)) return false;
//
//        RestaurantDto that = (RestaurantDto) obj;
//        return
//                Objects.equals(id, that.id) &&
//                        Objects.equals(city, that.city) &&
//                        Objects.equals(address, that.address) &&
//                        Objects.equals(restaurantDescription, that.restaurantDescription) &&
//                        Objects.equals(name, that.name);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id,name,address,city,restaurantDescription);
//    }
}
