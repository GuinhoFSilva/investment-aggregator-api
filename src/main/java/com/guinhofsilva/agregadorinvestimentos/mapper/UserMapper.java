package com.guinhofsilva.agregadorinvestimentos.mapper;

import com.guinhofsilva.agregadorinvestimentos.Dto.CreateUserDto;
import com.guinhofsilva.agregadorinvestimentos.Dto.UpdateUserDto;
import com.guinhofsilva.agregadorinvestimentos.model.User;

import java.time.Instant;
import java.util.UUID;

public class UserMapper {
    public static User createUserMapper(CreateUserDto userDto) {
        return new User(null, userDto.username(), userDto.email(), userDto.password(), Instant.now(), null);
    }

    public static User updateUserMapper(UpdateUserDto updateUserDto, User user){
        if(updateUserDto.username() != null){
            user.setUsername(updateUserDto.username());
        }

        if (updateUserDto.email() != null){
            user.setEmail(updateUserDto.email());
        }

        if (updateUserDto.password() != null){
            user.setPassword(updateUserDto.password());
        }

        user.setUpdatedAt(Instant.now());

        return user;
    }
}
