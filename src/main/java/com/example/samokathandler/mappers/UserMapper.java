package com.example.samokathandler.mappers;

import com.example.samokathandler.DTO.user.UserDto;
import com.example.samokathandler.entities.user.SamokatUser;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto toDto(SamokatUser samokatUser) {
        return new UserDto(samokatUser.getPhone(), samokatUser.getName());
    }

    public SamokatUser fromDto(UserDto userDto) {
        return new SamokatUser(userDto.getPhone_number(), userDto.getName());
    }
}
