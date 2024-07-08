package com.example.samokathandler.mappers;

import com.example.samokathandler.DTO.order.AddressDto;
import com.example.samokathandler.entities.user.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    public Address fromDto(AddressDto addressDto){
        Address address = new Address();
        address.setCity(addressDto.city);
        address.setHome(addressDto.home);
        address.setEntrance(addressDto.entrance);
        address.setApartment(addressDto.apartment);
        address.setPlate(addressDto.plate);
        return address;
    }

    public AddressDto toDto(Address address){
        return new AddressDto(address);
    }
}
