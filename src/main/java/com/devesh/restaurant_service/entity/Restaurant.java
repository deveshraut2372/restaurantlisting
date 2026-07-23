package com.devesh.restaurant_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "restaurant" )

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String address;

    private String city;

    private String restaurantDescription;

}


