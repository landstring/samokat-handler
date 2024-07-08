package com.example.samokathandler.DTO.order;

import com.example.samokathandler.entities.user.Address;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AddressDto {
    public String id;
    public String city;
    public String home;
    public String apartment;
    public String entrance;
    public Integer plate;

    public AddressDto(Address address){
        this.id = address.getId();
        this.city = address.getCity();
        this.home = address.getHome();
        this.apartment = address.getApartment();
        this.entrance = address.getEntrance();
        this.plate = address.getPlate();
    }
}
