package com.jmjung.table_reservation.model.restaurant;

import com.jmjung.table_reservation.repository.restaurant.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RestaurantRequest {
    String name;
    String description;
    String location;

    public Restaurant createEntity(Long merchantIdx) {
        return new Restaurant(
                name, description, location, merchantIdx
        );
    }

    public Restaurant update(Restaurant restaurant) {
        if (!name.isEmpty() && !name.isBlank()) {
            restaurant.setName(name);
        }
        if (!description.isEmpty() && !description.isBlank()) {
            restaurant.setDescription(description);
        }
        if (!location.isEmpty() && !!location.isBlank()) {
            restaurant.setLocation(location);
        }

        return restaurant;
    }
}

